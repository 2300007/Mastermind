/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package com.mycompany.mastermindapp;

public class MastermindApp {

    public static void main(String[] args) {
       int codeLength = 4 ; 
        
        MastermindGame game = new MastermindGame(codeLength, 10);
        
        new MastermindView(game , codeLength);
    }
}
