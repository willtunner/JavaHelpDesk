/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import static connection.ConnectionFactory.getConnection;
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
import javax.swing.JOptionPane;
import model.bean.Empresa;
import model.bean.Funcionario;

/**
 *
 * @author Suporte-07
 */
public class FuncionarioDAO {

    public void create(Funcionario f, Empresa e) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO funcionarios (`nome`, `telefone`, `email`, `genero`, `id_emp`, `nome_emp`, `conexao`) VALUES (?, ?, ?, ?, ?, ?, ?)");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getTelefone());
            stmt.setString(3, f.getEmail());
            stmt.setString(4, f.getGenero());
            stmt.setInt(5, e.getId_emp());
            stmt.setString(6, e.getNome_emp());
            stmt.setString(7, f.getConexao());
            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, f.getNome() + " Da " + e.getNome_emp() + " Salvo com sucesso!");
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public void dadosFunc(Funcionario f) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionarios WHERE id_func = ?");
            stmt.setInt(1, f.getId_func());
            stmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Funcionario> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionarios");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Funcionario funcionario = new Funcionario();

                funcionario.setId_func(rs.getInt("Id_func"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setGenero(rs.getString("genero"));
                funcionario.setId_emp(rs.getInt("id_emp"));
                funcionario.setNome_emp(rs.getString("nome_emp"));
                funcionario.setConexao(rs.getString("conexao"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return funcionarios;

    }

    public void Delete(Funcionario f) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM funcionarios WHERE id_func = ?");
            stmt.setInt(1, f.getId_func());
            stmt.executeUpdate();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            //JOptionPane.showConfirmDialog(null, "Deseja Excluir", "Fechar", dialogButton);
            JOptionPane.showMessageDialog(null, "Funcionario - " + f.getNome() + " Excluido com sucesso");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Empresa> readComBoboxFunc(String emp) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Empresa> empresas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionarios f INNER JOIN empxfunc x ON x.id_func = f.id_func WHERE x.id_emp =" + emp);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Empresa empresa = new Empresa();

                empresa.setId_emp(rs.getInt("id_emp"));
                empresa.setNome_emp(rs.getString("nome"));
                empresas.add(empresa);

            }
            System.out.println(rs);

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return empresas;

    }

    public ArrayList<Funcionario> getData(Empresa e) {

        ArrayList<Funcionario> list = new ArrayList<Funcionario>();
        Connection con = getConnection();
        Statement st;
        ResultSet rs;

        try {
            st = con.createStatement();
            rs = st.executeQuery("SELECT * FROM funcionarios  WHERE id_emp = " + e.getId_emp() + " ORDER BY nome");

            Funcionario p;
            while (rs.next()) {
                p = new Funcionario(
                        rs.getInt("id_func"),
                        rs.getString("nome"),
                        rs.getString("telefone"),
                        rs.getString("email"),
                        rs.getString("genero"),
                        rs.getInt("id_emp"),
                        rs.getString("nome_emp"),
                        rs.getString("conexao"));
                list.add(p);
            }

        } catch (SQLException ex) {
            Logger.getLogger(FuncionarioDAO.class.getName()).log(Level.SEVERE, null, ex);
        }
        return list;
    }

    public void update(Funcionario f) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        String msgEditar = "<html>"
                + "Funcionario( "
                + "<b>"
                + f.getNome()
                + "</b>"
                + ") editado com sucesso!";

        try {
            stmt = con.prepareStatement("UPDATE funcionarios SET `nome`= ?, `telefone`= ?, `email`= ?, `genero`=?, `conexao`= ? WHERE  `id_func`= ?");
            stmt.setString(1, f.getNome());
            stmt.setString(2, f.getTelefone());
            stmt.setString(3, f.getEmail());
            stmt.setString(4, f.getGenero());
            stmt.setString(5, f.getConexao());
            stmt.setInt(6, f.getId_func());

            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, msgEditar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public Vector<Funcionario> mostrarFuncionario(int idEstado) {

        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        Vector<Funcionario> datos = new Vector<Funcionario>();
        Funcionario dat = null;
        try {

            String sql = "SELECT * FROM funcionarios WHERE id_emp=" + idEstado + " ORDER BY nome asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Funcionario();
            dat.setId_func(idEstado);
            dat.setNome("Selecionar Funcionario");
            datos.add(dat);

            while (rs.next()) {
                dat = new Funcionario();
                dat.setId_func(rs.getInt("id_func"));
                dat.setNome(rs.getString("nome"));
                dat.setTelefone(rs.getString("telefone"));
                dat.setConexao(rs.getString("conexao"));
                datos.add(dat);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }

    public List<Funcionario> FuncEmp() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM funcionarios WHERE nome_emp=");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Funcionario funcionario = new Funcionario();

                funcionario.setId_func(rs.getInt("Id_func"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setGenero(rs.getString("genero"));
                funcionario.setId_emp(rs.getInt("id_emp"));
                funcionario.setNome_emp(rs.getString("nome_emp"));
                funcionario.setConexao(rs.getString("conexao"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return funcionarios;

    }

    public List<Funcionario> FuncEmp(String Nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Funcionario> funcionarios = new ArrayList<>();

        try {
            //JOptionPane.showMessageDialog(null, Nome+"Alerta!");
            stmt = con.prepareStatement("SELECT * FROM funcionarios WHERE nome_emp='" + Nome + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Funcionario funcionario = new Funcionario();

                funcionario.setId_func(rs.getInt("Id_func"));
                funcionario.setNome(rs.getString("nome"));
                funcionario.setTelefone(rs.getString("telefone"));
                funcionario.setEmail(rs.getString("email"));
                funcionario.setGenero(rs.getString("genero"));
                funcionario.setId_emp(rs.getInt("id_emp"));
                funcionario.setNome_emp(rs.getString("nome_emp"));
                funcionario.setConexao(rs.getString("conexao"));
                funcionarios.add(funcionario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return funcionarios;

    }
}
