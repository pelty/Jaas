/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.flash.jaas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.security.auth.callback.Callback;
import javax.security.auth.callback.CallbackHandler;
import javax.security.auth.callback.NameCallback;
import javax.security.auth.callback.PasswordCallback;
import javax.security.auth.callback.UnsupportedCallbackException;

/**
 *
 * @author DL
 * 
 * LoginModuleutilise les CallbackHandlerdeux pour 
 * recueillir les entrées des utilisateurs 
 * (telles qu'un mot de passe ou le code PIN d'une 
 * carte à puce) ou pour fournir des informations 
 * aux utilisateurs (telles que des informations d'état)
 */
public class JaasHandlerCallback implements CallbackHandler{

    /**
     * L'implémentation de la méthode handle vérifie la ou les instances du ou des objets Callback transmis pour récupérer ou afficher les informations demandées.
     * 
     * @param callbacks
     * @throws IOException
     * @throws UnsupportedCallbackException 
     */
    @Override
    public void handle(Callback[] callbacks) throws IOException, UnsupportedCallbackException {
        System.out.println("JaasHandlerCallback.handle.....");
           
        //Les services de sécurité sous-jacents instancient et transmettent 
        //un NameCallback à la méthode handle d'un CallbackHandler pour 
        //récupérer les informations de nom.
        NameCallback nameCallback = (NameCallback) callbacks[0];
        System.out.println(nameCallback.getPrompt());
        //Lis les octets et les décodes en caractères
        InputStreamReader inputStreamReader = new InputStreamReader(System.in);
        BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
        nameCallback.setName(bufferedReader.readLine());


        ////Les services de sécurité sous-jacents instancient et transmettent 
        //un PasswordCallback à la méthode handle d'un CallbackHandler pour 
        //récupérer les informations du mot de passe.
        PasswordCallback passwordCallback = (PasswordCallback) callbacks[1];
        System.out.println(passwordCallback.getPrompt());
        passwordCallback.setPassword(bufferedReader.readLine().toCharArray());
    }
    
}
