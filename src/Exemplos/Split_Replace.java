/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 *
 * @author Suporte-07
 */
public class Split_Replace {

    public static void main(String[] args) throws ParseException {
        /*String texto = "nome.cliente";
         String temp = texto.replace('.', '#');
         String[] array = temp.split("#");
         System.out.println(temp);*/

        /*String texto = "cliente.nome";
         String[] array = texto.split("[.]");
         System.out.print(array[0]+" ");
         System.out.print(array[1]);*/
        /*String s = "Hi there,my name  is Robertson,   Jamie ";
         String[] split = s.split("[\\s,]");
         for (int i = 0; i < split.length; i++) {
         System.out.println((i + 1) + "|" + split[i] + "|");
        
        
         }*/
        DateFormat formatDate = new SimpleDateFormat("yyyy-MM-DD");
        DateFormat formatTime = new SimpleDateFormat("HH:mm:ss");
        Date now = new Date();

        System.out.println("dateOnly: " + formatDate.format(now));
        System.out.println("timeOnly: " + formatTime.format(now));
    }
}
