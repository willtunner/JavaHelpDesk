/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;
import java.awt.Graphics;
import javax.swing.*;
/**
 *
 * @author Suporte-07
 */
public class applet extends JApplet{
    int choice;
    
    public void init(){
        String input;
        input = JOptionPane.showInputDialog(
                "Enter 1 to draw lines\n"+
                "Enter 2 to draw rectangles\n"+
                "Enter 3 to draw ovals\n"+
                "Enter 4 to draw circles\n");
        choice = Integer.parseInt(input);
    }
    
    public void paint (Graphics g){
        super.paint(g);
        int counter = 1;
        
        for(int i =0; i <10; i++){
            switch(choice){
                case 1:
                    g.drawLine(10, 10, 250, 10 + i * 10);
                    break;
                case 2:
                    g.drawRect(10 + i * 10, 10 + i * 10, 50 + i * 10, 50 + i * 10);
                    break;
                case 3:
                    g.drawOval(10 + i * 10, 10 + i * 10, 50 + i * 10, 50 + i * 10);
                    break;
                case 4:
                    do{
                        g.drawOval(110 - counter * 10, 110 - counter * 10, counter * 20, counter * 20);
                        ++counter;
                    }while(counter <= 10);
                    break;
                default:
                    g.drawString("Invalid value entered", 10, 20 + i *15);
            }
        }
    }
    
}
