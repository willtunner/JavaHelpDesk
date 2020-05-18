/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Month;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

/**
 *
 * @author Suporte-07
 */
public class PraticaDateTime {

    public static void main(String[] args) {
        //PEGA DATA/HORA ATUAL
        /*
        LocalDateTime DataHoraAtual = LocalDateTime.now();
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String format4 = formatter2.format(DataHoraAtual);
        System.out.println(format4);
        */
        
        //PEGA UMA DATA/HORA EM STRING VINDO DO BANCO EX...
        //DateTimeFormatter formatData = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        
        /*
        //https://www.guj.com.br/t/resolvido-como-subtrair-duas-datas-e-obter-a-resposta-em-dias/91498/4
        
        TemporalAccessor parse3 = formatter2.parse("25/02/2020 16:51:15");
        LocalDateTime DataHoraBanco = LocalDateTime.from(parse3);
        System.out.println(DataHoraBanco);

        String[] aux = jtextField.split("/");
        int dia = Integer.parseInt(aux[0]); // igual ao dia
        int mes = Integer.parseInt(aux[1]) - 1;// mes
        int ano = Integer.parseInt(aux[2]); //ano

        dte.set(ano, mes, dia);// data locação  - menor
        */  

        //https://www.guj.com.br/t/data-e-subtracao-de-data/73608/8
        GregorianCalendar c1 = new GregorianCalendar();
        c1.set(2020, Calendar.FEBRUARY, 24); // 15/08/2010
        GregorianCalendar c2 = new GregorianCalendar(); //data atual

        long diferenca = c2.getTimeInMillis() - c1.getTimeInMillis();
        long segundos = diferenca / 1000; //Transformando milisegundos em segundos
        System.out.println("Segundos: " + segundos);
        long minutos = (segundos/60);
        System.out.println("Minutos "+minutos);
        long horas = (segundos / 60) / 60; // segundos -> horas
        System.out.println("Horas: " + horas);
        long dias = horas / 24; // horas -> dias
        System.out.println("Diferenca em Dias: " + dias); //Vai imprimir 25.
    }
}
