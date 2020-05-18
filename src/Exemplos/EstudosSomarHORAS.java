/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.util.Scanner;

/**
 *
 * @author Suporte-07
 */
public class EstudosSomarHORAS {
    public static void main(String[] args){
        long totalMilisegundos = System.currentTimeMillis() /*-10800000*/;//o -10800000 é correspondente ao fuso horario de -3 horas
        
        long totalSegundos = totalMilisegundos / 1000;
        long segundoAtual = totalSegundos % 60;
        
        long totalMinutos = totalSegundos / 60;
        long minutoAtual = totalMinutos % 60;
        
        long totalHora = totalMinutos / 60;
        long horaAtual = totalHora % 24;

        System.out.println(horaAtual-3 +/*-3 é correspondente ao fuso horario(outra maneira de fazer)*/" : "+ minutoAtual + " : "+segundoAtual);
        
        /*CALCULAR DIAS VIVIDOS*/
        Scanner sc = new Scanner(System.in);
        String nome;
        int idade;
        int qtdDias;
        System.out.println("Informe seu primeiro nome:");
        nome = sc.next();
        System.out.println("Informe sua idade em anos:");
        idade = sc.nextInt();
        qtdDias = idade * 365;
        System.out.println(nome +" , você já viveu "+qtdDias + " Dias.");
    }
}
























