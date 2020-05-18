/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.time.format.ResolverStyle;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import javax.swing.JOptionPane;

/**
 *
 * @author Suporte-07
 */
public class TesteDatas {

    private static final DateTimeFormatter FORMATO_HORAS = DateTimeFormatter
            .ofPattern("HH:mm:ss")
            .withResolverStyle(ResolverStyle.STRICT);

    private static LocalTime faltando(LocalTime agora, LocalTime desejada) {
        return desejada.minusHours(agora.getHour()).minusMinutes(agora.getMinute());
    }

    private static void mostrar(LocalTime horario, String objetivo) {
        LocalTime desejada = LocalTime.parse(objetivo, FORMATO_HORAS);
        LocalTime falta = faltando(horario, desejada);
        System.out.println(
                "Entre " + horario.format(FORMATO_HORAS)
                + " e " + desejada.format(FORMATO_HORAS)
                + ", a diferença é de " + falta.format(FORMATO_HORAS)
                + ".");
    }

    public static void main(String[] args) throws ParseException {
        LocalTime agora = LocalTime.now();
        mostrar(agora, "07:30:25");
        mostrar(LocalTime.of(5, 30, 58), "07:30:25");
        mostrar(LocalTime.of(7, 10, 58), "07:30:25");
        mostrar(LocalTime.of(0, 0, 58), "07:30:25");
        mostrar(LocalTime.of(7, 0, 58), "07:30:25");
        mostrar(LocalTime.of(7, 30, 58), "07:30:25");
        mostrar(LocalTime.of(7, 31, 58), "07:30:25");
        mostrar(LocalTime.of(10, 0, 58), "07:30:25");
        mostrar(LocalTime.of(19, 30, 58), "07:30:25");
        mostrar(LocalTime.of(23, 59, 58), "07:30:25");
        mostrar(LocalTime.of(0, 0, 58), "23:59:25");
        mostrar(LocalTime.of(23, 59, 58), "00:00:25");

        System.out.println("######################### DIFERENÇA ENTRE DIAS #########################");
        SimpleDateFormat formato = new SimpleDateFormat("yyyy-MM-dd");
        Date dataIni = formato.parse("2020-02-02");
        Date dataFim = formato.parse("2020-03-09");
        Calendar calendario = Calendar.getInstance();
        Calendar calendario2 = Calendar.getInstance();
        calendario.setTime(dataIni);
        calendario2.setTime(dataFim);

        int anoIni = calendario.get(Calendar.YEAR);
        int mesIni = calendario.get(Calendar.MONTH);
        int diaIni = calendario.get(Calendar.DAY_OF_MONTH);

        int anoFim = calendario2.get(Calendar.YEAR);
        int mesFim = calendario2.get(Calendar.MONTH);
        int diaFim = calendario2.get(Calendar.DAY_OF_MONTH);

        System.out.println(anoIni + " AnoIni " + mesIni + " MesIni " + diaIni + " DiaIni");
        System.out.println(anoFim + " AnoFim " + mesFim + " MesFim " + diaFim + " DiaFim");

        LocalDate dataHoraEspecifica
                = LocalDate.of(anoIni, mesIni, diaIni);

        LocalDate dataHoraAtual
                = LocalDate.of(anoFim, mesFim, diaFim);

        Period periodo = Period.between(dataHoraEspecifica, dataHoraAtual);

        System.out.println(periodo.getYears() + " Anos " + periodo.getMonths() + " Meses " + periodo.getDays() + " Dias");

        System.out.println("############### DIFERENÇA ENTRE HORAS #######################");
        /*int seg = segundos();
         int minuto = min(seg);
         int horas = hora(minuto);

         System.out.println("A diferença em horas é: " + horas + " : " + minuto + " : " + seg);
         //System.out.println("A diferença é de " + intervalo + " minutos.");

         }

         public static int segundos() {
         LocalTime entrada = LocalTime.of(9, 00);
         LocalTime saida = LocalTime.of(17, 30);

         long intervalo = ChronoUnit.SECONDS.between(entrada, saida);
         return (int) intervalo;
         }

         public static int min(int minuto) {
         return (int) (minuto / 60);
         }

         public static int hora(int minutos) {

         return (int) (minutos / 60);
         }

         */
        Calendar rightNow = Calendar.getInstance();
        java.text.SimpleDateFormat df1 = new java.text.SimpleDateFormat("MM");
        java.text.SimpleDateFormat df2 = new java.text.SimpleDateFormat("MMM");
        java.text.SimpleDateFormat df3 = new java.text.SimpleDateFormat("MMMM");
        System.out.println(df1.format(rightNow.getTime()));
        System.out.println(df2.format(rightNow.getTime()));
        System.out.println(df3.format(rightNow.getTime()));

        //PEGA O MES ATUAL 
        LocalDate today = LocalDate.now();
        int day = today.getDayOfMonth();
        int month = today.getMonthValue();  // Returns 1-12 as values.
        int year = today.getYear();
        JOptionPane.showMessageDialog(null,day+"/"+month+"/"+year);

    }
}
