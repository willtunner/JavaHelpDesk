/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Month;
import java.time.Period;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.FormatStyle;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAccessor;
import java.util.Calendar;
import java.util.Date;
import javax.swing.text.DateFormatter;

/**
 *
 * @author Suporte-07
 */
public class DataTimeHour {

    public static void main(String[] args) {
        //####################### :: EXEMPLO DE CLASSES DATA :: ##########################
        System.out.println("##################### - EXEMPLO DE CLASSES DATA - #####################");
        //LocalDate - representa uma data: 06/08/1990
        LocalDate ldnow = LocalDate.now();
        System.out.println("LocalDate - " + ldnow);
        LocalDate ld = LocalDate.of(1986, Month.FEBRUARY, 11);
        LocalDate ld2 = ld.plusDays(2).minusDays(1);//existe para somar, subtrair etc...
        System.out.println(ld);
        System.out.println("Soma dia atual mais dois dias - " + ld2);
        //LocalTime - representa uma hora: 11:40:00
        LocalTime ltNow = LocalTime.now();
        System.out.println("LocalTime Hora - " + ltNow);
        LocalTime lt = LocalTime.of(1, 10, 01, 100000);
        System.out.println("LocalTime Hora 2 - " +lt);

        //LocalDateTime - representa data+hora
        // 06/08/1990 11:40:00
        LocalDateTime ldtNow = LocalDateTime.now();
        System.out.println("LocalDateTime - " + ldtNow);
        LocalDateTime ldt = LocalDateTime.of(ld, lt);
        System.out.println(ldt);

        //Instant - representa um instante/momento 
        //na linha do tempo (millisegundos a partir de 01/01/1970
        //06/08/1990 11:40:00 GMT/UTC
        Instant iNow = Instant.now();
        System.out.println("Instant - " + iNow);
        Instant i = Instant.ofEpochMilli(5000000);
        System.out.println(i);

        //ZoneDateTime - LocalDateTime + TimeZone (fuzo horario)
        //06/08/1990 11:40:00 GMT -3 (America/Sao_Paulo)
        ZonedDateTime zdtNow = ZonedDateTime.now();
        System.out.println("ZonedDateTime - " + zdtNow);
        ZonedDateTime zdt = ZonedDateTime.of(ldt, ZoneId.of("America/Sao_Paulo"));
        System.out.println(zdt);

        /**
         * JAVA 8 (PERIOD E DURATION)
         */
        //PERIOD - TRABALHA COM VALORES BASEADOS EM DATA
        //DURATION - TRABALHA COM VALORES BASEADOS EM HORAS
        //####################### :: EXEMPLO DE PERIOD :: ##########################
        System.out.println("##################### - EXEMPLO DE PERIOD - #####################");
        Period of = Period.of(1, 5, 3);//1 ano, 5 meses e 3 dias
        System.out.println("Period - " + of);

        Period CincoAnos = Period.ofYears(5);//retorna em dias 5*7 = 35
        System.out.println("retorna em dias 5*7 - " + CincoAnos);

        LocalDate dt1 = LocalDate.of(1990, Month.MARCH, 11);//insere a data 11/03/1990
        LocalDate dt2 = dt1.plusWeeks(2).plusDays(1).plusMonths(1);//pega a data acima e soma 2 semanas + 1 dia + 1 mes
        Period between = Period.between(dt1, dt2);//Retorna o periodo entre a data um e a data 2
        System.out.println("Retorna o periodo entre a data 1 e a data 2 -" + between);
        Period between2 = between.plusDays(10);//Pega o periodo acima e acrescenta 10 dias
        System.out.println("Pega o periodo acima e acrescenta 10 dias - " + between2);

        Period until = dt1.until(dt2);
        System.out.println("Também retorna o periodo entre duas datas pelo until - " + until);

        LocalDate plus = ld.plus(CincoAnos);
        System.out.println(plus);

        //####################### :: EXEMPLO DE DURATION :: ##########################
        System.out.println("##################### - EXEMPLO DE DURATION - #####################");
        Duration ofMinutes = Duration.ofMinutes(500);
        System.out.println("Retorna quanto tempo tem  500m  - " + ofMinutes);
        Duration ofSeconds = Duration.ofSeconds(10, 500);
        System.out.println("10s e 500ns - " + ofSeconds);

        LocalTime ltNow2 = LocalTime.now();
        LocalTime lt2 = LocalTime.of(11, 10, 01);
        Duration between3 = Duration.between(ltNow2, lt2);
        System.out.println("Soma da hora atual mais a que passamos 11:10:01 - " + between3);

        //LocalDateTime - Data + Hora
        //Instant - Data + Hora + GMT (NÃO TRABALHA COM DATA E SIM COM MILISEGUNDOS)
        //ZoneDateTime - Data + Hora + Fuso Horaio
        Instant now = Instant.now();
        //now.plus(5, ChronoUnit.MONTHS);//cria um timezone apartir de um Instant
        System.out.println(now);
        ZonedDateTime atZone = now.atZone(ZoneId.of("America/Sao_Paulo"));
        System.out.println(atZone);

        System.out.println("####################### :: FORMATAÇÃO DE DATA :: ##########################");
        //####################### :: FORMATAÇÃO DE DATA :: ##########################
        //DateTimeFormater
        DateTimeFormatter isoDateTime = DateTimeFormatter.ISO_DATE_TIME;
        DateTimeFormatter isoDateTime2 = DateTimeFormatter.ISO_DATE;
        DateTimeFormatter isoDateTime3 = DateTimeFormatter.ISO_TIME;

        LocalDateTime ldtNow2 = LocalDateTime.now();
        System.out.println(ldtNow2);

        String format = isoDateTime.format(ldtNow2);
        String format2 = isoDateTime2.format(ldtNow2);
        String format3 = isoDateTime3.format(ldtNow2);
        System.out.println(format);
        System.out.println(format2);
        System.out.println(format3);

        DateTimeFormatter formater = DateTimeFormatter.ofLocalizedDate(FormatStyle.MEDIUM);
        LocalDateTime ldtNow3 = LocalDateTime.now();
        System.out.println("DateTimeFormatter.ofLocalizedDate(FormatStyle.SHORT);");
        String format5 = formater.format(ldtNow3);
        System.out.println(format5);

        //DEFININDO UM PADRÃO DE DATA
        System.out.println("padronizando data");
        DateTimeFormatter formatter2 = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        String format4 = formatter2.format(ldtNow3);
        System.out.println(format4);
        System.out.println("invertendo tb funciona");

        String format6 = ldtNow3.format(formatter2);
        System.out.println(format6);

        System.out.println("Convertendo String para DateTime");
        TemporalAccessor parse = formatter2.parse("26/02/2020 14:35:00");
        LocalDateTime from = LocalDateTime.from(parse);
        System.out.println(from);

        //CONVERTENDO DATA
        DateTimeFormatter formatData
                = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        TemporalAccessor parse3 = formatData.parse("26/02/2020");
        LocalDate from3 = LocalDate.from(parse3);
        System.out.println("Convertendo String Data para DateTime");
        System.out.println(from3);

        //CONVERTENDO HORA
        System.out.println("Convertendo String Hora para DateTime");
        DateTimeFormatter formatterHora
                = DateTimeFormatter.ofPattern("HH:mm:ss");
        TemporalAccessor parse2 = formatterHora.parse("14:38:15");
        System.out.println(parse2);
        LocalTime from2 = LocalTime.from(parse2);
        System.out.println(from2);

        //https://www.youtube.com/watch?v=YHVD8PW97y4&list=PLuYctAHjg89Z6BDg319ADULCmIQJ2y0yE&index=4
        /**
         * ESTUDOS DO GUJ
         */
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        Calendar dts = Calendar.getInstance();
        dts.setTime(new Date());//data retirada - maior

        Calendar dte = Calendar.getInstance();
        dte.set(2013, 3, 15);// data locação  - menor

        dts.add(Calendar.DATE, -dte.get(Calendar.DAY_OF_MONTH));

        //totaldiarias.setText(sdf.format(dts.get(Calendar.DAY_OF_MONTH)));

    }

}
