DROP DATABASE IF EXISTS omnistream_db;
CREATE DATABASE IF NOT EXISTS omnistream_db;
USE omnistream_db;

-- ========================
-- CRIAÇÃO DAS TABELAS
-- ========================

CREATE TABLE `Usuario` (
  `id_usuario` int PRIMARY KEY AUTO_INCREMENT,
  `nickname` varchar(50) UNIQUE NOT NULL,
  `email` varchar(100) UNIQUE NOT NULL,
  `senha` varchar(255) NOT NULL,
  `data_cadastro` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE `Canal` (
  `id_canal` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int UNIQUE NOT NULL,
  `nome_canal` varchar(50) UNIQUE NOT NULL,
  `descricao` varchar(500),
  `data_criacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

CREATE TABLE `Live` (
  `id_live` int PRIMARY KEY AUTO_INCREMENT,
  `id_canal` int NOT NULL,
  `titulo_live` varchar(100) NOT NULL,
  `descricao` varchar(500),
  `data_inicio` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `data_fim` timestamp NULL DEFAULT NULL,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `Categoria` (
  `id_categoria` int PRIMARY KEY AUTO_INCREMENT,
  `titulo` varchar(50) UNIQUE NOT NULL,
  `descricao` varchar(200)
) ENGINE=InnoDB;

CREATE TABLE `Live_Categoria` (
  `id_live` int NOT NULL,
  `id_categoria` int NOT NULL,
  PRIMARY KEY (`id_live`, `id_categoria`)
) ENGINE=InnoDB;

CREATE TABLE `API_Externa` (
  `id_api` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `nome_servico` varchar(100) NOT NULL,
  `chave_api` varchar(255) NOT NULL,
  `data_conexao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `status` varchar(20) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `Conteudo_VOD` (
  `id_conteudo` int PRIMARY KEY AUTO_INCREMENT,
  `id_api` int NOT NULL,
  `titulo` varchar(200) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `ano` int,
  `descricao` varchar(500)
) ENGINE=InnoDB;

CREATE TABLE `Historico_Visualizacao` (
  `id_historico` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_live` int,
  `id_conteudo` int,
  `data_visualizacao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `progresso` int
) ENGINE=InnoDB;

CREATE TABLE `Inscricao` (
  `id_usuario` int NOT NULL,
  `id_canal` int NOT NULL,
  `data_inscricao` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id_usuario`, `id_canal`)
) ENGINE=InnoDB;

CREATE TABLE `Chat_mensagem` (
  `id_chat` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `id_live` int NOT NULL,
  `data_mensagem` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `conteudo` varchar(200) NOT NULL
) ENGINE=InnoDB;

CREATE TABLE `Notificacao` (
  `id_notificacao` int PRIMARY KEY AUTO_INCREMENT,
  `id_usuario` int NOT NULL,
  `conteudo` varchar(200) NOT NULL,
  `lida` boolean NOT NULL DEFAULT false,
  `data_envio` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP
) ENGINE=InnoDB;

-- Restrições de Chave Estrangeira
ALTER TABLE `Canal` ADD FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`);
ALTER TABLE `Live` ADD FOREIGN KEY (`id_canal`) REFERENCES `Canal` (`id_canal`);
ALTER TABLE `Live_Categoria` ADD FOREIGN KEY (`id_live`) REFERENCES `Live` (`id_live`);
ALTER TABLE `Live_Categoria` ADD FOREIGN KEY (`id_categoria`) REFERENCES `Categoria` (`id_categoria`);
ALTER TABLE `API_Externa` ADD FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`);
ALTER TABLE `Conteudo_VOD` ADD FOREIGN KEY (`id_api`) REFERENCES `API_Externa` (`id_api`);
ALTER TABLE `Historico_Visualizacao` ADD FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`);
ALTER TABLE `Historico_Visualizacao` ADD FOREIGN KEY (`id_live`) REFERENCES `Live` (`id_live`);
ALTER TABLE `Historico_Visualizacao` ADD FOREIGN KEY (`id_conteudo`) REFERENCES `Conteudo_VOD` (`id_conteudo`);
ALTER TABLE `Inscricao` ADD FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`);
ALTER TABLE `Inscricao` ADD FOREIGN KEY (`id_canal`) REFERENCES `Canal` (`id_canal`);
ALTER TABLE `Chat_mensagem` ADD FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`);
ALTER TABLE `Chat_mensagem` ADD FOREIGN KEY (`id_live`) REFERENCES `Live` (`id_live`);
ALTER TABLE `Notificacao` ADD FOREIGN KEY (`id_usuario`) REFERENCES `Usuario` (`id_usuario`);

-- ========================
-- INSERÇÃO DE DADOS (DML)
-- ========================

INSERT INTO `Usuario` (`nickname`, `email`, `senha`) VALUES
('joaogamer', 'joao@email.com', '$2b$12$hashdahashdahashdahashdahashdahashdahashdahashdahash1'),
('mariaplays', 'maria@email.com', '$2b$12$hashdahashdahashdahashdahashdahashdahashdahashdahash2'),
('carlos_dev', 'carlos@email.com', '$2b$12$hashdahashdahashdahashdahashdahashdahashdahashdahash3');

INSERT INTO `Canal` (`id_usuario`, `nome_canal`, `descricao`) VALUES
(1, 'JoaoGamerTV', 'Canal de games e lives'),
(2, 'MariaPlays', 'Variety streaming'),
(3, 'CarlosDev', 'Programação ao vivo');

INSERT INTO `Categoria` (`titulo`) VALUES
('Games'),
('Programação'),
('Variety');

INSERT INTO `Live` (`id_canal`, `titulo_live`, `descricao`, `status`) VALUES
(1, 'Jogando Minecraft ao vivo!', 'Survival com os inscritos', 'encerrada'),
(2, 'Variety Friday', 'Jogos variados toda sexta', 'encerrada'),
(3, 'Construindo uma API do zero', 'Live de programação', 'ativa');

INSERT INTO `Live_Categoria` (`id_live`, `id_categoria`) VALUES
(1, 1),
(2, 1),
(2, 3),
(3, 2);

INSERT INTO `API_Externa` (`id_usuario`, `nome_servico`, `chave_api`, `status`) VALUES
(1, 'Jellyfin', 'jf_abc123xyz456', 'ativa'),
(2, 'Plex', 'plx_def789uvw012', 'ativa');

INSERT INTO `Conteudo_VOD` (`id_api`, `titulo`, `tipo`, `ano`) VALUES
(1, 'Interestelar', 'filme', 2014),
(1, 'Breaking Bad', 'serie', 2008),
(2, 'Duna', 'filme', 2021);

INSERT INTO `Historico_Visualizacao` (`id_usuario`, `id_live`, `id_conteudo`, `progresso`) VALUES
(1, 1, NULL, NULL),
(1, NULL, 1, 3600),
(2, NULL, 3, 1800);

INSERT INTO `Inscricao` (`id_usuario`, `id_canal`) VALUES
(1, 2),
(1, 3),
(2, 1);

INSERT INTO `Chat_mensagem` (`id_chat`, `id_usuario`, `id_live`, `conteudo`) VALUES
(1, 2, 1, 'Que resenha!'),
(2, 3, 1, 'Pinadeira demais.'),
(3, 1, 3, 'Não sabe buildar deck award');

INSERT INTO `Notificacao` (`id_usuario`, `conteudo`, `lida`) VALUES
(1, 'MariaPlays começou uma live!', false),
(1, 'CarlosDev começou uma live!', false),
(2, 'JoaoGamerTV começou uma live!', false);

-- ========================
-- ATUALIZAÇÕES E EXCLUSÕES
-- ========================

-- Marcar todas as notificações de joaogamer como lidas
UPDATE `Notificacao`
SET `lida` = true
WHERE `id_usuario` = 1;

-- Encerrar a live ativa do carlos_dev
UPDATE `Live`
SET `status` = 'encerrada', `data_fim` = CURRENT_TIMESTAMP
WHERE `id_live` = 3;

-- Deletar uma mensagem do chat
DELETE FROM `Chat_mensagem`
WHERE `id_chat` = 1;

-- Cancelar inscrição de joaogamer no canal da mariaplays
DELETE FROM `Inscricao`
WHERE `id_usuario` = 1 AND `id_canal` = 2;

-- ========================
-- CONSULTAS (SELECTs)
-- ========================

-- Todos os usuários e seus canais
SELECT u.nickname, c.nome_canal, c.descricao
FROM Usuario u
JOIN Canal c ON c.id_usuario = u.id_usuario;

-- Notificações de joaogamer após o UPDATE
SELECT u.nickname, n.conteudo, n.lida, n.data_envio
FROM Notificacao n
JOIN Usuario u ON u.id_usuario = n.id_usuario
WHERE u.nickname = 'joaogamer';

-- Lives e suas categorias
SELECT l.titulo_live, l.status, cat.titulo AS categoria
FROM Live l
JOIN Live_Categoria lc ON lc.id_live = l.id_live
JOIN Categoria cat ON cat.id_categoria = lc.id_categoria;

-- Inscritos por canal após o DELETE
SELECT c.nome_canal, COUNT(i.id_usuario) AS total_inscritos
FROM Canal c
LEFT JOIN Inscricao i ON i.id_canal = c.id_canal
GROUP BY c.id_canal, c.nome_canal
ORDER BY total_inscritos DESC;

-- ========================
-- COMPONENTES AVANÇADOS
-- ========================

-- 1. STORED PROCEDURE
DELIMITER $$

CREATE PROCEDURE BuscarHistorico(IN p_nickname VARCHAR(50))
BEGIN
  SELECT
    u.nickname,
    COALESCE(l.titulo_live, v.titulo) AS titulo,
    CASE WHEN l.id_live IS NOT NULL THEN 'live' ELSE 'vod' END AS tipo,
    h.data_visualizacao,
    h.progresso
  FROM Historico_Visualizacao h
  JOIN Usuario u ON u.id_usuario = h.id_usuario
  LEFT JOIN Live l ON l.id_live = h.id_live
  LEFT JOIN Conteudo_VOD v ON v.id_conteudo = h.id_conteudo
  WHERE u.nickname = p_nickname;
END$$

DELIMITER ;

-- Teste do Stored Procedure
CALL BuscarHistorico('joaogamer');


-- 2. VIEW
CREATE VIEW Resumo_Canal AS
SELECT
  c.nome_canal,
  u.nickname AS dono,
  COUNT(DISTINCT i.id_usuario) AS total_inscritos,
  CASE
    WHEN EXISTS (
      SELECT 1 FROM Live l
      WHERE l.id_canal = c.id_canal AND l.status = 'ativa'
    )
    THEN 'ao vivo'
    ELSE 'offline'
  END AS status_canal
FROM Canal c
JOIN Usuario u ON u.id_usuario = c.id_usuario
LEFT JOIN Inscricao i ON i.id_canal = c.id_canal
GROUP BY c.id_canal, c.nome_canal, u.nickname;

-- Teste da View
SELECT * FROM Resumo_Canal;


-- 3. TRIGGER
DELIMITER $$

CREATE TRIGGER NotificarInscritos
AFTER INSERT ON Live
FOR EACH ROW
BEGIN
  INSERT INTO Notificacao (id_usuario, conteudo, lida)
  SELECT
    i.id_usuario,
    CONCAT((SELECT nome_canal FROM Canal WHERE id_canal = NEW.id_canal), ' começou uma live: ', NEW.titulo_live),
    false
  FROM Inscricao i
  WHERE i.id_canal = NEW.id_canal;
END$$

DELIMITER ;

-- Teste do Trigger
INSERT INTO `Live` (`id_canal`, `titulo_live`, `descricao`, `status`)
VALUES (1, 'Live de teste do trigger', 'Testando o trigger', 'ativa');

-- Verificando se a notificação foi gerada pelo Trigger
SELECT * FROM Notificacao;