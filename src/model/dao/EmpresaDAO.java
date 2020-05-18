package model.dao;

import View.Cad_emp_view2;
import View.exclui_func_cad_View;
import connection.ConnectionFactory;
import static connection.ConnectionFactory.getConnection;
import java.io.IOException;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.DefaultComboBoxModel;
import javax.swing.JComboBox;
import javax.swing.JOptionPane;
import model.bean.Empresa;

/**
 *
 * @author Suporte-07
 */
public class EmpresaDAO {

    public Vector<Empresa> getEmp(Empresa e) {
        Vector<Empresa> list = new Vector<Empresa>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;
        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM empresa WHERE id_emp = " + e.getId_emp());

            Empresa p;
            while (rs.next()) {
                p = new Empresa(
                        rs.getInt("id_emp"),
                        rs.getString("nome_emp"),
                        rs.getString("telefone"),
                        rs.getString("cnpj"),
                        rs.getString("obs"),
                        rs.getString("ip_scef"),
                        rs.getString("mac_scef"),
                        rs.getString("con_scef"),
                        rs.getString("ip_nfce"),
                        rs.getString("mac_nfce"),
                        rs.getString("con_nfce"),
                        rs.getString("ip_nfe"),
                        rs.getString("mac_nfe"),
                        rs.getString("con_nfe"),
                        rs.getString("ip_mobile"),
                        rs.getString("ip_coletor"),
                        rs.getString("v_estoque"),
                        rs.getString("v_nfce"),
                        rs.getString("v_nfe"),
                        rs.getString("v_sisseg"),
                        rs.getString("v_checkout"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void create(Empresa e) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String msgEditar = "<html>"
                + "Empresa :"
                + "<b>"
                + e.getNome_emp()
                + "</b>"
                + " Cadastrada com Sucesso com sucesso!";

        try {
            stmt = con.prepareStatement("INSERT INTO empresa (`nome_emp`, `telefone`, `cnpj`, `obs`, `ip_scef`, `mac_scef`, `con_scef`, `ip_nfce`, `mac_nfce`, `con_nfce`, `ip_nfe`, `mac_nfe`, `con_nfe`, `ip_mobile`, `ip_coletor`, `v_estoque`, `v_nfce`, `v_nfe`, `v_sisseg`, `v_checkout`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, e.getNome_emp());
            stmt.setString(2, e.getTelefone());
            stmt.setString(3, e.getCnpj());
            stmt.setString(4, e.getObs());
            stmt.setString(5, e.getIp_scef());
            stmt.setString(6, e.getMac_scef());
            stmt.setString(7, e.getCon_scef());
            stmt.setString(8, e.getIp_nfce());
            stmt.setString(9, e.getMac_nfce());
            stmt.setString(10, e.getCon_nfce());
            stmt.setString(11, e.getIp_nfe());
            stmt.setString(12, e.getMac_nfe());
            stmt.setString(13, e.getCon_nfe());
            stmt.setString(14, e.getIp_mobile());
            stmt.setString(15, e.getIp_coletor());
            stmt.setString(16, e.getV_estoque());
            stmt.setString(17, e.getV_nfce());
            stmt.setString(18, e.getV_nfe());
            stmt.setString(19, e.getV_sisseg());
            stmt.setString(20, e.getV_checkout());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, msgEditar);
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<Empresa> ListaEmpresa() {
        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Empresa> empresas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM empresa  ORDER BY nome_emp");
            rs = stmt.executeQuery();
            while (rs.next()) {
                Empresa empresa = new Empresa(
                        rs.getInt("id_emp"),
                        rs.getString("nome_emp"),
                        rs.getString("telefone"),
                        rs.getString("cnpj"),
                        rs.getString("obs"),
                        rs.getString("ip_scef"),
                        rs.getString("mac_scef"),
                        rs.getString("con_scef"),
                        rs.getString("ip_nfce"),
                        rs.getString("mac_nfce"),
                        rs.getString("con_nfce"),
                        rs.getString("ip_nfe"),
                        rs.getString("mac_nfe"),
                        rs.getString("con_nfe"),
                        rs.getString("ip_mobile"),
                        rs.getString("ip_coletor"),
                        rs.getString("v_estoque"),
                        rs.getString("v_nfce"),
                        rs.getString("v_nfe"),
                        rs.getString("v_sisseg"),
                        rs.getString("v_checkout"));
                empresas.add(empresa);
            }
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return empresas;
    }

    public void Delete(Empresa e) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String msgEditar = "<html>"
                + "Empresa "
                + "<b>"
                + e.getNome_emp()
                + "</b>"
                + " ecluida com sucesso!";

        try {
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Deseja Excluir", "Fechar", dialogButton);
            stmt = con.prepareStatement("DELETE FROM empresa WHERE id_emp = ?");
            stmt.setInt(1, e.getId_emp());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, msgEditar);
        } catch (SQLException ex) {
            //JOptionPane.showMessageDialog(null, "Erro ao excluir: Existem Funcionários vinculados a essa empresa " + e.getNome_emp() /*+ ex*/);
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Existe funcionarios vinculados a essa empresa! \nDeseja Excluir os Funcionarios dessa empresa?", "Fechar", dialogButton);
            exclui_func_cad_View excFuncEmp = new exclui_func_cad_View(e);
            excFuncEmp.setVisible(true);

            /**
             * EXEMPLO PARA PASSAR COMBO DE UM FRAME PARA LABEL DE OUTRO FRAME *
             */
            //exclui_func_cad_View.lb_nome_emp.setText(Cad_emp_view2.Combo_Cad_emp.getSelectedItem().toString());
            exclui_func_cad_View.lb_nome_emp.setText(e.getNome_emp());
            String nome_empresa = e.getNome_emp();
            FuncionarioDAO dao = new FuncionarioDAO();
            dao.FuncEmp(nome_empresa);

        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void Conexao(String Any) {
        try {
            String osArch = System.getProperty("os.arch");//Pega a Arquitetura do sistema

            String URL = "";
            if (osArch.equals("amd64")) {//se o sistema for 64 bits executa na pasta padrão de instalação
                URL = "C:\\Program Files (x86)\\AnyDesk\\AnyDesk.exe ";
            } else {
                URL = "C:\\Program Files\\AnyDesk\\AnyDesk.exe ";//se não procura na pasta 32 bits
            }

            Runtime rt = Runtime.getRuntime();
            Process p = rt.exec(URL + Any);//executa a url do anydesk junto com a conexão que o sistema passa 
            //JOptionPane.showMessageDialog(null, URL);
            //p.waitFor();

        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
        /*catch (InterruptedException ie) {
         ie.printStackTrace();
         }*/

    }

    public void update(Empresa e) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        
        //Coloca em negrito quando a empresa é editada
        String msgEditar = "<html>"
                + "Empresa "
                + "<b>"
                + e.getNome_emp()
                + "</b>"
                + " editada com sucesso!";

        try {
            stmt = con.prepareStatement("UPDATE empresa SET nome_emp = ?, telefone = ?, cnpj = ?, obs = ?, ip_scef = ?, mac_scef = ?, con_scef = ?, ip_nfce = ?, mac_nfce = ?, con_nfce = ?, ip_nfe = ?, mac_nfe = ?, con_nfe = ?, ip_mobile = ?, ip_coletor = ?, v_estoque = ?, v_nfce = ?, v_nfe = ?, v_sisseg = ?, v_checkout = ? WHERE id_emp = ?");
            stmt.setString(1, e.getNome_emp());
            stmt.setString(2, e.getTelefone());
            stmt.setString(3, e.getCnpj());
            stmt.setString(4, e.getObs());
            stmt.setString(5, e.getIp_scef());
            stmt.setString(6, e.getMac_scef());
            stmt.setString(7, e.getCon_scef());
            stmt.setString(8, e.getIp_nfce());
            stmt.setString(9, e.getMac_nfce());
            stmt.setString(10, e.getCon_nfce());
            stmt.setString(11, e.getIp_nfe());
            stmt.setString(12, e.getMac_nfe());
            stmt.setString(13, e.getCon_nfe());
            stmt.setString(14, e.getIp_mobile());
            stmt.setString(15, e.getIp_coletor());
            stmt.setString(16, e.getV_estoque());
            stmt.setString(17, e.getV_nfce());
            stmt.setString(18, e.getV_nfe());
            stmt.setString(19, e.getV_sisseg());
            stmt.setString(20, e.getV_checkout());
            stmt.setInt(21, e.getId_emp());

            stmt.executeUpdate();
            //JOptionPane.showMessageDialog(null,"");
            JOptionPane.showMessageDialog(null, msgEditar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public void listar_empresas(JComboBox box) {
        DefaultComboBoxModel value;
        ConnectionFactory conec = new ConnectionFactory();

        Statement st = null;
        Connection con = ConnectionFactory.getConnection();
        ResultSet rs = null;

        try {
            con = conec.getConexion();
            st = con.createStatement();
            rs = st.executeQuery("select * from empresa");
            value = new DefaultComboBoxModel();
            box.setModel(value);
            while (rs.next()) {
                value.addElement(new Empresa(
                        rs.getInt(1),
                        rs.getString(2),
                        rs.getString(3),
                        rs.getString(4),
                        rs.getString(5),
                        rs.getString(6),
                        rs.getString(7),
                        rs.getString(8),
                        rs.getString(9),
                        rs.getString(10),
                        rs.getString(11),
                        rs.getString(12),
                        rs.getString(13),
                        rs.getString(14),
                        rs.getString(15),
                        rs.getString(16),
                        rs.getString(17),
                        rs.getString(18),
                        rs.getString(19),
                        rs.getString(21),
                        rs.getString(22)));
            }
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        } catch (Exception ex) {
            System.out.println(ex.getMessage());
        } finally {
            try {
                rs.close();
                con.close();
            } catch (Exception ex) {
            }
        }
    }

    public Vector<Empresa> mostrarEmpresas() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        Vector<Empresa> datos = new Vector<Empresa>();
        Empresa dat = null;
        try {

            String sql = "SELECT * FROM empresa ORDER BY nome_emp";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Empresa();
            dat.setId_emp(0);
            dat.setNome_emp("Selecione Empresa");
            datos.add(dat);

            while (rs.next()) {
                dat = new Empresa();
                dat.setId_emp(rs.getInt("id_emp"));
                dat.setNome_emp(rs.getString("nome_emp"));
                datos.add(dat);
                //JOptionPane.showMessageDialog(null, datos);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
}
