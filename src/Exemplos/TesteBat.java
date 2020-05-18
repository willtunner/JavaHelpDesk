/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;
import java.io.*;
/**
 *
 * @author Suporte-07
 */
public class TesteBat {
    public static void main(String[] args) {
        try {
            // Conteudo
            String content = "Clauber Anjos";

            // Cria arquivo
            File file = new File("teste.bat");

            // Se o arquivo nao existir, ele gera
            if (!file.exists()) {
                file.createNewFile();
            }

            // Prepara para escrever no arquivo
            FileWriter fw = new FileWriter(file.getAbsoluteFile());
            BufferedWriter bw = new BufferedWriter(fw);
            
            // Escreve e fecha arquivo
            bw.write(content);
            bw.close();
            
           /* // Le o arquivo
            FileReader ler = new FileReader("teste.txt");
            BufferedReader reader = new BufferedReader(ler);  
            String linha;
            while( (linha = reader.readLine()) != null ){
                System.out.println(linha);
            }*/

            // Imprime confirmacao
            System.out.println("Feito =D");

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    }

