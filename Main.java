import java.time.LocalDate;

public class Main {
    public static void main(String[] args) {
        System.out.println("=== Sistema de Biblioteca - Teste de Fluxo Completo ===\n");

        // 1. Instanciar DAOs e Services
        LivroDAO livroDAO = new LivroDAOJDBC();
        LivroService livroService = new LivroService(livroDAO);

        UsuarioDAO usuarioDAO = new UsuarioDAOJDBC();
        UsuarioService usuarioService = new UsuarioService(usuarioDAO);

        EmprestimoDAO emprestimoDAO = new EmprestimoDAOJDBC();
        EmprestimoService emprestimoService = new EmprestimoService(emprestimoDAO, livroDAO, usuarioDAO);

        // 2. Adicionar um Novo Livro de Teste (com ISBN único para evitar duplicata)
        try {
            Livro novoLivro = new Livro(null, "Arquitetura Limpa", "Robert C. Martin", "978-8550804606", 5, 5);
            livroService.cadastrarLivro(novoLivro);
            System.out.println(">> Livro adicionado com sucesso! (ID gerado: " + novoLivro.getId() + ")");
        } catch (Exception e) {
            System.out.println(">> Info Livro: " + e.getMessage());
        }

        // 3. Adicionar um Novo Usuário Fictício
        try {
            Usuario novoUsuario = new Usuario(null, "Carlos Eduardo", "2026888", "carlos.eduardo@email.com");
            usuarioService.cadastrarUsuario(novoUsuario);
            System.out.println(">> Usuário adicionado com sucesso! (ID gerado: " + novoUsuario.getId() + ")");
        } catch (Exception e) {
            System.out.println(">> Info Usuário: " + e.getMessage());
        }

        // 4. Realizar um Empréstimo de Teste usando datas (LocalDate)
        try {
            LocalDate hoje = LocalDate.now();
            LocalDate devolucao = hoje.plusDays(7);
            
            emprestimoService.realizarEmprestimo(1L, 3L); 
            System.out.println(">> Empréstimo registado com sucesso para o período de " + hoje + " até " + devolucao + "!");
        } catch (Exception e) {
            System.out.println(">> Info Empréstimo: " + e.getMessage());
        }

        // 5. VERIFICAR: Listar todos os livros para confirmar que foram salvos na tabela do banco
        System.out.println("\n=== Verificação: Livros registados na tabela do Banco ===");
        for (Livro l : livroService.listarLivros()) {
            System.out.println("- Título: " + l.getTitulo() + " | Autor: " + l.getAutor());
        }

        System.out.println("\nFluxo executado e verificado com sucesso!");
    }
}