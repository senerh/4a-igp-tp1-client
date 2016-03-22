package model;

public class Mail {
    private int numeroMail;
    private String content;

    public Mail(String content, int numeroMail) {
        this.content = content;
        this.numeroMail = numeroMail;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public void setNumeroMail(int numeroMail) {
        this.numeroMail = numeroMail;
    }

    public String getContent() {
        return content;
    }

    public int getNumeroMail() {
        return numeroMail;
    }
}
