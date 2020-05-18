/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Funcoes;

import Funcoes.exportarExcel;
import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JTable;
import javax.swing.table.TableModel;
import jxl.Workbook;
import jxl.write.Label;
import jxl.write.WritableSheet;
import jxl.write.WritableWorkbook;


public class exportarExcel {
    public void crearArchivo(JTable tabela){
        try {
            JFileChooser file = new JFileChooser();//CRIA O FILECHOOSER
            file.showSaveDialog(file);//DA A OPÇÃO DE SALVAR 
            File guardar = file.getSelectedFile();//PEGA O CAMINHO DO ARQUIVO A SER SALVO
            
            if(guardar != null){//SE O CAMINHO ESTIVER DIFERENTE DE NULO
                guardar = new File(guardar.toString()+".xls");//PEGA O CAMINHO E ATRIBUI A EXTENSÃO DO EXCEL
                WritableWorkbook workbook1 = Workbook.createWorkbook(guardar);//FUNÇÃO PARA TRABALHAR COM ARQUIVOS DO OFFICE
                WritableSheet sheet1 = workbook1.createSheet("Relatorio de Chamados", 0);//CRIA UMA ABA DO EXCEL
                TableModel model = tabela.getModel();//PEGA O MODEL DA TABELA PASSADA
                
                for (int i = 0; i < model.getColumnCount(); i++) {//PEGA O NUMERO DE COLUNAS EXISTENTE NA TABELA 
                    Label collumn = new Label(i, 0, model.getColumnName(i));//SETA AS COLUNAS NO EXCEL
                    sheet1.addCell(collumn);
                }
                
                for (int i = 0; i < model.getRowCount(); i++) {//PEGA AS LINHAS E CONTA QUANTAS TEM
                    for (int j = 0; j < model.getColumnCount(); j++) {//COLOCA AS LINHAS CONFORME O NUMERO DE COLUNAS
                       Label row = null;
                        
                        if(model.getValueAt(i, j)== null){
                            row = new Label(j, i+1,"");
                        }
                        else
                        {
                          row = new Label(j, i+1, model.getValueAt(i, j).toString());//SALVA AS LINHAS E COLUNAS
                        }
                     //System.out.println("DEBUG EXCEL: "+model.getValueAt(i, j));
                        
                        
                        sheet1.addCell(row);
                    }
                }
                workbook1.write();
                workbook1.close();
                JOptionPane.showMessageDialog(null, "Arquivo criado");
                openFile(guardar.toString());
            }else{
                JOptionPane.showMessageDialog(null, "Erro em criar o arquivo");
            }
            
        } catch (Exception e) {
        }
    }
    
    public void openFile(String file){
        try {
            File path = new File(file);
            Desktop.getDesktop().open(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
