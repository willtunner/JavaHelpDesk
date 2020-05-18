package model.bean;

/**
 *
 * @author William
 */
public class ProBean {
    int id_pro;
    private String titulo_pro;
    private String conteudo_pro;
    byte[] arquivopdf;

    //Todos os Gets
    public int getId_pro() {
        return id_pro;
    }

    @Override
    public String toString() {
        return "ProBean{" + "titulo_pro=" + titulo_pro + '}';
    }
    
    public String getTitulo_pro() {
        return titulo_pro;
    }

    public void setId_pro(int id_pro) {
        this.id_pro = id_pro;
    }

    public String getConteudo_pro() {
        return conteudo_pro;
    }
    
    public byte[] getArquivopdf() {
        return arquivopdf;
    }
    
    //Todos os Sets
    public void setTitulo_pro(String titulo_pro) {
        this.titulo_pro = titulo_pro;
    }

    public void setConteudo_pro(String conteudo_pro) {
        this.conteudo_pro = conteudo_pro;
    }

    public void setArquivopdf(byte[] archivopdf) {
        this.arquivopdf = archivopdf;
    }
}
