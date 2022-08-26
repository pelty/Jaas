/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package com.doranco.flash.jaas;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import javax.security.auth.login.LoginContext;
import javax.security.auth.login.LoginException;

/**
 *
 * @author DL
 * 
 * 
 * Le service d'authentification et d'autorisation Java (JAAS) est un package 
 * facultatif (extension) du SDK Java 2. 
 * JAAS peut être utilisé à deux fins :
 * 1 - pour l' authentification des utilisateurs, pour déterminer de manière 
 * fiable et sécurisée qui exécute actuellement le code Java, que le code 
 * s'exécute en tant qu'application, applet, bean ou servlet ; et
 * 2 - pour l' autorisation des utilisateurs afin de s'assurer qu'ils disposent 
 * des droits de contrôle d'accès (autorisations) nécessaires pour effectuer les
 * actions effectuées.
 * 
 */
public class Jaas {
    
    public enum Action{
        ACTION1, ACTION2, LOGOUT
    }

    public static void main(String[] args) throws LoginException, IOException {
        System.out.println("Hello World! Ici le main............");
        //Définit la propriété système indiquée par la clé spécifiée. 
        //Tout d'abord, si un gestionnaire de sécurité existe, sa méthode 
        //SecurityManager.checkPermission est appelée avec une autorisation 
        //PropertyPermission(key, "write"). Cela peut entraîner la levée d'une 
        //SecurityException. Si aucune exception n'est levée, la propriété 
        //spécifiée est définie sur la valeur donnée.
        System.setProperty("java.security.auth.login.config", "jaas.config");
        LoginContext loginContext = new LoginContext("jaas", new JaasHandlerCallback());
        loginContext.login();
        boolean isLoggedIn = true;
        while (isLoggedIn) {
            isLoggedIn = effectuer(loginContext);
        }
    }
    
    private static  boolean effectuer(LoginContext loginContext) throws IOException, LoginException {
        System.out.println("Action à effectuer: {ACTION1,ACTION2,LOGOUT}");
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(System.in));
        switch(Action.valueOf(bufferedReader.readLine())) {
            case ACTION1:
                System.out.println("Action 1 effectuée");
                break;
            case ACTION2:
                System.out.println("Action 2 effectuée");
                break;
            case LOGOUT:
                System.out.println("Logout init");
                loginContext.logout();
                System.out.println("Logout OK");
                return false;
        }
        
        return true;
    }

}
