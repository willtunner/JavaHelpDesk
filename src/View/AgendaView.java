package View;

import java.awt.Color;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Agenda;
import model.dao.AgendaDAO;

public class AgendaView extends javax.swing.JFrame {

    public AgendaView() {
        initComponents();
        readJTableAgenda();
    }

    public void readJTableAgenda() {
        DefaultTableModel modelo = (DefaultTableModel) Table_agenda.getModel();
        Table_agenda.setRowSorter(new TableRowSorter(modelo));//Coloca para ordenar tabela quando clica na coluna
        Table_agenda.setAutoResizeMode(Table_agenda.AUTO_RESIZE_OFF);//coloca a tabela para mover na horizontal
        
        modelo.setNumRows(0);
        
        Table_agenda.getColumnModel().getColumn(0).setPreferredWidth(30);//Nome
        Table_agenda.getColumnModel().getColumn(1).setPreferredWidth(160);//Nome
        Table_agenda.getColumnModel().getColumn(2).setPreferredWidth(300);//Endereço
        Table_agenda.getColumnModel().getColumn(3).setPreferredWidth(190);//Email
        Table_agenda.getColumnModel().getColumn(4).setPreferredWidth(100);//Telefone
        Table_agenda.getColumnModel().getColumn(5).setPreferredWidth(200);//Observação

        AgendaDAO adao = new AgendaDAO();

        for (Agenda a : adao.Listar()) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getTelefone(),
                a.getEmail(),
                a.getEndereco(),
                a.getObservacao()
            });
        }
    }

    public void readJTableForNome(String nome) {
        DefaultTableModel modelo = (DefaultTableModel) Table_agenda.getModel();
        modelo.setNumRows(0);
        AgendaDAO adao = new AgendaDAO();

        for (Agenda a : adao.Pesquisar(nome)) {
            modelo.addRow(new Object[]{
                a.getId(),
                a.getNome(),
                a.getTelefone(),
                a.getEmail(),
                a.getEndereco(),
                a.getObservacao()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        txt_nome_agenda = new javax.swing.JTextField();
        txt_endereco_agenda = new javax.swing.JTextField();
        txt_observacao_agenda = new javax.swing.JTextField();
        jLabel1 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        jLabel5 = new javax.swing.JLabel();
        btn_salvar_agenda = new javax.swing.JButton();
        btn_excluir_agenda = new javax.swing.JButton();
        btn_atualizar_agenda = new javax.swing.JButton();
        novo = new javax.swing.JButton();
        jLabel2 = new javax.swing.JLabel();
        txt_email_agenda = new javax.swing.JTextField();
        txt_telefone_agenda = new javax.swing.JFormattedTextField();
        jPanel2 = new javax.swing.JPanel();
        btn_pesquisa_agenda = new javax.swing.JButton();
        txt_pesquisa_agenda = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        Table_agenda = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jRadioButton1 = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        getContentPane().setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Agenda"));
        jPanel1.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        txt_nome_agenda.setText("Digite o Nome....");
        txt_nome_agenda.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nome_agendaFocusLost(evt);
            }
        });
        txt_nome_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nome_agendaMouseClicked(evt);
            }
        });
        txt_nome_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_nome_agendaActionPerformed(evt);
            }
        });
        jPanel1.add(txt_nome_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 20, 270, -1));
        jPanel1.add(txt_endereco_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 110, 270, -1));

        txt_observacao_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_observacao_agendaActionPerformed(evt);
            }
        });
        jPanel1.add(txt_observacao_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 140, 270, -1));

        jLabel1.setText("Nome:");
        jPanel1.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 20, -1, -1));

        jLabel3.setText("Observação:");
        jPanel1.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 140, -1, -1));

        jLabel4.setText("Endereço:");
        jPanel1.add(jLabel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 110, -1, -1));

        jLabel5.setText("Telefone:");
        jPanel1.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(30, 50, -1, -1));

        btn_salvar_agenda.setText("Salvar");
        btn_salvar_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_agendaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_salvar_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 170, -1, -1));

        btn_excluir_agenda.setText("Excluir");
        btn_excluir_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excluir_agendaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_excluir_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(110, 170, -1, -1));

        btn_atualizar_agenda.setText("Atualizar");
        btn_atualizar_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizar_agendaActionPerformed(evt);
            }
        });
        jPanel1.add(btn_atualizar_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(200, 170, -1, -1));

        novo.setText("Novo");
        novo.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                novoActionPerformed(evt);
            }
        });
        jPanel1.add(novo, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 170, 70, -1));

        jLabel2.setText("Email:");
        jPanel1.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(50, 80, -1, -1));
        jPanel1.add(txt_email_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 80, 270, -1));

        try {
            txt_telefone_agenda.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        jPanel1.add(txt_telefone_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(90, 50, 270, -1));

        getContentPane().add(jPanel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 30, 370, 220));

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisa"));
        jPanel2.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        btn_pesquisa_agenda.setText("Pesquisa");
        btn_pesquisa_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesquisa_agendaActionPerformed(evt);
            }
        });
        jPanel2.add(btn_pesquisa_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(230, 20, 110, -1));

        txt_pesquisa_agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_pesquisa_agendaActionPerformed(evt);
            }
        });
        txt_pesquisa_agenda.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_pesquisa_agendaKeyPressed(evt);
            }
            public void keyTyped(java.awt.event.KeyEvent evt) {
                txt_pesquisa_agendaKeyTyped(evt);
            }
        });
        jPanel2.add(txt_pesquisa_agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 200, -1));

        getContentPane().add(jPanel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 270, 350, 60));

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Contatos"));
        jPanel3.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Table_agenda.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null},
                {null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Endereço", "Email", "Telefone", "Observação"
            }
        ));
        Table_agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                Table_agendaMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(Table_agenda);
        if (Table_agenda.getColumnModel().getColumnCount() > 0) {
            Table_agenda.getColumnModel().getColumn(0).setMinWidth(25);
            Table_agenda.getColumnModel().getColumn(0).setPreferredWidth(25);
            Table_agenda.getColumnModel().getColumn(0).setMaxWidth(50);
            Table_agenda.getColumnModel().getColumn(1).setMinWidth(50);
            Table_agenda.getColumnModel().getColumn(1).setPreferredWidth(100);
            Table_agenda.getColumnModel().getColumn(1).setMaxWidth(150);
            Table_agenda.getColumnModel().getColumn(2).setMinWidth(50);
            Table_agenda.getColumnModel().getColumn(2).setPreferredWidth(100);
            Table_agenda.getColumnModel().getColumn(2).setMaxWidth(150);
            Table_agenda.getColumnModel().getColumn(3).setMinWidth(50);
            Table_agenda.getColumnModel().getColumn(3).setPreferredWidth(100);
            Table_agenda.getColumnModel().getColumn(3).setMaxWidth(150);
            Table_agenda.getColumnModel().getColumn(4).setMinWidth(50);
            Table_agenda.getColumnModel().getColumn(4).setPreferredWidth(80);
            Table_agenda.getColumnModel().getColumn(4).setMaxWidth(100);
            Table_agenda.getColumnModel().getColumn(5).setMinWidth(100);
            Table_agenda.getColumnModel().getColumn(5).setPreferredWidth(100);
            Table_agenda.getColumnModel().getColumn(5).setMaxWidth(100);
        }

        jPanel3.add(jScrollPane1, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 20, 500, 120));

        getContentPane().add(jPanel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 340, 520, 150));

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Gênero"));

        jRadioButton1.setText("Masculino");

        jRadioButton2.setText("Femenino");

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jRadioButton1)
                    .addComponent(jRadioButton2))
                .addGap(0, 47, Short.MAX_VALUE))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jRadioButton1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jRadioButton2)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        getContentPane().add(jPanel4, new org.netbeans.lib.awtextra.AbsoluteConstraints(380, 40, 130, -1));

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void txt_observacao_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_observacao_agendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_observacao_agendaActionPerformed

    private void btn_salvar_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_agendaActionPerformed

        if (txt_nome_agenda.getText().equals("Digite o Nome....") || txt_nome_agenda.getText().equals("")) {
            txt_nome_agenda.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(null, "O campo Nome é Obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_nome_agenda.setText("");
            txt_nome_agenda.requestFocus();
            return;
        } else {
            txt_nome_agenda.setBorder(new LineBorder(Color.lightGray));
        }

        if (txt_telefone_agenda.getText().equals("(  )      -    ") || txt_telefone_agenda.getText().equals("")) {
            txt_telefone_agenda.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(null, "O campo Telefone é Obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_telefone_agenda.setText("");
            txt_telefone_agenda.requestFocus();
            return;
        } else {
            txt_telefone_agenda.setBorder(new LineBorder(Color.lightGray));
        }
        try {
            Agenda a = new Agenda();
            AgendaDAO adao = new AgendaDAO();

            a.setNome(txt_nome_agenda.getText());
            a.setTelefone(txt_telefone_agenda.getText());
            a.setEmail(txt_email_agenda.getText());
            a.setEndereco(txt_endereco_agenda.getText());
            a.setObservacao(txt_observacao_agenda.getText());
            adao.Inserir(a);

            txt_nome_agenda.setText("");
            txt_email_agenda.setText("");
            txt_endereco_agenda.setText("");
            txt_telefone_agenda.setText("");
            txt_observacao_agenda.setText("");
            readJTableAgenda();
        } catch (Exception e) {
        }

    }//GEN-LAST:event_btn_salvar_agendaActionPerformed

    private void btn_excluir_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excluir_agendaActionPerformed
        if (Table_agenda.getSelectedRow() != -1) {
            Agenda a = new Agenda();
            AgendaDAO adao = new AgendaDAO();

            a.setId((int) Table_agenda.getValueAt(Table_agenda.getSelectedRow(), 0));

            adao.Delete(a);

            txt_nome_agenda.setText("");
            txt_telefone_agenda.setText("");
            txt_email_agenda.setText("");
            txt_endereco_agenda.setText("");
            txt_observacao_agenda.setText("");
            readJTableAgenda();

        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Deseja Excluir ?", "SAIR", dialogButton);
        }
    }//GEN-LAST:event_btn_excluir_agendaActionPerformed

    private void btn_pesquisa_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesquisa_agendaActionPerformed
        readJTableForNome(txt_pesquisa_agenda.getText());
    }//GEN-LAST:event_btn_pesquisa_agendaActionPerformed

    private void btn_atualizar_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizar_agendaActionPerformed
        if (Table_agenda.getSelectedRow() != -1) {
            Agenda a = new Agenda();
            AgendaDAO adao = new AgendaDAO();

            a.setNome(txt_nome_agenda.getText());
            a.setTelefone(txt_telefone_agenda.getText());
            a.setEmail(txt_email_agenda.getText());
            a.setEndereco(txt_endereco_agenda.getText());
            a.setObservacao(txt_observacao_agenda.getText());
            adao.update(a);

            txt_nome_agenda.setText("");
            txt_email_agenda.setText("");
            txt_endereco_agenda.setText("");
            txt_telefone_agenda.setText("");
            txt_observacao_agenda.setText("");

            readJTableAgenda();

        }
    }//GEN-LAST:event_btn_atualizar_agendaActionPerformed

    private void Table_agendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Table_agendaMouseClicked
        if (Table_agenda.getSelectedRow() != -1) {
            txt_nome_agenda.setText(Table_agenda.getValueAt(Table_agenda.getSelectedRow(), 1).toString());
            txt_telefone_agenda.setText(Table_agenda.getValueAt(Table_agenda.getSelectedRow(), 4).toString());
            txt_email_agenda.setText(Table_agenda.getValueAt(Table_agenda.getSelectedRow(), 3).toString());
            txt_endereco_agenda.setText(Table_agenda.getValueAt(Table_agenda.getSelectedRow(), 2).toString());
            txt_observacao_agenda.setText(Table_agenda.getValueAt(Table_agenda.getSelectedRow(), 5).toString());
        }
    }//GEN-LAST:event_Table_agendaMouseClicked

    private void novoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_novoActionPerformed
        txt_nome_agenda.setText("");
        txt_email_agenda.setText("");
        txt_endereco_agenda.setText("");
        txt_telefone_agenda.setText("");
        txt_observacao_agenda.setText("");
    }//GEN-LAST:event_novoActionPerformed

    private void txt_pesquisa_agendaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisa_agendaKeyPressed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pesquisa_agendaKeyPressed

    private void txt_pesquisa_agendaKeyTyped(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_pesquisa_agendaKeyTyped
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_pesquisa_agendaKeyTyped

    private void txt_pesquisa_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_pesquisa_agendaActionPerformed

    }//GEN-LAST:event_txt_pesquisa_agendaActionPerformed

    private void txt_nome_agendaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nome_agendaMouseClicked
        if (txt_nome_agenda.getText().equals("Digite o Nome....")) {
            txt_nome_agenda.setText("");
        }
    }//GEN-LAST:event_txt_nome_agendaMouseClicked

    private void txt_nome_agendaFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nome_agendaFocusLost
        if (txt_nome_agenda.getText().equals("")) {
            txt_nome_agenda.setText("Digite o Nome....");
        }
    }//GEN-LAST:event_txt_nome_agendaFocusLost

    private void txt_nome_agendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_nome_agendaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_nome_agendaActionPerformed

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
            java.util.logging.Logger.getLogger(AgendaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(AgendaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(AgendaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(AgendaView.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new AgendaView().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JTable Table_agenda;
    private javax.swing.JButton btn_atualizar_agenda;
    private javax.swing.JButton btn_excluir_agenda;
    private javax.swing.JButton btn_pesquisa_agenda;
    private javax.swing.JButton btn_salvar_agenda;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JRadioButton jRadioButton1;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JButton novo;
    private javax.swing.JTextField txt_email_agenda;
    private javax.swing.JTextField txt_endereco_agenda;
    private javax.swing.JTextField txt_nome_agenda;
    private javax.swing.JTextField txt_observacao_agenda;
    private javax.swing.JTextField txt_pesquisa_agenda;
    private javax.swing.JFormattedTextField txt_telefone_agenda;
    // End of variables declaration//GEN-END:variables
}
