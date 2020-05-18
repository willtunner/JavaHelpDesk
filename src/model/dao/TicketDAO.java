/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model.dao;

import connection.ConnectionFactory;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Empresa;
import model.bean.Funcionario;
import model.bean.Ticket;
import model.bean.Usuario;

/**
 *
 * @author WillTunner
 */
public class TicketDAO {

    public List<Ticket> ListarTicket() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ticket> TicketList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM ticket ORDER BY idticket asc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                ticket.setIdticket(rs.getInt("idticket"));
                ticket.setTitulo(rs.getString("titulo"));
                ticket.setDescricao(rs.getString("descricao"));
                ticket.setStatus(rs.getString("status"));
                ticket.setDesenvolvedor(rs.getString("desenvolvedor"));
                ticket.setEmpresa(rs.getString("empresa"));
                ticket.setCliente(rs.getString("cliente"));
                ticket.setTelefone(rs.getString("telefone"));
                ticket.setDataticket(rs.getDate("dataticket"));
                ticket.setHoraticket(rs.getTime("horaticket"));
                ticket.setIdchamado(rs.getString("idchamado"));
                ticket.setConexaochamado(rs.getString("conexaochamado"));
                ticket.setTags(rs.getString("tags"));
                TicketList.add(ticket);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return TicketList;
    }
    
    public void SalvarTicket(Ticket t, Empresa e, Funcionario f, String developer, String Status){
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO ticket (`titulo`, `descricao`, `status`, `desenvolvedor`, `empresa`, `cliente`, `telefone`, `dataticket`, `horaticket`, `idchamado`, `conexaochamado`, `tags`) VALUES (?, ?, ?, ?, ?, ?, ?, CURDATE( ), CURTIME( ), ?, ?, ?)");
            stmt.setString(1, t.getTitulo());
            stmt.setString(2, t.getDescricao());
            stmt.setString(3, Status);
            stmt.setString(4, developer);
            stmt.setString(5, e.getNome_emp());
            stmt.setString(6, f.getNome());
            stmt.setString(7, t.getTelefone());
            stmt.setString(8, t.getIdchamado());
            stmt.setString(9, t.getConexaochamado());
            stmt.setString(10, t.getTags());
            stmt.executeUpdate();
            JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        }catch(SQLException ex){
            JOptionPane.showMessageDialog(null, ex);
        }
    }

    public List<Usuario> read() {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Usuario> usuarios = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM usuarios WHERE permissao='D' ORDER BY usuario asc");
            rs = stmt.executeQuery();

            while (rs.next()) {

                Usuario usuario = new Usuario();

                usuario.setId(rs.getInt("id_func"));
                usuario.setUsuario(rs.getString("usuario"));
                usuario.setSenha(rs.getString("senha"));
                usuario.setPermissao(rs.getString("permissao"));
                usuarios.add(usuario);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return usuarios;
    }

    public Vector<Usuario> mostrarDeveloper() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        Vector<Usuario> datos = new Vector<Usuario>();
        Usuario dat = null;
        try {

            String sql = "SELECT * FROM usuarios WHERE permissao='D' ORDER BY usuario asc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Usuario();
            dat.setId(0);
            dat.setUsuario("Selecione Desenvolvedor");
            datos.add(dat);

            while (rs.next()) {
                dat = new Usuario();
                dat.setId(rs.getInt("id_func"));
                dat.setUsuario(rs.getString("usuario"));
                datos.add(dat);
            }
            rs.close();
        } catch (SQLException ex) {
            System.err.println("Error consulta :" + ex.getMessage());
        }
        return datos;
    }
    
    public List<Ticket> ListarTicketDeveloper() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Ticket> TicketList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT DISTINCT  desenvolvedor, SUM(CASE STATUS WHEN 'A' THEN 1 ELSE 0 END) as andamento, SUM(CASE STATUS WHEN 'P' THEN 1 ELSE 0 END) as pendente,SUM(CASE STATUS WHEN 'R' THEN 1 ELSE 0 END) as resolvido FROM ticket GROUP BY desenvolvedor ORDER BY desenvolvedor");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Ticket ticket = new Ticket();
                
                ticket.setDesenvolvedor(rs.getString("desenvolvedor"));
                ticket.setAndamento(rs.getString("andamento"));
                ticket.setPendente(rs.getString("pendente"));
                ticket.setResolvido(rs.getString("resolvido"));

                TicketList.add(ticket);
            }
           
        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return TicketList;
    }
}
