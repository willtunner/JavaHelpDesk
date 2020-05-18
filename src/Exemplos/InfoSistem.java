/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.io.File;
import java.lang.management.ManagementFactory;

/**
 *
 * @author William
 */
public class InfoSistem {

    public static void main(String[] args) {
        String path = "g:/";
        File file = new File(path);

        System.out.println("File name: " + file.getName() + "\n");

        System.out.println("Path: " + file.getPath());
        System.out.println("Absolutely  path: " + file.getAbsolutePath());

        System.out.println("Parent directory: " + file.getParent());
        System.out.println("File Lenght: " + file.length() + " bytes \n");

        System.out.println("File " + (file.exists() ? "Existe" : "Não Existe!"));
        System.out.println("File " + (file.canWrite() ? "Writable" : "Não Writable"));
        System.out.println("File " + (file.canRead() ? "Readble" : "Not Readble"));
        System.out.println("File " + (file.isDirectory() ? "is Directory" : "is not directory"));
        System.out.println("File " + (file.isFile() ? "is Regular file" : "is not Regular file"));
        System.out.println("File " + (file.isHidden() ? "hidden" : "is not hidden") + "\n");

        System.out.println("Espaço Livre: " + CapacityFormatter.toGigabytes(file.getFreeSpace()) + "Gb Livres");
        System.out.println("Espaço Total: " + CapacityFormatter.toGigabytes(file.getTotalSpace()) + "Gb ");
        System.out.println("Espaço Usado: " + CapacityFormatter.toGigabytes(file.getTotalSpace() - file.getFreeSpace()) + "Gb Usado \n");

        Runtime rt = Runtime.getRuntime();
            
        System.out.println("Used Memory Virtual java  :  " + (rt.totalMemory() - Runtime.getRuntime().freeMemory()) + " bytes");
        System.out.println("Free Memory  Virtual java : " + rt.freeMemory() + " bytes");
        System.out.println("Total Memory Virtual java : " + rt.totalMemory() + " bytes");
        System.out.println("Max Memory Virtual java : " + rt.maxMemory() + " bytes \n");

        com.sun.management.OperatingSystemMXBean mxbean
                = (com.sun.management.OperatingSystemMXBean) ManagementFactory.getOperatingSystemMXBean();

        Long total = mxbean.getTotalPhysicalMemorySize();
        Long livre = mxbean.getFreePhysicalMemorySize();
        
        System.out.println("Memoria Total : " + CapacityFormatter.toGigabytes(mxbean.getTotalPhysicalMemorySize()) + " Gb ");
        System.out.println("Memoria disponivel : " + CapacityFormatter.toGigabytes(mxbean.getFreePhysicalMemorySize()) + " Gb");
        System.out.println("Memoria Usada : " + CapacityFormatter.toGigabytes(total-livre) + " Gb \n");
        
        System.out.println("Número de processadores logicos: " + rt.availableProcessors());
    }

    private static class CapacityFormatter {

        private static long toGigabytes(long capacity) {
            return capacity / (long) Math.pow(10,9);
        }
    }
}
