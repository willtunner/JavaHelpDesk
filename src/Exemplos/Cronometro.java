/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import javax.swing.JFrame;

/**
 *
 * @author - henrique: moura Criando um cronômetro simples em Java
 * https://www.youtube.com/watch?v=Vb-Qbz8dT0E&t=296s
 */
class Cronometro {

    public void init() {
        JFrame.setDefaultLookAndFeelDecorated(true);
        JFrame janela = new JFrame("Cronometro");
        janela.setSize(300,200);
        janela.setAlwaysOnTop(true);
        janela.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        janela.setLayout(new BorderLayout());
    }

    public static void main(String[] args) {
        Cronometro c = new Cronometro();
        EventQueue.invokeLater(new Runnable() {

            @Override
            public void run() {
                c.init();
            }

        }
        );
    }
}
