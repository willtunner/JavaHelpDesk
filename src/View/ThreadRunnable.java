/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

/**
 *
 * @author Suporte-07
 */
public class ThreadRunnable implements Runnable {

    private String nome;
    private int tempo;

    public ThreadRunnable(String nome, int tempo) {
        this.nome = nome;
        this.tempo = tempo;
        Thread t = new Thread(this);
        t.start();
    }

    @Override
    public void run() {
        try {
            for (int i = 0; i < 6; i++) {
                System.out.println(nome + "Contador " + i);
                Thread.sleep(tempo);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(nome+" terminou a execução");
    }

}
