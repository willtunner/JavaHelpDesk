/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package View;

import Funcoes.exportarExcel;
import connection.ConnectionFactory;
import java.awt.Color;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableModel;
import javax.swing.table.TableRowSorter;
import model.bean.Chamado;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.bean.Ticket;
import model.bean.Usuario;
import model.dao.ChamadoDAO;
import model.dao.EmpresaDAO;
import model.dao.FuncionarioDAO;
import model.dao.TicketDAO;
import model.dao.UsuarioDAO;

/**
 *
 * @author WillTunner
 */
public class Gerencia2 extends javax.swing.JFrame {

    //CHAMA TELA CHAMADO
    Chamado_View4 chamadoView2;

    //Variavel do status
    String status;

    //Pega usuario
    //String user = LoginNew3.txt_Login.getText();
    /**
     * Creates new form Gerencia2
     */
    public Gerencia2() {
        initComponents();
        readJTableTicket();
        readJtable_User_Chamado();
        comboDev();
        listacontadorMesChamados();
        listacontadorMesTicket();
        listaChamadosAbertoCount();
        listaChamadosFechadoCount();
        listaTodosChamados();
        listaTicketAndamento();
        listaTicketPendente();
        listaTicketResolvido();
        readJTableUsuario();
        readtabelaUserAtendente();
        readtabelaTicketDeveloper();
        
        contadorChamadosHOJEAbertos();//CONTA CHAMADOS ABERTOS DO DIA 
        contadorChamadosHOJEFechados();//CONTA CHAMADOS FECHADOS DO DIA

        //gerencia chamados HOJE
        lbl3.setVisible(false);
        img3.setVisible(false);
        cont3.setVisible(false);

        //ICONE FRAME
        URL caminhoIcone = getClass().getResource("/imagem/Logo128x128.png");
        Image iconeTitulo = Toolkit.getDefaultToolkit().getImage(caminhoIcone);
        this.setIconImage(iconeTitulo);

        //Popula ComboBox Empresa
        EmpresaDAO emp = new EmpresaDAO();
        DefaultComboBoxModel modelEstado = new DefaultComboBoxModel(emp.mostrarEmpresas());
        combo_emp.setModel(modelEstado);

    }

    public String listacontadorMesChamados() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE dataChamado BETWEEN  '" + dataMes + "' AND '" + data + "'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_chamados.setText(ChamadoMes);
        return ChamadoMes;
    }
    
    //LISTA CONTADOR HOJE CHAMADOS
    public String contadorChamadosHOJEAbertos() {
        String ChamadoMes = "";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE dataChamado = CURDATE() AND STATUS ='A'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        cont1.setText(ChamadoMes);
        return ChamadoMes;
    }
    
    //LISTA CONTADOR HOJE CHAMADOS
    public String contadorChamadosHOJEFechados() {
        String ChamadoMes = "";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE dataChamado = CURDATE() AND STATUS ='F'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        cont2.setText(ChamadoMes);
        return ChamadoMes;
    }
    
    //LISTA CONTADOR HOJE TICKET PENDENTE
    public String contadorTickeHOJEPendentes() {
        String ticketPendenteHoje = "";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket  WHERE dataticket = CURDATE() AND STATUS ='P'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ticketPendenteHoje = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        cont1.setText(ticketPendenteHoje);
        return ticketPendenteHoje;
    }
    
    //LISTA CONTADOR HOJE TICKET ANDAMENTO
    public String contadorTickeHOJEAndamento() {
        String ticketPendenteHoje = "";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket  WHERE dataticket = CURDATE() AND STATUS ='A'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ticketPendenteHoje = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        cont2.setText(ticketPendenteHoje);
        return ticketPendenteHoje;
    }
    
    //LISTA CONTADOR HOJE TICKET ANDAMENTO
    public String contadorTickeHOJEResolvido() {
        String ticketPendenteHoje = "";

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket  WHERE dataticket = CURDATE() AND STATUS ='R'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ticketPendenteHoje = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        cont3.setText(ticketPendenteHoje);
        return ticketPendenteHoje;
    }

    public String listacontadorMesTicket() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket  WHERE dataticket BETWEEN  '" + dataMes + "' AND '" + data + "'");
            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_Ticket.setText(ChamadoMes);
        return ChamadoMes;
    }

    public String listaChamadosAbertoCount() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE STATUS = 'A' and dataChamado BETWEEN  '" + dataMes + "' AND '" + data + "'");

            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_ChamadoAberto.setText(ChamadoMes);
        return ChamadoMes;
    }

    public String listaChamadosFechadoCount() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado  WHERE STATUS = 'F' and dataChamado BETWEEN  '" + dataMes + "' AND '" + data + "'");

            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_ChamadoFechado.setText(ChamadoMes);
        return ChamadoMes;
    }

    public String listaTodosChamados() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        try {
            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM chamado");

            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_TodosChamados.setText(ChamadoMes);
        return ChamadoMes;
    }

    public String listaTicketAndamento() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket WHERE STATUS = 'A'  and dataticket BETWEEN  '" + dataMes + "' AND '" + data + "'");

            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_TicketAndamento.setText(ChamadoMes);
        return ChamadoMes;
    }

    public String listaTicketPendente() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket WHERE STATUS = 'P'  and dataticket BETWEEN  '" + dataMes + "' AND '" + data + "'");

            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_TicketPendente.setText(ChamadoMes);
        return ChamadoMes;
    }

    public String listaTicketResolvido() {
        String ChamadoMes = "";

        Calendar dataAtual = Calendar.getInstance();
        dataAtual.add(Calendar.DAY_OF_MONTH, -30);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String dataMes = simpleDateFormat.format(dataAtual.getTime());
        //System.out.println("Data Mes:" + dataMes);

        try {
            //String nome = LoginNew3.tf_login.getText();
            //Data e Hora do sistema
            Date dataHoraAtual = new Date();
            String data = new SimpleDateFormat("yyyy-MM-dd").format(dataHoraAtual);

            Connection con = ConnectionFactory.getConnection();
            PreparedStatement stmt = null;
            ResultSet rs = null;
            stmt = con.prepareStatement("SELECT COUNT(*) FROM ticket WHERE STATUS = 'R'  and dataticket BETWEEN  '" + dataMes + "' AND '" + data + "'");

            rs = stmt.executeQuery();
            if (rs.next()) {
                ChamadoMes = rs.getString(1);
            }
        } catch (SQLException e) {
            System.out.println(e);
        }
        lbl_TicketResolvido.setText(ChamadoMes);
        return ChamadoMes;
    }

    //Popula o jtable Chamado - PAINEL CONTROLE USER
    public void readJtable_User_Chamado() {
        String user = "willtunner";
        DefaultTableModel modelo = (DefaultTableModel) jt_Painel_User_Chamado.getModel();
        modelo.setNumRows(0);

        jt_Painel_User_Chamado.setAutoResizeMode(jt_Painel_User_Chamado.AUTO_RESIZE_OFF);
        jt_Painel_User_Chamado.setRowSorter(new TableRowSorter(modelo));
        jt_Painel_User_Chamado.setModel(modelo);
        jt_Painel_User_Chamado.getColumnModel().getColumn(0).setPreferredWidth(40);//ID
        jt_Painel_User_Chamado.getColumnModel().getColumn(1).setPreferredWidth(280);//TITULO
        jt_Painel_User_Chamado.getColumnModel().getColumn(2).setPreferredWidth(200);//DESCRIÇÃO
        jt_Painel_User_Chamado.getColumnModel().getColumn(3).setPreferredWidth(280);//SOLUÇÃO
        jt_Painel_User_Chamado.getColumnModel().getColumn(4).setPreferredWidth(200);//EMPRESA
        jt_Painel_User_Chamado.getColumnModel().getColumn(5).setPreferredWidth(100);//FUNCIONARIO
        jt_Painel_User_Chamado.getColumnModel().getColumn(6).setPreferredWidth(100);//TAGS
        jt_Painel_User_Chamado.getColumnModel().getColumn(7).setPreferredWidth(150);//FONE
        jt_Painel_User_Chamado.getColumnModel().getColumn(8).setPreferredWidth(80);//CONEXÃO
        jt_Painel_User_Chamado.getColumnModel().getColumn(9).setPreferredWidth(100);//USUARIO LOGICOM
        jt_Painel_User_Chamado.getColumnModel().getColumn(10).setPreferredWidth(100);//DATA
        jt_Painel_User_Chamado.getColumnModel().getColumn(11).setPreferredWidth(90);//HORA CHAMADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(12).setPreferredWidth(50);//STATUS
        jt_Painel_User_Chamado.getColumnModel().getColumn(13).setPreferredWidth(100);//EDITADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(14).setPreferredWidth(90);//DATA EDITADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(15).setPreferredWidth(90);//HORA EDITADO
        //acrescentados
        jt_Painel_User_Chamado.getColumnModel().getColumn(16).setPreferredWidth(100);//CRONOMETRO
        jt_Painel_User_Chamado.getColumnModel().getColumn(17).setPreferredWidth(90);//HORA FECHADO
        jt_Painel_User_Chamado.getColumnModel().getColumn(18).setPreferredWidth(90);//DATA FECHADO

        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.ListarChamadoUser(user)) {
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

    //POPULA COMBO DESENVOLVEDOR
    private void comboDev() {
        ResultSet rs;
        PreparedStatement stmt = null;
        Connection con = ConnectionFactory.getConnection();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE permissao='D' ORDER BY usuario asc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                String name = rs.getString("usuario");
                comboDeveloper.addItem(name);
            }

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, e);
        }
    }

    public void LimpaCamposUsuario() {
        txt_usuario.setText("");
        txt_senha.setText("");
        combo_colaborador.setSelectedIndex(0);

        txt_Pesquisar.setText("Pesquisar usuário");
        txt_Pesquisar.setHorizontalAlignment(txt_Pesquisar.CENTER);
    }

    //Metodo de 2 click na Chamado do perfil/usuario
    public class MyMouseListener3 extends MouseAdapter {

        @Override
        public void mouseClicked(MouseEvent evt) {
            if (evt.getClickCount() == 2) {
                int index = jt_Painel_User_Chamado.getSelectedRow();

                TableModel model = jt_Painel_User_Chamado.getModel();

                String Id = model.getValueAt(index, 0).toString();
                String Titulo = model.getValueAt(index, 1).toString();
                String Conteudo = model.getValueAt(index, 2).toString();
                String Empresa = model.getValueAt(index, 4).toString();
                String Funcionario = model.getValueAt(index, 5).toString();
                String Tags = model.getValueAt(index, 6).toString();
                String Solucao = model.getValueAt(index, 3).toString();
                String Conexao = model.getValueAt(index, 8).toString();
                String User = model.getValueAt(index, 9).toString();
                String Telefone = model.getValueAt(index, 7).toString();
                String Status = model.getValueAt(index, 12).toString();
                String DataChamado = model.getValueAt(index, 10).toString();//PEGA A DATA CERTA DO CHAMADO

                //Data e Hora do sistema
                Date dataHoraAtual = new Date();
                String data = new SimpleDateFormat("dd/MM/yyyy").format(dataHoraAtual);
                String hora = new SimpleDateFormat("HH:mm:ss").format(dataHoraAtual);

                //função para enviar o status para outro frame
                if (Status.equals("A")) {
                    chamadoView2.cmb_Status.setSelectedIndex(0);
                } else {
                    chamadoView2.cmb_Status.setSelectedIndex(1);
                }

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
                chamadoView2.lb_data_chamado.setText(DataChamado);//PRECISA AJUSTAR ESSA DATA DO CHAMADO VIEW
                chamadoView2.lb_hora.setText(hora);
                chamadoView2.setVisible(true);
                chamadoView2.pack();
                chamadoView2.setLocationRelativeTo(null);
                chamadoView2.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
            }
        }
    }

    //Pesuisar Chamado
    public void pesquisar_Usuario(String usuario) {
        DefaultTableModel modelo = (DefaultTableModel) tabela_Usuario.getModel();
        modelo.setNumRows(0);
        UsuarioDAO udao = new UsuarioDAO();

        for (Usuario u : udao.PesquisarUsuario(usuario)) {
            modelo.addRow(new Object[]{
                u.getId(),
                u.getUsuario(),
                u.getSenha(),
                u.getPermissao()
            });
        }
    }

    //Pesuisar Chamado
    public void pesquisar_chamadoPerfil(String nome_emp, String data) {
        DefaultTableModel modelo = (DefaultTableModel) jt_Painel_User_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.PesquisarChamado(nome_emp, data)) {
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

    public void pesquisar_chamadoPerfil(String nome_emp, String data, String dataIni, String dataFim) {
        DefaultTableModel modelo = (DefaultTableModel) jt_Painel_User_Chamado.getModel();
        modelo.setNumRows(0);
        ChamadoDAO cadao = new ChamadoDAO();

        for (Chamado c : cadao.PesquisarChamadoPerfil(nome_emp, data, dataIni, dataFim)) {
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

    //Popula o jtable ticket
    public void readJTableTicket() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaTicket.getModel();
        modelo.setNumRows(0);

        tabelaTicket.setAutoResizeMode(tabelaTicket.AUTO_RESIZE_OFF);
        tabelaTicket.setRowSorter(new TableRowSorter(modelo));
        tabelaTicket.setModel(modelo);
        tabelaTicket.getColumnModel().getColumn(0).setPreferredWidth(50);//ID
        tabelaTicket.getColumnModel().getColumn(1).setPreferredWidth(200);//TITULO
        tabelaTicket.getColumnModel().getColumn(2).setPreferredWidth(200);//DESCRIÇÃO
        tabelaTicket.getColumnModel().getColumn(3).setPreferredWidth(70);//STATUS
        tabelaTicket.getColumnModel().getColumn(4).setPreferredWidth(150);//DESENVOLVEDOR
        tabelaTicket.getColumnModel().getColumn(5).setPreferredWidth(100);//EMPRESA
        tabelaTicket.getColumnModel().getColumn(6).setPreferredWidth(100);//CLIENTE
        tabelaTicket.getColumnModel().getColumn(7).setPreferredWidth(150);//TELEFONE
        tabelaTicket.getColumnModel().getColumn(8).setPreferredWidth(80);//DATA
        tabelaTicket.getColumnModel().getColumn(9).setPreferredWidth(100);//HORA

        TicketDAO cadao = new TicketDAO();

        for (Ticket c : cadao.ListarTicket()) {
            modelo.addRow(new Object[]{
                c.getIdticket(),
                c.getTitulo(),
                c.getDescricao(),
                c.getStatus(),
                c.getDesenvolvedor(),
                c.getEmpresa(),
                c.getCliente(),
                c.getTelefone(),
                c.getDataticket(),
                c.getHoraticket(),
                c.getIdchamado(),
                c.getConexaochamado(),
                c.getTags()
            });
        }

    }
    
    //Popula o jtable UserAtendente
    public void readtabelaUserAtendente() {
        DefaultTableModel modelo = (DefaultTableModel) tabelaChamadoAtendente.getModel();
        modelo.setNumRows(0);

        tabelaChamadoAtendente.setAutoResizeMode(tabelaChamadoAtendente.AUTO_RESIZE_ALL_COLUMNS);
        tabelaChamadoAtendente.setRowSorter(new TableRowSorter(modelo));
        tabelaChamadoAtendente.setModel(modelo);

        ChamadoDAO cdao = new ChamadoDAO();

        for (Chamado c : cdao.ListarChamadoUserAtendente()) {
            
            modelo.addRow(new Object[]{
                c.getUser(),
                c.getAbertos(),
                c.getFechados()
            });
        }

    }
    
    //Popula o jtable UserAtendente
    public void readtabelaTicketDeveloper() {
        DefaultTableModel modelo = (DefaultTableModel) tabela_Ticket_desenvolvedor.getModel();
        modelo.setNumRows(0);

        tabela_Ticket_desenvolvedor.setAutoResizeMode(tabelaChamadoAtendente.AUTO_RESIZE_ALL_COLUMNS);
        tabela_Ticket_desenvolvedor.setRowSorter(new TableRowSorter(modelo));
        tabela_Ticket_desenvolvedor.setModel(modelo);

        TicketDAO tdao =  new TicketDAO();

        for (Ticket t : tdao.ListarTicketDeveloper()) {
            
            modelo.addRow(new Object[]{
                t.getDesenvolvedor(),
                t.getAndamento(),
                t.getPendente(),
                t.getResolvido()
            });
        }

    }
    
    

    //Popula o jtable Usuario
    public void readJTableUsuario() {
        DefaultTableModel modelo = (DefaultTableModel) tabela_Usuario.getModel();
        modelo.setNumRows(0);

        tabela_Usuario.setAutoResizeMode(tabelaTicket.AUTO_RESIZE_ALL_COLUMNS);
        tabela_Usuario.setRowSorter(new TableRowSorter(modelo));
        tabela_Usuario.setModel(modelo);

        UsuarioDAO udao = new UsuarioDAO();

        for (Usuario u : udao.ListarUsuario()) {
            modelo.addRow(new Object[]{
                u.getId(),
                u.getUsuario(),
                u.getSenha(),
                u.getPermissao()
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

        Body = new javax.swing.JPanel();
        Gerencia = new javax.swing.JTabbedPane();
        jPanel2 = new javax.swing.JPanel();
        PanelHoje = new javax.swing.JPanel();
        jPanel5 = new javax.swing.JPanel();
        jLabel1 = new javax.swing.JLabel();
        jLabel29 = new javax.swing.JLabel();
        btn_refreshHoje = new javax.swing.JButton();
        img1 = new javax.swing.JLabel();
        img2 = new javax.swing.JLabel();
        img3 = new javax.swing.JLabel();
        lbl_1 = new javax.swing.JLabel();
        lbl_2 = new javax.swing.JLabel();
        lbl3 = new javax.swing.JLabel();
        jSeparator1 = new javax.swing.JSeparator();
        img6 = new javax.swing.JLabel();
        lbl6 = new javax.swing.JLabel();
        jSeparator2 = new javax.swing.JSeparator();
        cont1 = new javax.swing.JLabel();
        cont2 = new javax.swing.JLabel();
        cont3 = new javax.swing.JLabel();
        jLabel19 = new javax.swing.JLabel();
        jComboBox1 = new javax.swing.JComboBox<>();
        PanelTicketMes1 = new javax.swing.JPanel();
        jPanel7 = new javax.swing.JPanel();
        jLabel4 = new javax.swing.JLabel();
        jLabel28 = new javax.swing.JLabel();
        btn_refresh = new javax.swing.JButton();
        jLabel36 = new javax.swing.JLabel();
        lbl_chamados = new javax.swing.JLabel();
        jLabel38 = new javax.swing.JLabel();
        lbl_ChamadoAberto = new javax.swing.JLabel();
        jLabel40 = new javax.swing.JLabel();
        lbl_Ticket = new javax.swing.JLabel();
        jLabel42 = new javax.swing.JLabel();
        lbl_ChamadoFechado = new javax.swing.JLabel();
        jLabel41 = new javax.swing.JLabel();
        lbl_TicketPendente = new javax.swing.JLabel();
        jLabel50 = new javax.swing.JLabel();
        lbl_TicketAndamento = new javax.swing.JLabel();
        jLabel52 = new javax.swing.JLabel();
        lbl_TicketResolvido = new javax.swing.JLabel();
        jLabel55 = new javax.swing.JLabel();
        lbl_TodosChamados = new javax.swing.JLabel();
        jPanel8 = new javax.swing.JPanel();
        jPanel9 = new javax.swing.JPanel();
        jLabel2 = new javax.swing.JLabel();
        jLabel21 = new javax.swing.JLabel();
        btn_refreshAtendentes = new javax.swing.JButton();
        jScrollPane5 = new javax.swing.JScrollPane();
        tabelaChamadoAtendente = new javax.swing.JTable();
        PanelAbertoxFechado = new javax.swing.JPanel();
        jPanel10 = new javax.swing.JPanel();
        jLabel20 = new javax.swing.JLabel();
        jLabel30 = new javax.swing.JLabel();
        btn_refreshDesenvolvedor = new javax.swing.JButton();
        jScrollPane4 = new javax.swing.JScrollPane();
        tabela_Ticket_desenvolvedor = new javax.swing.JTable();
        jPanel4 = new javax.swing.JPanel();
        jPanel11 = new javax.swing.JPanel();
        jLabel22 = new javax.swing.JLabel();
        jPanel12 = new javax.swing.JPanel();
        jLabel23 = new javax.swing.JLabel();
        combo_emp = new javax.swing.JComboBox<>();
        combo_func = new javax.swing.JComboBox<>();
        jLabel24 = new javax.swing.JLabel();
        txt_telefoneTicket = new javax.swing.JTextField();
        jLabel25 = new javax.swing.JLabel();
        jLabel26 = new javax.swing.JLabel();
        txt_conexao = new javax.swing.JTextField();
        jPanel13 = new javax.swing.JPanel();
        jLabel31 = new javax.swing.JLabel();
        txt_titulo_ticket = new javax.swing.JTextField();
        jLabel32 = new javax.swing.JLabel();
        jScrollPane2 = new javax.swing.JScrollPane();
        txt_DescTicket = new javax.swing.JTextArea();
        jLabel34 = new javax.swing.JLabel();
        txt_tag = new javax.swing.JTextField();
        jPanel3 = new javax.swing.JPanel();
        jLabel33 = new javax.swing.JLabel();
        txt_idChamado = new javax.swing.JTextField();
        jLabel35 = new javax.swing.JLabel();
        jLabel44 = new javax.swing.JLabel();
        comboStatus = new javax.swing.JComboBox<>();
        comboDeveloper = new javax.swing.JComboBox<>();
        jButton2 = new javax.swing.JButton();
        jButton1 = new javax.swing.JButton();
        btn_salvar = new javax.swing.JButton();
        jButton7 = new javax.swing.JButton();
        jScrollPane3 = new javax.swing.JScrollPane();
        tabelaTicket = new javax.swing.JTable();
        jPanel15 = new javax.swing.JPanel();
        jPanel23 = new javax.swing.JPanel();
        jScrollPane7 = new javax.swing.JScrollPane();
        jt_Painel_User_Chamado = new javax.swing.JTable();
        jPanel24 = new javax.swing.JPanel();
        jLabel74 = new javax.swing.JLabel();
        txt_Puser_dataIni = new javax.swing.JTextField();
        txt_Puser_dataFim = new javax.swing.JTextField();
        jLabel54 = new javax.swing.JLabel();
        txt_Puser_Pesquisar = new javax.swing.JTextField();
        radEmpUser = new javax.swing.JRadioButton();
        radFuncUser = new javax.swing.JRadioButton();
        radDescUser = new javax.swing.JRadioButton();
        radClienteUser = new javax.swing.JRadioButton();
        radTagsUser = new javax.swing.JRadioButton();
        btnPesquisarUser = new javax.swing.JButton();
        btnConverteExcel = new javax.swing.JButton();
        jPanel1 = new javax.swing.JPanel();
        jPanel6 = new javax.swing.JPanel();
        txt_usuario = new javax.swing.JTextField();
        jLabel3 = new javax.swing.JLabel();
        txt_senha = new javax.swing.JTextField();
        jLabel27 = new javax.swing.JLabel();
        combo_colaborador = new javax.swing.JComboBox<>();
        jLabel37 = new javax.swing.JLabel();
        btn_salvarUsuario = new javax.swing.JButton();
        btn_DeleteUsuario = new javax.swing.JButton();
        btn_LimparCampos = new javax.swing.JButton();
        btn_EditarUsuario = new javax.swing.JButton();
        txt_Pesquisar = new javax.swing.JTextField();
        jScrollPane1 = new javax.swing.JScrollPane();
        tabela_Usuario = new javax.swing.JTable();
        jPanel14 = new javax.swing.JPanel();
        jLabel39 = new javax.swing.JLabel();

        setDefaultCloseOperation(javax.swing.WindowConstants.DISPOSE_ON_CLOSE);
        setTitle("HelpDesk :: Tela de Gerência");

        PanelHoje.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel5.setBackground(new java.awt.Color(19, 29, 38));

        jLabel1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/icons8_today_50px.png"))); // NOI18N

        jLabel29.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel29.setForeground(new java.awt.Color(255, 255, 255));
        jLabel29.setText("HOJE");

        btn_refreshHoje.setBackground(new java.awt.Color(0, 102, 102));
        btn_refreshHoje.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_update_left_rotation_25px.png"))); // NOI18N
        btn_refreshHoje.setToolTipText("Atualizar");
        btn_refreshHoje.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refreshHoje.setOpaque(false);
        btn_refreshHoje.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_refreshHojeMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_refreshHojeMouseExited(evt);
            }
        });
        btn_refreshHoje.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshHojeActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel5Layout = new javax.swing.GroupLayout(jPanel5);
        jPanel5.setLayout(jPanel5Layout);
        jPanel5Layout.setHorizontalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel5Layout.createSequentialGroup()
                .addComponent(jLabel1)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jLabel29)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refreshHoje, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel5Layout.setVerticalGroup(
            jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel5Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel5Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_refreshHoje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel5Layout.createSequentialGroup()
                        .addGap(0, 0, Short.MAX_VALUE)
                        .addComponent(jLabel29)))
                .addContainerGap())
        );

        img1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/barraverde.PNG"))); // NOI18N

        img2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/barraverde.PNG"))); // NOI18N

        img3.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/barraverde.PNG"))); // NOI18N

        lbl_1.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_1.setText("Chamados abertos");

        lbl_2.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl_2.setText("Chamados Fechados");

        lbl3.setFont(new java.awt.Font("Tahoma", 0, 24)); // NOI18N
        lbl3.setText("Resolvidos");

        img6.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/barraverde.PNG"))); // NOI18N

        lbl6.setFont(new java.awt.Font("Tahoma", 1, 24)); // NOI18N
        lbl6.setText("SLA");

        cont1.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cont1.setText("065");

        cont2.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cont2.setText("040");

        cont3.setFont(new java.awt.Font("Tahoma", 0, 18)); // NOI18N
        cont3.setText("023");

        jLabel19.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel19.setText("99,68%");

        jComboBox1.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Chamados", "Tickets" }));
        jComboBox1.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                jComboBox1ActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout PanelHojeLayout = new javax.swing.GroupLayout(PanelHoje);
        PanelHoje.setLayout(PanelHojeLayout);
        PanelHojeLayout.setHorizontalGroup(
            PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, PanelHojeLayout.createSequentialGroup()
                .addGap(0, 0, Short.MAX_VALUE)
                .addGroup(PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(91, 91, 91)
                        .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, 275, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(img1)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_1)
                        .addGap(128, 128, 128)
                        .addComponent(cont1))
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(img2)
                        .addGap(18, 18, 18)
                        .addComponent(lbl_2)
                        .addGap(108, 108, 108)
                        .addComponent(cont2))
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 448, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(img6)
                        .addGap(18, 18, 18)
                        .addComponent(lbl6)
                        .addGap(239, 239, 239)
                        .addComponent(jLabel19))
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 457, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(19, 19, 19)
                        .addComponent(img3)
                        .addGap(18, 18, 18)
                        .addComponent(lbl3)
                        .addGap(215, 215, 215)
                        .addComponent(cont3)))
                .addContainerGap())
        );
        PanelHojeLayout.setVerticalGroup(
            PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelHojeLayout.createSequentialGroup()
                .addComponent(jPanel5, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addComponent(jComboBox1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(23, 23, 23)
                .addGroup(PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cont1)
                    .addComponent(lbl_1)
                    .addComponent(img1))
                .addGap(11, 11, 11)
                .addGroup(PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cont2)
                    .addComponent(lbl_2)
                    .addComponent(img2))
                .addGap(11, 11, 11)
                .addGroup(PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.CENTER)
                    .addComponent(cont3)
                    .addComponent(lbl3)
                    .addComponent(img3))
                .addGap(78, 78, 78)
                .addComponent(jSeparator1, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(6, 6, 6)
                .addGroup(PanelHojeLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(5, 5, 5)
                        .addComponent(img6))
                    .addComponent(lbl6)
                    .addGroup(PanelHojeLayout.createSequentialGroup()
                        .addGap(3, 3, 3)
                        .addComponent(jLabel19)))
                .addGap(6, 6, 6)
                .addComponent(jSeparator2, javax.swing.GroupLayout.PREFERRED_SIZE, 10, javax.swing.GroupLayout.PREFERRED_SIZE))
        );

        PanelTicketMes1.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel7.setBackground(new java.awt.Color(19, 29, 38));

        jLabel4.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/icons8_calendar_50px.png"))); // NOI18N

        jLabel28.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel28.setForeground(new java.awt.Color(255, 255, 255));
        jLabel28.setText("CHAMADOS/TICKET MENSAIS");

        btn_refresh.setBackground(new java.awt.Color(0, 102, 102));
        btn_refresh.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_update_left_rotation_25px.png"))); // NOI18N
        btn_refresh.setToolTipText("Atualizar");
        btn_refresh.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refresh.setOpaque(false);
        btn_refresh.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_refreshMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_refreshMouseExited(evt);
            }
        });
        btn_refresh.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel7Layout = new javax.swing.GroupLayout(jPanel7);
        jPanel7.setLayout(jPanel7Layout);
        jPanel7Layout.setHorizontalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addComponent(jLabel4)
                .addGap(18, 18, 18)
                .addComponent(jLabel28, javax.swing.GroupLayout.PREFERRED_SIZE, 291, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refresh, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel7Layout.setVerticalGroup(
            jPanel7Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel7Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(jLabel28)
                .addContainerGap())
            .addGroup(jPanel7Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(btn_refresh, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(13, 13, 13))
        );

        jLabel36.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel36.setText("Chamados");

        lbl_chamados.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_chamados.setText("3084");

        jLabel38.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel38.setForeground(new java.awt.Color(102, 102, 102));
        jLabel38.setText("Abertos");

        lbl_ChamadoAberto.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_ChamadoAberto.setText("28");

        jLabel40.setFont(new java.awt.Font("Tahoma", 1, 14)); // NOI18N
        jLabel40.setText("Ticket");

        lbl_Ticket.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_Ticket.setText("15");

        jLabel42.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel42.setForeground(new java.awt.Color(102, 102, 102));
        jLabel42.setText("Fechados");

        lbl_ChamadoFechado.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_ChamadoFechado.setText("2045");

        jLabel41.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel41.setForeground(new java.awt.Color(102, 102, 102));
        jLabel41.setText("Pendente");

        lbl_TicketPendente.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_TicketPendente.setText("28");

        jLabel50.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel50.setForeground(new java.awt.Color(102, 102, 102));
        jLabel50.setText("Andamento");

        lbl_TicketAndamento.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_TicketAndamento.setText("2045");

        jLabel52.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel52.setForeground(new java.awt.Color(102, 102, 102));
        jLabel52.setText("Resolvidos");

        lbl_TicketResolvido.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_TicketResolvido.setText("2045");

        jLabel55.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        jLabel55.setForeground(new java.awt.Color(102, 102, 102));
        jLabel55.setText("Todos");

        lbl_TodosChamados.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        lbl_TodosChamados.setText("2045");

        javax.swing.GroupLayout PanelTicketMes1Layout = new javax.swing.GroupLayout(PanelTicketMes1);
        PanelTicketMes1.setLayout(PanelTicketMes1Layout);
        PanelTicketMes1Layout.setHorizontalGroup(
            PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel7, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel36)
                    .addComponent(lbl_chamados)
                    .addComponent(jLabel40)
                    .addComponent(lbl_Ticket))
                .addGap(47, 47, 47)
                .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel38)
                    .addComponent(lbl_ChamadoAberto)
                    .addComponent(jLabel41)
                    .addComponent(lbl_TicketPendente))
                .addGap(49, 49, 49)
                .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel42)
                            .addComponent(lbl_ChamadoFechado))
                        .addGap(50, 50, 50)
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel55)
                            .addComponent(lbl_TodosChamados))
                        .addGap(23, 23, 23))
                    .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel50)
                            .addComponent(lbl_TicketAndamento))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(lbl_TicketResolvido)
                            .addComponent(jLabel52))
                        .addGap(31, 31, 31))))
        );
        PanelTicketMes1Layout.setVerticalGroup(
            PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                .addComponent(jPanel7, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                    .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING, false)
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel36)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_chamados)
                                .addGap(56, 56, 56))
                            .addGroup(javax.swing.GroupLayout.Alignment.LEADING, PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel55)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_TodosChamados)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel52)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_TicketResolvido))
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel40)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_Ticket))))
                    .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel38)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_ChamadoAberto))
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel42)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_ChamadoFechado)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGroup(PanelTicketMes1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel50)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_TicketAndamento))
                            .addGroup(PanelTicketMes1Layout.createSequentialGroup()
                                .addComponent(jLabel41)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(lbl_TicketPendente)))))
                .addContainerGap(47, Short.MAX_VALUE))
        );

        jPanel8.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel9.setBackground(new java.awt.Color(19, 29, 38));

        jLabel2.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/icons8_management_50px.png"))); // NOI18N

        jLabel21.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel21.setForeground(new java.awt.Color(255, 255, 255));
        jLabel21.setText("CHAMADOS POR ATENDENTES");

        btn_refreshAtendentes.setBackground(new java.awt.Color(0, 102, 102));
        btn_refreshAtendentes.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_update_left_rotation_25px.png"))); // NOI18N
        btn_refreshAtendentes.setToolTipText("Atualizar");
        btn_refreshAtendentes.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refreshAtendentes.setOpaque(false);
        btn_refreshAtendentes.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_refreshAtendentesMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_refreshAtendentesMouseExited(evt);
            }
        });
        btn_refreshAtendentes.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshAtendentesActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel9Layout = new javax.swing.GroupLayout(jPanel9);
        jPanel9.setLayout(jPanel9Layout);
        jPanel9Layout.setHorizontalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel2)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel21)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refreshAtendentes, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel9Layout.setVerticalGroup(
            jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel9Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel2, javax.swing.GroupLayout.Alignment.CENTER, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel9Layout.createSequentialGroup()
                        .addGroup(jPanel9Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel21, javax.swing.GroupLayout.Alignment.CENTER)
                            .addComponent(btn_refreshAtendentes))
                        .addGap(0, 0, Short.MAX_VALUE)))
                .addContainerGap())
        );

        tabelaChamadoAtendente.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {"João", "2", "30"},
                {"Maria", "4", "28"},
                {"William", "3", "50"},
                {"José", "15", "45"}
            },
            new String [] {
                "Atendentes", "Abertos", "Fechados"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane5.setViewportView(tabelaChamadoAtendente);

        javax.swing.GroupLayout jPanel8Layout = new javax.swing.GroupLayout(jPanel8);
        jPanel8.setLayout(jPanel8Layout);
        jPanel8Layout.setHorizontalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel9, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane5, javax.swing.GroupLayout.DEFAULT_SIZE, 657, Short.MAX_VALUE)
        );
        jPanel8Layout.setVerticalGroup(
            jPanel8Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel8Layout.createSequentialGroup()
                .addComponent(jPanel9, javax.swing.GroupLayout.PREFERRED_SIZE, 59, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(2, 2, 2)
                .addComponent(jScrollPane5, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        PanelAbertoxFechado.setBorder(javax.swing.BorderFactory.createLineBorder(new java.awt.Color(0, 0, 0)));

        jPanel10.setBackground(new java.awt.Color(19, 29, 38));

        jLabel20.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/PanelGerente/icons8_two_tickets_50px.png"))); // NOI18N

        jLabel30.setFont(new java.awt.Font("Tahoma", 0, 20)); // NOI18N
        jLabel30.setForeground(new java.awt.Color(255, 255, 255));
        jLabel30.setText("TICKET - POR DESENVOLVEDOR");

        btn_refreshDesenvolvedor.setBackground(new java.awt.Color(0, 102, 102));
        btn_refreshDesenvolvedor.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_update_left_rotation_25px.png"))); // NOI18N
        btn_refreshDesenvolvedor.setToolTipText("Atualizar");
        btn_refreshDesenvolvedor.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_refreshDesenvolvedor.setOpaque(false);
        btn_refreshDesenvolvedor.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_refreshDesenvolvedorMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_refreshDesenvolvedorMouseExited(evt);
            }
        });
        btn_refreshDesenvolvedor.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_refreshDesenvolvedorActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel10Layout = new javax.swing.GroupLayout(jPanel10);
        jPanel10.setLayout(jPanel10Layout);
        jPanel10Layout.setHorizontalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel10Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel20)
                .addGap(18, 18, 18)
                .addComponent(jLabel30)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addComponent(btn_refreshDesenvolvedor, javax.swing.GroupLayout.PREFERRED_SIZE, 41, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        jPanel10Layout.setVerticalGroup(
            jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel20, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel10Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel10Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                    .addComponent(btn_refreshDesenvolvedor)
                    .addComponent(jLabel30))
                .addContainerGap())
        );

        tabela_Ticket_desenvolvedor.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null},
                {null, null, null, null}
            },
            new String [] {
                "Desenvolvedor", "Resolvidos", "Andamento", "Pendente"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane4.setViewportView(tabela_Ticket_desenvolvedor);

        javax.swing.GroupLayout PanelAbertoxFechadoLayout = new javax.swing.GroupLayout(PanelAbertoxFechado);
        PanelAbertoxFechado.setLayout(PanelAbertoxFechadoLayout);
        PanelAbertoxFechadoLayout.setHorizontalGroup(
            PanelAbertoxFechadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel10, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jScrollPane4)
        );
        PanelAbertoxFechadoLayout.setVerticalGroup(
            PanelAbertoxFechadoLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(PanelAbertoxFechadoLayout.createSequentialGroup()
                .addComponent(jPanel10, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jScrollPane4, javax.swing.GroupLayout.PREFERRED_SIZE, 0, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel2Layout = new javax.swing.GroupLayout(jPanel2);
        jPanel2.setLayout(jPanel2Layout);
        jPanel2Layout.setHorizontalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PanelHoje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTicketMes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelAbertoxFechado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel2Layout.setVerticalGroup(
            jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel2Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jPanel8, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelHoje, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addGroup(jPanel2Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(PanelAbertoxFechado, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(PanelTicketMes1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap(64, Short.MAX_VALUE))
        );

        Gerencia.addTab("Tela do Gerente", jPanel2);

        jPanel11.setBackground(new java.awt.Color(19, 29, 38));

        jLabel22.setBackground(new java.awt.Color(255, 255, 255));
        jLabel22.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel22.setForeground(new java.awt.Color(255, 255, 255));
        jLabel22.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel22.setText("ABERTURA DE TICKET");

        javax.swing.GroupLayout jPanel11Layout = new javax.swing.GroupLayout(jPanel11);
        jPanel11.setLayout(jPanel11Layout);
        jPanel11Layout.setHorizontalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addComponent(jLabel22)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel11Layout.setVerticalGroup(
            jPanel11Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel11Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel22, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        jPanel12.setBorder(javax.swing.BorderFactory.createTitledBorder("Selecione Cliente:"));

        jLabel23.setText("Empresa:");

        combo_emp.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecione Empresa" }));
        combo_emp.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_empItemStateChanged(evt);
            }
        });
        combo_emp.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                combo_empMouseClicked(evt);
            }
        });

        combo_func.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Selecionar Funcionário" }));
        combo_func.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                combo_funcItemStateChanged(evt);
            }
        });

        jLabel24.setText("Cliente:");

        jLabel25.setText("Telefone:");

        jLabel26.setText("Conexão:");

        javax.swing.GroupLayout jPanel12Layout = new javax.swing.GroupLayout(jPanel12);
        jPanel12.setLayout(jPanel12Layout);
        jPanel12Layout.setHorizontalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, jPanel12Layout.createSequentialGroup()
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel23)
                    .addComponent(combo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, 220, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel24)
                    .addComponent(combo_func, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel25)
                    .addComponent(txt_telefoneTicket, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel26)
                    .addComponent(txt_conexao, javax.swing.GroupLayout.PREFERRED_SIZE, 137, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap())
        );
        jPanel12Layout.setVerticalGroup(
            jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel12Layout.createSequentialGroup()
                .addGroup(jPanel12Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel26)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_conexao, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel25)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(txt_telefoneTicket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel24)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_func, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                    .addGroup(jPanel12Layout.createSequentialGroup()
                        .addComponent(jLabel23)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(combo_emp, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(0, 21, Short.MAX_VALUE))
        );

        jPanel13.setBorder(javax.swing.BorderFactory.createTitledBorder("Descrição do ticket:"));

        jLabel31.setText("Titulo:");

        txt_titulo_ticket.setText("Digite o titulo do Ticket...");
        txt_titulo_ticket.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_titulo_ticketFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_titulo_ticketFocusLost(evt);
            }
        });

        jLabel32.setText("Descrição:");

        txt_DescTicket.setColumns(20);
        txt_DescTicket.setRows(5);
        jScrollPane2.setViewportView(txt_DescTicket);

        jLabel34.setText("Tags:");

        jPanel3.setBorder(javax.swing.BorderFactory.createTitledBorder("Logicom"));

        jLabel33.setText("Desenvolvedor:");

        jLabel35.setText("ID Cham. vinculado:");

        jLabel44.setText("Status:");

        comboStatus.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Pendente", "Andamento", "Resolvido" }));

        comboDeveloper.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Escolha o Dev." }));
        comboDeveloper.addItemListener(new java.awt.event.ItemListener() {
            public void itemStateChanged(java.awt.event.ItemEvent evt) {
                comboDeveloperItemStateChanged(evt);
            }
        });
        comboDeveloper.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                comboDeveloperMouseClicked(evt);
            }
        });

        jButton2.setText("Abrir Chamado");

        javax.swing.GroupLayout jPanel3Layout = new javax.swing.GroupLayout(jPanel3);
        jPanel3.setLayout(jPanel3Layout);
        jPanel3Layout.setHorizontalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel33)
                    .addComponent(comboDeveloper, javax.swing.GroupLayout.PREFERRED_SIZE, 164, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(18, 18, 18)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addGap(1, 1, 1)
                        .addComponent(jLabel44))
                    .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(33, 33, 33)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel35)
                    .addGroup(jPanel3Layout.createSequentialGroup()
                        .addComponent(txt_idChamado, javax.swing.GroupLayout.PREFERRED_SIZE, 100, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jButton2)))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel3Layout.setVerticalGroup(
            jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel3Layout.createSequentialGroup()
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(jLabel33)
                    .addComponent(jLabel35)
                    .addComponent(jLabel44))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(jPanel3Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_idChamado, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboStatus, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(comboDeveloper, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(jButton2))
                .addContainerGap(13, Short.MAX_VALUE))
        );

        jButton1.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_image_35px.png"))); // NOI18N
        jButton1.setText("inserir imagem");
        jButton1.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton1.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton1MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton1MouseExited(evt);
            }
        });

        btn_salvar.setBackground(new java.awt.Color(0, 102, 102));
        btn_salvar.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_save_35px.png"))); // NOI18N
        btn_salvar.setText("Salvar Ticket");
        btn_salvar.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salvarMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salvarMouseExited(evt);
            }
        });
        btn_salvar.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarActionPerformed(evt);
            }
        });

        jButton7.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_file_delete_25px.png"))); // NOI18N
        jButton7.setText("Limpar Campos");
        jButton7.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        jButton7.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                jButton7MouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                jButton7MouseExited(evt);
            }
        });

        javax.swing.GroupLayout jPanel13Layout = new javax.swing.GroupLayout(jPanel13);
        jPanel13.setLayout(jPanel13Layout);
        jPanel13Layout.setHorizontalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(txt_titulo_ticket)
                    .addComponent(txt_tag)
                    .addComponent(jPanel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jScrollPane2)
                    .addGroup(jPanel13Layout.createSequentialGroup()
                        .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel31)
                            .addComponent(jLabel32)
                            .addComponent(jLabel34)
                            .addGroup(jPanel13Layout.createSequentialGroup()
                                .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(74, 74, 74)
                                .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addComponent(btn_salvar, javax.swing.GroupLayout.PREFERRED_SIZE, 180, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addContainerGap())
        );
        jPanel13Layout.setVerticalGroup(
            jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel13Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel31)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_titulo_ticket, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jLabel32)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane2, javax.swing.GroupLayout.PREFERRED_SIZE, 177, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jLabel34)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(txt_tag, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(jPanel3, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jButton7, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addGroup(jPanel13Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(jButton1, javax.swing.GroupLayout.PREFERRED_SIZE, 45, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addComponent(btn_salvar)))
                .addContainerGap(104, Short.MAX_VALUE))
        );

        tabelaTicket.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null},
                {null, null, null, null, null, null, null, null, null, null, null, null, null}
            },
            new String [] {
                "Ticket", "Titulo", "Descrição", "Status", "Desenvolvedor", "Empresa", "Cliente", "Telefone", "Data", "Hora", "ID Chamado", "Conexão", "Tags"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        tabelaTicket.setAutoResizeMode(javax.swing.JTable.AUTO_RESIZE_OFF);
        tabelaTicket.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabelaTicketMouseClicked(evt);
            }
        });
        jScrollPane3.setViewportView(tabelaTicket);

        javax.swing.GroupLayout jPanel4Layout = new javax.swing.GroupLayout(jPanel4);
        jPanel4.setLayout(jPanel4Layout);
        jPanel4Layout.setHorizontalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel11, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addComponent(jPanel12, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addGap(18, 18, 18)
                .addComponent(jScrollPane3, javax.swing.GroupLayout.DEFAULT_SIZE, 414, Short.MAX_VALUE)
                .addContainerGap())
        );
        jPanel4Layout.setVerticalGroup(
            jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel4Layout.createSequentialGroup()
                .addComponent(jPanel11, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addGroup(jPanel4Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel4Layout.createSequentialGroup()
                        .addComponent(jPanel12, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(jPanel13, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addComponent(jScrollPane3))
                .addContainerGap())
        );

        Gerencia.addTab("Ticket", jPanel4);

        jt_Painel_User_Chamado.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {

            },
            new String [] {
                "Id", "Titulo", "Descrição", "Solução", "Empresa", "Cliente", "Tags", "Telefone", "Conexão", "Usuario Logicom", "Data Chamado", "Hora Chamado", "Status", "Editado", "Data Editado", "Hora Editado", "Cronometro", "Hora Fechado", "Data Fechado"
            }
        ) {
            boolean[] canEdit = new boolean [] {
                false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false, false
            };

            public boolean isCellEditable(int rowIndex, int columnIndex) {
                return canEdit [columnIndex];
            }
        });
        jScrollPane7.setViewportView(jt_Painel_User_Chamado);

        javax.swing.GroupLayout jPanel23Layout = new javax.swing.GroupLayout(jPanel23);
        jPanel23.setLayout(jPanel23Layout);
        jPanel23Layout.setHorizontalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7)
        );
        jPanel23Layout.setVerticalGroup(
            jPanel23Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jScrollPane7, javax.swing.GroupLayout.PREFERRED_SIZE, 335, javax.swing.GroupLayout.PREFERRED_SIZE)
        );

        jPanel24.setBorder(javax.swing.BorderFactory.createEtchedBorder());

        jLabel74.setBackground(new java.awt.Color(19, 29, 38));
        jLabel74.setFont(new java.awt.Font("Tahoma", 1, 18)); // NOI18N
        jLabel74.setForeground(new java.awt.Color(241, 117, 9));
        jLabel74.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel74.setText("Pesquisar chamado usuario");
        jLabel74.setOpaque(true);

        txt_Puser_dataIni.setText("Data inicial");
        txt_Puser_dataIni.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_Puser_dataIniFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Puser_dataIniFocusLost(evt);
            }
        });

        txt_Puser_dataFim.setText("Data final");
        txt_Puser_dataFim.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_Puser_dataFimFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Puser_dataFimFocusLost(evt);
            }
        });

        jLabel54.setText("Data periodo:");

        txt_Puser_Pesquisar.setToolTipText("Pesquisar Chamado");
        txt_Puser_Pesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_Puser_PesquisarFocusLost(evt);
            }
        });
        txt_Puser_Pesquisar.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                txt_Puser_PesquisarMouseClicked(evt);
            }
        });

        radEmpUser.setSelected(true);
        radEmpUser.setText("Empresa");

        radFuncUser.setText("Funcionário");

        radDescUser.setText("Descrição");

        radClienteUser.setText("Cliente");

        radTagsUser.setText("Tags");

        btnPesquisarUser.setBackground(new java.awt.Color(0, 102, 102));
        btnPesquisarUser.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_search_more_35px.png"))); // NOI18N
        btnPesquisarUser.setText("Pesquisar");
        btnPesquisarUser.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnPesquisarUser.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnPesquisarUserMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnPesquisarUserMouseExited(evt);
            }
        });
        btnPesquisarUser.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnPesquisarUserActionPerformed(evt);
            }
        });

        btnConverteExcel.setBackground(new java.awt.Color(0, 102, 102));
        btnConverteExcel.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_microsoft_excel_35px.png"))); // NOI18N
        btnConverteExcel.setText("Converter Excel");
        btnConverteExcel.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btnConverteExcel.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btnConverteExcelMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btnConverteExcelMouseExited(evt);
            }
        });
        btnConverteExcel.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btnConverteExcelActionPerformed(evt);
            }
        });

        javax.swing.GroupLayout jPanel24Layout = new javax.swing.GroupLayout(jPanel24);
        jPanel24.setLayout(jPanel24Layout);
        jPanel24Layout.setHorizontalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jLabel74, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addComponent(btnPesquisarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addComponent(btnConverteExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 160, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(jPanel24Layout.createSequentialGroup()
                        .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addGroup(jPanel24Layout.createSequentialGroup()
                                .addComponent(txt_Puser_dataIni, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(txt_Puser_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, 135, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addGap(80, 80, 80)
                                .addComponent(radEmpUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                                .addComponent(radFuncUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radDescUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radClienteUser)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                                .addComponent(radTagsUser))
                            .addComponent(jLabel54)
                            .addComponent(txt_Puser_Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 1144, Short.MAX_VALUE))
                        .addGap(18, 18, 18))))
        );
        jPanel24Layout.setVerticalGroup(
            jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel24Layout.createSequentialGroup()
                .addComponent(jLabel74, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(txt_Puser_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(22, 22, 22)
                .addComponent(jLabel54)
                .addGap(2, 2, 2)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(txt_Puser_dataIni, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(txt_Puser_dataFim, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(radEmpUser)
                    .addComponent(radFuncUser)
                    .addComponent(radDescUser)
                    .addComponent(radClienteUser)
                    .addComponent(radTagsUser))
                .addGap(18, 18, 18)
                .addGroup(jPanel24Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btnPesquisarUser, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btnConverteExcel, javax.swing.GroupLayout.PREFERRED_SIZE, 35, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addGap(0, 134, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout jPanel15Layout = new javax.swing.GroupLayout(jPanel15);
        jPanel15.setLayout(jPanel15Layout);
        jPanel15Layout.setHorizontalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel23, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addComponent(jPanel24, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        jPanel15Layout.setVerticalGroup(
            jPanel15Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel15Layout.createSequentialGroup()
                .addComponent(jPanel23, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(18, 18, 18)
                .addComponent(jPanel24, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        Gerencia.addTab("Relatório Chamados", jPanel15);

        jPanel1.setBackground(new java.awt.Color(231, 235, 238));

        jPanel6.setBackground(new java.awt.Color(231, 235, 238));
        jPanel6.setBorder(javax.swing.BorderFactory.createTitledBorder("Cadastrar usuario"));

        jLabel3.setText("Usuario:");

        jLabel27.setText("Senha:");

        combo_colaborador.setModel(new javax.swing.DefaultComboBoxModel<>(new String[] { "Administrador", "Colaborador", "Desenvolvedor" }));

        jLabel37.setText("Colaborador:");

        btn_salvarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btn_salvarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_save_close_28px.png"))); // NOI18N
        btn_salvarUsuario.setText("Salvar");
        btn_salvarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_salvarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_salvarUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_salvarUsuarioMouseExited(evt);
            }
        });
        btn_salvarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_salvarUsuarioActionPerformed(evt);
            }
        });

        btn_DeleteUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btn_DeleteUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_delete_28px_2.png"))); // NOI18N
        btn_DeleteUsuario.setText("Delete");
        btn_DeleteUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_DeleteUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_DeleteUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_DeleteUsuarioMouseExited(evt);
            }
        });
        btn_DeleteUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_DeleteUsuarioActionPerformed(evt);
            }
        });

        btn_LimparCampos.setBackground(new java.awt.Color(0, 102, 102));
        btn_LimparCampos.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_cancel_subscription_20px.png"))); // NOI18N
        btn_LimparCampos.setText("Limpar");
        btn_LimparCampos.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_LimparCampos.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_LimparCamposMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_LimparCamposMouseExited(evt);
            }
        });
        btn_LimparCampos.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_LimparCamposActionPerformed(evt);
            }
        });

        btn_EditarUsuario.setBackground(new java.awt.Color(0, 102, 102));
        btn_EditarUsuario.setIcon(new javax.swing.ImageIcon(getClass().getResource("/imagem/news/icons8_refresh_28px.png"))); // NOI18N
        btn_EditarUsuario.setText("Editar");
        btn_EditarUsuario.setCursor(new java.awt.Cursor(java.awt.Cursor.HAND_CURSOR));
        btn_EditarUsuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseEntered(java.awt.event.MouseEvent evt) {
                btn_EditarUsuarioMouseEntered(evt);
            }
            public void mouseExited(java.awt.event.MouseEvent evt) {
                btn_EditarUsuarioMouseExited(evt);
            }
        });
        btn_EditarUsuario.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                btn_EditarUsuarioActionPerformed(evt);
            }
        });

        txt_Pesquisar.setFont(new java.awt.Font("Tahoma", 0, 14)); // NOI18N
        txt_Pesquisar.setHorizontalAlignment(javax.swing.JTextField.CENTER);
        txt_Pesquisar.setText("Pesquisar usuário");
        txt_Pesquisar.addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                txt_PesquisarFocusGained(evt);
            }
            public void focusLost(java.awt.event.FocusEvent evt) {
                txt_PesquisarFocusLost(evt);
            }
        });
        txt_Pesquisar.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                txt_PesquisarKeyPressed(evt);
            }
        });

        javax.swing.GroupLayout jPanel6Layout = new javax.swing.GroupLayout(jPanel6);
        jPanel6.setLayout(jPanel6Layout);
        jPanel6Layout.setHorizontalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(jLabel3, javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, 171, javax.swing.GroupLayout.PREFERRED_SIZE)
                            .addComponent(jLabel27))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                        .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(jLabel37)
                            .addComponent(combo_colaborador, 0, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                    .addGroup(jPanel6Layout.createSequentialGroup()
                        .addComponent(btn_salvarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_DeleteUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_EditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(btn_LimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 120, javax.swing.GroupLayout.PREFERRED_SIZE)))
                .addGap(117, 117, 117)
                .addComponent(txt_Pesquisar, javax.swing.GroupLayout.DEFAULT_SIZE, 401, Short.MAX_VALUE)
                .addGap(118, 118, 118))
        );
        jPanel6Layout.setVerticalGroup(
            jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel6Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jLabel37)
                    .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel27)
                            .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                                .addComponent(txt_senha, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(combo_colaborador, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                                .addComponent(txt_Pesquisar, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)))
                        .addGroup(jPanel6Layout.createSequentialGroup()
                            .addComponent(jLabel3)
                            .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                            .addComponent(txt_usuario, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addGroup(jPanel6Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(btn_salvarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_DeleteUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_EditarUsuario, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE)
                    .addComponent(btn_LimparCampos, javax.swing.GroupLayout.PREFERRED_SIZE, 28, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        tabela_Usuario.setModel(new javax.swing.table.DefaultTableModel(
            new Object [][] {
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null},
                {null, null, null, null, null}
            },
            new String [] {
                "ID", "Usuario", "Senha", "Permissão", "Imagem"
            }
        ));
        tabela_Usuario.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                tabela_UsuarioMouseClicked(evt);
            }
        });
        jScrollPane1.setViewportView(tabela_Usuario);

        jPanel14.setBackground(new java.awt.Color(19, 29, 38));

        jLabel39.setBackground(new java.awt.Color(255, 255, 255));
        jLabel39.setFont(new java.awt.Font("Tahoma", 0, 36)); // NOI18N
        jLabel39.setForeground(new java.awt.Color(255, 255, 255));
        jLabel39.setHorizontalAlignment(javax.swing.SwingConstants.CENTER);
        jLabel39.setText("Cadastro de Usuários");

        javax.swing.GroupLayout jPanel14Layout = new javax.swing.GroupLayout(jPanel14);
        jPanel14.setLayout(jPanel14Layout);
        jPanel14Layout.setHorizontalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addGap(362, 362, 362)
                .addComponent(jLabel39)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );
        jPanel14Layout.setVerticalGroup(
            jPanel14Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel14Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jLabel39, javax.swing.GroupLayout.PREFERRED_SIZE, 38, Short.MAX_VALUE)
                .addContainerGap())
        );

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(jPanel14, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 1156, Short.MAX_VALUE)
                    .addComponent(jPanel6, javax.swing.GroupLayout.Alignment.TRAILING, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addContainerGap())
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addComponent(jPanel14, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jPanel6, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(jScrollPane1, javax.swing.GroupLayout.DEFAULT_SIZE, 575, Short.MAX_VALUE))
        );

        Gerencia.addTab("Cadastrar Usuario", jPanel1);

        javax.swing.GroupLayout BodyLayout = new javax.swing.GroupLayout(Body);
        Body.setLayout(BodyLayout);
        BodyLayout.setHorizontalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Gerencia)
                .addContainerGap())
        );
        BodyLayout.setVerticalGroup(
            BodyLayout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(BodyLayout.createSequentialGroup()
                .addContainerGap()
                .addComponent(Gerencia, javax.swing.GroupLayout.PREFERRED_SIZE, 799, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
        );

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(getContentPane());
        getContentPane().setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addComponent(Body, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addComponent(Body, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );

        pack();
        setLocationRelativeTo(null);
    }// </editor-fold>//GEN-END:initComponents

    private void combo_empItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_empItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {
            Empresa emp = (Empresa) combo_emp.getSelectedItem();
            FuncionarioDAO func = new FuncionarioDAO();
            DefaultComboBoxModel modelMunicipio = new DefaultComboBoxModel(func.mostrarFuncionario(emp.getId_emp()));
            combo_func.setModel(modelMunicipio);
        }
    }//GEN-LAST:event_combo_empItemStateChanged

    private void combo_funcItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_combo_funcItemStateChanged
        if (evt.getStateChange() == ItemEvent.SELECTED) {

            Funcionario func = (Funcionario) combo_func.getSelectedItem();

            txt_conexao.setText(func.getConexao());
            txt_telefoneTicket.setText(func.getTelefone());
            //ChecaTexto();

        }
    }//GEN-LAST:event_combo_funcItemStateChanged

    private void combo_empMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_combo_empMouseClicked

    }//GEN-LAST:event_combo_empMouseClicked

    private void btn_salvarActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarActionPerformed
        if (combo_emp.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Campo Empresa Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            combo_emp.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            combo_emp.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        if (combo_func.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Campo Funcionário Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            combo_func.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            combo_func.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        if (txt_telefoneTicket.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo Telefone Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_telefoneTicket.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            txt_telefoneTicket.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        if (txt_titulo_ticket.getText().equals("") || txt_titulo_ticket.getText().equals("Digite o titulo do Ticket...")) {
            txt_titulo_ticket.setBorder(new LineBorder(Color.RED));
            txt_titulo_ticket.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Titulo Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            txt_titulo_ticket.setBorder(new LineBorder(Color.lightGray));
        }

        if (txt_DescTicket.getText().equals("")) {
            txt_DescTicket.setBorder(new LineBorder(Color.RED));
            txt_DescTicket.requestFocus();
            JOptionPane.showMessageDialog(null, "Campo Chamado Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        } else {
            txt_DescTicket.setBorder(new LineBorder(Color.lightGray));
        }
        if (comboDeveloper.getSelectedIndex() == 0) {
            JOptionPane.showMessageDialog(null, "Escolha o desenvolvedor", "Aviso", JOptionPane.WARNING_MESSAGE);
            comboDeveloper.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            comboDeveloper.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }

        try {
            Ticket t = new Ticket();
            TicketDAO tdao = new TicketDAO();

            Empresa empresa = (Empresa) combo_emp.getSelectedItem();
            Funcionario funcionario = (Funcionario) combo_func.getSelectedItem();
            Object developer = comboDeveloper.getSelectedItem();
            //Object status = comboStatus.getSelectedItem();
            String Status = (String) comboStatus.getSelectedItem();
            if (Status.equals("Pendente")) {
                status = "P";
            } else if (Status.equals("Andamento")) {
                status = "A";
            } else {
                status = "R";
            }

            t.setTitulo(txt_titulo_ticket.getText());
            t.setDescricao(txt_DescTicket.getText());
            t.setTelefone(txt_telefoneTicket.getText());
            t.setConexaochamado(txt_conexao.getText());
            t.setTags(txt_tag.getText());
            t.setIdchamado(txt_idChamado.getText());

            tdao.SalvarTicket(t, empresa, funcionario, (String) developer, (String) status);

            readJTableTicket();//ATUALIZA A TABELA TICKET DEPOIS DE SALVAR
            
            readtabelaTicketDeveloper();//popula tabela ticket po desenvolvedor

            //LIMPA OS CAMPOS DEPOIS DE SALVAR
            combo_emp.setSelectedIndex(0);
            combo_func.setSelectedIndex(0);
            comboDeveloper.setSelectedIndex(0);
            comboStatus.setSelectedIndex(0);

            txt_titulo_ticket.setText("");
            txt_telefoneTicket.setText("");
            txt_DescTicket.setText("");
            txt_conexao.setText("");
            txt_tag.setText("");
            txt_idChamado.setText("");

        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e);
        }
        //JOptionPane.showMessageDialog(this, "Empresa: "+empresa+" Funcionario: "+funcionario+" Desenvolvedor: "+developer+" Status: "+status);
    }//GEN-LAST:event_btn_salvarActionPerformed

    private void comboDeveloperItemStateChanged(java.awt.event.ItemEvent evt) {//GEN-FIRST:event_comboDeveloperItemStateChanged
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDeveloperItemStateChanged

    private void comboDeveloperMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_comboDeveloperMouseClicked
        // TODO add your handling code here:
    }//GEN-LAST:event_comboDeveloperMouseClicked

    private void btnConverteExcelActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnConverteExcelActionPerformed
        exportarExcel excel = new exportarExcel();
        excel.crearArchivo(jt_Painel_User_Chamado);
    }//GEN-LAST:event_btnConverteExcelActionPerformed

    private void btnPesquisarUserActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btnPesquisarUserActionPerformed
        String dataIni = txt_Puser_dataIni.getText().trim();
        String dataFim = txt_Puser_dataFim.getText().trim();
        String campoTexto = txt_Puser_Pesquisar.getText();

        if (dataIni.equals("Data inicial") || dataFim.equals("Data final")) {
            if (radEmpUser.isSelected()) {
                String data = "nome_emp";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radFuncUser.isSelected()) {
                String data = "usuario";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radDescUser.isSelected()) {
                String data = "texto";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radClienteUser.isSelected()) {
                String data = "nome";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
            if (radTagsUser.isSelected()) {
                String data = "tags";
                pesquisar_chamadoPerfil(campoTexto, data);
            }
        } else {
            if (radEmpUser.isSelected()) {
                String data = "nome_emp";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }

            if (radFuncUser.isSelected()) {
                String data = "usuario";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }

            if (radDescUser.isSelected()) {
                String data = "tags";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }
            if (radClienteUser.isSelected()) {
                //txt_Pesquisa_Chamado.setDocument(new soNumeros());
                String data = "nome";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }
            if (radTagsUser.isSelected()) {
                String data = "tags";
                pesquisar_chamadoPerfil(campoTexto, data, dataIni, dataFim);
            }
        }
    }//GEN-LAST:event_btnPesquisarUserActionPerformed

    private void txt_Puser_PesquisarMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_txt_Puser_PesquisarMouseClicked
        /*if (txt_Puser_Pesquisar.getText().equals("Pesquisar Chamado....")) {
            txt_Puser_Pesquisar.setText("");
        } else {
            txt_Puser_Pesquisar.setText("Pesquisar Chamado....");
        }*/
    }//GEN-LAST:event_txt_Puser_PesquisarMouseClicked

    private void txt_Puser_PesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_PesquisarFocusLost
        /*if (txt_Puser_Pesquisar.getText().equals("")) {
            txt_Puser_Pesquisar.setText("Pesquisar Chamado....");
        } else {
            txt_Puser_Pesquisar.setText("");
        }*/
    }//GEN-LAST:event_txt_Puser_PesquisarFocusLost

    private void txt_Puser_dataFimFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataFimFocusLost
        if (txt_Puser_dataFim.getText().equals("")) {
            txt_Puser_dataFim.setText("Data final");
        }
    }//GEN-LAST:event_txt_Puser_dataFimFocusLost

    private void txt_Puser_dataFimFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataFimFocusGained
        if (txt_Puser_dataFim.getText().equals("Data final")) {
            txt_Puser_dataFim.setText("");
        }
    }//GEN-LAST:event_txt_Puser_dataFimFocusGained

    private void txt_Puser_dataIniFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataIniFocusLost
        if (txt_Puser_dataIni.getText().equals("")) {
            txt_Puser_dataIni.setText("Data inicial");
        }
    }//GEN-LAST:event_txt_Puser_dataIniFocusLost

    private void txt_Puser_dataIniFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_Puser_dataIniFocusGained
        if (txt_Puser_dataIni.getText().equals("Data inicial")) {
            txt_Puser_dataIni.setText("");
        }
    }//GEN-LAST:event_txt_Puser_dataIniFocusGained

    private void btn_salvarMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvarMouseEntered
        btn_salvar.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_salvarMouseEntered

    private void btn_salvarMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvarMouseExited
        btn_salvar.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_salvarMouseExited

    private void jButton1MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseEntered
        jButton1.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_jButton1MouseEntered

    private void jButton1MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton1MouseExited
        jButton1.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_jButton1MouseExited

    private void txt_titulo_ticketFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_titulo_ticketFocusGained
        if (txt_titulo_ticket.getText().equals("Digite o titulo do Ticket...")) {
            txt_titulo_ticket.setText("");
        }
    }//GEN-LAST:event_txt_titulo_ticketFocusGained

    private void txt_titulo_ticketFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_titulo_ticketFocusLost
        if (txt_titulo_ticket.getText().equals("")) {
            txt_titulo_ticket.setText("Digite o titulo do Ticket...");
        }
    }//GEN-LAST:event_txt_titulo_ticketFocusLost

    private void tabelaTicketMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabelaTicketMouseClicked
        Ticket t = new Ticket();
        TicketDAO tdao = new TicketDAO();
        int index = tabelaTicket.getSelectedRow();

        t = tdao.ListarTicket().get(index);

        txt_titulo_ticket.setText(t.getTitulo());
        txt_telefoneTicket.setText(t.getTelefone());
        txt_conexao.setText(t.getConexaochamado());
        txt_DescTicket.setText(t.getDescricao());
        txt_tag.setText(t.getTags());
        txt_idChamado.setText(t.getIdchamado());
    }//GEN-LAST:event_tabelaTicketMouseClicked

    private void btn_refreshMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshMouseEntered
        btn_refresh.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_refreshMouseEntered

    private void btn_refreshMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshMouseExited
        btn_refresh.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_refreshMouseExited

    private void btn_refreshActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshActionPerformed
        listacontadorMesChamados();
        listacontadorMesTicket();
        listaChamadosAbertoCount();
        listaChamadosFechadoCount();
        listaTodosChamados();
        listaTicketAndamento();
        listaTicketPendente();
        listaTicketResolvido();
    }//GEN-LAST:event_btn_refreshActionPerformed

    private void btn_refreshHojeMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshHojeMouseEntered
        btn_refreshHoje.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_refreshHojeMouseEntered

    private void btn_refreshHojeMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshHojeMouseExited
        btn_refreshHoje.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_refreshHojeMouseExited

    private void btn_refreshHojeActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshHojeActionPerformed
        // TODO add your handling code here:
    }//GEN-LAST:event_btn_refreshHojeActionPerformed

    private void btn_refreshAtendentesMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshAtendentesMouseEntered
        btn_refreshAtendentes.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_refreshAtendentesMouseEntered

    private void btn_refreshAtendentesMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshAtendentesMouseExited
        btn_refreshAtendentes.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_refreshAtendentesMouseExited

    private void btn_refreshAtendentesActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshAtendentesActionPerformed
        readtabelaUserAtendente();
    }//GEN-LAST:event_btn_refreshAtendentesActionPerformed

    private void btn_refreshDesenvolvedorMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshDesenvolvedorMouseEntered
        btn_refreshDesenvolvedor.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_refreshDesenvolvedorMouseEntered

    private void btn_refreshDesenvolvedorMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_refreshDesenvolvedorMouseExited
        btn_refreshDesenvolvedor.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_refreshDesenvolvedorMouseExited

    private void btn_refreshDesenvolvedorActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_refreshDesenvolvedorActionPerformed
        readtabelaTicketDeveloper();
    }//GEN-LAST:event_btn_refreshDesenvolvedorActionPerformed

    private void jButton7MouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseEntered
        jButton7.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_jButton7MouseEntered

    private void jButton7MouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_jButton7MouseExited
        jButton7.setBackground(new Color(240, 240, 240));
    }//GEN-LAST:event_jButton7MouseExited

    private void btnPesquisarUserMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPesquisarUserMouseEntered
        btnPesquisarUser.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btnPesquisarUserMouseEntered

    private void btnPesquisarUserMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnPesquisarUserMouseExited
        btnPesquisarUser.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnPesquisarUserMouseExited

    private void btnConverteExcelMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConverteExcelMouseEntered
        btnConverteExcel.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btnConverteExcelMouseEntered

    private void btnConverteExcelMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btnConverteExcelMouseExited
        btnConverteExcel.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btnConverteExcelMouseExited

    private void btn_salvarUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvarUsuarioMouseEntered
        btn_salvarUsuario.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_salvarUsuarioMouseEntered

    private void btn_salvarUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_salvarUsuarioMouseExited
        btn_salvarUsuario.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_salvarUsuarioMouseExited

    private void btn_DeleteUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_DeleteUsuarioMouseEntered
        btn_DeleteUsuario.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_DeleteUsuarioMouseEntered

    private void btn_DeleteUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_DeleteUsuarioMouseExited
        btn_DeleteUsuario.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_DeleteUsuarioMouseExited

    private void btn_EditarUsuarioMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_EditarUsuarioMouseEntered
        btn_EditarUsuario.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_EditarUsuarioMouseEntered

    private void btn_EditarUsuarioMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_EditarUsuarioMouseExited
        btn_EditarUsuario.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_EditarUsuarioMouseExited

    private void btn_LimparCamposMouseEntered(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LimparCamposMouseEntered
        btn_LimparCampos.setBackground(new Color(241, 117, 9));
    }//GEN-LAST:event_btn_LimparCamposMouseEntered

    private void btn_LimparCamposMouseExited(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_btn_LimparCamposMouseExited
        btn_LimparCampos.setBackground(new Color(0, 102, 102));
    }//GEN-LAST:event_btn_LimparCamposMouseExited

    private void txt_PesquisarFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PesquisarFocusGained
        if (txt_Pesquisar.getText().equals("Pesquisar usuário")) {
            txt_Pesquisar.setText("");
            txt_Pesquisar.setHorizontalAlignment(txt_Pesquisar.LEFT);
        }
    }//GEN-LAST:event_txt_PesquisarFocusGained

    private void btn_DeleteUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_DeleteUsuarioActionPerformed
        if (tabela_Usuario.getSelectedRow() != -1) {
            try {
                int dialogButton = JOptionPane.YES_NO_OPTION;
                String nomeUser = tabela_Usuario.getValueAt(tabela_Usuario.getSelectedRow(), 1).toString();
                int result = JOptionPane.showConfirmDialog(null, "Deseja excluir o Funcionário : " + nomeUser, "SAIR", dialogButton);
                if (result == 0) {
                    Usuario u = new Usuario();
                    UsuarioDAO udao = new UsuarioDAO();

                    //f.setId_func((int) tbl_func.getValueAt(tbl_func.getSelectedRow(), 0));
                    u.setId((int) tabela_Usuario.getValueAt(tabela_Usuario.getSelectedRow(), 0));
                    
                    btn_salvarUsuario.setEnabled(true);
                    udao.DeleteUsuario(u);
                    LimpaCamposUsuario();
                    readJTableUsuario();

                }
            } catch (Exception e) {
            }
        }
    }//GEN-LAST:event_btn_DeleteUsuarioActionPerformed

    private void btn_salvarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_salvarUsuarioActionPerformed
        if (txt_usuario.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo Telefone Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_usuario.setBorder(new LineBorder(new java.awt.Color(35, 35, 255), 1, true));
            return;
        } else {
            txt_usuario.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }
        
        if (txt_senha.getText().equals("")) {
            JOptionPane.showMessageDialog(null, "Campo Telefone Obrigatório!", "Aviso", JOptionPane.WARNING_MESSAGE);
            txt_senha.setBorder(new LineBorder(Color.RED));
            return;
        } else {
            txt_senha.setBorder(new LineBorder(Color.LIGHT_GRAY));
        }
        
        try {
            Usuario u = new Usuario();
            UsuarioDAO udao = new UsuarioDAO();
            String colaborador;

            if (combo_colaborador.getSelectedItem().equals("Administrador")) {
                colaborador = "A";
            } else if (combo_colaborador.getSelectedItem().equals("Colaborador")) {
                colaborador = "C";
            } else {
                colaborador = "D";
            }

            u.setUsuario(txt_usuario.getText());
            u.setSenha(txt_senha.getText());
            u.setPermissao(colaborador);

            udao.SalvarUsuario(u);
            LimpaCamposUsuario();
            readJTableUsuario();

        } catch (Exception e) {
            JOptionPane.showMessageDialog(null, "Erro: " + e);
        }
    }//GEN-LAST:event_btn_salvarUsuarioActionPerformed

    private void tabela_UsuarioMouseClicked(java.awt.event.MouseEvent evt) {//GEN-FIRST:event_tabela_UsuarioMouseClicked
        if (tabela_Usuario.getSelectedRow() != -1) {
            String comboColaborador = tabela_Usuario.getValueAt(tabela_Usuario.getSelectedRow(), 3).toString();

            txt_usuario.setText(tabela_Usuario.getValueAt(tabela_Usuario.getSelectedRow(), 1).toString());//Nome
            txt_senha.setText(tabela_Usuario.getValueAt(tabela_Usuario.getSelectedRow(), 2).toString());//Telefone

            if (comboColaborador.equals("A")) {
                combo_colaborador.setSelectedIndex(0);
            } else if (comboColaborador.equals("C")) {
                combo_colaborador.setSelectedIndex(1);
            } else {
                combo_colaborador.setSelectedIndex(2);
            }

            btn_salvarUsuario.setEnabled(false);
        }
    }//GEN-LAST:event_tabela_UsuarioMouseClicked

    private void btn_LimparCamposActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_LimparCamposActionPerformed
        LimpaCamposUsuario();
        btn_salvarUsuario.setEnabled(true);
    }//GEN-LAST:event_btn_LimparCamposActionPerformed

    private void btn_EditarUsuarioActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_btn_EditarUsuarioActionPerformed
        if (tabela_Usuario.getSelectedRow() != -1) {
            Usuario u = new Usuario();
            UsuarioDAO udao = new UsuarioDAO();
            String colaborador = "";

            String ler = combo_colaborador.getSelectedItem().toString();

            if (ler.equals("Administrador")) {
                colaborador = "A";
                JOptionPane.showMessageDialog(this, "A");
            } else if (ler.equals("Colaborador")) {
                colaborador = "C";
                JOptionPane.showMessageDialog(this, "C");
            } else {
                colaborador = "D";
                JOptionPane.showMessageDialog(this, "D");
            }

            u.setUsuario(txt_usuario.getText());
            u.setSenha(txt_senha.getText());
            u.setPermissao(colaborador);
            u.setId((int) tabela_Usuario.getValueAt(tabela_Usuario.getSelectedRow(), 0));
            udao.update(u);

            LimpaCamposUsuario();
            readJTableUsuario();
            btn_salvar.setEnabled(true);
        }
    }//GEN-LAST:event_btn_EditarUsuarioActionPerformed

    private void txt_PesquisarFocusLost(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_txt_PesquisarFocusLost
        if (txt_Pesquisar.getText().equals("")) {
            txt_Pesquisar.setText("Pesquisar usuário");
            txt_Pesquisar.setHorizontalAlignment(txt_Pesquisar.CENTER);
        }
    }//GEN-LAST:event_txt_PesquisarFocusLost

    private void txt_PesquisarKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_txt_PesquisarKeyPressed
        pesquisar_Usuario(txt_Pesquisar.getText());
    }//GEN-LAST:event_txt_PesquisarKeyPressed

    private void jComboBox1ActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_jComboBox1ActionPerformed
        if (jComboBox1.getSelectedIndex() == 0) {
            contadorChamadosHOJEAbertos();
            contadorChamadosHOJEFechados();
            
            lbl_1.setText("Chamados abertos");
            lbl_2.setText("Chamados Fechados");
            
            lbl3.setVisible(false);
            img3.setVisible(false);
            cont3.setVisible(false);
        } else {
            contadorTickeHOJEPendentes();
            contadorTickeHOJEAndamento();
            contadorTickeHOJEResolvido();
            
            lbl_1.setText("Tickets Pendente");
            lbl_2.setText("Tickets Andamento");
            
            lbl3.setVisible(true);
            img3.setVisible(true);
            cont3.setVisible(true);
        }
    }//GEN-LAST:event_jComboBox1ActionPerformed

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
            java.util.logging.Logger.getLogger(Gerencia2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (InstantiationException ex) {
            java.util.logging.Logger.getLogger(Gerencia2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            java.util.logging.Logger.getLogger(Gerencia2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        } catch (javax.swing.UnsupportedLookAndFeelException ex) {
            java.util.logging.Logger.getLogger(Gerencia2.class.getName()).log(java.util.logging.Level.SEVERE, null, ex);
        }
        //</editor-fold>

        /* Create and display the form */
        java.awt.EventQueue.invokeLater(new Runnable() {
            public void run() {
                new Gerencia2().setVisible(true);
            }
        });
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JPanel Body;
    private javax.swing.JTabbedPane Gerencia;
    private javax.swing.JPanel PanelAbertoxFechado;
    private javax.swing.JPanel PanelHoje;
    private javax.swing.JPanel PanelTicketMes1;
    private javax.swing.JButton btnConverteExcel;
    private javax.swing.JButton btnPesquisarUser;
    private javax.swing.JButton btn_DeleteUsuario;
    private javax.swing.JButton btn_EditarUsuario;
    private javax.swing.JButton btn_LimparCampos;
    private javax.swing.JButton btn_refresh;
    private javax.swing.JButton btn_refreshAtendentes;
    private javax.swing.JButton btn_refreshDesenvolvedor;
    private javax.swing.JButton btn_refreshHoje;
    private javax.swing.JButton btn_salvar;
    private javax.swing.JButton btn_salvarUsuario;
    private javax.swing.JComboBox<String> comboDeveloper;
    private javax.swing.JComboBox<String> comboStatus;
    private javax.swing.JComboBox<String> combo_colaborador;
    private javax.swing.JComboBox<String> combo_emp;
    private javax.swing.JComboBox<String> combo_func;
    private javax.swing.JLabel cont1;
    private javax.swing.JLabel cont2;
    private javax.swing.JLabel cont3;
    private javax.swing.JLabel img1;
    private javax.swing.JLabel img2;
    private javax.swing.JLabel img3;
    private javax.swing.JLabel img6;
    private javax.swing.JButton jButton1;
    private javax.swing.JButton jButton2;
    private javax.swing.JButton jButton7;
    private javax.swing.JComboBox<String> jComboBox1;
    private javax.swing.JLabel jLabel1;
    private javax.swing.JLabel jLabel19;
    private javax.swing.JLabel jLabel2;
    private javax.swing.JLabel jLabel20;
    private javax.swing.JLabel jLabel21;
    private javax.swing.JLabel jLabel22;
    private javax.swing.JLabel jLabel23;
    private javax.swing.JLabel jLabel24;
    private javax.swing.JLabel jLabel25;
    private javax.swing.JLabel jLabel26;
    private javax.swing.JLabel jLabel27;
    private javax.swing.JLabel jLabel28;
    private javax.swing.JLabel jLabel29;
    private javax.swing.JLabel jLabel3;
    private javax.swing.JLabel jLabel30;
    private javax.swing.JLabel jLabel31;
    private javax.swing.JLabel jLabel32;
    private javax.swing.JLabel jLabel33;
    private javax.swing.JLabel jLabel34;
    private javax.swing.JLabel jLabel35;
    private javax.swing.JLabel jLabel36;
    private javax.swing.JLabel jLabel37;
    private javax.swing.JLabel jLabel38;
    private javax.swing.JLabel jLabel39;
    private javax.swing.JLabel jLabel4;
    private javax.swing.JLabel jLabel40;
    private javax.swing.JLabel jLabel41;
    private javax.swing.JLabel jLabel42;
    private javax.swing.JLabel jLabel44;
    private javax.swing.JLabel jLabel50;
    private javax.swing.JLabel jLabel52;
    private javax.swing.JLabel jLabel54;
    private javax.swing.JLabel jLabel55;
    private javax.swing.JLabel jLabel74;
    private javax.swing.JPanel jPanel1;
    private javax.swing.JPanel jPanel10;
    private javax.swing.JPanel jPanel11;
    private javax.swing.JPanel jPanel12;
    private javax.swing.JPanel jPanel13;
    private javax.swing.JPanel jPanel14;
    private javax.swing.JPanel jPanel15;
    private javax.swing.JPanel jPanel2;
    private javax.swing.JPanel jPanel23;
    private javax.swing.JPanel jPanel24;
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
    private javax.swing.JScrollPane jScrollPane4;
    private javax.swing.JScrollPane jScrollPane5;
    private javax.swing.JScrollPane jScrollPane7;
    private javax.swing.JSeparator jSeparator1;
    private javax.swing.JSeparator jSeparator2;
    private javax.swing.JTable jt_Painel_User_Chamado;
    private javax.swing.JLabel lbl3;
    private javax.swing.JLabel lbl6;
    private javax.swing.JLabel lbl_1;
    private javax.swing.JLabel lbl_2;
    private javax.swing.JLabel lbl_ChamadoAberto;
    private javax.swing.JLabel lbl_ChamadoFechado;
    private javax.swing.JLabel lbl_Ticket;
    private javax.swing.JLabel lbl_TicketAndamento;
    private javax.swing.JLabel lbl_TicketPendente;
    private javax.swing.JLabel lbl_TicketResolvido;
    private javax.swing.JLabel lbl_TodosChamados;
    private javax.swing.JLabel lbl_chamados;
    private javax.swing.JRadioButton radClienteUser;
    private javax.swing.JRadioButton radDescUser;
    private javax.swing.JRadioButton radEmpUser;
    private javax.swing.JRadioButton radFuncUser;
    private javax.swing.JRadioButton radTagsUser;
    private javax.swing.JTable tabelaChamadoAtendente;
    private javax.swing.JTable tabelaTicket;
    private javax.swing.JTable tabela_Ticket_desenvolvedor;
    private javax.swing.JTable tabela_Usuario;
    private javax.swing.JTextArea txt_DescTicket;
    private javax.swing.JTextField txt_Pesquisar;
    private javax.swing.JTextField txt_Puser_Pesquisar;
    private javax.swing.JTextField txt_Puser_dataFim;
    private javax.swing.JTextField txt_Puser_dataIni;
    private javax.swing.JTextField txt_conexao;
    private javax.swing.JTextField txt_idChamado;
    private javax.swing.JTextField txt_senha;
    private javax.swing.JTextField txt_tag;
    private javax.swing.JTextField txt_telefoneTicket;
    private javax.swing.JTextField txt_titulo_ticket;
    private javax.swing.JTextField txt_usuario;
    // End of variables declaration//GEN-END:variables
}
