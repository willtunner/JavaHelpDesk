/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Suporte-07
 */
public class MySQLBackup {
     // Constantes da classe

  private static String VERSION = "4.0.3";

 

  private static String SEPARATOR = File.separator;

 

  private static String MYSQL_PATH =

    "G:" + SEPARATOR +

    "Arquivos de programas" + SEPARATOR +

    "MySQL" + SEPARATOR +

    "MySQL Server 5.0" + SEPARATOR +

    "bin" + SEPARATOR;

 

  private static String PRESENTATION =

    "==========================================================\n" +

    "  Backup do banco de dados MySQL - Versao " + VERSION + "\n" +

    "  Autor: Marcelo Carvalho Pinto da Cunha\n\n" +

    "  Desenvolvido em 07/09/2009\n\n" +

    "  MarcWare Software, 2009-2012\n" +

    "==========================================================\n\n";

 

  // Lista dos bancos de dados a serem "backupeados"; se desejar adicionar mais,

  // basta colocar o nome separado por espaços dos outros nomes

  private static String DATABASES = 

    "suportedesk";

 

  private List<String> dbList = new ArrayList<String>();

 

  public MySQLBackup() {

    String command = MYSQL_PATH + "C:\\wamp64\\bin\\mysql\\mysql5.7.23\\bin\\mysqldump.exe";

 

    String[] databases = DATABASES.split(" ");

 

    for (int i = 0; i < databases.length; i++)

      dbList.add(databases[i]);

 

    // Mostra apresentação

    System.out.println(PRESENTATION);

 

    System.out.println("Iniciando backups...\n\n");

 

    // Contador

    int i = 1;

 

    // Tempo

    long time1, time2, time;

 

    // Início

    time1 = System.currentTimeMillis();

 

    for (String dbName : dbList) {

      ProcessBuilder pb = new ProcessBuilder(

        command,

        "william",

        "lft11790",

        dbName,

        "--result-file=" + "." + SEPARATOR + "Backup" + SEPARATOR + dbName + ".sql");

 

      try {

        System.out.println(

          "Backup do banco de dados (" + i + "): " + dbName + " ...");

 

        pb.start();

      }

      catch (Exception e) {

        e.printStackTrace();

      }

 

      i++;

    }

 

    // Fim

    time2 = System.currentTimeMillis();

 

    // Tempo total da operação

    time = time2 - time1;

 

    // Avisa do sucesso

    System.out.println("\nBackups realizados com sucesso.\n\n");

    System.out.println("Tempo total de processamento: " + time + " ms\n");

    System.out.println("Finalizando...");

 

    try {

      // Paralisa por 2 segundos

      Thread.sleep(2000);

    }

    catch (Exception e) {}

 

    // Termina o aplicativo

    System.exit(0);

  }

 

  public static void main(String[] args) {

    MySQLBackup app = new MySQLBackup();

  } 
}
