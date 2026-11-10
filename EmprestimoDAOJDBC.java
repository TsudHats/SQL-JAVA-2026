import java.sql.*;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class EmprestimoDAOJDBC implements EmprestimoDAO {
    private LivroDAO livroDAO = new LivroDAOJDBC();
    private UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();

    @Override
    public void salvar(Emprestimo emprestimo) {
        String sql = "INSERT INTO emprestimo (livro_id, usuario_id, data_emprestimo, data_devolucao, devolvido) VALUES (?, ?, ?, ?, ?)";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {
            stmt.setLong(1, emprestimo.getLivro().getId());
            stmt.setLong(2, emprestimo.getUsuario().getId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.setBoolean(5, emprestimo.getDevolvido());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                emprestimo.setId(rs.getLong(1));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Emprestimo buscarPorId(Long id) {
        String sql = "SELECT * FROM emprestimo WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            ResultSet rs = stmt.executeQuery();
            if (rs.next()) {
                return montarEmprestimo(rs);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public List<Emprestimo> listarTodos() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(montarEmprestimo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public void atualizar(Emprestimo emprestimo) {
        String sql = "UPDATE emprestimo SET livro_id = ?, usuario_id = ?, data_emprestimo = ?, data_devolucao = ?, devolvido = ? WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, emprestimo.getLivro().getId());
            stmt.setLong(2, emprestimo.getUsuario().getId());
            stmt.setDate(3, Date.valueOf(emprestimo.getDataEmprestimo()));
            stmt.setDate(4, Date.valueOf(emprestimo.getDataDevolucao()));
            stmt.setBoolean(5, emprestimo.getDevolvido());
            stmt.setLong(6, emprestimo.getId());
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void deletar(Long id) {
        String sql = "DELETE FROM emprestimo WHERE id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, id);
            stmt.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    @Override
    public List<Emprestimo> listarPorUsuario(Long usuarioId) {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE usuario_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, usuarioId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarEmprestimo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Emprestimo> listarPorLivro(Long livroId) {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE livro_id = ?";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setLong(1, livroId);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                lista.add(montarEmprestimo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    @Override
    public List<Emprestimo> listarPendentes() {
        List<Emprestimo> lista = new ArrayList<>();
        String sql = "SELECT * FROM emprestimo WHERE devolvido = false";
        try (Connection conn = ConnectionFactory.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {
            while (rs.next()) {
                lista.add(montarEmprestimo(rs));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return lista;
    }

    private Emprestimo montarEmprestimo(ResultSet rs) throws SQLException {
        Long id = rs.getLong("id");
        Long livroId = rs.getLong("livro_id");
        Long usuarioId = rs.getLong("usuario_id");
        LocalDate dataEmprestimo = rs.getDate("data_emprestimo").toLocalDate();
        LocalDate dataDevolucao = rs.getDate("data_devolucao").toLocalDate();
        Boolean devolvido = rs.getBoolean("devolvido");

        Livro livro = livroDAO.buscarPorId(livroId);
        Usuario usuario = usuarioDAO.buscarPorId(usuarioId);

        return new Emprestimo(id, livro, usuario, dataEmprestimo, dataDevolucao, devolvido);
    }
}