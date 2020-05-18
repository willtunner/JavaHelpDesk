package Exemplos;


import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import javax.swing.JOptionPane;

public class ExemploSplit {

    public static void main(String[] args) throws ParseException {
        /*String texto = "nome.cliente";
         String temp = texto.replace('.', '#');
         String[] array = temp.split("#");
         System.out.println(temp);*/

        /*String texto = "cliente.nome";
         String[] array = texto.split("[.]");
         System.out.print(array[0]+" ");
         System.out.print(array[1]);*/
        String s = "Hi there,my name  is Robertson,   Jamie ";
        String[] split = s.split("[\\s,]");
        for (int i = 0; i < split.length; i++) {
            System.out.println((i + 1) + "|" + split[i] + "|");
        }
    }
}
