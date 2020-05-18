/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Stroke;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;

/**
 *
 * @author Suporte-07
 */
public class RoundImage {
    private static final Stroke stroke = new BasicStroke(5f);
    
    public RoundImage() {
    }
    
    public static Image getRoundImage(Image imageSource, int radius) {
        int width = imageSource.getWidth(null);
        int height = imageSource.getHeight(null);

        BufferedImage img = new BufferedImage(width, height, BufferedImage.TYPE_INT_ARGB);
        RoundRectangle2D rect = new RoundRectangle2D.Double(0, 0, width, height, radius, radius);

        Graphics2D g2 = (Graphics2D) img.getGraphics();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.clip(rect);
        g2.drawImage(imageSource, 0, 0, null);

        g2.setStroke(stroke);
        g2.setColor(new Color(231, 235, 238));
        g2.draw(rect);

        return img;
    }
}
