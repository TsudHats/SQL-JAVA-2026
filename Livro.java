public class Livro {
    private Long id;
    private String titulo;
    private String autor;
    private String isbn;
    private Integer quantidade;
    private Integer disponivel;

    public Livro() {}

    public Livro(Long id, String titulo, String autor, String isbn, Integer quantidade, Integer disponivel) {
        this.id = id;
        this.titulo = titulo;
        this.autor = autor;
        this.isbn = isbn;
        this.quantidade = quantidade;
        this.disponivel = disponivel;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getTitulo() { return titulo; }
    public void setTitulo(String titulo) { this.titulo = titulo; }

    public String getAutor() { return autor; }
    public void setAutor(String autor) { this.autor = autor; }

    public String getIsbn() { return isbn; }
    public void setIsbn(String isbn) { this.isbn = isbn; }

    public Integer getQuantidade() { return quantidade; }
    public void setQuantidade(Integer quantidade) { this.quantidade = quantidade; }

    public Integer getDisponibilidade() { return disponivel; }
    public void setDisponibilidade(Integer disponivel) { this.disponivel = disponivel; }
}