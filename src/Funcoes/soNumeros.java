/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;

/**
 *
 * @author Suporte-07
 */
//PARA UTILIZAR CLASSES DO PLAINDOCUMENT 
public class soNumeros extends PlainDocument{
    @Override
    public void insertString(int offs, String str, AttributeSet a) throws BadLocationException{
        super.insertString(offs, str.replaceAll("[^0-9]", ""), a);//replaceAll("[^0-9]", "") substitua tudo por "[^0-9]" <-- APENAS NUMEROS
    }
}





















