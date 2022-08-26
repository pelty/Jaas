/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.doranco.flash.jaas;

import java.security.Principal;

/**
 *
 * @author DL
 * 
 * Les Principals lient simplement les noms à un Subject.
 */
public class JaasPrincipal implements Principal{
    private String username;

    public JaasPrincipal(String username) {
        this.username = username;
    }
    

    @Override
    public String getName() {
        System.out.println("JaasPrincipal.getName.....");
        return username;
    }
    
    
}
