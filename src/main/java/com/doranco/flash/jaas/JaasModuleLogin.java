/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.flash.jaas;

import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.security.auth.Subject;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;
import javax.security.auth.login.LoginException;
import javax.security.auth.spi.LoginModule;

/**
 *
 * @author DL
 * 
 * L' interface donne aux développeurs la possibilité d'implémenter 
 * différents types de technologies d'authentification pouvant être 
 * connectées à une application. Par exemple, un type peut effectuer 
 * une forme d'authentification basée sur un nom d'utilisateur/mot de passe. 
 * D'autres peuvent s'interfacer avec des dispositifs matériels tels que des 
 * cartes à puce ou des dispositifs biométriques.
 * 
 */
public class JaasModuleLogin implements LoginModule{
    private CallbackHandler callbackHandler;
    private Subject subject;
    private final static String[][] USERS_TEST = {{"daniel", "123"},{"vide","1234"}};
    private JaasPrincipal principal;
   
    /**
     * 
     * Cette méthode est appelée par le LoginContext après l'instanciation de ce LoginModule. 
     * Le but de cette méthode est d'initialiser ce LoginModule avec les informations pertinentes. 
     * Si ce LoginModule ne comprend aucune des données stockées dans l'état partagé ou les paramètres 
     * d'options, ils peuvent être ignorés.
     * 
     * 
     * @param subject - a authentifier
     * @param callbackHandler - un CallbackHandler pour communiquer avec l'utilisateur final 
     * (demander des noms d'utilisateur et des mots de passe, par exemple).
     * @param sharedState - état partagé avec d'autres modules de connexion configurés.
     * @param options - les options spécifiées dans la config de login pour ce LoginModule
     */
    @Override
    public void initialize(Subject subject, CallbackHandler callbackHandler, Map<String, ?> sharedState, Map<String, ?> options) {
        System.out.println("JaasModuleLogin.initialize......");
        this.subject = subject;
        this.callbackHandler = callbackHandler;
    }

    /**
     * 
     * L'implémentation de cette méthode authentifie un sujet. 
     * Par exemple, il peut demander des informations sur le sujet 
     * telles qu'un nom d'utilisateur et un mot de passe, puis tenter 
     * de vérifier le mot de passe. Cette méthode enregistre le résultat 
     * de la tentative d'authentification en tant qu'état privé dans le LoginModule.
     * 
     * @return
     * @throws LoginException 
     */
    @Override
    public boolean login() throws LoginException {
        System.out.println("JaasModuleLogin.login......");
        Callback[] callbacks = new Callback[2];
        callbacks[0] = new NameCallback("Login");
        callbacks[1] = new PasswordCallback("Password", false);
        
        try {
            this.callbackHandler.handle(callbacks);
            String login = ((NameCallback)callbacks[0]).getName();
            String password = new String (((PasswordCallback)callbacks[1]).getPassword());
            int i = 0;
            while(i<USERS_TEST.length){
                if(USERS_TEST[i][0].equals(login) && USERS_TEST[i][1].equals(password)){
                    System.out.println("Authentification Réussie");
                    principal = new JaasPrincipal(login);
                    return true;
                }
                i++;
            }
            
        } catch (IOException ex) {
            Logger.getLogger(JaasModuleLogin.class.getName()).log(Level.SEVERE, null, ex);
        } catch (UnsupportedCallbackException ex) {
            Logger.getLogger(JaasModuleLogin.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    /**
     * 
     * Si la propre tentative d'authentification de ce LoginModule a réussi 
     * (vérifiée en récupérant l'état privé enregistré par la méthode de connexion), 
     * cette méthode associe les principals et les informations d'identification 
     * pertinents au sujet situé dans le LoginModule. Si la propre tentative 
     * d'authentification de ce LoginModule a échoué, cette méthode supprime/détruit 
     * tout état initialement enregistré.
     * 
     * @return
     * @throws LoginException 
     */
    @Override
    public boolean commit() throws LoginException {
        System.out.println("JaasModuleLogin.commit......");
        if(subject != null && !subject.getPrincipals().contains(principal)){
            // ajouter un principal et des informations d'identification au sujet
            subject.getPrincipals().add(principal);
            return true;
        }
        return  false;
    }

    /**
     * 
     * Cette méthode est appelée si l'authentification globale de LoginContext a échoué. .
     * 
     * Si la propre tentative d'authentification de ce LoginModule a réussi 
     * (vérifiée en récupérant l'état privé enregistré par la méthode de connexion), 
     * cette méthode nettoie tout état initialement enregistré.
     * 
     * @return
     * @throws LoginException 
     */
    @Override
    public boolean abort() throws LoginException {
        System.out.println("JaasModuleLogin.abort......");
        subject = null;
        principal = null;
        return true;
    }

    /**
     * 
     * Une implémentation de cette méthode peut supprimer/détruire 
     * les principals et les informations d'identification d'un sujet.
     * 
     * @return
     * @throws LoginException 
     */
    @Override
    public boolean logout() throws LoginException {
        System.out.println("JaasModuleLogin.logout......");
        subject = null;
        principal = null;
        return  true;
    }
    
}
