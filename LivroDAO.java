import java.util.List;

public interface LivroDAO {
    void salvar(Livro livro);
    Livro buscarPorId(Long id);
    List<Livro> listarTodos();
    void atualizar(Livro livro);
    void deletar(Long id);
    Livro buscarPorISBN(String isbn);
    List<Livro> listarPorAutor(String autor);
}