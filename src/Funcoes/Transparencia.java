/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import java.awt.Color;
import javax.swing.JFrame;

/**
 *
 * @author Suporte-07
 */
public class Transparencia {
    
    public void aplicarTransparencia(JFrame frame){
        frame.setUndecorated(true);
        //frame.setOpacity(0.5f);
        frame.setBackground(new Color(0,0,0,0));
    }
}












