/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import static View.Principal3.combo_func;
import java.awt.Color;
import java.awt.event.KeyEvent;
import java.util.ArrayList;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.dao.EmpresaDAO;
import model.dao.FuncionarioDAO;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author William
 */
public class Cad_Func_View extends javax.swing.JFrame {

    /**
     * Creates new form Cad_Func_View
     */
    String genero;

    public Cad_Func_View() {
        initComponents();
        rad_M.setSelected(true);
        readJTable();
        AutoCompleteDecorator.decorate(combo_emp_cadFunc);
        EmpresaDAO edao = new EmpresaDAO();
        for (Empresa e : edao.ListaEmpresa()) {
            combo_emp_cadFunc.addItem(e);
        }
    }

    public void readJTable() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_func.getModel();
        modelo.setNumRows(0);
        tbl_func.setAutoResizeMode(tbl_func.AUTO_RESIZE_OFF);
        tbl_func.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
        tbl_func.getColumnModel().getColumn(1).setPreferredWidth(100);//Nome
        tbl_func.getColumnModel().getColumn(2).setPreferredWidth(90);//Telefone
        tbl_func.getColumnModel().getColumn(3).setPreferredWidth(180);//Email
        tbl_func.getColumnModel().getColumn(4).setPreferredWidth(100);//Genero
        tbl_func.getColumnModel().getColumn(5).setPreferredWidth(60);//ID Empresa
        tbl_func.getColumnModel().getColumn(6).setPreferredWidth(180);//Empresa
        tbl_func.getColumnModel().getColumn(7).setPreferredWidth(100);//Conexão
        tbl_func.getColumnModel().getColumn(8).setPreferredWidth(100);//AnyDesck

        FuncionarioDAO adao = new FuncionarioDAO();

        for (Funcionario f : adao.read()) {
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

    public void readJTable(Empresa empresa) {
        DefaultTableModel modelo = (DefaultTableModel) tbl_func.getModel();
        modelo.setNumRows(0);
        tbl_func.setAutoResizeMode(tbl_func.AUTO_RESIZE_OFF);
        tbl_func.getColumnModel().getColumn(0).setPreferredWidth(50);//ID
        tbl_func.getColumnModel().getColumn(1).setPreferredWidth(100);//Nome
        tbl_func.getColumnModel().getColumn(2).setPreferredWidth(90);//Telefone
        tbl_func.getColumnModel().getColumn(3).setPreferredWidth(180);//Email
        tbl_func.getColumnModel().getColumn(4).setPreferredWidth(100);//Genero
        tbl_func.getColumnModel().getColumn(5).setPreferredWidth(60);//ID Empresa
        tbl_func.getColumnModel().getColumn(6).setPreferredWidth(180);//Empresa
        tbl_func.getColumnModel().getColumn(7).setPreferredWidth(100);//Conexão
        tbl_func.getColumnModel().getColumn(8).setPreferredWidth(100);//AnyDesck

        FuncionarioDAO adao = new FuncionarioDAO();

        for (Funcionario f : adao.getData(empresa)) {
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

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        GrupoSexFunc = new javax.swing.ButtonGroup();
        jPanel1 = new javax.swing.JPanel();
        jLabel3 = new javax.swing.JLabel();
        jLabel1 = new javax.swing.JLabel();
        txt_nome = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        txt_email = new javax.swing.JTextField();
        jLabel4 = new javax.swing.JLabel();
        txt_fone = new javax.swing.JTextField();
        jPanel2 = new javax.swing.JPanel();
        rad_M = new javax.swing.JRadioButton();
        rad_F = new javax.swing.JRadioButton();
        btn_salvar = new javax.swing.JButton();
        btn_limpar = new javax.swing.JButton();
        jPanel3 = new javax.swing.JPanel();
        txt_pesquisa = new javax.swing.JTextField();
        btn_pesquisa = new javax.swing.JButton();
        jPanel4 = new javax.swing.JPanel();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_func = new javax.swing.JTable();
        jPanel5 = new javax.swing.JPanel();
        txt_conexao = new javax.swing.JTextField();
        jPanel6 = new javax.swing.JPanel();
        combo_emp_cadFunc = new javax.swing.JComboBox();
        btn_editar = new javax.swing.JButton();
        btn_Excluir = new javax.swing.JButton();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);

        jPanel1.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastro Funcionário"));

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel3.setText("Email:");

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel1.setText("Nome:");

        txt_nome.setText("Digite o nome....");
        txt_nome.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_nomeFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_nomeFocusLost(evt);
            }
        });
        txt_nome.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_nomeMouseClicked(evt);
            }
        });

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel2.setText("Telefone:");

        txt_email.setText("Digite o email....");
        txt_email.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_emailFocusGained(evt);
            }
        });
        txt_email.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_emailMouseClicked(evt);
            }
        });

        jLabel4.setForeground(new java.awt.Color(255, 0, 0));
        jLabel4.setText("*");

        txt_fone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_foneKeyReleased(evt);
            }
        });

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(jLabel1)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jLabel4))
                    .addComponent(jLabel2)
                    .addComponent(jLabel3))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(txt_fone, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_email, javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_nome, javax.swing.GroupLayout.DEFAULT_SIZE, 199, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel4))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_nome, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_fone, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_email, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 18, Short.MAX_VALUE))
        );

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Genêro"));

        GrupoSexFunc.add(rad_M);
        rad_M.setText("Masculino");

        GrupoSexFunc.add(rad_F);
        rad_F.setText("Femenino");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(rad_M)
                    .addComponent(rad_F))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(rad_M)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(rad_F)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_salvar.setText("Salvar");
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        btn_limpar.setText("Limpar");
        btn_limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparActionPerformed(evt);
            }
        });

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar"));

        btn_pesquisa.setText("Pesquisar");
        btn_pesquisa.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_pesquisaActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addComponent(txt_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 228, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(btn_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, 101, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                .addComponent(txt_pesquisa, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addComponent(btn_pesquisa))
        );

        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder("Funcionários"));

        tbl_func.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone", "Email", "Genêro", "ID Empresa", "Empresa", "Conexão", "AnyDesk"
            }
        ));
        tbl_func.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_funcMouseClicked(evt);
            }
        });
        tbl_func.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                tbl_funcKeyReleased(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_func);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                .addComponent(jScrollPane1)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 135, Short.MAX_VALUE)
        );

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Conexão"));

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(txt_conexao, javax.swing.GroupLayout.DEFAULT_SIZE, 120, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_conexao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Empresas"));

        combo_emp_cadFunc.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Selecione a empresa" }));
        combo_emp_cadFunc.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_emp_cadFuncActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addComponent(combo_emp_cadFunc, javax.swing.GroupLayout.PREFERRED_SIZE, 241, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_emp_cadFunc, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        btn_editar.setText("Editar");
        btn_editar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_editarActionPerformed(evt);
            }
        });

        btn_Excluir.setText("Excluir");
        btn_Excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_ExcluirActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(layout.createSequentialGroup()
                        .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(59, 59, 59)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(0, 0, Short.MAX_VALUE))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_editar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(9, 9, 9)
                        .addComponent(btn_Excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_limpar, javax.swing.GroupLayout.PREFERRED_SIZE, 70, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(387, 387, 387)))
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addComponent(jPanel5, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel2, javax.swing.GroupLayout.Alignment.LEADING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGap(18, 18, 18)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvar)
                    .addComponent(btn_limpar)
                    .addComponent(btn_editar)
                    .addComponent(btn_Excluir))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        if (txt_nome.getText().equals("Digite o nome....") || txt_nome.getText().equals("")) {
            txt_nome.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(null, "O campo Nome é Obrigatório", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_nome.setText("");
            txt_nome.requestFocus();
            return;
        }

        Funcionario f = new Funcionario();
        Empresa empresa = (Empresa) combo_emp_cadFunc.getSelectedItem();
        FuncionarioDAO dao = new FuncionarioDAO();

        f.setNome(txt_nome.getText());
        f.setTelefone(txt_fone.getText());
        f.setEmail(txt_email.getText());

        if (rad_M.isSelected()) {
            genero = "Masculino";
        }
        if (rad_F.isSelected()) {
            genero = "Femenino";
        }
        f.setGenero(genero);
        empresa.getId_emp();
        empresa.getNome_emp();
        f.setConexao(txt_conexao.getText());

        dao.create(f, empresa);
        //Cad_Func_View.combo_emp_cadFunc.setSelectedItem(combo_emp.getSelectedItem());
        Principal3.combo_emp.setSelectedItem(combo_emp_cadFunc.getSelectedItem());
        Principal3.combo_func.setSelectedItem(txt_fone.getText());
        txt_nome.setText("");
        txt_fone.setText("");
        txt_email.setText("");
        readJTable(empresa);

    }//GEN-LAST:event_btn_salvarActionPerformed

    private void tbl_funcMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_funcMouseClicked
        if (tbl_func.getSelectedRow() != -1) {
            btn_salvar.setEnabled(false);

            tbl_func.setAutoResizeMode(tbl_func.AUTO_RESIZE_OFF);
            tbl_func.getColumnModel().getColumn(0).setPreferredWidth(50);//ID
            tbl_func.getColumnModel().getColumn(1).setPreferredWidth(100);//Nome
            tbl_func.getColumnModel().getColumn(2).setPreferredWidth(90);//Telefone
            tbl_func.getColumnModel().getColumn(3).setPreferredWidth(180);//Email
            tbl_func.getColumnModel().getColumn(4).setPreferredWidth(100);//Genero
            tbl_func.getColumnModel().getColumn(5).setPreferredWidth(30);//ID Empresa
            tbl_func.getColumnModel().getColumn(6).setPreferredWidth(180);//Empresa
            tbl_func.getColumnModel().getColumn(7).setPreferredWidth(100);//Conexão
            tbl_func.getColumnModel().getColumn(8).setPreferredWidth(100);//AnyDesck

            txt_nome.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 1).toString());//Nome
            txt_fone.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 2).toString());//Telefone
            txt_email.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 3).toString());//Email
            if (tbl_func.getValueAt(tbl_func.getSelectedRow(), 4).toString().equals("Masculino")) {
                rad_M.setSelected(true);

            } else {
                rad_F.setSelected(true);
            }
            //jComboBoxData.addItem("NÃO HÁ ATESTADOS CADASTRADOS!");
            String empresa = tbl_func.getValueAt(tbl_func.getSelectedRow(), 6).toString();
            //combo_emp_cadFunc.setSelectedItem(empresa);

            txt_conexao.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 7).toString());//Conexão
        }
    }//GEN-LAST:event_tbl_funcMouseClicked

    private void btn_limparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparActionPerformed
        txt_email.setText("");
        txt_fone.setText("");
        txt_nome.setText("");
        txt_conexao.setText("");
        btn_salvar.setEnabled(true);
    }//GEN-LAST:event_btn_limparActionPerformed

    private void txt_nomeMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_nomeMouseClicked
        if (txt_nome.getText().equals("Digite o nome....")) {
            txt_nome.setText("");
        }
    }//GEN-LAST:event_txt_nomeMouseClicked

    private void txt_nomeFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nomeFocusLost
        if (txt_nome.getText().equals("")) {
            txt_nome.setText("Digite o nome....");
        }
    }//GEN-LAST:event_txt_nomeFocusLost

    public String AnyDesk(String conexao) {
        //JOptionPane.showMessageDialog(null, conexao);
        conexao = null;
        if (conexao == null) {
            conexao = String.valueOf("Sem Acesso");

        } else {
            conexao = String.valueOf("Com Acesso");
        }
        return conexao;
    }

    private void combo_emp_cadFuncActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_emp_cadFuncActionPerformed
        if (combo_emp_cadFunc.getSelectedItem().equals("Selecione a empresa")) {
            JOptionPane.showMessageDialog(null, "Escolha a empresa");
        } else {
            DefaultTableModel model = new DefaultTableModel();
            tbl_func.setAutoResizeMode(tbl_func.AUTO_RESIZE_OFF);
            tbl_func.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
            tbl_func.getColumnModel().getColumn(1).setPreferredWidth(100);//Nome
            tbl_func.getColumnModel().getColumn(2).setPreferredWidth(90);//Telefone
            tbl_func.getColumnModel().getColumn(3).setPreferredWidth(180);//Email
            tbl_func.getColumnModel().getColumn(4).setPreferredWidth(100);//Genero
            tbl_func.getColumnModel().getColumn(5).setPreferredWidth(30);//ID Empresa
            tbl_func.getColumnModel().getColumn(6).setPreferredWidth(180);//Empresa
            tbl_func.getColumnModel().getColumn(7).setPreferredWidth(100);//Conexão
            tbl_func.getColumnModel().getColumn(8).setPreferredWidth(100);//AnyDesck

            FuncionarioDAO fdao = new FuncionarioDAO();
            ArrayList<Funcionario> list = fdao.getData((Empresa) combo_emp_cadFunc.getSelectedItem());
            model.setColumnIdentifiers(new Object[]{"ID", "Nome", "telefone", "Email", "Genero", "ID Empresa", "Nome Empresa", "Conexão", "AnyDesk"});
            Object[] row = new Object[9];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_func();
                row[1] = list.get(i).getNome();
                row[2] = list.get(i).getTelefone();
                row[3] = list.get(i).getEmail();
                row[4] = list.get(i).getGenero();
                row[5] = list.get(i).getId_emp();
                row[6] = list.get(i).getNome_emp();
                row[7] = list.get(i).getConexao();
                row[8] = String.valueOf("Conexão");
                row[8] = AnyDesk(String.valueOf(list.get(i).getConexao()));
                model.addRow(row);
            }
            tbl_func.setModel(model);
        }
    }//GEN-LAST:event_combo_emp_cadFuncActionPerformed

    private void txt_nomeFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_nomeFocusGained
        if (txt_nome.getText().equals("Digite o nome....")) {
            txt_nome.setText("");
        }
    }//GEN-LAST:event_txt_nomeFocusGained

    private void txt_emailFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_emailFocusGained
        if (txt_email.getText().equals("Digite o email....")) {
            txt_email.setText("");
        }
    }//GEN-LAST:event_txt_emailFocusGained

    private void txt_emailMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_emailMouseClicked
        if (txt_email.getText().equals("Digite o email....")) {
            txt_email.setText("");
        }
    }//GEN-LAST:event_txt_emailMouseClicked

    private void btn_editarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_editarActionPerformed
        if (tbl_func.getSelectedRow() != -1) {
            Funcionario f = new Funcionario();
            FuncionarioDAO fdao = new FuncionarioDAO();
            Empresa empresa = (Empresa) combo_emp_cadFunc.getSelectedItem();

            f.setNome(txt_nome.getText());
            f.setTelefone(txt_fone.getText());
            f.setEmail(txt_email.getText());
            if (rad_M.isSelected()) {
                genero = "Masculino";
            }
            if (rad_F.isSelected()) {
                genero = "Femenino";
            }
            f.setGenero(genero);
            f.setConexao(txt_conexao.getText());
            f.setId_func((int) tbl_func.getValueAt(tbl_func.getSelectedRow(), 0));

            fdao.update(f);

            FuncionarioDAO func = new FuncionarioDAO();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(func.mostrarFuncionario(empresa.getId_emp()));
            combo_func.setModel(modelMunicipio);

            txt_conexao.setText("");
            txt_email.setText("");
            txt_fone.setText("");
            txt_nome.setText("");
            btn_editar.setEnabled(true);
            readJTable(empresa);
        }
    }//GEN-LAST:event_btn_editarActionPerformed

    private void txt_foneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_foneKeyReleased
        txt_fone.setText(Jm.JMascara.GetJmascaraFone(txt_fone.getText()));
    }//GEN-LAST:event_txt_foneKeyReleased

    private void btn_ExcluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_ExcluirActionPerformed
        if (tbl_func.getSelectedRow() != -1) {
            try {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                String nomeFunc = tbl_func.getValueAt(tbl_func.getSelectedRow(), 1).toString();
                int result = JOptionPane.showConfirmDialog(null, "Deseja excluir o Funcionário : " + nomeFunc, "SAIR", dialogButton);
                if (result == 0) {
                    Empresa empresa = (Empresa) combo_emp_cadFunc.getSelectedItem();
                    Funcionario f = new Funcionario();
                    FuncionarioDAO adao = new FuncionarioDAO();

                    f.setId_func((int) tbl_func.getValueAt(tbl_func.getSelectedRow(), 0));

                    adao.Delete(f);

                    txt_nome.setText("");
                    txt_email.setText("");
                    txt_fone.setText("");

                    readJTable(empresa);
                } else {
                    JOptionPane.showMessageDialog(null, "Selecione um funcionário!");
                }
            } catch (Exception e) {
                JOptionPane.showMessageDialog(this, e);
            }
        }
    }//GEN-LAST:event_btn_ExcluirActionPerformed

    private void tbl_funcKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_funcKeyReleased

        if (evt.getKeyCode() == KeyEvent.VK_UP || evt.getKeyCode() == KeyEvent.VK_DOWN) {
            tbl_func.setAutoResizeMode(tbl_func.AUTO_RESIZE_OFF);
            tbl_func.getColumnModel().getColumn(0).setPreferredWidth(50);//ID
            tbl_func.getColumnModel().getColumn(1).setPreferredWidth(100);//Nome
            tbl_func.getColumnModel().getColumn(2).setPreferredWidth(90);//Telefone
            tbl_func.getColumnModel().getColumn(3).setPreferredWidth(180);//Email
            tbl_func.getColumnModel().getColumn(4).setPreferredWidth(100);//Genero
            tbl_func.getColumnModel().getColumn(5).setPreferredWidth(30);//ID Empresa
            tbl_func.getColumnModel().getColumn(6).setPreferredWidth(180);//Empresa
            tbl_func.getColumnModel().getColumn(7).setPreferredWidth(100);//Conexão
            tbl_func.getColumnModel().getColumn(8).setPreferredWidth(100);//AnyDesck

            txt_nome.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 1).toString());//Nome
            txt_fone.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 2).toString());//Telefone
            txt_email.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 3).toString());//Email
            if (tbl_func.getValueAt(tbl_func.getSelectedRow(), 4).toString().equals("Masculino")) {
                rad_M.setSelected(true);
            } else {
                rad_F.setSelected(true);
            }
            txt_conexao.setText(tbl_func.getValueAt(tbl_func.getSelectedRow(), 7).toString());//Conexão
        }
    }//GEN-LAST:event_tbl_funcKeyReleased

    private void btn_pesquisaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_pesquisaActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_pesquisaActionPerformed

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
            java.util.logging.Logger.getLogger(Cad_Func_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cad_Func_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cad_Func_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cad_Func_View.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cad_Func_View().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.ButtonGroup GrupoSexFunc;
    private javax.swing.JButton btn_Excluir;
    private javax.swing.JButton btn_editar;
    private javax.swing.JButton btn_limpar;
    private javax.swing.JButton btn_pesquisa;
    private javax.swing.JButton btn_salvar;
    public static javax.swing.JComboBox combo_emp_cadFunc;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JRadioButton rad_F;
    private javax.swing.JRadioButton rad_M;
    private javax.swing.JTable tbl_func;
    private javax.swing.JTextField txt_conexao;
    private javax.swing.JTextField txt_email;
    private javax.swing.JTextField txt_fone;
    private javax.swing.JTextField txt_nome;
    private javax.swing.JTextField txt_pesquisa;
    // End of variables declaration//GEN-END:variables
}
