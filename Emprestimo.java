import java.time.LocalDate;

public class Emprestimo {
    private Long id;
    private Livro livro;
    private Usuario usuario;
    private LocalDate dataEmprestimo;
    private LocalDate dataDevolucao;
    private Boolean devolvido;

    public Emprestimo() {}

    public Emprestimo(Long id, Livro livro, Usuario usuario, LocalDate dataEmprestimo, LocalDate dataDevolucao, Boolean devolvido) {
        this.id = id;
        this.livro = livro;
        this.usuario = usuario;
        this.dataEmprestimo = dataEmprestimo;
        this.dataDevolucao = dataDevolucao;
        this.devolvido = devolvido;
    }

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Livro getLivro() { return livro; }
    public void setLivro(Livro livro) { this.livro = livro; }

    public Usuario getUsuario() { return usuario; }
    public void setUsuario(Usuario usuario) { this.usuario = usuario; }

    public LocalDate getDataEmprestimo() { return dataEmprestimo; }
    public void setDataEmprestimo(LocalDate dataEmprestimo) { this.dataEmprestimo = dataEmprestimo; }

    public LocalDate getDataDevolucao() { return dataDevolucao; }
    public void setDataDevolucao(LocalDate dataDevolucao) { this.dataDevolucao = dataDevolucao; }

    public Boolean getDevolvido() { return devolvido; }
    public void setDevolvido(Boolean devolvido) { this.devolvido = devolvido; }

    public Boolean isAtrasado() {
        if (devolvido != null && devolvido) return false;
        return LocalDate.now().isAfter(dataDevolucao);
    }

    public void renovar() {
        this.dataDevolucao = this.dataDevolucao.plusDays(7);
    }
}