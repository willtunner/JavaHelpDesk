package View;

import Jm.JMascara;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.event.WindowListener;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Vector;
import javax.swing.DefaultComboBoxModel;
import javax.swing.ImageIcon;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Empresa;
import model.dao.EmpresaDAO;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Suporte-07
 */
public class Cad_emp_view2 extends javax.swing.JFrame implements WindowListener {

    PreparedStatement pst = null;
    ResultSet rs = null;
    //connection.Conecta conecta = new connection.Conecta();

    /**
     * Creates new form Cad_emp_view2
     */
    public Cad_emp_view2() {

        initComponents();
        ChecaTextoAny();
        
        addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                EmpresaDAO emp = new EmpresaDAO();
                DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(emp.mostrarEmpresas());
                Principal3.combo_emp.setModel(modelEstado);
                super.windowClosing(e);
            }
        });
        
        DefaultTableModel modelo = (DefaultTableModel) tbl_empresa.getModel();
        tbl_empresa.setRowSorter(new TableRowSorter(modelo));
        readJTableEmp();
        popuptabble();
        btn_atualiza.setEnabled(false);
        btn_excluir.setEnabled(false);
        lb_ID.setEnabled(false);

        EmpresaDAO edao = new EmpresaDAO();
        //Chama as empresas no combobox empresa do frame inicial
        for (Empresa e : edao.ListaEmpresa()) {
            Combo_Cad_emp.addItem(e);
        }
        AutoCompleteDecorator.decorate(Combo_Cad_emp);
    }

    //Metodo para checar o se tem algo na conexão muda o botão
    public void ChecaTextoAny() {
        String anyScef = txt_con_scef.getText().trim();
        String anynfce = txt_con_scef.getText().trim();
        String anynfe = txt_con_nfe.getText().trim();

        if (anyScef.trim().equals("")) {
            btnAnyScef.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
            btnAnyScef.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            btnAnyScef.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
            btnAnyScef.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (anynfce.trim().equals("")) {
            btnAnyNfce.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
            btnAnyNfce.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            btnAnyNfce.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
            btnAnyNfce.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

        if (anynfe.trim().equals("")) {
            btnAnyNfe.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk bw.png")));
            btnAnyNfe.setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
        } else {
            btnAnyNfe.setIcon(new ImageIcon(getClass().getResource("/imagem/anydesk.png")));
            btnAnyNfe.setCursor(new Cursor(Cursor.HAND_CURSOR));
        }

    }

    private void readJTableEmp() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_empresa.getModel();
        modelo.setNumRows(0);
        tbl_empresa.setAutoResizeMode(tbl_empresa.AUTO_RESIZE_OFF);
        tbl_empresa.setModel(modelo);
        tbl_empresa.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
        tbl_empresa.getColumnModel().getColumn(1).setPreferredWidth(170);//EMPRESA
        tbl_empresa.getColumnModel().getColumn(2).setPreferredWidth(100);//TELEFONE
        tbl_empresa.getColumnModel().getColumn(3).setPreferredWidth(120);//CNPJ
        tbl_empresa.getColumnModel().getColumn(4).setPreferredWidth(250);//OBS
        tbl_empresa.getColumnModel().getColumn(5).setPreferredWidth(100);//IP SCEF
        tbl_empresa.getColumnModel().getColumn(6).setPreferredWidth(100);//MAC SCEF
        tbl_empresa.getColumnModel().getColumn(7).setPreferredWidth(100);//CON SCEF
        tbl_empresa.getColumnModel().getColumn(8).setPreferredWidth(100);//IP NFCE
        tbl_empresa.getColumnModel().getColumn(9).setPreferredWidth(100);//MAC NFCE
        tbl_empresa.getColumnModel().getColumn(10).setPreferredWidth(100);//CON NFCE
        tbl_empresa.getColumnModel().getColumn(11).setPreferredWidth(100);//IP NFE
        tbl_empresa.getColumnModel().getColumn(12).setPreferredWidth(100);//MAC NFE
        tbl_empresa.getColumnModel().getColumn(13).setPreferredWidth(100);//CON NFE
        tbl_empresa.getColumnModel().getColumn(14).setPreferredWidth(100);//MOBILE
        tbl_empresa.getColumnModel().getColumn(15).setPreferredWidth(100);//COLETOR
        tbl_empresa.getColumnModel().getColumn(16).setPreferredWidth(100);//ESTOQUE
        tbl_empresa.getColumnModel().getColumn(17).setPreferredWidth(100);//NFCE
        tbl_empresa.getColumnModel().getColumn(18).setPreferredWidth(100);//NFE
        tbl_empresa.getColumnModel().getColumn(19).setPreferredWidth(100);//SOSSEG
        tbl_empresa.getColumnModel().getColumn(10).setPreferredWidth(100);//CHECKOUT

        EmpresaDAO adao = new EmpresaDAO();

        for (Empresa e : adao.ListaEmpresa()) {
            modelo.addRow(new Object[]{
                e.getId_emp(),
                e.getNome_emp(),
                e.getTelefone(),
                e.getCnpj(),
                e.getObs(),
                e.getIp_scef(),
                e.getMac_scef(),
                e.getCon_scef(),
                e.getIp_nfce(),
                e.getMac_nfce(),
                e.getCon_nfce(),
                e.getIp_nfe(),
                e.getMac_nfe(),
                e.getCon_nfe(),
                e.getIp_mobile(),
                e.getIp_coletor(),
                e.getV_estoque(),
                e.getV_nfce(),
                e.getV_nfe(),
                e.getV_sisseg(),
                e.getV_checkout()
            });
        }
    }

    private void popuptabble() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Visualizar", new ImageIcon(getClass().getResource("/imagem/System/icon_Pro_Orange.png")));
        menuItem.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                JOptionPane.showMessageDialog(null, "Visualizar", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
            }
        });
        popupMenu.add(menuItem);
        tbl_empresa.setComponentPopupMenu(popupMenu);
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jScrollPane1 = new javax.swing.JScrollPane();
        jPanel1 = new javax.swing.JPanel();
        jPanel2 = new javax.swing.JPanel();
        btn_excluir = new javax.swing.JButton();
        jLabel17 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_obs = new javax.swing.JTextPane();
        btn_Salvar = new javax.swing.JButton();
        jLabel1 = new javax.swing.JLabel();
        txt_telefone = new javax.swing.JTextField();
        btn_limparcampos = new javax.swing.JButton();
        txt_empresa = new javax.swing.JTextField();
        lb_ID = new javax.swing.JTextField();
        jLabel2 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        txt_cnpj = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        btn_atualiza = new javax.swing.JButton();
        jLabel20 = new javax.swing.JLabel();
        jPanel4 = new javax.swing.JPanel();
        jLabel5 = new javax.swing.JLabel();
        jLabel4 = new javax.swing.JLabel();
        txt_mac_scef = new javax.swing.JTextField();
        jLabel7 = new javax.swing.JLabel();
        txt_mac_nfce = new javax.swing.JTextField();
        jLabel8 = new javax.swing.JLabel();
        jLabel9 = new javax.swing.JLabel();
        txt_mac_nfe = new javax.swing.JTextField();
        jLabel10 = new javax.swing.JLabel();
        jLabel11 = new javax.swing.JLabel();
        jLabel12 = new javax.swing.JLabel();
        jLabel16 = new javax.swing.JLabel();
        txt_con_scef = new javax.swing.JTextField();
        txt_con_nfce = new javax.swing.JTextField();
        txt_con_nfe = new javax.swing.JTextField();
        txt_ip_scef = new javax.swing.JTextField();
        btnAnyScef = new javax.swing.JToggleButton();
        btnAnyNfce = new javax.swing.JToggleButton();
        btnAnyNfe = new javax.swing.JToggleButton();
        txt_ip_nfce = new javax.swing.JTextField();
        txt_ip_nfe = new javax.swing.JTextField();
        txt_mobile = new javax.swing.JTextField();
        txt_ip_coletor = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        AtualizarTabela = new javax.swing.JButton();
        Combo_Cad_emp = new javax.swing.JComboBox();
        jPanel5 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_empresa = new javax.swing.JTable();
        jPanel6 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel13 = new javax.swing.JLabel();
        txt_v_estoque = new javax.swing.JTextField();
        jPanel8 = new javax.swing.JPanel();
        jLabel14 = new javax.swing.JLabel();
        txt_v_nfce = new javax.swing.JTextField();
        jPanel9 = new javax.swing.JPanel();
        jLabel15 = new javax.swing.JLabel();
        txt_v_nfe = new javax.swing.JTextField();
        jPanel10 = new javax.swing.JPanel();
        jLabel18 = new javax.swing.JLabel();
        txt_v_sisseg = new javax.swing.JTextField();
        jPanel11 = new javax.swing.JPanel();
        jLabel19 = new javax.swing.JLabel();
        txt_v_checkout = new javax.swing.JTextField();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setPreferredSize(new java.awt.Dimension(680, 850));

        jScrollPane1.setBackground(new java.awt.Color(0, 102, 102));
        jScrollPane1.setForeground(new java.awt.Color(0, 102, 102));

        jPanel1.setBackground(new java.awt.Color(0, 102, 102));
        jPanel1.setPreferredSize(new java.awt.Dimension(680, 640));

        jPanel2.setBackground(new java.awt.Color(0, 0, 0));
        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Cadastro empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel2.setOpaque(false);

        btn_excluir.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_excluir_60x_1.png"))); // NOI18N
        btn_excluir.setBorderPainted(false);
        btn_excluir.setContentAreaFilled(false);
        btn_excluir.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_excluir_60x_2.png"))); // NOI18N
        btn_excluir.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_excluirActionPerformed(evt);
            }
        });

        jLabel17.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel17.setForeground(new java.awt.Color(255, 255, 255));
        jLabel17.setText("Observação:");

        jScrollPane2.setViewportView(txt_obs);

        btn_Salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_gravar_60x_1.png"))); // NOI18N
        btn_Salvar.setBorderPainted(false);
        btn_Salvar.setContentAreaFilled(false);
        btn_Salvar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_gravar_60x_2.png"))); // NOI18N
        btn_Salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_SalvarActionPerformed(evt);
            }
        });

        jLabel1.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel1.setForeground(new java.awt.Color(255, 255, 255));
        jLabel1.setText("Empresa:");

        txt_telefone.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_telefoneKeyReleased(evt);
            }
        });

        btn_limparcampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_novo_60x_1.png"))); // NOI18N
        btn_limparcampos.setBorderPainted(false);
        btn_limparcampos.setContentAreaFilled(false);
        btn_limparcampos.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_novo_60x_2.png"))); // NOI18N
        btn_limparcampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_limparcamposActionPerformed(evt);
            }
        });

        txt_empresa.setPreferredSize(new java.awt.Dimension(6, 25));

        jLabel2.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel2.setForeground(new java.awt.Color(255, 255, 255));
        jLabel2.setText("Telefone:");

        jLabel6.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 255, 255));
        jLabel6.setText("ID:");

        txt_cnpj.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyReleased(java.awt.event.KeyEvent evt) {
                txt_cnpjKeyReleased(evt);
            }
        });

        jLabel3.setFont(new java.awt.Font("Tahoma", 3, 10)); // NOI18N
        jLabel3.setForeground(new java.awt.Color(255, 255, 255));
        jLabel3.setText("CNPJ:");

        btn_atualiza.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_editar_60x_1.png"))); // NOI18N
        btn_atualiza.setBorderPainted(false);
        btn_atualiza.setContentAreaFilled(false);
        btn_atualiza.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_editar_60x_2.png"))); // NOI18N
        btn_atualiza.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_atualizaActionPerformed(evt);
            }
        });

        jLabel20.setForeground(new java.awt.Color(255, 0, 0));
        jLabel20.setText("*");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel17)
                            .addComponent(jLabel3)
                            .addComponent(jLabel2)
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel6)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(lb_ID, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGap(718, 718, 718))
                    .addGroup(jPanel2Layout.createSequentialGroup()
                        .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                .addGroup(jPanel2Layout.createSequentialGroup()
                                    .addComponent(btn_Salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_atualiza, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_excluir, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                    .addComponent(btn_limparcampos, javax.swing.GroupLayout.PREFERRED_SIZE, 60, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addComponent(jScrollPane2)
                                .addComponent(txt_cnpj)
                                .addComponent(txt_telefone)
                                .addComponent(txt_empresa, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                            .addGroup(jPanel2Layout.createSequentialGroup()
                                .addComponent(jLabel1)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jLabel20)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel6)
                    .addComponent(lb_ID, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel1)
                    .addComponent(jLabel20))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_empresa, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_telefone, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(4, 4, 4)
                .addComponent(jLabel3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_cnpj, javax.swing.GroupLayout.PREFERRED_SIZE, 25, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel17)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btn_Salvar, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_atualiza, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_excluir, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(btn_limparcampos, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );

        jPanel4.setBackground(new java.awt.Color(0, 0, 0));
        jPanel4.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Dados do Servidor:", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 10), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel4.setOpaque(false);

        jLabel5.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 255, 255));
        jLabel5.setText("MAC Servidor SCEF:");

        jLabel4.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel4.setForeground(new java.awt.Color(255, 255, 255));
        jLabel4.setText("IP Servidor SCEF:");

        jLabel7.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 255, 255));
        jLabel7.setText("IP Servidor NFCE");

        jLabel8.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 255, 255));
        jLabel8.setText("MAC Servidor NFCE");

        jLabel9.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel9.setForeground(new java.awt.Color(255, 255, 255));
        jLabel9.setText("IP Servidor NFE");

        jLabel10.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel10.setForeground(new java.awt.Color(255, 255, 255));
        jLabel10.setText("MAC Servidor NFE");

        jLabel11.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel11.setForeground(new java.awt.Color(255, 255, 255));
        jLabel11.setText("IP Serviço MOBILE");

        jLabel12.setFont(new java.awt.Font("Tahoma", 1, 12)); // NOI18N
        jLabel12.setForeground(new java.awt.Color(255, 255, 255));
        jLabel12.setText("IP Serviço COLETOR");

        jLabel16.setFont(new java.awt.Font("Tahoma", 1, 11)); // NOI18N
        jLabel16.setForeground(new java.awt.Color(255, 255, 255));
        jLabel16.setText("Conexão:");

        btnAnyScef.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/anydesk bw.png"))); // NOI18N
        btnAnyScef.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAnyScef.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnyScefActionPerformed(evt);
            }
        });

        btnAnyNfce.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/anydesk bw.png"))); // NOI18N
        btnAnyNfce.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAnyNfce.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnyNfceActionPerformed(evt);
            }
        });

        btnAnyNfe.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/anydesk bw.png"))); // NOI18N
        btnAnyNfe.setCursor(new java.awt.Cursor(java.awt.Cursor.DEFAULT_CURSOR));
        btnAnyNfe.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnAnyNfeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel4)
                        .addGap(205, 205, 205)
                        .addComponent(jLabel16)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addComponent(txt_ip_scef, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(jLabel7)
                                    .addComponent(txt_ip_nfce, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                        .addComponent(txt_mac_scef, javax.swing.GroupLayout.DEFAULT_SIZE, 145, Short.MAX_VALUE)
                                        .addComponent(txt_mac_nfce))
                                    .addComponent(jLabel8)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                    .addGroup(jPanel4Layout.createSequentialGroup()
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(jLabel9)
                                                .addGap(54, 54, 54))
                                            .addGroup(jPanel4Layout.createSequentialGroup()
                                                .addComponent(txt_ip_nfe)
                                                .addGap(27, 27, 27)))
                                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                            .addComponent(txt_mac_nfe, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE)
                                            .addComponent(jLabel10)))
                                    .addComponent(jLabel11)
                                    .addComponent(txt_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(txt_con_nfe)
                            .addComponent(txt_con_scef)
                            .addComponent(txt_con_nfce)
                            .addComponent(jLabel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(txt_ip_coletor))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)))
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(btnAnyScef, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnyNfce, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnAnyNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(16, 16, 16))
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel4)
                                    .addComponent(jLabel5))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_ip_scef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_mac_scef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addComponent(jLabel16)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_con_scef, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(btnAnyScef, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createSequentialGroup()
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(jLabel7)
                                    .addComponent(jLabel8))
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                    .addComponent(txt_mac_nfce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_con_nfce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                    .addComponent(txt_ip_nfce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                            .addComponent(btnAnyNfce, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(jLabel9)
                            .addComponent(jLabel10))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(txt_con_nfe, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_mac_nfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addComponent(txt_ip_nfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                    .addComponent(btnAnyNfe, javax.swing.GroupLayout.PREFERRED_SIZE, 30, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel11)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_mobile, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jLabel12)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_ip_coletor, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(26, 26, 26))))
        );

        jPanel3.setBackground(new java.awt.Color(0, 0, 0));
        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Pesquisar", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 0, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel3.setForeground(new java.awt.Color(255, 255, 255));
        jPanel3.setFont(new java.awt.Font("Tahoma", 1, 10)); // NOI18N
        jPanel3.setOpaque(false);

        AtualizarTabela.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_atualizar_1_100x.png"))); // NOI18N
        AtualizarTabela.setBorderPainted(false);
        AtualizarTabela.setContentAreaFilled(false);
        AtualizarTabela.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/Botoes/btn_atualizar_2_100x.png"))); // NOI18N
        AtualizarTabela.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AtualizarTabelaActionPerformed(evt);
            }
        });

        Combo_Cad_emp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empresas" }));
        Combo_Cad_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Combo_Cad_empActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(23, 23, 23)
                .addComponent(AtualizarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(Combo_Cad_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGap(21, 21, 21)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(Combo_Cad_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(AtualizarTabela, javax.swing.GroupLayout.PREFERRED_SIZE, 20, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(36, Short.MAX_VALUE))
        );

        jPanel5.setBackground(new java.awt.Color(0, 0, 0));
        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Empresa", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel5.setOpaque(false);

        tbl_empresa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "ID", "Nome", "Telefone", "Cnpj", "Obs", "IP Scef", "Mac Scef", "Conexão Scef", "IP NFCE", "Mac NFCE", "Conexão NFCE", "IP NFE", "Mac NFE", "Conexão NFE", "Mobile", "Coletor", "V. Estoque", "V. Nfce", "V. Nfe", "V. Sisseg", "V. Checkout"
            }
        ));
        tbl_empresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_empresaMouseClicked(evt);
            }
        });
        tbl_empresa.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                tbl_empresaKeyPressed(evt);
            }
        });
        jScrollPane3.setViewportView(tbl_empresa);

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3)
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 237, Short.MAX_VALUE)
        );

        jPanel6.setBackground(new java.awt.Color(0, 0, 0));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder(null, "Versões bancos", javax.swing.border.TitledBorder.DEFAULT_JUSTIFICATION, javax.swing.border.TitledBorder.DEFAULT_POSITION, new java.awt.Font("Tahoma", 1, 11), new java.awt.Color(255, 255, 255))); // NOI18N
        jPanel6.setOpaque(false);

        jPanel7.setPreferredSize(new java.awt.Dimension(200, 120));

        jLabel13.setBackground(new java.awt.Color(102, 102, 102));
        jLabel13.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel13.setForeground(new java.awt.Color(255, 255, 255));
        jLabel13.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel13.setText("Banco Estoque");
        jLabel13.setOpaque(true);

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel13, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_v_estoque)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel13, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_v_estoque, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel8.setPreferredSize(new java.awt.Dimension(200, 120));

        jLabel14.setBackground(new java.awt.Color(51, 102, 0));
        jLabel14.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel14.setForeground(new java.awt.Color(255, 255, 255));
        jLabel14.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel14.setText("Banco Nfce");
        jLabel14.setOpaque(true);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel14, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_v_nfce)
                .addContainerGap())
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jLabel14, javax.swing.GroupLayout.PREFERRED_SIZE, 37, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_v_nfce, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 25, Short.MAX_VALUE))
        );

        jPanel9.setPreferredSize(new java.awt.Dimension(200, 120));

        jLabel15.setBackground(new java.awt.Color(102, 0, 255));
        jLabel15.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel15.setForeground(new java.awt.Color(255, 255, 255));
        jLabel15.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel15.setText("Banco Nfe");
        jLabel15.setOpaque(true);

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_v_nfe, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 145, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addComponent(jLabel15, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_v_nfe, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 27, Short.MAX_VALUE))
        );

        jPanel10.setPreferredSize(new java.awt.Dimension(144, 100));

        jLabel18.setBackground(new java.awt.Color(153, 153, 255));
        jLabel18.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel18.setForeground(new java.awt.Color(255, 255, 255));
        jLabel18.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel18.setText("Banco Sisseg");
        jLabel18.setOpaque(true);

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_v_sisseg, javax.swing.GroupLayout.PREFERRED_SIZE, 125, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
            .addComponent(jLabel18, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addComponent(jLabel18, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_v_sisseg, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(27, Short.MAX_VALUE))
        );

        jPanel11.setPreferredSize(new java.awt.Dimension(144, 100));

        jLabel19.setBackground(new java.awt.Color(255, 102, 0));
        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel19.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel19.setText("Banco Checkout");
        jLabel19.setOpaque(true);

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel19, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, 144, Short.MAX_VALUE)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(txt_v_checkout)
                .addContainerGap())
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addComponent(jLabel19, javax.swing.GroupLayout.PREFERRED_SIZE, 34, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(txt_v_checkout, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                            .addGroup(jPanel6Layout.createSequentialGroup()
                                .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 144, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, 100, Short.MAX_VALUE)
                    .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(javax.swing.GroupLayout.Alignment.LEADING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, 292, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(jPanel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(57, 57, 57))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel1Layout.createSequentialGroup()
                        .addComponent(jPanel4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(11, 11, 11)
                        .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                        .addComponent(jPanel2, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel3.getAccessibleContext().setAccessibleName("");

        jScrollPane1.setViewportView(jPanel1);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 1180, javax.swing.GroupLayout.PREFERRED_SIZE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 8, Short.MAX_VALUE))
        );

        setSize(new java.awt.Dimension(1196, 689));
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void btn_excluirActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_excluirActionPerformed
        if (tbl_empresa.getSelectedRow() != -1) {
            Empresa e = new Empresa();
            EmpresaDAO adao = new EmpresaDAO();

            e.setId_emp((int) tbl_empresa.getValueAt(tbl_empresa.getSelectedRow(), 0));
            e.setNome_emp(tbl_empresa.getValueAt(tbl_empresa.getSelectedRow(), 1).toString());

            adao.Delete(e);

            txt_empresa.setText("");
            txt_telefone.setText("");
            txt_cnpj.setText("");
            txt_obs.setText("");
            txt_con_nfce.setText("");
            txt_con_nfe.setText("");
            txt_con_scef.setText("");
            txt_ip_coletor.setText("");
            txt_ip_nfce.setText("");
            txt_ip_nfe.setText("");
            txt_ip_scef.setText("");
            txt_mac_nfce.setText("");
            txt_mac_nfe.setText("");
            txt_mac_scef.setText("");
            txt_mobile.setText("");
            txt_v_estoque.setText("");
            txt_v_nfce.setText("");
            txt_v_nfe.setText("");
            txt_v_sisseg.setText("");
            txt_v_checkout.setText("");
            readJTableEmp();
        } else {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Deseja Excluir ?", "SAIR", dialogButton);
        }
    }//GEN-LAST:event_btn_excluirActionPerformed

    private void btn_SalvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_SalvarActionPerformed
        if (txt_empresa.getText().equals("")) {
            txt_empresa.setBorder(new LineBorder(Color.RED));
            JOptionPane.showMessageDialog(null, "Coloque ao menos o nome!");
            return;
        } else {
            txt_empresa.setBorder(new LineBorder(Color.lightGray));
        }
        try {
            Empresa e = new Empresa();
            EmpresaDAO dao = new EmpresaDAO();

            e.setNome_emp(txt_empresa.getText().trim());
            e.setTelefone(txt_telefone.getText().trim());
            e.setCnpj(txt_cnpj.getText().trim());
            e.setObs(txt_obs.getText().trim());
            e.setIp_scef(txt_ip_scef.getText().trim());
            e.setMac_scef(txt_mac_scef.getText().trim());
            e.setCon_scef(txt_con_scef.getText().trim());
            e.setIp_nfce(txt_con_nfce.getText().trim());
            e.setMac_nfce(txt_mac_nfce.getText().trim());
            e.setCon_nfce(txt_con_nfce.getText().trim());
            e.setIp_nfe(txt_ip_nfe.getText().trim());
            e.setMac_nfe(txt_mac_nfe.getText().trim());
            e.setCon_nfe(txt_con_nfe.getText().trim());
            e.setIp_mobile(txt_mobile.getText().trim());
            e.setIp_coletor(txt_ip_coletor.getText().trim());
            e.setV_estoque(txt_v_estoque.getText().trim());
            e.setV_nfce(txt_v_nfce.getText().trim());
            e.setV_nfe(txt_v_nfe.getText().trim());
            e.setV_sisseg(txt_v_sisseg.getText().trim());
            e.setV_checkout(txt_v_checkout.getText().trim());
            dao.create(e);

            txt_empresa.setText("");
            txt_telefone.setText("");
            txt_cnpj.setText("");
            txt_obs.setText("");
            txt_con_nfce.setText("");
            txt_con_nfe.setText("");
            txt_con_scef.setText("");
            txt_ip_coletor.setText("");
            txt_ip_nfce.setText("");
            txt_ip_nfe.setText("");
            txt_ip_scef.setText("");
            txt_mac_nfce.setText("");
            txt_mac_nfe.setText("");
            txt_mac_scef.setText("");
            txt_mobile.setText("");
            txt_v_estoque.setText("");
            txt_v_nfce.setText("");
            txt_v_nfe.setText("");
            txt_v_sisseg.setText("");
            txt_v_checkout.setText("");

            readJTableEmp();
        } catch (Exception e) {
            System.out.println("Deu erro x9 =P");
        }
    }//GEN-LAST:event_btn_SalvarActionPerformed

    private void btn_limparcamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_limparcamposActionPerformed
        //Limpa os Campos
        lb_ID.setText("");
        lb_ID.setEnabled(false);
        txt_empresa.setText("");
        txt_telefone.setText("");
        txt_cnpj.setText("");
        txt_obs.setText("");
        txt_con_nfce.setText("");
        txt_con_nfe.setText("");
        txt_con_scef.setText("");
        txt_ip_coletor.setText("");
        txt_ip_nfce.setText("");
        txt_ip_nfe.setText("");
        txt_ip_scef.setText("");
        txt_mac_nfce.setText("");
        txt_mac_nfe.setText("");
        txt_mac_scef.setText("");
        txt_mobile.setText("");
        txt_v_estoque.setText("");
        txt_v_nfce.setText("");
        txt_v_nfe.setText("");
        txt_v_sisseg.setText("");
        txt_v_checkout.setText("");
        //Habilita o salvar
        btn_Salvar.setEnabled(true);

        //Desabilita os botões.
        btn_atualiza.setEnabled(false);
        btn_excluir.setEnabled(false);
    }//GEN-LAST:event_btn_limparcamposActionPerformed

    private void btn_atualizaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_atualizaActionPerformed
        if (tbl_empresa.getSelectedRow() != -1) {
            Empresa e = new Empresa();

            EmpresaDAO adao = new EmpresaDAO();

            e.setNome_emp(txt_empresa.getText());
            e.setTelefone(txt_telefone.getText());
            e.setCnpj(txt_cnpj.getText());
            e.setObs(txt_obs.getText());
            e.setIp_scef(txt_ip_scef.getText());
            e.setMac_scef(txt_mac_scef.getText());
            e.setCon_scef(txt_con_scef.getText());
            e.setIp_nfce(txt_ip_nfce.getText());
            e.setMac_nfce(txt_mac_nfce.getText());
            e.setCon_nfce(txt_con_nfce.getText());
            e.setIp_nfe(txt_ip_nfe.getText());
            e.setMac_nfe(txt_mac_nfe.getText());
            e.setCon_nfe(txt_con_nfe.getText());
            e.setIp_mobile(txt_mobile.getText());
            e.setIp_coletor(txt_ip_coletor.getText());
            e.setV_estoque(txt_v_estoque.getText());
            e.setV_nfce(txt_v_nfce.getText());
            e.setV_nfe(txt_v_nfe.getText());
            e.setV_sisseg(txt_v_sisseg.getText());
            e.setV_checkout(txt_v_checkout.getText());
            e.setId_emp((int) tbl_empresa.getValueAt(tbl_empresa.getSelectedRow(), 0));
            adao.update(e);

            lb_ID.setText("");
            txt_empresa.setText("");
            txt_telefone.setText("");
            txt_cnpj.setText("");
            txt_obs.setText("");
            txt_con_nfce.setText("");
            txt_con_nfe.setText("");
            txt_con_scef.setText("");
            txt_ip_coletor.setText("");
            txt_ip_nfce.setText("");
            txt_ip_nfe.setText("");
            txt_ip_scef.setText("");
            txt_mac_nfce.setText("");
            txt_mac_nfe.setText("");
            txt_mac_scef.setText("");
            txt_mobile.setText("");
            txt_v_estoque.setText("");
            txt_v_nfce.setText("");
            txt_v_nfe.setText("");
            txt_v_sisseg.setText("");
            txt_v_checkout.setText("");

            readJTableEmp();
        }
    }//GEN-LAST:event_btn_atualizaActionPerformed

    private void AtualizarTabelaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AtualizarTabelaActionPerformed
        readJTableEmp();
    }//GEN-LAST:event_AtualizarTabelaActionPerformed

    private void Combo_Cad_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Combo_Cad_empActionPerformed
        if (Combo_Cad_emp.getSelectedItem().equals("Empresas")) {
            readJTableEmp();
        } else {
            DefaultTableModel modelo = (DefaultTableModel) tbl_empresa.getModel();
            modelo.setNumRows(0);
            tbl_empresa.setAutoResizeMode(tbl_empresa.AUTO_RESIZE_OFF);
            tbl_empresa.setModel(modelo);
            tbl_empresa.getColumnModel().getColumn(0).setPreferredWidth(30);//ID
            tbl_empresa.getColumnModel().getColumn(1).setPreferredWidth(170);//EMPRESA
            tbl_empresa.getColumnModel().getColumn(2).setPreferredWidth(100);//TELEFONE
            tbl_empresa.getColumnModel().getColumn(3).setPreferredWidth(120);//CNPJ
            tbl_empresa.getColumnModel().getColumn(4).setPreferredWidth(250);//OBS
            tbl_empresa.getColumnModel().getColumn(5).setPreferredWidth(100);//IP SCEF
            tbl_empresa.getColumnModel().getColumn(6).setPreferredWidth(100);//MAC SCEF
            tbl_empresa.getColumnModel().getColumn(7).setPreferredWidth(100);//CON SCEF
            tbl_empresa.getColumnModel().getColumn(8).setPreferredWidth(100);//IP NFCE
            tbl_empresa.getColumnModel().getColumn(9).setPreferredWidth(100);//MAC NFCE
            tbl_empresa.getColumnModel().getColumn(10).setPreferredWidth(100);//CON NFCE
            tbl_empresa.getColumnModel().getColumn(11).setPreferredWidth(100);//IP NFE
            tbl_empresa.getColumnModel().getColumn(12).setPreferredWidth(100);//MAC NFE
            tbl_empresa.getColumnModel().getColumn(13).setPreferredWidth(100);//CON NFE
            tbl_empresa.getColumnModel().getColumn(14).setPreferredWidth(100);//MOBILE
            tbl_empresa.getColumnModel().getColumn(15).setPreferredWidth(100);//COLETOR
            tbl_empresa.getColumnModel().getColumn(16).setPreferredWidth(100);//V.Estoque
            tbl_empresa.getColumnModel().getColumn(17).setPreferredWidth(100);//V.Nfce
            tbl_empresa.getColumnModel().getColumn(18).setPreferredWidth(100);//V.Nfe
            tbl_empresa.getColumnModel().getColumn(19).setPreferredWidth(100);//V.Sisseg
            tbl_empresa.getColumnModel().getColumn(20).setPreferredWidth(100);//V.Checkout

            EmpresaDAO fdao = new EmpresaDAO();
            Vector<Empresa> list = fdao.getEmp((Empresa) Combo_Cad_emp.getSelectedItem());
            modelo.setColumnIdentifiers(new Object[]{"ID", "Nome", "telefone", "Cnpj", "Observação", "Ip Scef", "Mac Scef", "Conexão Scef", "Ip Nfce", "Mac Nfce", "Conexão Nfce", "Ip Nfe", "Mac Nfe", "Conexão Nfe", "Mobile", "Coletor", "V_Estoque", "V_Nfce", "V_Nfe", "V_Sisseg", "V_Checkout"});
            Object[] row = new Object[21];
            for (int i = 0; i < list.size(); i++) {
                row[0] = list.get(i).getId_emp();
                row[1] = list.get(i).getNome_emp();
                row[2] = list.get(i).getTelefone();
                row[3] = list.get(i).getCnpj();
                row[4] = list.get(i).getObs();
                row[5] = list.get(i).getIp_scef();
                row[6] = list.get(i).getMac_scef();
                row[7] = list.get(i).getCon_scef();
                row[8] = list.get(i).getIp_nfce();
                row[9] = list.get(i).getMac_nfce();
                row[10] = list.get(i).getCon_nfce();
                row[11] = list.get(i).getIp_nfe();
                row[12] = list.get(i).getMac_nfe();
                row[13] = list.get(i).getCon_nfe();
                row[14] = list.get(i).getIp_mobile();
                row[15] = list.get(i).getIp_coletor();
                row[16] = list.get(i).getV_estoque();
                row[17] = list.get(i).getV_nfce();
                row[18] = list.get(i).getV_nfe();
                row[19] = list.get(i).getV_sisseg();
                row[20] = list.get(i).getV_checkout();
                modelo.addRow(row);
            }
            tbl_empresa.setModel(modelo);
        }
    }//GEN-LAST:event_Combo_Cad_empActionPerformed

    private void tbl_empresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_empresaMouseClicked
        btn_atualiza.setEnabled(true);
        btn_excluir.setEnabled(true);
        btn_Salvar.setEnabled(false);
        lb_ID.setEnabled(true);

        Empresa e = new Empresa();
        EmpresaDAO edao = new EmpresaDAO();

        int index = tbl_empresa.getSelectedRow();

        e = edao.ListaEmpresa().get(index);
        
        
        
        lb_ID.setText(String.valueOf(e.getId_emp()));
        txt_empresa.setText(e.getNome_emp());
        txt_telefone.setText(e.getTelefone());
        txt_cnpj.setText(e.getCnpj());
        txt_obs.setText(e.getObs());
        txt_ip_scef.setText(e.getIp_scef());
        txt_mac_scef.setText(e.getMac_nfce());
        txt_con_scef.setText(e.getCon_scef());
        txt_ip_nfce.setText(e.getIp_nfce());
        txt_mac_nfce.setText(e.getIp_nfce());
        txt_mac_nfce.setText(e.getMac_nfce());
        txt_con_nfce.setText(e.getCon_nfce());
        txt_ip_nfe.setText(e.getIp_nfe());
        txt_mac_nfe.setText(e.getMac_nfe());
        txt_con_nfe.setText(e.getCon_nfe());
        txt_mobile.setText(e.getIp_mobile());
        txt_ip_coletor.setText(e.getIp_coletor());
        txt_v_estoque.setText(e.getV_estoque());
        txt_v_checkout.setText(e.getV_checkout());
        txt_v_nfce.setText(e.getV_nfce());
        txt_v_nfe.setText(e.getV_nfe());
        txt_v_sisseg.setText(e.getV_sisseg());
        
        ChecaTextoAny();
    }//GEN-LAST:event_tbl_empresaMouseClicked

    private void tbl_empresaKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_tbl_empresaKeyPressed
        btn_atualiza.setEnabled(true);
        btn_excluir.setEnabled(true);
        btn_Salvar.setEnabled(false);
        lb_ID.setEnabled(true);

        Empresa e = new Empresa();
        EmpresaDAO edao = new EmpresaDAO();

        int index = tbl_empresa.getSelectedRow();

        e = edao.ListaEmpresa().get(index);
        lb_ID.setText(String.valueOf(e.getId_emp()));
        txt_empresa.setText(e.getNome_emp());
        txt_telefone.setText(e.getTelefone());
        txt_cnpj.setText(e.getCnpj());
        txt_obs.setText(e.getObs());
        txt_ip_scef.setText(e.getIp_scef());
        txt_mac_scef.setText(e.getMac_nfce());
        txt_con_scef.setText(e.getCon_scef());
        txt_ip_nfce.setText(e.getIp_nfce());
        txt_mac_nfce.setText(e.getIp_nfce());
        txt_mac_nfce.setText(e.getMac_nfce());
        txt_con_nfce.setText(e.getCon_nfce());
        txt_ip_nfe.setText(e.getIp_nfe());
        txt_mac_nfe.setText(e.getMac_nfe());
        txt_con_nfe.setText(e.getCon_nfe());
        txt_mobile.setText(e.getIp_mobile());
        txt_ip_coletor.setText(e.getIp_coletor());
        txt_v_estoque.setText(e.getV_estoque());
        txt_v_checkout.setText(e.getV_checkout());
        txt_v_nfce.setText(e.getV_nfce());
        txt_v_nfe.setText(e.getV_nfe());
        txt_v_sisseg.setText(e.getV_sisseg());
    }//GEN-LAST:event_tbl_empresaKeyPressed

    private void btnAnyScefActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnyScefActionPerformed
        if (tbl_empresa.getSelectedRow() != -1) {
            EmpresaDAO edao = new EmpresaDAO();
            String any = tbl_empresa.getValueAt(tbl_empresa.getSelectedRow(), 7).toString();
            edao.Conexao(any);
        }
    }//GEN-LAST:event_btnAnyScefActionPerformed

    private void btnAnyNfceActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnyNfceActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btnAnyNfceActionPerformed

    private void btnAnyNfeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnAnyNfeActionPerformed
        if (tbl_empresa.getSelectedRow() != -1) {
            EmpresaDAO edao = new EmpresaDAO();
            String any = tbl_empresa.getValueAt(tbl_empresa.getSelectedRow(), 13).toString();
            edao.Conexao(any);
        }
    }//GEN-LAST:event_btnAnyNfeActionPerformed

    private void txt_cnpjKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_cnpjKeyReleased
        txt_cnpj.setText(JMascara.GetJmascaraCpfCnpj(txt_cnpj.getText()));
    }//GEN-LAST:event_txt_cnpjKeyReleased

    private void txt_telefoneKeyReleased(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_telefoneKeyReleased
        txt_telefone.setText(JMascara.GetJmascaraFone(txt_telefone.getText()));
    }//GEN-LAST:event_txt_telefoneKeyReleased

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
            java.util.logging.Logger.getLogger(Cad_emp_view2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Cad_emp_view2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Cad_emp_view2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Cad_emp_view2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Cad_emp_view2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AtualizarTabela;
    public static javax.swing.JComboBox Combo_Cad_emp;
    private javax.swing.JToggleButton btnAnyNfce;
    private javax.swing.JToggleButton btnAnyNfe;
    private javax.swing.JToggleButton btnAnyScef;
    private javax.swing.JButton btn_Salvar;
    private javax.swing.JButton btn_atualiza;
    private javax.swing.JButton btn_excluir;
    private javax.swing.JButton btn_limparcampos;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel13;
    private javax.swing.JLabel jLabel14;
    private javax.swing.JLabel jLabel15;
    private javax.swing.JLabel jLabel16;
    private javax.swing.JLabel jLabel17;
    private javax.swing.JLabel jLabel18;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel4;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel6;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JPanel jPanel8;
    private javax.swing.JPanel jPanel9;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JTextField lb_ID;
    private javax.swing.JTable tbl_empresa;
    private javax.swing.JTextField txt_cnpj;
    private javax.swing.JTextField txt_con_nfce;
    private javax.swing.JTextField txt_con_nfe;
    private javax.swing.JTextField txt_con_scef;
    private javax.swing.JTextField txt_empresa;
    private javax.swing.JTextField txt_ip_coletor;
    private javax.swing.JTextField txt_ip_nfce;
    private javax.swing.JTextField txt_ip_nfe;
    private javax.swing.JTextField txt_ip_scef;
    private javax.swing.JTextField txt_mac_nfce;
    private javax.swing.JTextField txt_mac_nfe;
    private javax.swing.JTextField txt_mac_scef;
    private javax.swing.JTextField txt_mobile;
    private javax.swing.JTextPane txt_obs;
    private javax.swing.JTextField txt_telefone;
    private javax.swing.JTextField txt_v_checkout;
    private javax.swing.JTextField txt_v_estoque;
    private javax.swing.JTextField txt_v_nfce;
    private javax.swing.JTextField txt_v_nfe;
    private javax.swing.JTextField txt_v_sisseg;
    // End of variables declaration//GEN-END:variables

    @Override
    public void windowOpened(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosing(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowClosed(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowIconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeiconified(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowActivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void windowDeactivated(WindowEvent e) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}
