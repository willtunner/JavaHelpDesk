/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import static Exemplos.TesteUltimaDataArquivo.lb_Thread;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Suporte-07
 */
public class MinhaThread extends Thread {//extender a classe tread

    private String nome;
    private int tempo;
    
    //faz o papel do BEAN
    public MinhaThread(String nome, int tempo) {
        this.nome = nome;
        this.tempo = tempo;
        start();
    }

    public void run() {//precisa criar o método run
        try {
            for(int i=0; i<6; i++){//conta até 6
                System.out.println(nome + "Contador" +i);
                lb_Thread.setText(nome + "Contador" +i);
                Thread.sleep(tempo);
            }
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(nome + "Terminou de executar");//quando termina ele printa a msg 
        lb_Thread.setText(nome+ "Terminou de executar");
    }
}
