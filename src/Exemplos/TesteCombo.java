/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.awt.event.ItemEvent;
import javax.swing.DefaultComboBoxModel;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.dao.EmpresaDAO;
import model.dao.FuncionarioDAO;

/**
 *
 * @author Suporte-07
 */
public class TesteCombo extends javax.swing.JFrame {

    EmpresaDAO emp = new EmpresaDAO();

    public TesteCombo() {
        initComponents();

        /*DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(cc.mostrarEstados());
         cbxEstado.setModel(modelEstado);*/
        DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(emp.mostrarEmpresas());
        jcombo_emp.setModel(modelEstado);
    }
   
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jcombo_emp = new javax.swing.JComboBox();
        jcombo_func = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        lbFunc = new javax.swing.JLabel();
        lb_tel_func = new javax.swing.JLabel();
        lb_ConexFunc = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        lbEmp = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        jcombo_emp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcombo_empItemStateChanged(evt);
            }
        });

        jcombo_func.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                jcombo_funcItemStateChanged(evt);
            }
        });

        jLabel1.setText("Funcionario:");

        jLabel2.setText("Telefone:");

        jLabel3.setText("Conexão:");

        lbFunc.setText("NomeFunc");

        lb_tel_func.setText("TelFunc");

        lb_ConexFunc.setText("ConexFunc");

        jLabel4.setText("Empresa:");

        lbEmp.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        lbEmp.setText("Emp");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(29, 29, 29)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(18, 18, 18)
                        .addComponent(lbEmp, javax.swing.GroupLayout.PREFERRED_SIZE, 197, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(41, 41, 41))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel3)
                        .addGap(18, 18, 18)
                        .addComponent(lb_ConexFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 230, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(8, 8, 8))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jcombo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(18, 18, 18)
                        .addComponent(jcombo_func, javax.swing.GroupLayout.PREFERRED_SIZE, 150, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(lbFunc, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, layout.createSequentialGroup()
                        .addComponent(jLabel2)
                        .addGap(18, 18, 18)
                        .addComponent(lb_tel_func, javax.swing.GroupLayout.PREFERRED_SIZE, 209, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(73, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(98, 98, 98)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jcombo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jcombo_func, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(20, 20, 20)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel4)
                    .addComponent(lbEmp))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(lbFunc))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel2)
                    .addComponent(lb_tel_func))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel3)
                    .addComponent(lb_ConexFunc))
                .addContainerGap(85, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void jcombo_empItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcombo_empItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Empresa emp = (Empresa) jcombo_emp.getSelectedItem();
            FuncionarioDAO func = new FuncionarioDAO();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(func.mostrarFuncionario(emp.getId_emp()));
            jcombo_func.setModel(modelMunicipio);
        }

    }//GEN-LAST:event_jcombo_empItemStateChanged

    private void jcombo_funcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_jcombo_funcItemStateChanged
         if (evt.getStateChange() == ItemEvent.SELECTED) {
           
            Empresa emp = (Empresa) jcombo_emp.getSelectedItem();
            Funcionario func = (Funcionario) jcombo_func.getSelectedItem();
            
            lbEmp.setText(emp.getNome_emp());
            lbFunc.setText(func.getNome());
            lb_ConexFunc.setText(func.getConexao());
            lb_tel_func.setText(func.getTelefone());
         }
    }//GEN-LAST:event_jcombo_funcItemStateChanged

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
            java.util.logging.Logger.getLogger(TesteCombo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(TesteCombo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(TesteCombo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(TesteCombo.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new TesteCombo().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JComboBox jcombo_emp;
    private javax.swing.JComboBox jcombo_func;
    private javax.swing.JLabel lbEmp;
    private javax.swing.JLabel lbFunc;
    private javax.swing.JLabel lb_ConexFunc;
    private javax.swing.JLabel lb_tel_func;
    // End of variables declaration//GEN-END:variables
}
