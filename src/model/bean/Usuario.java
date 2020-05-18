package model.bean;

/**
 *
 * @author William Pereira
 */
public class Usuario {

    private int id;
    private String usuario;
    private String senha;
    private String imagem;
    private String permissao;

    public Usuario(){
        
    }
    
    public Usuario(int id, String usuario, String senha, String permissao){
        this.id =  id;
        this.usuario = usuario;
        this.senha = senha;
        this.permissao = permissao;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getImagem() {
        return imagem;
    }

    public void setImagem(String imagem) {
        this.imagem = imagem;
    }

    public String getPermissao() {
        return permissao;
    }

    public void setPermissao(String permissao) {
        this.permissao = permissao;
    }

    @Override
    public String toString() {
        return "Usuario{" + "id=" + id + ", usuario=" + usuario + ", senha=" + senha + ", imagem=" + imagem + ", permissao=" + permissao + '}';
    }
}
