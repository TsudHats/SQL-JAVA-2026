-- Seleciona a base de dados do projeto
USE biblioteca;

-- 1. Verificar se o livro inserido pelo Java apareceu na tabela
SELECT * FROM livro;

-- 2. Verificar se o usuário fictício (Carlos Eduardo) foi gravado corretamente
SELECT * FROM usuario;

-- 3. Verificar os empréstimos ativos cruzando as tabelas (Mostra o nome do livro e do usuário)
SELECT 
    e.id AS emprestimo_id,
    l.titulo AS livro_titulo,
    u.nome AS usuario_nome,
    e.data_emprestimo,
    e.data_devolucao,
    e.devolvido
FROM emprestimo e
JOIN livro l ON e.livro_id = l.id
JOIN usuario u ON e.usuario_id = u.id;