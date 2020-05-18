package model.dao;

import connection.ConnectionFactory;
import ds.desktop.notify.DesktopNotify;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import model.bean.Agenda;

/**
 *
 * @author ::WILLIAM-PEREIRA::
 */
public class AgendaDAO {

    public void Inserir(Agenda a) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("INSERT INTO agenda (nome, email, telefone, endereco, observacao)VALUES(?,?,?,?,?)");
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEmail());
            stmt.setString(3, a.getEndereco());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getObservacao());
            stmt.executeUpdate();

            DesktopNotify.showDesktopMessage("Chamado Salvo com Sucesso",
                    "Contato: " + a.getNome()
                    + " - email: " + a.getTelefone()
                    + " - Endereço: " + a.getEndereco()
                    + " - Telefone " + a.getTelefone()
                    + " - Observação: " + a.getObservacao(), DesktopNotify.SUCCESS, 5000L);
            //JOptionPane.showMessageDialog(null, "Salvo com sucesso");
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, "Erro ao Salvar" + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public List<Agenda> Listar() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Agenda> agendalist = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM agenda");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Agenda agenda = new Agenda();
                agenda.setId(rs.getInt("id"));
                agenda.setNome(rs.getString("nome"));
                agenda.setEmail(rs.getString("email"));
                agenda.setTelefone(rs.getString("telefone"));
                agenda.setEndereco(rs.getString("endereco"));
                agenda.setObservacao(rs.getString("observacao"));
                agendalist.add(agenda);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return agendalist;

    }

    public List<Agenda> Pesquisar(String nome) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Agenda> agendas = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM agenda WHERE nome LIKE ?");
            stmt.setString(1, "%" + nome + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Agenda agenda = new Agenda();
                agenda.setId(rs.getInt("id"));
                agenda.setNome(rs.getString("nome"));
                agenda.setEmail(rs.getString("email"));
                agenda.setTelefone(rs.getString("telefone"));
                agenda.setEndereco(rs.getString("endereco"));
                agenda.setObservacao(rs.getString("observacao"));
                agendas.add(agenda);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }

        return agendas;

    }

    public void update(Agenda a) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("UPDATE agenda SET nome = ? ,email = ?, endereco = ?, telefone = ?, observacao = ? WHERE id = ?");
            stmt.setString(1, a.getNome());
            stmt.setString(2, a.getEmail());
            stmt.setString(3, a.getEndereco());
            stmt.setString(4, a.getTelefone());
            stmt.setString(5, a.getObservacao());
            stmt.setInt(6, a.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, "Atualizado com sucesso!");
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }

    }

    public void Delete(Agenda a) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;

        try {
            stmt = con.prepareStatement("DELETE FROM agenda WHERE id = ?");
            stmt.setInt(1, a.getId());
            stmt.executeUpdate();
            int dialogButton = JOptionPane.YES_NO_OPTION;
            JOptionPane.showConfirmDialog(null, "Deseja Excluir", "Fechar", dialogButton);

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao excluir: Existem Funcionarios vinculados a essa empresa." /*+ ex*/);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

}
