/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.awt.Dimension;
import java.util.ArrayList;
import model.bean.Chamado;
import org.jfree.chart.ChartFactory;
import org.jfree.chart.ChartPanel;
import org.jfree.chart.JFreeChart;
import org.jfree.chart.plot.PlotOrientation;
import org.jfree.data.category.CategoryDataset;
import org.jfree.data.category.DefaultCategoryDataset;

/**
 *
 * @author Suporte-07
 */
public class ClasseGraficos {
    //Criar data set
    /*public CategoryDataset createDataSet(ArrayList <Chamado> listaChamado){
        DefaultCategoryDataset dataSet = new DefaultCategoryDataset();
        
        for(Chamado chamado : listaChamado){
            dataSet.addValue(chamado.getId(), chamado.getNome_emp(), "");//(Dados horizontal, Titulo vertical, Dados Vertical)
        }
        return dataSet;
    }*/
    
    //Criar o grafico
    public JFreeChart createBarChart (CategoryDataset dataset){
    JFreeChart graficoBarras = ChartFactory.createBarChart("Empresas",
            "", 
            "Idade", 
            dataset,
            PlotOrientation.VERTICAL,
            true,
            false,
            false);
        return graficoBarras;
    }
    
    
    //Criar grafico completo
    /*public ChartPanel criarGrafico(ArrayList<Chamado> listaChamado){
        CategoryDataset dataSet = this.createDataSet(listaChamado);
        
        JFreeChart grafico = this.createBarChart(dataSet);
        
        ChartPanel painelGrafico = new ChartPanel(grafico);
        painelGrafico.setPreferredSize(new Dimension(400, 400));
        return painelGrafico;
        
    }*/
}




































































































