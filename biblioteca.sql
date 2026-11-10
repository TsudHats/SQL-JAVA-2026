-- ==========================================
-- 1. CRIAÇÃO E CONFIGURAÇÃO DA BASE DE DADOS
-- ==========================================
CREATE DATABASE IF NOT EXISTS biblioteca;
USE biblioteca;

-- Desativa temporariamente as chaves estrangeiras para permitir limpeza limpa se necessário
SET FOREIGN_KEY_CHECKS = 0;
DROP TABLE IF EXISTS emprestimo;
DROP TABLE IF EXISTS livro;
DROP TABLE IF EXISTS usuario;
SET FOREIGN_KEY_CHECKS = 1;

-- ==========================================
-- 2. CRIAÇÃO DAS TABELAS
-- ==========================================
CREATE TABLE livro (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    titulo VARCHAR(255) NOT NULL,
    autor VARCHAR(255) NOT NULL,
    isbn VARCHAR(50) NOT NULL UNIQUE,
    quantidade INT NOT NULL,
    disponivel INT NOT NULL
);

CREATE TABLE usuario (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    nome VARCHAR(255) NOT NULL,
    matricula VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL
);

CREATE TABLE emprestimo (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    livro_id BIGINT NOT NULL,
    usuario_id BIGINT NOT NULL,
    data_emprestimo DATE NOT NULL,
    data_devolucao DATE NOT NULL,
    devolvido BOOLEAN NOT NULL,
    CONSTRAINT fk_emprestimo_livro FOREIGN KEY (livro_id) REFERENCES livro(id),
    CONSTRAINT fk_emprestimo_usuario FOREIGN KEY (usuario_id) REFERENCES usuario(id)
);

-- ==========================================
-- 3. INSERÇÃO DE DADOS INICIAIS (SEED)
-- ==========================================
INSERT INTO livro (titulo, autor, isbn, quantidade, disponivel) VALUES 
('Java para Iniciantes', 'Professor', '978-8535902778', 3, 3),
('Dom Casmurro', 'Machado de Assis', '978-8535902785', 2, 2),
('Clean Code', 'Robert C. Martin', '978-8579360189', 4, 4);

INSERT INTO usuario (nome, matricula, email) VALUES 
('Marcos Paulo', '2026099', 'marcos@email.com'),
('Fernanda Silva', '2026100', 'fernanda@email.com');

-- Registando um empréstimo de exemplo (Livro ID 1 para o Usuário ID 1)
INSERT INTO emprestimo (livro_id, usuario_id, data_emprestimo, data_devolucao, devolvido) VALUES 
(1, 1, CURDATE(), DATE_ADD(CURDATE(), INTERVAL 7 DAY), FALSE);

-- Atualiza a quantidade disponível do livro emprestado
UPDATE livro SET disponivel = disponivel - 1 WHERE id = 1;


-- ==========================================
-- 4. CONSULTAS (SELECT) PARA TESTES E RELATÓRIOS
-- ==========================================

-- A. Listar todos os livros cadastrados
SELECT * FROM livro;

-- B. Listar todos os usuários cadastrados
SELECT * FROM usuario;

-- C. Listar empréstimos ativos juntando os nomes dos livros e dos usuários (JOIN)
SELECT 
    e.id AS emprestimo_id,
    l.titulo AS livro_titulo,
    u.nome AS usuario_nome,
    e.data_emprestimo,
    e.data_devolucao,
    e.devolvido
FROM emprestimo e
JOIN livro l ON e.livro_id = l.id
JOIN usuario u ON e.usuario_id = u.id
WHERE e.devolvido = FALSE;

-- D. Consultar livros que estão com estoque baixo ou indisponíveis
SELECT titulo, quantidade, disponivel 
FROM livro 
WHERE disponivel = 0;el