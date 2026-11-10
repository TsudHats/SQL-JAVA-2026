import java.util.List;

public class LivroService {
    private LivroDAO livroDAO;

    public LivroService(LivroDAO livroDAO) {
        this.livroDAO = livroDAO;
    }

    public void cadastrarLivro(Livro livro) {
        livroDAO.salvar(livro);
    }

    public Livro buscarLivro(Long id) {
        return livroDAO.buscarPorId(id);
    }

    public List<Livro> listarLivros() {
        return livroDAO.listarTodos();
    }

    public void atualizarLivro(Livro livro) {
        livroDAO.atualizar(livro);
    }

    public void removerLivro(Long id) {
        livroDAO.deletar(id);
    }
}