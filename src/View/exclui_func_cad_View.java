/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.dao.FuncionarioDAO;

/**
 *
 * @author Suporte-07
 */
public final class exclui_func_cad_View extends javax.swing.JFrame {

    /**
     * Creates new form exclui_func_cad_View
     */
    public exclui_func_cad_View(Empresa e) {
        FuncionarioDAO fdao = new FuncionarioDAO();
        initComponents();
        //readJTable();
        //fdao.FuncEmp(e.getNome_emp());
         DefaultTableModel modelo = (DefaultTableModel) jt_exclui_func.getModel();
        modelo.setNumRows(0);
        jt_exclui_func.setRowSorter(new TableRowSorter(modelo));
        jt_exclui_func.setAutoResizeMode(jt_exclui_func.AUTO_RESIZE_LAST_COLUMN);
        jt_exclui_func.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
        jt_exclui_func.getColumnModel().getColumn(1).setPreferredWidth(100);//Nome
        jt_exclui_func.getColumnModel().getColumn(2).setPreferredWidth(90);//Telefone
        jt_exclui_func.getColumnModel().getColumn(3).setPreferredWidth(180);//Email
        jt_exclui_func.getColumnModel().getColumn(4).setPreferredWidth(100);//Genero
        jt_exclui_func.getColumnModel().getColumn(5).setPreferredWidth(60);//ID Empresa
        jt_exclui_func.getColumnModel().getColumn(6).setPreferredWidth(180);//Empresa
        jt_exclui_func.getColumnModel().getColumn(7).setPreferredWidth(100);//Conexão
        jt_exclui_func.getColumnModel().getColumn(8).setPreferredWidth(120);//AnyDesck

        //FuncionarioDAO adao = new FuncionarioDAO();
        for (Funcionario f : fdao.FuncEmp(e.getNome_emp())) {
            modelo.addRow(new Object[]{
                f.getId_func(),
                f.getNome(),
                f.getTelefone(),
                f.getEmail(),
                f.getGenero(),
                f.getId_emp(),
                f.getNome_emp(),
                f.getConexao()
            });
        }
    }

    private exclui_func_cad_View() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jt_exclui_func = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        lb_nome_emp = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jt_exclui_func.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone", "Email", "Genêro", "ID Emp", "Empresa", "Conexão", "Anydesk"
            }
        ));
        jScrollPane1.setViewportView(jt_exclui_func);

        jPanel1.setBackground(new java.awt.Color(51, 51, 51));

        lb_nome_emp.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lb_nome_emp.setForeground(new java.awt.Color(255, 255, 255));
        lb_nome_emp.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_nome_emp.setText("Empresa...");

        jLabel2.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel2.setText("Funcioarios da empresa :");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGap(271, 271, 271)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(lb_nome_emp)
                .addContainerGap(340, Short.MAX_VALUE))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(lb_nome_emp)
                    .addComponent(jLabel2))
                .addContainerGap())
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jScrollPane1)
                .addContainerGap())
            .addComponent(jPanel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(59, 59, 59)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 129, Short.MAX_VALUE)
                .addGap(141, 141, 141))
        );

        setSize(new java.awt.Dimension(1058, 419));
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
            java.util.logging.Logger.getLogger(exclui_func_cad_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(exclui_func_cad_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(exclui_func_cad_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(exclui_func_cad_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new exclui_func_cad_View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel2;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JTable jt_exclui_func;
    public static javax.swing.JLabel lb_nome_emp;
    // End of variables declaration//GEN-END:variables
    
    
    
}