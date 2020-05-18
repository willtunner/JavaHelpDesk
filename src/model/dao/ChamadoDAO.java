package model.dao;

import static View.Principal3.txt_Solucao;
import connection.ConnectionFactory;
import ds.desktop.notify.DesktopNotify;
import java.awt.Color;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Vector;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.border.LineBorder;
import model.bean.Chamado;
import model.bean.Empresa;
import model.bean.Funcionario;

/**
 *
 * @author William
 */
public class ChamadoDAO {

    public void Cad_Chamado(Empresa e, Funcionario f, Chamado c, String user) throws ParseException {

        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());
        String dataEditado = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss").format(new Date());

        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        try {
            stmt = con.prepareStatement("INSERT INTO chamado (`nome_emp`, `nome`, `fone`, `titulo`, `texto`, `tags`, `solucao`, `conexao`, `usuario`, `dataChamado`, `hora` , `status`, `editado`, `dataEditado`, `horaeditado`, `cronometro`, `hora_fechar_chamado`, `data_chamado_fechado`) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, CURRENT_TIME(), ?, ?, ?, ?, ?, ? ,?)");
            stmt.setString(1, e.getNome_emp());
            stmt.setString(2, f.getNome());
            stmt.setString(3, c.getFone());
            stmt.setString(4, c.getTitulo());
            stmt.setString(5, c.getTexto());
            stmt.setString(6, c.getTags());
            stmt.setString(7, c.getSolucao());
            stmt.setString(8, c.getConexao());
            stmt.setString(9, user);
            stmt.setString(10, data);
            stmt.setString(11, c.getStatus());
            stmt.setString(12, user);
            stmt.setString(13, data);
            stmt.setString(14, hora);
            stmt.setTime(15, c.getCronometro());
            if (c.getStatus().equals("A")) {
                stmt.setTime(16, null);
                stmt.setDate(17, null);
            } else {
                stmt.setTime(16, java.sql.Time.valueOf(hora));//CONVERTE STRING PARA TIME
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date DataFechaChamado = sdf.parse(data);
                stmt.setDate(17, new java.sql.Date(DataFechaChamado.getTime()));//CONVERTE DATA PARA SALVAR NO BANCO
            }
            stmt.executeUpdate();

            //NOTIFICAÇÃO DO CHAMADO SALVO
            DesktopNotify.showDesktopMessage("Chamado Salvo com Sucesso",
                    "Empresa: " + e.getNome_emp()
                    + " - Funcionário: " + f.getNome()
                    + " - Telefone: " + c.getFone()
                    + " - Data " + dataEditado
                    + " - Hora: " + hora, DesktopNotify.SUCCESS, 5000L);

            txt_Solucao.setBorder(new LineBorder(Color.LIGHT_GRAY));
        } catch (SQLException ex) {
            System.out.println(ex);
            JOptionPane.showMessageDialog(null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public List<Chamado> ListarChamado() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado ORDER BY id desc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamado(String user, String data) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado WHERE usuario = '" + user + "' AND dataChamado ='" + data + "'");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamadoMes(String user, String dataSemana, String dataAtual) {
        //JOptionPane.showMessageDialog(null, user + "-" + dataSemana + "-" + dataAtual);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado  WHERE usuario = '" + user + "' AND  dataChamado BETWEEN  '" + dataSemana + "' AND '" + dataAtual + "'");

            System.out.println("SQL retorno lista semana/mes :" + stmt);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamadoSemana(String user, String dataSemana, String dataAtual) {
        //JOptionPane.showMessageDialog(null, user + "-" + dataSemana + "-" + dataAtual);
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado  WHERE usuario = '" + user + "' AND  dataChamado BETWEEN  '" + dataSemana + "' AND '" + dataAtual + "'");

            System.out.println("SQL retorno lista semana/mes :" + stmt);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamado(String id) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado WHERE id = " + id);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamadoAberto(String Usuario) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado WHERE usuario='" + Usuario + "' and STATUS='A' ");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamadoUser(String user) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado where usuario='" + user + "' ORDER BY dataChamado desc");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));

                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamadoUserAtendente() {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT DISTINCT  usuario, SUM(CASE STATUS	WHEN 'A' THEN 1 ELSE 0 END) as abertos, SUM(CASE STATUS WHEN 'F' THEN 1	ELSE 0 END) as fechados	FROM chamado GROUP BY usuario ORDER BY usuario");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();

                chamado.setUser(rs.getString("usuario"));
                chamado.setAbertos(rs.getString("abertos"));
                chamado.setFechados(rs.getString("fechados"));

                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> ListarChamado(Empresa c) {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        ResultSet rs = null;
        List<Chamado> chamadoList = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado WHERE nome_emp = '" + c.getNome_emp() + "' ORDER BY id DESC LIMIT 10");
            //JOptionPane.showMessageDialog(null, stmt);
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));

                chamadoList.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamadoList;
    }

    public List<Chamado> PesquisarChamado(String nome_emp, String data) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Chamado> chamados = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado WHERE " + data + " LIKE ? ORDER BY id desc");
            stmt.setString(1, "%" + nome_emp + "%");
            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamados.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamados;
    }

    public List<Chamado> PesquisarChamadoPerfil(String nome_emp, String data, String dataIni, String dataFim) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Chamado> chamados = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado WHERE " + data + " LIKE ? AND dataChamado BETWEEN ('" + dataIni + "') AND ('" + dataFim + "') ORDER BY dataChamado DESC");
            stmt.setString(1, "%" + nome_emp + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setId(rs.getInt("id"));
                chamado.setNome_emp(rs.getString("nome_emp"));
                chamado.setNome(rs.getString("nome"));
                chamado.setFone(rs.getString("fone"));
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamado.setSolucao(rs.getString("solucao"));
                chamado.setConexao(rs.getString("conexao"));
                chamado.setUser(rs.getString("usuario"));
                chamado.setData(rs.getDate("dataChamado"));
                chamado.setHora(rs.getString("hora"));
                chamado.setStatus(rs.getString("status"));
                chamado.setEditado(rs.getString("editado"));
                chamado.setDataeditado(rs.getString("dataEditado"));
                chamado.setHoraeditado(rs.getString("horaeditado"));
                chamado.setCronometro(rs.getTime("cronometro"));
                chamado.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                chamado.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
                chamados.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamados;
    }

    public List<Chamado> Pesquisar(String tags) {

        Connection con = ConnectionFactory.getConnection();

        PreparedStatement stmt = null;
        ResultSet rs = null;

        List<Chamado> chamados = new ArrayList<>();

        try {
            stmt = con.prepareStatement("SELECT * FROM chamado c WHERE c.tags LIKE  ? ORDER BY id desc");
            stmt.setString(1, "%" + tags + "%");

            rs = stmt.executeQuery();

            while (rs.next()) {
                Chamado chamado = new Chamado();
                chamado.setTitulo(rs.getString("titulo"));
                chamado.setTexto(rs.getString("texto"));
                chamado.setTags(rs.getString("tags"));
                chamados.add(chamado);
            }

        } catch (SQLException ex) {
            Logger.getLogger(ProdutoDAO.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt, rs);
        }
        return chamados;
    }

    public Iterable<Chamado> PesquisarChamado(String nome_emp) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void EditarChamado(Chamado c) throws ParseException {
        Connection con = ConnectionFactory.getConnection();
        PreparedStatement stmt = null;
        //PEGA DATA
        String data = new SimpleDateFormat("yyyy-MM-dd").format(new Date());
        //PEGA HORA
        String hora = new SimpleDateFormat("HH:mm:ss").format(new Date());

        //DATA EDITADO
        String dataEditado = new SimpleDateFormat("yyyy/MM/dd").format(new Date());

        String msgEditar = "<html>"
                + "Chamado( "
                + "<b>"
                + c.getNome_emp()
                + "</b>"
                + ") editado com sucesso!";

        try {
            stmt = con.prepareStatement("UPDATE chamado SET `nome_emp`= ?, `nome`= ?, `fone`= ?, `titulo`= ?, `texto`= ?, `tags`= ?, `solucao`= ?, `conexao`= ?, `usuario`= ?, `dataChamado`= ?, `hora`= ?, `status`= ?, `editado`= ?, `dataEditado`= ? , `horaeditado`= ?, `data_chamado_fechado`= ?, `hora_fechar_chamado`= ? WHERE  `id`= ?");
            stmt.setString(1, c.getNome_emp());
            stmt.setString(2, c.getNome());
            stmt.setString(3, c.getFone());
            stmt.setString(4, c.getTitulo());
            stmt.setString(5, c.getTexto());
            stmt.setString(6, c.getTags());
            stmt.setString(7, c.getSolucao());
            stmt.setString(8, c.getConexao());
            stmt.setString(9, c.getUser());
            //CONVERTE DATE UTIL PARA SQL.DATE (sem isso da erro)
            stmt.setDate(10, new java.sql.Date(c.getData().getTime()));
            stmt.setString(11, c.getHora());
            stmt.setString(12, c.getStatus());
            stmt.setString(13, c.getEditado());
            stmt.setString(14, dataEditado);
            stmt.setString(15, c.getHoraeditado());

            if (c.getStatus().equals("A")) {
                stmt.setDate(16, null);
                stmt.setTime(17, null);
            } else {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                Date DataFechaChamado = sdf.parse(data);
                stmt.setDate(16, new java.sql.Date(DataFechaChamado.getTime()));//CONVERTE DATA PARA SALVAR NO BANCO
                stmt.setTime(17, java.sql.Time.valueOf(hora));
            }

            stmt.setInt(18, c.getId());

            stmt.executeUpdate();

            JOptionPane.showMessageDialog(null, msgEditar);
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Erro ao atualizar: " + ex);
        } finally {
            ConnectionFactory.closeConnection(con, stmt);
        }
    }

    public Iterable<Chamado> ListarChamado(String user, String dataSemana, String dataAtual) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public Vector<Chamado> mostrarChamado() {
        PreparedStatement ps = null;
        ResultSet rs = null;
        Connection con = ConnectionFactory.getConnection();

        Vector<Chamado> datos = new Vector<Chamado>();
        Chamado dat = null;
        try {

            String sql = "SELECT * FROM chamado ORDER BY id desc";
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();

            dat = new Chamado();
            dat.setId(0);
            //dat.setNome_emp("Selecione Empresa");
            
            datos.add(dat);

            while (rs.next()) {
                dat = new Chamado();
                //dat.setId_emp(rs.getInt("id_emp"));
                dat.setId(rs.getInt("id"));
                dat.setNome_emp(rs.getString("nome_emp"));
                dat.setNome(rs.getString("nome"));
                dat.setFone(rs.getString("fone"));
                dat.setTitulo(rs.getString("titulo"));
                dat.setTexto(rs.getString("texto"));
                dat.setTags(rs.getString("tags"));
                dat.setSolucao(rs.getString("solucao"));
                dat.setConexao(rs.getString("conexao"));
                dat.setUser(rs.getString("usuario"));
                dat.setData(rs.getDate("dataChamado"));
                dat.setHora(rs.getString("hora"));
                dat.setStatus(rs.getString("status"));
                dat.setEditado(rs.getString("editado"));
                dat.setDataeditado(rs.getString("dataEditado"));
                dat.setHoraeditado(rs.getString("horaeditado"));
                dat.setCronometro(rs.getTime("cronometro"));
                dat.setHoraFechaChamado(rs.getTime("hora_fechar_chamado"));
                dat.setDataChamadoFechado(rs.getDate("data_chamado_fechado"));
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

