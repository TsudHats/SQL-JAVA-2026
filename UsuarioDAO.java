import java.util.List;

public interface UsuarioDAO {
    void salvar(Usuario usuario);
    Usuario buscarPorId(Long id);
    List<Usuario> listarTodos();
    void atualizar(Usuario usuario);
    void deletar(Long id);
    Usuario buscarPorMatricula(String matricula);
}