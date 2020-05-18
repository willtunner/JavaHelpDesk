
package Exemplos;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.Period;
import java.time.temporal.ChronoUnit;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author William
 * @see https://www.youtube.com/watch?v=JC23Ory81LI
 */
public class DateTimeDiferencaDatas {
    public static void main(String[] args){
        
        try {
            diferencaEntreDatasJava7();
        } catch (ParseException ex) {
            Logger.getLogger(DateTimeDiferencaDatas.class.getName()).log(Level.SEVERE, null, ex);
        }
        System.out.println("-----------------------------");
        diferencaEntreDatasJava8();
        System.out.println("-----------------------------");
        diferencaEntreDatasJava8ComTempo();
        System.out.println("-----------------------------");
        diferencaEntreDatasJava8ComChronoUnit();
        
        System.out.println("##############################");
        String horario1 = "00:00:00";
        String horario2 = "05:03:48";
        // também tem outros construtores para utilizar números
        LocalTime lt1 = LocalTime.parse(horario1);
        LocalTime lt2 = LocalTime.parse(horario2);
        // diferenca
        long emHoras = lt1.until(lt2, ChronoUnit.HOURS);
        long emMinutos = lt1.until(lt2, ChronoUnit.MINUTES);
        long emSegundos = lt1.until(lt2, ChronoUnit.SECONDS);
        // etc
        System.out.println(emHoras);
        System.out.println(emMinutos);
        System.out.println(emSegundos);
        System.out.println("#########################################");
    }
    
    public static void diferencaEntreDatasJava7() throws ParseException{
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        Date primeiroDt = sdf.parse("26/02/2020");
        Date segundaDt = sdf.parse("27/02/2020");
        
        //pega a quantidade de milissegundos da segunda data menos a primeira
        long diffEmmil =  Math.abs(segundaDt.getTime() - primeiroDt.getTime());
        
        long diff = TimeUnit.DAYS.convert(diffEmmil, TimeUnit.MILLISECONDS);
        int dias = (int) (diffEmmil / (1000 * 60 * 60 * 24));
        
        System.out.println(diff);
        System.out.println(dias);
    }
    
    public static void diferencaEntreDatasJava8(){
        LocalDate data1 = LocalDate.of(2019, 1, 1);
        LocalDate data2 = LocalDate.of(2020, 2, 27);
        
        Period periodo = Period.between(data1, data2);
        int dias = periodo.getDays();
        int meses = periodo.getMonths();
        int anos = periodo.getYears();
        
        System.out.println("Dias : "+dias);
        System.out.println("Meses : "+meses);
        System.out.println("Anos : "+anos);
    }
    
    public static void diferencaEntreDatasJava8ComTempo(){
        LocalDateTime data1 = LocalDateTime.of(2019, 1, 1, 7, 30, 52);
        LocalDateTime data2 = LocalDateTime.of(2020, 2, 27, 10, 14, 25);
        
        Duration duracao = Duration.between(data1, data2);
        long segundos = duracao.getSeconds();
        long minutos = duracao.toMinutes();
        long horas = duracao.toHours();
        
        System.out.println("Horas : "+horas+" Minutos : "+minutos+" Segundos : "+segundos);
    }
    
    public static void diferencaEntreDatasJava8ComChronoUnit(){
        LocalDateTime data1 = LocalDateTime.of(2020, 2, 27, 7, 30, 52);
        LocalDateTime data2 = LocalDateTime.of(2020, 2, 27, 10, 15, 25);
        
        long minutos = ChronoUnit.MINUTES.between(data1, data2);
        long diff = ChronoUnit.HOURS.between(data1, data2);
        long diffDias = ChronoUnit.DAYS.between(data1, data2);
        long diffMeses = ChronoUnit.MONTHS.between(data1, data2);
        long diffAnos = ChronoUnit.YEARS.between(data1, data2);
        
        System.out.println("Anos: "+diffAnos+" Meses: "+diffMeses+" Dias:"+diffDias+" Horas: "+diff+" Minutos: "+minutos);
    }
}














