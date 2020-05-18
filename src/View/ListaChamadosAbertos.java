/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import java.awt.Color;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Chamado;
import model.dao.ChamadoDAO;

/**
 *
 * @author William
 */
public class ListaChamadosAbertos extends javax.swing.JFrame {
   

    public ListaChamadosAbertos(String userLogado) {
        //this.chamadoView2 = new Chamado_View4();
        initComponents();

        ListaChamadosAberto(userLogado);

        //adiciona o metodo 2  clicks na tabela chamado
        tbl_Chamado.addMouseListener(new ListaChamadosAbertos.MyMouseListener());
    }

    private ListaChamadosAbertos() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    

//Chama a teça chamadoView2
        //Metodo de 2 click na tabela chamado
    public class MyMouseListener extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                try {
                    carregaDados();
                } catch (ParseException ex) {
                    Logger.getLogger(ListaChamadosAbertos.class.getName()).log(Level.SEVERE, null, ex);
                }
            }
        }
    }

    public void carregaDados() throws ParseException {
        Chamado_View4 chamadoView2 = new Chamado_View4();
        int index = tbl_Chamado.getSelectedRow();

        TableModel model = tbl_Chamado.getModel();

        String Id = model.getValueAt(index, 0).toString();
        String Titulo = model.getValueAt(index, 1).toString();
        String Conteudo = model.getValueAt(index, 2).toString();
        String Solucao = model.getValueAt(index, 3).toString();
        String Empresa = model.getValueAt(index, 4).toString();
        String Funcionario = model.getValueAt(index, 5).toString();
        String Tags = model.getValueAt(index, 6).toString();
        String Telefone = model.getValueAt(index, 7).toString();
        String Conexao = model.getValueAt(index, 8).toString();
        String User = model.getValueAt(index, 9).toString();
        String DataChamado = model.getValueAt(index, 10).toString();//PEGA A DATA CERTA DO CHAMADO
        String HoraChamado = model.getValueAt(index, 11).toString();//PEGA A HORA DO CHAMADO
        String Status = model.getValueAt(index, 12).toString();
        String Cronometro = model.getValueAt(index, 16).toString();

        if (model.getValueAt(index, 17) != null) {
            String HoraFechado = model.getValueAt(index, 17).toString();//PEGA HORA DO CHAMADO FECHADO
            chamadoView2.lbl_HoraChamadoFecha.setForeground(new Color(204, 102, 0));
            chamadoView2.lbl_HoraChamadoFecha.setText(HoraFechado);
            chamadoView2.HoraFim = HoraFechado;
            
            JOptionPane.showMessageDialog(this, HoraFechado);
        } else {
            chamadoView2.lbl_HoraChamadoFecha.setForeground(Color.red);
            chamadoView2.lbl_HoraChamadoFecha.setText("Em Aberto");
            LocalTime horaEmAberto = LocalTime.now();
            chamadoView2.HoraFim = horaEmAberto.toString();
        }

        if (model.getValueAt(index, 18) != null) {
            String DataFechado = model.getValueAt(index, 18).toString();//PEGA DATA DO CHAMADO FECHADO
            chamadoView2.lbl_DataChamadoFecha.setForeground(new Color(204, 102, 0));
            chamadoView2.lbl_DataChamadoFecha.setText(DataFechado);
            chamadoView2.dataFim = DataFechado;
        } else {
            String dataAberto = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
            chamadoView2.lbl_DataChamadoFecha.setForeground(Color.red);
            chamadoView2.lbl_DataChamadoFecha.setText("Em Aberto");
            chamadoView2.dataFim = dataAberto;
        }

        //função para enviar o status para outro frame
        if (Status.equals("A")) {
            chamadoView2.cmb_Status.setSelectedIndex(0);
        } else {
            chamadoView2.cmb_Status.setSelectedIndex(1);
        }
        chamadoView2.dataIni = DataChamado;//PASSA A DATA PARA A VAREAVEL EM OUTRA TELA
        chamadoView2.txt_id_Chamado.setText(Id);
        chamadoView2.txt_titulo.setText(Titulo);
        chamadoView2.txt_chamado.setText(Conteudo);
        chamadoView2.lb_empresa.setText(Empresa);
        chamadoView2.lb_Funcionario.setText(Funcionario);
        chamadoView2.txt_solucao.setText(Solucao);
        chamadoView2.txt_conexao.setText(Conexao);
        chamadoView2.txt_tags.setText(Tags);
        chamadoView2.txt_usuario_log.setText(User);
        chamadoView2.lb_telefone.setText(Telefone);
        chamadoView2.lb_data_chamado.setText(DataChamado);
        chamadoView2.lbl_Cronometro.setText(Cronometro);

        chamadoView2.lb_hora.setText(HoraChamado);
        chamadoView2.SomaDatas();//PASSA OS DADOS DO CHAMADO ANTES DE CHAMAR A TELA 
        chamadoView2.setVisible(true);//CHAMA A TELA
        chamadoView2.pack();
        chamadoView2.setLocationRelativeTo(null);
        chamadoView2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
    }

    public  void ListaChamadosAberto(String user) {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado.getModel();
        modelo.setNumRows(0);

        tbl_Chamado.setAutoResizeMode(tbl_Chamado.AUTO_RESIZE_OFF);
        tbl_Chamado.setRowSorter(new TableRowSorter(modelo));
        tbl_Chamado.setModel(modelo);
        tbl_Chamado.getColumnModel().getColumn(0).setPreferredWidth(40);//ID
        tbl_Chamado.getColumnModel().getColumn(1).setPreferredWidth(280);//TITULO
        tbl_Chamado.getColumnModel().getColumn(2).setPreferredWidth(200);//DESCRIÇÃO
        tbl_Chamado.getColumnModel().getColumn(3).setPreferredWidth(280);//SOLUÇÃO
        tbl_Chamado.getColumnModel().getColumn(4).setPreferredWidth(200);//EMPRESA
        tbl_Chamado.getColumnModel().getColumn(5).setPreferredWidth(100);//FUNCIONARIO
        tbl_Chamado.getColumnModel().getColumn(6).setPreferredWidth(100);//TAGS
        tbl_Chamado.getColumnModel().getColumn(7).setPreferredWidth(150);//FONE
        tbl_Chamado.getColumnModel().getColumn(8).setPreferredWidth(80);//CONEXÃO
        tbl_Chamado.getColumnModel().getColumn(9).setPreferredWidth(100);//USUARIO LOGICOM
        tbl_Chamado.getColumnModel().getColumn(10).setPreferredWidth(100);//DATA
        tbl_Chamado.getColumnModel().getColumn(11).setPreferredWidth(90);//HORA CHAMADO
        tbl_Chamado.getColumnModel().getColumn(12).setPreferredWidth(50);//STATUS
        tbl_Chamado.getColumnModel().getColumn(13).setPreferredWidth(100);//EDITADO
        tbl_Chamado.getColumnModel().getColumn(14).setPreferredWidth(90);//DATA EDITADO
        tbl_Chamado.getColumnModel().getColumn(15).setPreferredWidth(90);//HORA EDITADO
        //acrescentados
        tbl_Chamado.getColumnModel().getColumn(16).setPreferredWidth(100);//CRONOMETRO
        tbl_Chamado.getColumnModel().getColumn(17).setPreferredWidth(90);//HORA FECHADO
        tbl_Chamado.getColumnModel().getColumn(18).setPreferredWidth(90);//DATA FECHADO

        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.ListarChamadoAberto(user)) {
            modelo.addRow(new Object[]{
                c.getId(),
                c.getTitulo(),
                c.getTexto(),
                c.getSolucao(),
                c.getNome_emp(),
                c.getNome(),
                c.getTags(),
                c.getFone(),
                c.getConexao(),
                c.getUser(),
                c.getData(),
                c.getHora(),
                c.getStatus(),
                c.getEditado(),
                c.getDataeditado(),
                c.getHoraeditado(),
                c.getCronometro(),
                c.getHoraFechaChamado(),
                c.getDataChamadoFechado()
            });
        }
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Head = new javax.swing.JPanel();
        lbl_ChamadosTit = new javax.swing.JLabel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Chamado = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        Head.setBackground(new java.awt.Color(19, 29, 38));
        Head.setForeground(new java.awt.Color(255, 255, 255));

        lbl_ChamadosTit.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_ChamadosTit.setForeground(new java.awt.Color(255, 255, 255));
        lbl_ChamadosTit.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lbl_ChamadosTit.setText("Chamados em Aberto");

        javax.swing.GroupLayout HeadLayout = new javax.swing.GroupLayout(Head);
        Head.setLayout(HeadLayout);
        HeadLayout.setHorizontalGroup(
            HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(lbl_ChamadosTit, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 1200, Short.MAX_VALUE)
        );
        HeadLayout.setVerticalGroup(
            HeadLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(HeadLayout.createSequentialGroup()
                .addGap(33, 33, 33)
                .addComponent(lbl_ChamadosTit, javax.swing.GroupLayout.PREFERRED_SIZE, 43, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(24, Short.MAX_VALUE))
        );

        jScrollPane1.setAutoscrolls(true);
        jScrollPane1.setOpaque(false);

        tbl_Chamado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Titulo", "Descrição", "Solução", "Empresa", "Cliente", "Tags", "Telefone", "Conexão", "Logicom", "Data Chamado", "Hora Chamado", "Status", "Editado", "Data Editado", "Hora Editado", "Cronometro", "Hora Fechado", "Data Fechado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Chamado.setMaximumSize(new java.awt.Dimension(900, 0));
        tbl_Chamado.setOpaque(false);
        jScrollPane1.setViewportView(tbl_Chamado);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Head, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1180, Short.MAX_VALUE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Head, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 582, Short.MAX_VALUE)
                .addGap(10, 10, 10))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        /* Set the Nimbus look and feel */
        //<editor-fold defaultstate="collapsed" desc=" Look and feel setting code (optional) ">
        /* If Nimbus (introduced in Java SE 6) is not available, stay with the default look and feel.
         * For details see http://download.oracle.com/javase/tutorial/uiswing/lookandfeel/plaf.html 
         */
        try {
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels()) {
                if ("Nimbus".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
            }
        } catch (ClassNotFoundException ex) {
            java.util.logging.Logger.getLogger(ListaChamadosAbertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(ListaChamadosAbertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(ListaChamadosAbertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(ListaChamadosAbertos.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new ListaChamadosAbertos().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Head;
    private javax.swing.JScrollPane jScrollPane1;
    public static javax.swing.JLabel lbl_ChamadosTit;
    public static javax.swing.JTable tbl_Chamado;
    // End of variables declaration//GEN-END:variables

}
