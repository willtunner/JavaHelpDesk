/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Exemplos;

import java.awt.Color;
import javax.swing.ImageIcon;

/**
 *
 * @author Suporte-07
 */
public class imgButton extends javax.swing.JFrame {

    /**
     * Creates new form imgButton
     */
    public imgButton() {
        initComponents();
        //ChechaAnyButton();
        jTextField1.setText("Texto Inserido xD");
        ChecaTexto();
       
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        btn1 = new javax.swing.JToggleButton();
        jTextField1 = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);

        btn1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/anydesk bw.png"))); // NOI18N
        btn1.setContentAreaFilled(false);
        btn1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn1ActionPerformed(evt);
            }
        });

        jTextField1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jTextField1ActionPerformed(evt);
            }
        });
        jTextField1.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                jTextField1KeyPressed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(166, 166, 166)
                        .addComponent(btn1, javax.swing.GroupLayout.PREFERRED_SIZE, 42, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(74, 74, 74)
                        .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, 236, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap(90, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGap(52, 52, 52)
                .addComponent(btn1)
                .addGap(49, 49, 49)
                .addComponent(jTextField1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(138, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn1ActionPerformed
        if(btn1.isSelected()){
            btn1.setBackground(Color.red);
            //btn1.setText("ON");
            btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
        }else{
            btn1.setBackground(Color.GRAY);
            //btn1.setText("OFF");
            btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
        }
    }//GEN-LAST:event_btn1ActionPerformed
    public void ChecaTexto(){
        String texto = jTextField1.getText();
       
       if(texto == null || texto.equals("")){
           System.out.println("Vazio");
           btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
       }else{
           System.out.println("Texto inserido");
           btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
       }
    }
    
    public void ChechaAnyButton(){
        if(jTextField1.getText().equals("")){
            //btn1.isSelected();
            btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
        }else{
            btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
        }
    }
    
    private void jTextField1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jTextField1ActionPerformed
        if(jTextField1.getText().equals("")){
            btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
        }else{
            btn1.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
        }
    }//GEN-LAST:event_jTextField1ActionPerformed

    private void jTextField1KeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_jTextField1KeyPressed
        ChecaTexto();
    }//GEN-LAST:event_jTextField1KeyPressed
    boolean clicado;
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
            java.util.logging.Logger.getLogger(imgButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(imgButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(imgButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(imgButton.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new imgButton().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JToggleButton btn1;
    private javax.swing.JTextField jTextField1;
    // End of variables declaration//GEN-END:variables
}
