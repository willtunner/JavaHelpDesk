package model.bean;

public class Funcionario {
    private int id_func;
    private String nome;
    private String telefone;
    private String email;
    private String genero;
    private int id_emp;
    private String nome_emp;
    private String conexao;
    
    public Funcionario(){}
    
    public Funcionario(int id_func, String nome, String telefone, String email, String genero, int id_emp, String nome_emp, String conexao){
        this.id_func = id_func;
        this.nome = nome;
        this.telefone = telefone;
        this.email = email;
        this.genero = genero;
        this.id_emp = id_emp;
        this.nome_emp = nome_emp;
        this.conexao = conexao;
    }
    
    public String getNome_emp() {
        return nome_emp;
    }

    public void setNome_emp(String nome_emp) {
        this.nome_emp = nome_emp;
    }

    public int getId_emp() {
        return id_emp;
    }

    public void setId_emp(int id_emp) {
        this.id_emp = id_emp;
    }

    
    public int getId_func() {
        return id_func;
    }

    public void setId_func(int id_func) {
        this.id_func = id_func;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getTelefone() {
        return telefone;
    }

    public void setTelefone(String telefone) {
        this.telefone = telefone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGenero() {
        return genero;
    }

    public void setGenero(String genero) {
        this.genero = genero;
    }
    
    public String getConexao() {
        return conexao;
    }

    public void setConexao(String conexao) {
        this.conexao = conexao;
    }
    
    @Override
    public String toString() {
        return getNome();
    }
    

    
    
}










