package connection;

import java.sql.*;
import javax.swing.*;
import java.sql.DriverManager;
import java.sql.Connection;

public class Conecta {
    final private String driver = "org.postgresql.Driver";
    //vocês verão ainda como efetuar conexão com diversos bancos
    //final private String url = "jdbc:postgresql://192.168.2.252:5432/bo";
    final private String url = "jdbc:postgresql://localhost:5432/HelpDesk";
    
    final private String usuario = "postgres";
    final private String senha = "1234";
    public Connection conexao; //responsavel por fazer a conexão com o banco (Conexao é o objeto e Connection é do tipo"Connection")
    public Statement statement; //responsável por abrir caminho até o meu local do banco de dados
    public ResultSet resultset; //armazena o resultado dos comandos sql
    

    public boolean conecta() {
	boolean result = true;
	try{
	   Class.forName(driver);
	   conexao = DriverManager.getConnection(url, usuario, senha);
           //JOptionPane.showMessageDialog(null, "Entrou no banco");
	}catch(ClassNotFoundException Driver){
            JOptionPane.showMessageDialog(null, "Driver não localizado: "+Driver);
            result = false;	
	}catch(SQLException Fonte){
            JOptionPane.showMessageDialog(null, "Deu erro na conexão com o Fonte de dados: "+Fonte);
            result = false;	
	}
	return result;
    }
    
}

