/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 *
 * @author WillTunner
 */
public class EfeitoTexto extends JPanel {

    int x = 0, y = 100;

    public void paint(Graphics g) {
        super.paint(g);
        Graphics2D g2 = (Graphics2D) g;
        Font font = new Font("Tahoma", Font.BOLD + Font.PLAIN, 48);
        g2.setFont(font);
        g2.setColor(Color.red);
        g2.drawString("#FiqueEmCasa", x, y);

        try {
            Thread.sleep(100);
        } catch (Exception ex) {

        }
        x += 10;
        if (x > this.getWidth()) {
            x = 0;
        }
        repaint();
    }
    
    public static void main(String[] args){
        JFrame jf = new JFrame("EfeitoTexto");
        jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        jf.setSize(700, 200);
        jf.add(new EfeitoTexto());
        jf.setVisible(true);
    }
}









































