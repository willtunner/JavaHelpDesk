/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import javax.swing.JOptionPane;

/**
 *
 * @author Suporte-07
 */
public class ComoProgramar {
    public static void main(String args[]){
        /*
        System.out.print("oi ");
        System.out.println("\nWilliam\nPereira\nPinheiro");
        JOptionPane.showMessageDialog(null,"nome: William\nSobre nome: Pereira");
        */
        
        String num1,
        num2;        
        int number1,
        number2;
        
        int sum;
        
        num1 = JOptionPane.showInputDialog("num1: ");
        num2 = JOptionPane.showInputDialog("num2: ");
        
        number1 = Integer.parseInt(num1);
        number2 = Integer.parseInt(num2);
        
        sum =  number1 + number2;
        
        JOptionPane.showMessageDialog(null, "The sum is "+ sum," Results",JOptionPane.INFORMATION_MESSAGE);
        
        //System.out.println("*\n**\n***\n****");
        System.out.println("    ***    ");
        System.out.println("  *     *");
        System.out.println("*         *");
        System.out.println("*         *");
        System.out.println("*         *");
        System.out.println(" *      *");
        System.out.println("   *** ");
        
       System.exit(0);
    }
}
