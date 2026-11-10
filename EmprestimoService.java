import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoService {
    private EmprestimoDAO emprestimoDAO;
    private LivroDAO livroDAO;
    private UsuarioDAO usuarioDAO;

    public EmprestimoService(EmprestimoDAO emprestimoDAO, LivroDAO livroDAO, UsuarioDAO usuarioDAO) {
        this.emprestimoDAO = emprestimoDAO;
        this.livroDAO = livroDAO;
        this.usuarioDAO = usuarioDAO;
    }

    public Emprestimo realizarEmprestimo(Long livroId, Long usuarioId) {
        Livro livro = livroDAO.buscarPorId(livroId);
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);

        if (livro != null && usuario != null && livro.getDisponibilidade() > 0) {
            Emprestimo emprestimo = new Emprestimo();
            emprestimo.setLivro(livro);
            emprestimo.setUsuario(usuario);
            emprestimo.setDataEmprestimo(LocalDate.now());
            emprestimo.setDataDevolucao(LocalDate.now().plusDays(14));
            emprestimo.setDevolvido(false);

            livro.setDisponibilidade(livro.getDisponibilidade() - 1);
            livroDAO.atualizar(livro);
            
            emprestimoDAO.salvar(emprestimo);
            return emprestimo;
        }
        return null;
    }

    public void registrarDevolucao(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);
        if (emprestimo != null && !emprestimo.getDevolvido()) {
            emprestimo.setDevolvido(true);
            Livro livro = emprestimo.getLivro();
            livro.setDisponibilidade(livro.getDisponibilidade() + 1);
            
            livroDAO.atualizar(livro);
            emprestimoDAO.atualizar(emprestimo);
        }
    }

    public List<Emprestimo> historicoUsuario(Long usuarioId) {
        return emprestimoDAO.listarPorUsuario(usuarioId);
    }

    public List<Emprestimo> listarEmprestimosAtrasados() {
        List<Emprestimo> pendentes = emprestimoDAO.listarPendentes();
        List<Emprestimo> atrasados = new ArrayList<>();
        for (Emprestimo e : pendentes) {
            if (e.isAtrasado()) {
                atrasados.add(e);
            }
        }
        return atrasados;
    }

    public Emprestimo renovarEmprestimo(Long emprestimoId) {
        Emprestimo emprestimo = emprestimoDAO.buscarPorId(emprestimoId);
        if (emprestimo != null && !emprestimo.getDevolvido() && !emprestimo.isAtrasado()) {
            emprestimo.renovar();
            emprestimoDAO.atualizar(emprestimo);
            return emprestimo;
        }
        return null;
    }
}