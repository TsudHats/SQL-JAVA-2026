import java.util.List;

public interface EmprestimoDAO {
    void salvar(Emprestimo emprestimo);
    Emprestimo buscarPorId(Long id);
    List<Emprestimo> listarTodos();
    void atualizar(Emprestimo emprestimo);
    void deletar(Long id);
    List<Emprestimo> listarPorUsuario(Long usuarioId);
    List<Emprestimo> listarPorLivro(Long livroId);
    List<Emprestimo> listarPendentes();
}