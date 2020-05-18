package View;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JFrame;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPopupMenu;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import model.bean.Chamado;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.bean.Ramais;
import model.dao.ChamadoDAO;
import model.dao.EmpresaDAO;
import model.dao.FuncionarioDAO;
import model.dao.RamaisDAO;
import org.jdesktop.swingx.autocomplete.AutoCompleteDecorator;

/**
 *
 * @author Suporte-07
 */
public class Principal_new extends javax.swing.JFrame {

    //Variaveis para movimentar o painel principal com o mouse
    int xMouse;
    int yMouse;

    public Principal_new() {
        initComponents();
        RadEmp.setSelected(true);
        popuptabble();
        readJTable();
        readJTableChamado();

        EmpresaDAO edao = new EmpresaDAO();


        //Chama as empresas no combobox empresa do frame inicial
        for (Empresa e : edao.ListaEmpresa()) {
            combo_emp.addItem(e);
            combo_emp_clientes.addItem(e);
        }

        //Insere o autocompletar no combobox empresa
        AutoCompleteDecorator.decorate(combo_emp);

        //Pega o Usuario que logou no sistema
        userlogado.setVisible(false);
        lb_user.setVisible(false);
    }

    Chamado_view chamadoView = new Chamado_view();

    //Função de quando clica com o botão direito no tabela.
    public void popuptabble() {
        JPopupMenu popupMenu = new JPopupMenu();
        JMenuItem menuItem = new JMenuItem("Visualizar", new ImageIcon(getClass().getResource("/imagem/System/icon_Pro_Orange.png")));
        menuItem.addActionListener(new ActionListener() {

            @Override
            public void actionPerformed(ActionEvent e) {
                //JOptionPane.showMessageDialog(null, "Visualizar", "Mensagem", JOptionPane.INFORMATION_MESSAGE);
                int index = tbl_Chamado.getSelectedRow();
                TableModel model = tbl_Chamado.getModel();

                String Titulo = model.getValueAt(index, 0).toString();
                String Conteudo = model.getValueAt(index, 1).toString();
                String Empresa = model.getValueAt(index, 2).toString();
                String Funcionario = model.getValueAt(index, 3).toString();
                String Tags = model.getValueAt(index, 4).toString();
                String Solucao = model.getValueAt(index, 5).toString();
                String Conexao = model.getValueAt(index, 6).toString();

                chamadoView.setVisible(true);
                chamadoView.pack();
                chamadoView.setLocationRelativeTo(null);
                chamadoView.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

                chamadoView.txt_tit.setText(Titulo);
                chamadoView.txt_desc.setText(Conteudo);
                chamadoView.txt_emp.setText(Empresa);
                chamadoView.txt_func.setText(Funcionario);
                chamadoView.txt_solucao.setText(Solucao);
                chamadoView.txt_conex.setText(Conexao);
                chamadoView.txt_tags.setText(Tags);
                //chamadoView.txt_.setText(Funcionario);
            }
        });
        popupMenu.add(menuItem);
        tbl_Chamado.setComponentPopupMenu(popupMenu);
    }

    //Popula o jtable Empresa
    public void readJTable() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Ramais.getModel();
        modelo.setNumRows(0);
        RamaisDAO rdao = new RamaisDAO();

        for (Ramais r : rdao.Listar()) {
            modelo.addRow(new Object[]{
                r.getNome(),
                r.getRamal()
            });
        }
    }

    //Popula o jtable Chamado
    public void readJTableChamado() {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.ListarChamado()) {
            modelo.addRow(new Object[]{
                c.getTitulo(),
                c.getTexto(),
                c.getSolucao(),
                c.getNome_emp(),
                c.getNome(),
                c.getTags(),
                c.getConexao()
            });
        }
    }

    public void pesquisar_chamado(String nome_emp, String data) {
        DefaultTableModel modelo = (DefaultTableModel) tbl_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.PesquisarChamado(nome_emp, data)) {
            modelo.addRow(new Object[]{
                c.getTitulo(),
                c.getTexto(),
                c.getSolucao(),
                c.getNome_emp(),
                c.getNome(),
                c.getTags(),
                c.getConexao()
            });
        }
    }

    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        Pesquisar_Radbutton = new javax.swing.ButtonGroup();
        Principal = new javax.swing.JPanel();
        Menu = new javax.swing.JPanel();
        Chamado = new javax.swing.JLabel();
        Agenda = new javax.swing.JLabel();
        Cadastrar1 = new javax.swing.JLabel();
        Relatorio = new javax.swing.JLabel();
        PRO = new javax.swing.JLabel();
        Sites = new javax.swing.JLabel();
        Btn_Chamado = new javax.swing.JButton();
        Btn_Agenda = new javax.swing.JButton();
        Btn_Cadastrar = new javax.swing.JButton();
        Btn_Relatorio = new javax.swing.JButton();
        Btn_PRO = new javax.swing.JButton();
        Btn_Sites = new javax.swing.JButton();
        Menu_lateral = new javax.swing.JLabel();
        lb_user = new javax.swing.JLabel();
        BarraSuperior = new javax.swing.JPanel();
        Btn_SlideMenu = new javax.swing.JToggleButton();
        Btn_fechar = new javax.swing.JButton();
        MenuBar = new javax.swing.JLabel();
        Panel_Base = new javax.swing.JPanel();
        Tab_Chamado = new javax.swing.JTabbedPane();
        Chamados = new javax.swing.JPanel();
        combo_emp = new javax.swing.JComboBox();
        combo_func = new javax.swing.JComboBox();
        jLabel1 = new javax.swing.JLabel();
        jLabel2 = new javax.swing.JLabel();
        jLabel3 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_Solucao = new javax.swing.JEditorPane();
        jPanel7 = new javax.swing.JPanel();
        txt_tag1 = new javax.swing.JTextField();
        txt_tag2 = new javax.swing.JTextField();
        txt_tag3 = new javax.swing.JTextField();
        txt_tag5 = new javax.swing.JTextField();
        txt_tag4 = new javax.swing.JTextField();
        Btn_Limpar = new javax.swing.JButton();
        btn_salvar_chamado = new javax.swing.JButton();
        txt_titulo_chamado = new javax.swing.JTextField();
        jLabel5 = new javax.swing.JLabel();
        jLabel6 = new javax.swing.JLabel();
        jLabel7 = new javax.swing.JLabel();
        jLabel8 = new javax.swing.JLabel();
        txt_telefone_chamado2 = new javax.swing.JFormattedTextField();
        jLabel9 = new javax.swing.JLabel();
        jScrollPane4 = new javax.swing.JScrollPane();
        txt_chamado_desc1 = new javax.swing.JEditorPane();
        jLabel10 = new javax.swing.JLabel();
        txt_conexao = new javax.swing.JTextField();
        jLabel11 = new javax.swing.JLabel();
        jButton1 = new javax.swing.JButton();
        jButton2 = new javax.swing.JButton();
        jLabel12 = new javax.swing.JLabel();
        Lb_data = new javax.swing.JLabel();
        Pesq_chammado = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        txt_Pesquisa_Chamado = new javax.swing.JTextField();
        btn_Pesquisa_chamado = new javax.swing.JButton();
        jPanel2 = new javax.swing.JPanel();
        RadEmp = new javax.swing.JRadioButton();
        jRadioButton2 = new javax.swing.JRadioButton();
        jRadioButton3 = new javax.swing.JRadioButton();
        jRadioButton4 = new javax.swing.JRadioButton();
        jScrollPane1 = new javax.swing.JScrollPane();
        tbl_Chamado = new javax.swing.JTable();
        Ramais = new javax.swing.JPanel();
        jPanel12 = new javax.swing.JPanel();
        jScrollPane3 = new javax.swing.JScrollPane();
        tbl_Ramais = new javax.swing.JTable();
        jPanel1 = new javax.swing.JPanel();
        jPanel3 = new javax.swing.JPanel();
        combo_emp_clientes = new javax.swing.JComboBox();
        jScrollPane5 = new javax.swing.JScrollPane();
        tbl_empresa = new javax.swing.JTable();

        setDefaultCloseOperation(javax.swing.WindowConstants.EXIT_ON_CLOSE);
        setUndecorated(true);
        addMouseMotionListener(new java.awt.event.MouseMotionAdapter() {
            public void mouseDragged(java.awt.event.MouseEvent evt) {
                formMouseDragged(evt);
            }
        });
        addMouseListener(new java.awt.event.MouseAdapter() {
            public void mousePressed(java.awt.event.MouseEvent evt) {
                formMousePressed(evt);
            }
        });

        Principal.setBackground(new java.awt.Color(236, 239, 245));
        Principal.setLayout(null);

        Menu.setBackground(new java.awt.Color(0, 102, 102));
        Menu.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Chamado.setBackground(new java.awt.Color(204, 204, 204));
        Chamado.setFont(new java.awt.Font("Arial Black", 1, 16)); // NOI18N
        Chamado.setForeground(new java.awt.Color(153, 153, 153));
        Chamado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Chamado.setText("Chamados");
        Menu.add(Chamado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 60, 120, 20));

        Agenda.setBackground(new java.awt.Color(102, 102, 102));
        Agenda.setFont(new java.awt.Font("Arial Black", 1, 16)); // NOI18N
        Agenda.setForeground(new java.awt.Color(153, 153, 153));
        Agenda.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Agenda.setText("Agenda");
        Menu.add(Agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 110, 120, 20));

        Cadastrar1.setBackground(new java.awt.Color(102, 102, 102));
        Cadastrar1.setFont(new java.awt.Font("Arial Black", 1, 16)); // NOI18N
        Cadastrar1.setForeground(new java.awt.Color(153, 153, 153));
        Cadastrar1.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Cadastrar1.setText("Cadastrar");
        Menu.add(Cadastrar1, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 170, 120, 20));

        Relatorio.setBackground(new java.awt.Color(102, 102, 102));
        Relatorio.setFont(new java.awt.Font("Arial Black", 1, 16)); // NOI18N
        Relatorio.setForeground(new java.awt.Color(153, 153, 153));
        Relatorio.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Relatorio.setText("Relatórios");
        Menu.add(Relatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 230, 120, 20));

        PRO.setBackground(new java.awt.Color(102, 102, 102));
        PRO.setFont(new java.awt.Font("Arial Black", 1, 16)); // NOI18N
        PRO.setForeground(new java.awt.Color(153, 153, 153));
        PRO.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        PRO.setText("Sites");
        Menu.add(PRO, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 330, 120, 20));

        Sites.setBackground(new java.awt.Color(102, 102, 102));
        Sites.setFont(new java.awt.Font("Arial Black", 1, 16)); // NOI18N
        Sites.setForeground(new java.awt.Color(153, 153, 153));
        Sites.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Sites.setText("P.R.Os");
        Menu.add(Sites, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 280, 120, 20));

        Btn_Chamado.setBackground(new java.awt.Color(204, 153, 0));
        Btn_Chamado.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_chamado.png"))); // NOI18N
        Btn_Chamado.setToolTipText("Chamados");
        Btn_Chamado.setBorderPainted(false);
        Btn_Chamado.setContentAreaFilled(false);
        Btn_Chamado.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_chamado_orange.png"))); // NOI18N
        Btn_Chamado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_ChamadoMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_ChamadoMouseExited(evt);
            }
        });
        Btn_Chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_ChamadoActionPerformed(evt);
            }
        });
        Menu.add(Btn_Chamado, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 50, 40, 40));

        Btn_Agenda.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_agenda.png"))); // NOI18N
        Btn_Agenda.setToolTipText("Agenda");
        Btn_Agenda.setBorderPainted(false);
        Btn_Agenda.setContentAreaFilled(false);
        Btn_Agenda.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_agenda_orange.png"))); // NOI18N
        Btn_Agenda.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_AgendaMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_AgendaMouseExited(evt);
            }
        });
        Btn_Agenda.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_AgendaActionPerformed(evt);
            }
        });
        Menu.add(Btn_Agenda, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 100, 40, 40));

        Btn_Cadastrar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar.png"))); // NOI18N
        Btn_Cadastrar.setToolTipText("Cadastrar");
        Btn_Cadastrar.setBorderPainted(false);
        Btn_Cadastrar.setContentAreaFilled(false);
        Btn_Cadastrar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Cadastrar_Orange.png"))); // NOI18N
        Btn_Cadastrar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_CadastrarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_CadastrarMouseExited(evt);
            }
        });
        Btn_Cadastrar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_CadastrarActionPerformed(evt);
            }
        });
        Menu.add(Btn_Cadastrar, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 150, 40, 50));

        Btn_Relatorio.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_relatorio.png"))); // NOI18N
        Btn_Relatorio.setToolTipText("Relatorios");
        Btn_Relatorio.setBorderPainted(false);
        Btn_Relatorio.setContentAreaFilled(false);
        Btn_Relatorio.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_relatorio_Relatorio.png"))); // NOI18N
        Btn_Relatorio.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_RelatorioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_RelatorioMouseExited(evt);
            }
        });
        Menu.add(Btn_Relatorio, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 210, 40, 50));

        Btn_PRO.setBackground(new java.awt.Color(102, 102, 102));
        Btn_PRO.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Pro.png"))); // NOI18N
        Btn_PRO.setToolTipText("P.R.Os");
        Btn_PRO.setBorderPainted(false);
        Btn_PRO.setContentAreaFilled(false);
        Btn_PRO.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_Pro_Orange.png"))); // NOI18N
        Btn_PRO.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_PROMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_PROMouseExited(evt);
            }
        });
        Menu.add(Btn_PRO, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 270, 40, -1));

        Btn_Sites.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_sites.png"))); // NOI18N
        Btn_Sites.setToolTipText("Sites");
        Btn_Sites.setBorderPainted(false);
        Btn_Sites.setContentAreaFilled(false);
        Btn_Sites.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/icon_sites_Orange.png"))); // NOI18N
        Btn_Sites.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                Btn_SitesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                Btn_SitesMouseExited(evt);
            }
        });
        Btn_Sites.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SitesActionPerformed(evt);
            }
        });
        Menu.add(Btn_Sites, new org.netbeans.lib.awtextra.AbsoluteConstraints(120, 320, 40, 50));
        Menu.add(Menu_lateral, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, -30, -1, 460));

        lb_user.setFont(new java.awt.Font("Tahoma", 2, 14)); // NOI18N
        lb_user.setForeground(new java.awt.Color(102, 102, 102));
        lb_user.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        lb_user.setText("Suporte");
        Menu.add(lb_user, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 380, 170, 20));

        userlogado.setFont(new java.awt.Font("Myanmar Text", 3, 18)); // NOI18N
        userlogado.setForeground(new java.awt.Color(255, 150, 34));
        userlogado.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        Menu.add(userlogado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 400, 170, 30));

        Principal.add(Menu);
        Menu.setBounds(-110, 50, 170, 450);

        BarraSuperior.setBackground(new java.awt.Color(0, 102, 102));
        BarraSuperior.setForeground(new java.awt.Color(153, 153, 255));
        BarraSuperior.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Btn_SlideMenu.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Btn_Menu.png"))); // NOI18N
        Btn_SlideMenu.setBorderPainted(false);
        Btn_SlideMenu.setContentAreaFilled(false);
        Btn_SlideMenu.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Btn_Menu_Orange.png"))); // NOI18N
        Btn_SlideMenu.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_SlideMenuActionPerformed(evt);
            }
        });
        BarraSuperior.add(Btn_SlideMenu, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 60, 50));

        Btn_fechar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Btn_Fechar.png"))); // NOI18N
        Btn_fechar.setToolTipText("Fechar");
        Btn_fechar.setBorderPainted(false);
        Btn_fechar.setContentAreaFilled(false);
        Btn_fechar.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/System/Btn_Fechar_Orange.png"))); // NOI18N
        Btn_fechar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_fecharActionPerformed(evt);
            }
        });
        BarraSuperior.add(Btn_fechar, new org.netbeans.lib.awtextra.AbsoluteConstraints(790, 0, 60, 50));
        BarraSuperior.add(MenuBar, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, -1, 40));

        Principal.add(BarraSuperior);
        BarraSuperior.setBounds(0, 0, 850, 50);

        Panel_Base.setOpaque(false);
        Panel_Base.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        Chamados.setBorder(javax.swing.BorderFactory.createTitledBorder("Chamados Rápido"));
        Chamados.setOpaque(false);
        Chamados.setLayout(new org.netbeans.lib.awtextra.AbsoluteLayout());

        combo_emp.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empresas" }));
        combo_emp.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                combo_empFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                combo_empFocusLost(evt);
            }
        });
        combo_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_empMouseClicked(evt);
            }
        });
        combo_emp.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_empActionPerformed(evt);
            }
        });
        Chamados.add(combo_emp, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 40, 180, -1));

        combo_func.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Funcionarios" }));
        combo_func.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_funcItemStateChanged(evt);
            }
        });
        combo_func.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_funcActionPerformed(evt);
            }
        });
        Chamados.add(combo_func, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 40, 130, -1));

        jLabel1.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel1.setText("Empresa:");
        Chamados.add(jLabel1, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 20, 50, -1));

        jLabel2.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel2.setText("Cliente:");
        Chamados.add(jLabel2, new org.netbeans.lib.awtextra.AbsoluteConstraints(210, 20, -1, -1));

        jLabel3.setFont(new java.awt.Font("Tahoma", 2, 12)); // NOI18N
        jLabel3.setText("Telefone:");
        Chamados.add(jLabel3, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 20, 80, -1));

        jScrollPane2.setViewportView(txt_Solucao);

        Chamados.add(jScrollPane2, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 240, 490, 80));

        jPanel7.setBorder(javax.swing.BorderFactory.createTitledBorder("Tags de erro"));

        Btn_Limpar.setText("Limpar");
        Btn_Limpar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                Btn_LimparActionPerformed(evt);
            }
        });

        btn_salvar_chamado.setText("Salvar");
        btn_salvar_chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvar_chamadoActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_tag1, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tag2, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tag3, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tag5, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_tag4, javax.swing.GroupLayout.PREFERRED_SIZE, 80, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(Btn_Limpar)
                    .addComponent(btn_salvar_chamado))
                .addContainerGap(18, Short.MAX_VALUE))
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(txt_tag1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tag2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tag3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tag5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tag4, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 21, Short.MAX_VALUE)
                .addComponent(btn_salvar_chamado)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(Btn_Limpar))
        );

        Chamados.add(jPanel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 70, 120, 220));

        txt_titulo_chamado.setText("Digite o titulo do Chamado...");
        txt_titulo_chamado.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_titulo_chamadoFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_titulo_chamadoFocusLost(evt);
            }
        });
        txt_titulo_chamado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_titulo_chamadoMouseClicked(evt);
            }
        });
        txt_titulo_chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                txt_titulo_chamadoActionPerformed(evt);
            }
        });
        Chamados.add(txt_titulo_chamado, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 70, 490, -1));

        jLabel5.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel5.setForeground(new java.awt.Color(255, 0, 51));
        jLabel5.setText("*");
        Chamados.add(jLabel5, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 70, -1, -1));

        jLabel6.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel6.setForeground(new java.awt.Color(255, 0, 51));
        jLabel6.setText("*");
        Chamados.add(jLabel6, new org.netbeans.lib.awtextra.AbsoluteConstraints(80, 20, 10, -1));

        jLabel7.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel7.setForeground(new java.awt.Color(255, 0, 51));
        jLabel7.setText("*");
        Chamados.add(jLabel7, new org.netbeans.lib.awtextra.AbsoluteConstraints(260, 20, 10, -1));

        jLabel8.setFont(new java.awt.Font("Tahoma", 0, 12)); // NOI18N
        jLabel8.setForeground(new java.awt.Color(255, 0, 51));
        jLabel8.setText("*");
        Chamados.add(jLabel8, new org.netbeans.lib.awtextra.AbsoluteConstraints(510, 120, 10, -1));

        try {
            txt_telefone_chamado2.setFormatterFactory(new javax.swing.text.DefaultFormatterFactory(new javax.swing.text.MaskFormatter("(##) #####-####")));
        } catch (java.text.ParseException ex) {
            ex.printStackTrace();
        }
        Chamados.add(txt_telefone_chamado2, new org.netbeans.lib.awtextra.AbsoluteConstraints(350, 40, 160, -1));

        jLabel9.setText("Descrição do Chamado:");
        Chamados.add(jLabel9, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 100, -1, -1));

        jScrollPane4.setViewportView(txt_chamado_desc1);

        Chamados.add(jScrollPane4, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 120, 490, 90));

        jLabel10.setText("Solução do problema:");
        Chamados.add(jLabel10, new org.netbeans.lib.awtextra.AbsoluteConstraints(20, 220, -1, -1));
        Chamados.add(txt_conexao, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 40, 120, -1));

        jLabel11.setText("Conexão:");
        Chamados.add(jLabel11, new org.netbeans.lib.awtextra.AbsoluteConstraints(540, 20, -1, -1));

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/emp_02.png"))); // NOI18N
        jButton1.setToolTipText("Cadastrar Empresa");
        jButton1.setBorderPainted(false);
        jButton1.setContentAreaFilled(false);
        jButton1.setPressedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/emp_03.png"))); // NOI18N
        jButton1.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/emp_01.png"))); // NOI18N
        jButton1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton1ActionPerformed(evt);
            }
        });
        Chamados.add(jButton1, new org.netbeans.lib.awtextra.AbsoluteConstraints(100, 10, 30, 40));

        jButton2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/User_03.png"))); // NOI18N
        jButton2.setToolTipText("Cadastrar Funcionario");
        jButton2.setBorderPainted(false);
        jButton2.setContentAreaFilled(false);
        jButton2.setRolloverIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/User_02.png"))); // NOI18N
        jButton2.setSelectedIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/User_01.png"))); // NOI18N
        jButton2.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jButton2ActionPerformed(evt);
            }
        });
        Chamados.add(jButton2, new org.netbeans.lib.awtextra.AbsoluteConstraints(290, 10, 30, 40));

        jLabel12.setText("Hora:");
        Chamados.add(jLabel12, new org.netbeans.lib.awtextra.AbsoluteConstraints(180, 400, -1, -1));

        Lb_data.setText("Data:");
        Chamados.add(Lb_data, new org.netbeans.lib.awtextra.AbsoluteConstraints(10, 400, -1, -1));

        Tab_Chamado.addTab("Chamados", Chamados);

        Pesq_chammado.setOpaque(false);

        jPanel5.setBorder(javax.swing.BorderFactory.createTitledBorder("Campos de busca - Chamado"));
        jPanel5.setOpaque(false);

        jLabel4.setText("Nome:");

        btn_Pesquisa_chamado.setText("Pesquisar");
        btn_Pesquisa_chamado.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_Pesquisa_chamadoActionPerformed(evt);
            }
        });

        jPanel2.setBorder(javax.swing.BorderFactory.createTitledBorder("Pesquisar por:"));

        Pesquisar_Radbutton.add(RadEmp);
        RadEmp.setText("Empresa");

        Pesquisar_Radbutton.add(jRadioButton2);
        jRadioButton2.setText("Conteudo");

        Pesquisar_Radbutton.add(jRadioButton3);
        jRadioButton3.setText("Funcionario");

        Pesquisar_Radbutton.add(jRadioButton4);
        jRadioButton4.setText("Tags");

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addGap(17, 17, 17)
                .addComponent(RadEmp)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton3)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jRadioButton4)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel2Layout.createSequentialGroup()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(RadEmp)
                    .addComponent(jRadioButton2)
                    .addComponent(jRadioButton3)
                    .addComponent(jRadioButton4))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel4)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addComponent(txt_Pesquisa_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 229, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_Pesquisa_chamado, javax.swing.GroupLayout.PREFERRED_SIZE, 108, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Pesquisa_Chamado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_Pesquisa_chamado))
                .addGap(0, 0, Short.MAX_VALUE))
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel2, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(16, Short.MAX_VALUE))
        );

        jScrollPane1.setOpaque(false);

        tbl_Chamado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null}
            },
            new String [] {
                "Titulo", "Descrição", "Solução", "Empresa", "Funcionario", "Tags", "Conexão"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, true, false, false, false, true
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tbl_Chamado.setOpaque(false);
        tbl_Chamado.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_ChamadoMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tbl_Chamado);

        javax.swing.GroupLayout Pesq_chammadoLayout = new javax.swing.GroupLayout(Pesq_chammado);
        Pesq_chammado.setLayout(Pesq_chammadoLayout);
        Pesq_chammadoLayout.setHorizontalGroup(
            Pesq_chammadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(Pesq_chammadoLayout.createSequentialGroup()
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 768, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(17, Short.MAX_VALUE))
        );
        Pesq_chammadoLayout.setVerticalGroup(
            Pesq_chammadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(Pesq_chammadoLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.PREFERRED_SIZE, 213, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(100, Short.MAX_VALUE))
        );

        Tab_Chamado.addTab("Pesquisa Chamados", Pesq_chammado);

        Ramais.setOpaque(false);

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Listas de Ramais da empresa"));

        tbl_Ramais.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null},
                {null, null},
                {null, null},
                {null, null}
            },
            new String [] {
                "Nome", "Ramal"
            }
        ));
        jScrollPane3.setViewportView(tbl_Ramais);

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 773, Short.MAX_VALUE)
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addComponent(jScrollPane3, javax.swing.GroupLayout.PREFERRED_SIZE, 336, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout RamaisLayout = new javax.swing.GroupLayout(Ramais);
        Ramais.setLayout(RamaisLayout);
        RamaisLayout.setHorizontalGroup(
            RamaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        RamaisLayout.setVerticalGroup(
            RamaisLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(RamaisLayout.createSequentialGroup()
                .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        Tab_Chamado.addTab("Lista de ramais", Ramais);

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Empresas"));

        combo_emp_clientes.setModel(new javax.swing.DefaultComboBoxModel(new String[] { "Empresas" }));
        combo_emp_clientes.setOpaque(false);
        combo_emp_clientes.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                combo_emp_clientesFocusLost(evt);
            }
        });
        combo_emp_clientes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_emp_clientesMouseClicked(evt);
            }
        });
        combo_emp_clientes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                combo_emp_clientesActionPerformed(evt);
            }
        });

        tbl_empresa.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "ID", "Nome", "Telefone", "Cnpj", "Obs", "IP Scef", "Mac Scef", "Conexão Scef", "IP NFCE", "Mac NFCE", "Conexão NFCE", "IP NFE", "Mac NFE", "Conexão NFE", "Mobile", "Coletor"
            }
        ));
        tbl_empresa.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tbl_empresaMouseClicked(evt);
            }
        });
        jScrollPane5.setViewportView(tbl_empresa);

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(combo_emp_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, 219, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addGap(0, 0, Short.MAX_VALUE))
                    .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 763, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(combo_emp_clientes, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 147, Short.MAX_VALUE)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 210, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );

        Tab_Chamado.addTab("Servidores clientes", jPanel1);

        Panel_Base.add(Tab_Chamado, new org.netbeans.lib.awtextra.AbsoluteConstraints(0, 0, 790, 450));

        Principal.add(Panel_Base);
        Panel_Base.setBounds(60, 50, 790, 450);

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 850, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Principal, javax.swing.GroupLayout.PREFERRED_SIZE, 500, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void Btn_SlideMenuActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SlideMenuActionPerformed
        int posicion = Menu.getX();
        if (posicion < -1) {
            Animacion.Animacion.mover_derecha(-110, 0, 1, 2, Menu);
            userlogado.setVisible(true);
            lb_user.setVisible(true);
        } else {
            Animacion.Animacion.mover_izquierda(0, -110, 1, 2, Menu);
            userlogado.setVisible(false);
            lb_user.setVisible(false);
        }
    }//GEN-LAST:event_Btn_SlideMenuActionPerformed

    private void Btn_fecharActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_fecharActionPerformed
        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            int result = JOptionPane.showConfirmDialog(null, "Deseja Fechar o Sistema?", "SAIR", dialogButton);
            if (result == 0) {
                System.exit(0);
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
    }//GEN-LAST:event_Btn_fecharActionPerformed

    private void Btn_ChamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_ChamadoActionPerformed
        //PanelChamado.setVisible(false);
    }//GEN-LAST:event_Btn_ChamadoActionPerformed

    private void Btn_AgendaActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_AgendaActionPerformed
        AgendaView agenda2 = new AgendaView();
        agenda2.show();
    }//GEN-LAST:event_Btn_AgendaActionPerformed

    private void Btn_CadastrarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_CadastrarActionPerformed
        
    }//GEN-LAST:event_Btn_CadastrarActionPerformed

    private void Btn_SitesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_SitesActionPerformed
        Sites_View site = new Sites_View();
        site.show();
    }//GEN-LAST:event_Btn_SitesActionPerformed

    private void Btn_ChamadoMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_ChamadoMouseEntered
        Chamado.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_ChamadoMouseEntered

    private void Btn_ChamadoMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_ChamadoMouseExited
        Chamado.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_ChamadoMouseExited

    private void Btn_AgendaMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_AgendaMouseEntered
        Agenda.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_AgendaMouseEntered

    private void Btn_CadastrarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_CadastrarMouseEntered
        Cadastrar1.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_CadastrarMouseEntered

    private void Btn_RelatorioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_RelatorioMouseEntered
        Relatorio.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_RelatorioMouseEntered

    private void Btn_PROMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_PROMouseEntered
        Sites.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_PROMouseEntered

    private void Btn_SitesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_SitesMouseEntered
        PRO.setForeground(Color.ORANGE);
    }//GEN-LAST:event_Btn_SitesMouseEntered

    private void Btn_AgendaMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_AgendaMouseExited
        Agenda.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_AgendaMouseExited

    private void Btn_CadastrarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_CadastrarMouseExited
        Cadastrar1.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_CadastrarMouseExited

    private void Btn_RelatorioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_RelatorioMouseExited
        Relatorio.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_RelatorioMouseExited

    private void Btn_PROMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_PROMouseExited
        Sites.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_PROMouseExited

    private void Btn_SitesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_Btn_SitesMouseExited
        PRO.setForeground(Color.GRAY);
    }//GEN-LAST:event_Btn_SitesMouseExited

    private void combo_emp_clientesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_emp_clientesActionPerformed

    }//GEN-LAST:event_combo_emp_clientesActionPerformed

    private void combo_emp_clientesMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_emp_clientesMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_emp_clientesMouseClicked

    private void combo_emp_clientesFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_emp_clientesFocusLost
        // TODO add your handling code here:
    }//GEN-LAST:event_combo_emp_clientesFocusLost

    private void btn_Pesquisa_chamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_Pesquisa_chamadoActionPerformed
        //pesquisar_chamado(txt_Pesquisa_Chamado.getText());
        if (RadEmp.isSelected()) {
            String data = "nome_emp";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText(), data);
        }

        if (jRadioButton2.isSelected()) {
            String data = "texto";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText(), data);
        }

        if (jRadioButton3.isSelected()) {
            String data = "nome";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText(), data);
        }

        if (jRadioButton4.isSelected()) {
            String data = "tags";
            pesquisar_chamado(txt_Pesquisa_Chamado.getText(), data);
        }
    }//GEN-LAST:event_btn_Pesquisa_chamadoActionPerformed

    private void txt_titulo_chamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_txt_titulo_chamadoActionPerformed

    private void txt_titulo_chamadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoMouseClicked
        if (txt_titulo_chamado.getText().equals("Digite o titulo do Chamado...")) {
            txt_titulo_chamado.setText("");
        }
    }//GEN-LAST:event_txt_titulo_chamadoMouseClicked

    private void txt_titulo_chamadoFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoFocusLost
        if (txt_titulo_chamado.getText().equals("")) {
            txt_titulo_chamado.setText("Digite o titulo do Chamado...");
        }
    }//GEN-LAST:event_txt_titulo_chamadoFocusLost

    private void txt_titulo_chamadoFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_titulo_chamadoFocusGained
        if (txt_titulo_chamado.getText().equals("Digite o titulo do Chamado...")) {
            txt_titulo_chamado.setText("");
        }
    }//GEN-LAST:event_txt_titulo_chamadoFocusGained

    private void Btn_LimparActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_Btn_LimparActionPerformed
        txt_telefone_chamado2.setText("");
        txt_chamado_desc1.setText("");
        txt_titulo_chamado.setText("");
        txt_Solucao.setText("");
        combo_func.removeAllItems();
        txt_tag1.setText("");
        txt_tag2.setText("");
        txt_tag3.setText("");
        txt_tag4.setText("");
        txt_tag5.setText("");
        txt_conexao.setText("");

    }//GEN-LAST:event_Btn_LimparActionPerformed

    private void btn_salvar_chamadoActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvar_chamadoActionPerformed
        Chamado c = new Chamado();
        ChamadoDAO cdao = new ChamadoDAO();

        Empresa empresa = (Empresa) combo_emp.getSelectedItem();
        Funcionario func = (Funcionario) combo_func.getSelectedItem();

        empresa.getNome_emp();
        c.setFone(txt_telefone_chamado2.getText());
        c.setTitulo(txt_titulo_chamado.getText());
        c.setTexto(txt_Solucao.getText());
        c.setTags(txt_tag1.getText() + " " + txt_tag2.getText() + " " + txt_tag3.getText() + " " + txt_tag4.getText() + " " + txt_tag5.getText());
        c.setSolucao(txt_Solucao.getText());
        c.setConexao(txt_conexao.getText());
        //c.setUser(userlogado.getText());
        //cdao.Cad_Chamado(empresa, func, c);
    }//GEN-LAST:event_btn_salvar_chamadoActionPerformed

    private void combo_funcActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_funcActionPerformed

    }//GEN-LAST:event_combo_funcActionPerformed

    private void combo_empActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_combo_empActionPerformed
        String user = userlogado.toString();
        //JOptionPane.showMessageDialog(null, user);

        if (combo_emp.getSelectedItem().equals("Empresas")) {
            JOptionPane.showMessageDialog(null, "Selecione uma empresa");
            combo_emp.setBorder(new LineBorder(Color.RED));
        } else {
            combo_func.removeAllItems();
            combo_emp.setBorder(new LineBorder(Color.lightGray));
            FuncionarioDAO fdao = new FuncionarioDAO();
            ArrayList<Funcionario> list = fdao.getData((Empresa) combo_emp.getSelectedItem());
            for (int i = 0; i < list.size(); i++) {
                combo_func.addItem(list.get(i).getNome());
                txt_telefone_chamado2.setText(list.get(i).getTelefone());
            }
        }
    }//GEN-LAST:event_combo_empActionPerformed

    private void combo_empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_empMouseClicked

    }//GEN-LAST:event_combo_empMouseClicked

    private void combo_empFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_empFocusLost
        if (combo_emp.getSelectedItem().equals("Empresas")) {
            JOptionPane.showMessageDialog(null, "Selecione uma empresa");
        }
    }//GEN-LAST:event_combo_empFocusLost

    private void combo_empFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_combo_empFocusGained
        if (combo_emp.getSelectedItem().toString().equals("Empresas")) {
            JOptionPane.showMessageDialog(null, "Escolha uma empresa");
            combo_emp.setBorder(new LineBorder(Color.RED));

        } else {
            combo_emp.setBorder(new LineBorder(Color.GREEN));
        }
    }//GEN-LAST:event_combo_empFocusGained

    private void tbl_empresaMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_empresaMouseClicked

    }//GEN-LAST:event_tbl_empresaMouseClicked

    private void jButton1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton1ActionPerformed
        //Cad_emp_view func = new Cad_emp_view();
        //func.show();
    }//GEN-LAST:event_jButton1ActionPerformed

    private void jButton2ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton2ActionPerformed
        Cad_Func_View func = new Cad_Func_View();
        func.show();
    }//GEN-LAST:event_jButton2ActionPerformed

    private void combo_funcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_funcItemStateChanged

    }//GEN-LAST:event_combo_funcItemStateChanged

    private void formMousePressed(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMousePressed
        //Quando pressionar o mouse movimenta o frame
        xMouse = evt.getX();
        yMouse = evt.getY();
    }//GEN-LAST:event_formMousePressed

    private void formMouseDragged(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_formMouseDragged
        //Verifica as dimensões e deixa o frame flotuante na posição exata
        int x = evt.getXOnScreen();
        int y = evt.getYOnScreen();

        this.setLocation(x - xMouse, y - yMouse);
    }//GEN-LAST:event_formMouseDragged

    private void tbl_ChamadoMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tbl_ChamadoMouseClicked

    }//GEN-LAST:event_tbl_ChamadoMouseClicked

    private void jButton3ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jButton3ActionPerformed
        readJTable();
    }//GEN-LAST:event_jButton3ActionPerformed

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
            java.util.logging.Logger.getLogger(Principal_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Principal_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Principal_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Principal_new.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Principal_new().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel Agenda;
    private javax.swing.JPanel BarraSuperior;
    private javax.swing.JButton Btn_Agenda;
    private javax.swing.JButton Btn_Cadastrar;
    private javax.swing.JButton Btn_Chamado;
    private javax.swing.JButton Btn_Limpar;
    private javax.swing.JButton Btn_PRO;
    private javax.swing.JButton Btn_Relatorio;
    private javax.swing.JButton Btn_Sites;
    private javax.swing.JToggleButton Btn_SlideMenu;
    private javax.swing.JButton Btn_fechar;
    private javax.swing.JLabel Cadastrar1;
    private javax.swing.JLabel Chamado;
    private javax.swing.JPanel Chamados;
    private javax.swing.JLabel Lb_data;
    private javax.swing.JPanel Menu;
    private javax.swing.JLabel MenuBar;
    private javax.swing.JLabel Menu_lateral;
    private javax.swing.JLabel PRO;
    private javax.swing.JPanel Panel_Base;
    private javax.swing.JPanel Pesq_chammado;
    private javax.swing.ButtonGroup Pesquisar_Radbutton;
    private javax.swing.JPanel Principal;
    private javax.swing.JRadioButton RadEmp;
    private javax.swing.JPanel Ramais;
    private javax.swing.JLabel Relatorio;
    private javax.swing.JLabel Sites;
    private javax.swing.JTabbedPane Tab_Chamado;
    private javax.swing.JButton btn_Pesquisa_chamado;
    private javax.swing.JButton btn_salvar_chamado;
    private javax.swing.JComboBox combo_emp;
    private javax.swing.JComboBox combo_emp_clientes;
    private javax.swing.JComboBox combo_func;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel10;
    private javax.swing.JLabel jLabel11;
    private javax.swing.JLabel jLabel12;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel5;
    private javax.swing.JLabel jLabel6;
    private javax.swing.JLabel jLabel7;
    private javax.swing.JLabel jLabel8;
    private javax.swing.JLabel jLabel9;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel3;
    private javax.swing.JPanel jPanel5;
    private javax.swing.JPanel jPanel7;
    private javax.swing.JRadioButton jRadioButton2;
    private javax.swing.JRadioButton jRadioButton3;
    private javax.swing.JRadioButton jRadioButton4;
    private javax.swing.JScrollPane jScrollPane1;
    private javax.swing.JScrollPane jScrollPane2;
    private javax.swing.JScrollPane jScrollPane3;
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JLabel lb_user;
    private javax.swing.JTable tbl_Chamado;
    private javax.swing.JTable tbl_Ramais;
    private javax.swing.JTable tbl_empresa;
    private javax.swing.JTextField txt_Pesquisa_Chamado;
    private javax.swing.JEditorPane txt_Solucao;
    private javax.swing.JEditorPane txt_chamado_desc1;
    private javax.swing.JTextField txt_conexao;
    private javax.swing.JTextField txt_tag1;
    private javax.swing.JTextField txt_tag2;
    private javax.swing.JTextField txt_tag3;
    private javax.swing.JTextField txt_tag4;
    private javax.swing.JTextField txt_tag5;
    private javax.swing.JFormattedTextField txt_telefone_chamado2;
    private javax.swing.JTextField txt_titulo_chamado;
    public static final javax.swing.JLabel userlogado = new javax.swing.JLabel();
    // End of variables declaration//GEN-END:variables
}
