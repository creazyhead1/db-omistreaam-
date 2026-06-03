import java.sql.*;
import java.util.Scanner;

public class Omistreaam {

    static Scanner sc = new Scanner(System.in);

    public static void main(String args[]) {
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/omnistream_db", "root", "root");
            Statement stm = con.createStatement();
            int entrada;
            int opcao;
            do {
                System.out.println("Menu de entrada:");
                System.out.println("1 - Usuário");
                System.out.println("2 - Canal");
                System.out.println("3 - Live");
                System.out.println("4 - Categorias");
                System.out.println("5 - Inscrição");
                System.out.println("6 - Mensagem de chat");
                System.out.println("7 - Notificações");
                System.out.println("8 - Sair");

                System.out.println();
                entrada = lerIntObrigatorio("Digite a opção desejada");

                switch (entrada) {
                    // Usuario ( INSERIR - ALTERAR - REMOVER - CONSULTA - LIVES ASSISTIDAS - PROCURAR - SAIR )
                    case 1:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Usuário");
                            System.out.println("2 - Alterar Usuário");
                            System.out.println("3 - Remover Usuário");
                            System.out.println("4 - Consultar Usuários");
                            System.out.println("5 - Lives Assistidas");
                            System.out.println("6 - Procurar Usuário");
                            System.out.println("7 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        String nickName = lerStringObrigatorio("Informe o nome de usuário que deseja cadastrar");
                                        String email = lerStringObrigatorio("Informe o email");
                                        String senha = lerStringObrigatorio("Informe o senha");
                                        String sqlInsertUsuario = " INSERT INTO Usuario (nickname, email, senha) VALUES ('" + nickName + "', '" + email + "', '" + senha + "')";
                                        stm.executeUpdate(sqlInsertUsuario);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir usuário: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Atualizar
                                    try {
                                        String nickNameBusca = lerStringObrigatorio("Informe o nome do usuário");
                                        String nickNameNovo = lerStringObrigatorio("Informe o novo nome usuário que deseja atualizar");
                                        String sqlUpdateUsuario = " UPDATE Usuario set nickname = '" + nickNameNovo + "' where nickname = '" + nickNameBusca + "'";
                                        sqlVerificador(stm.executeUpdate(sqlUpdateUsuario));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao atualizar usuário: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Deletar
                                    try {
                                        String nickNameDeletar = lerStringObrigatorio("Informe o nome de usuário que deseja remover");
                                        String sqlDeletarUsuario = " DELETE FROM Usuario where nickname = '" + nickNameDeletar + "'";
                                        sqlVerificador(stm.executeUpdate(sqlDeletarUsuario));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar usuário: " + e.getMessage());
                                    }
                                    break;
                                case 4:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Usuario ORDER BY id_usuario ");
                                    while (rs.next()) {
                                        int id_usuario = rs.getInt("id_usuario");
                                        String nickNameUsuarios = rs.getString("nickname");
                                        String emailUsuarios = rs.getString("email");
                                        System.out.println("Id: " + id_usuario + " Nome: " + nickNameUsuarios + " Email: " + emailUsuarios);
                                    }
                                    break;
                                case 5:
                                    // Buscar lives assistidas pelo usuario
                                    int idUsuario = lerIntObrigatorio("Informe o id do usuário das lives assistidas");
                                    String sqlBuscarTodosUsuarios1 = " SELECT l.id_live, l.titulo_live FROM Historico_Visualizacao h JOIN Live l ON l.id_live = h.id_live WHERE h.id_usuario = " + idUsuario;
                                    ResultSet rs4 = stm.executeQuery(sqlBuscarTodosUsuarios1);
                                    sqlVerificador(rs4);
                                    while (rs4.next()) {
                                        int idLive = rs4.getInt("id_live");
                                        String tituloLive = rs4.getString("titulo_live");
                                        System.out.println("Id: " + idLive + " Titulo: " + tituloLive);
                                    }
                                    break;
                                case 6:
                                    // Buscar Alguns
                                    String nicknameBusca = lerStringObrigatorio("Informe algum nick quem deseja procurar");
                                    String SqlConsulta1 = " SELECT * FROM Usuario where nickname like '" + nicknameBusca + "%'";
                                    ResultSet rs1 = stm.executeQuery(SqlConsulta1);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int id_usuario = rs1.getInt("id_usuario");
                                        String nickname = rs1.getString("nickname");
                                        String email1 = rs1.getString("email");
                                        System.out.println("Id: " + id_usuario + " Nome: " + nickname + " Email: " + email1);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 7);
                        break;
                    // Canal ( INSERIR - ALTERAR - REMOVER - CONSULTA - QUANTIDADE DE INSCRITOS - PROCURAR - SAIR )
                    case 2:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Canal");
                            System.out.println("2 - Alterar Canal");
                            System.out.println("3 - Remover Canal");
                            System.out.println("4 - Consultar Canais");
                            System.out.println("5 - Quantidade de Inscritos");
                            System.out.println("6 - Procurar Canal");
                            System.out.println("7 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        String nomeCanal = lerStringObrigatorio("Informe o nome do canal");
                                        int usuarioId = lerIntObrigatorio("Informe o id do usuário do canal");
                                        String descricao = lerStringObrigatorio("Informe a descrição do canal");
                                        String sqlInsertUsuario = " INSERT INTO Canal (nome_canal, id_usuario, descricao) VALUES ('" + nomeCanal + "', '" + usuarioId + "', '" + descricao + "')";
                                        stm.executeUpdate(sqlInsertUsuario);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir canal: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Atualizar
                                    try {
                                        String nomeCanalBusca = lerStringObrigatorio("Informe o nome do canal");
                                        String nomeCanalNovo = lerStringObrigatorio("Informe o novo nome do canal que deseja atualizar");
                                        String sqlUpdateUsuario = " UPDATE Canal set nome_canal = '" + nomeCanalNovo + "' where nome_canal = '" + nomeCanalBusca + "'";
                                        sqlVerificador(stm.executeUpdate(sqlUpdateUsuario));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao atualizar canal: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Deletar
                                    try {
                                        String nomeCanalDeletar = lerStringObrigatorio("Informe o nome de canal que deseja remover");
                                        String sqlDeletarUsuario = " DELETE FROM Canal where nome_canal = '" + nomeCanalDeletar + "'";
                                        sqlVerificador(stm.executeUpdate(sqlDeletarUsuario));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar canal: " + e.getMessage());
                                    }
                                    break;
                                case 4:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Canal ORDER BY id_canal ");
                                    while (rs.next()) {
                                        int id_canal = rs.getInt("id_canal");
                                        String nome_canal = rs.getString("nome_canal");
                                        int id_usuario = rs.getInt("id_usuario");
                                        String descricao1 = rs.getString("descricao");
                                        System.out.println("Id: " + id_canal + " Nome do canal: " + nome_canal + " Id do usuário: " + id_usuario + " Descrição: " + descricao1);
                                    }
                                    break;
                                case 5:
                                    // Buscar todos
                                    int idCanal4 = lerIntObrigatorio("Informe um id de canal que deseja ver a quantidade de inscritos");
                                    String sqlBuscarQuantidade = " SELECT COUNT(*) FROM Inscricao WHERE id_canal = " + idCanal4;
                                    ResultSet rs1 = stm.executeQuery(sqlBuscarQuantidade);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int quantidadeInscritos = rs1.getInt("COUNT(*)");
                                        System.out.println("Quantidade de Inscritos: " + quantidadeInscritos);
                                    }
                                    break;
                                case 6:
                                    // Buscar Alguns
                                    String nomeBusca = lerStringObrigatorio("Informe algum nome de canal que deseja procurar");
                                    String SqlConsultaCanal = " SELECT * FROM Canal where nome_canal like '" + nomeBusca + "%'";
                                    ResultSet rs2 = stm.executeQuery(SqlConsultaCanal);
                                    sqlVerificador(rs2);
                                    while (rs2.next()) {
                                        int id_canal = rs2.getInt("id_canal");
                                        String nomeCanais = rs2.getString("nome_canal");
                                        String descricaoCanais = rs2.getString("descricao");
                                        System.out.println("Id: " + id_canal + " Nome: " + nomeCanais + " Descricao: " + descricaoCanais);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 7);
                        break;
                    // Live ( INSERIR - ALTERAR - REMOVER - CONSULTA - Quantidade VIEWS - CONSULTAR VIEWERS - PROCURAR - SAIR )
                    case 3:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Live");
                            System.out.println("2 - Alterar Live");
                            System.out.println("3 - Remover Live");
                            System.out.println("4 - Consultar Lives");
                            System.out.println("5 - Quantidade de views");
                            System.out.println("6 - Consultar Viewers");
                            System.out.println("7 - Procurar Live");
                            System.out.println("8 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        int idCanalLive = lerIntObrigatorio("Informe o id do canal");
                                        String tituloLive = lerStringObrigatorio("Informe o título da live");
                                        String descricaoLive = lerStringObrigatorio("Informe a descrição da live");
                                        String statusLive = lerStringObrigatorio("Informe o status da live");
                                        String sqlInsertLive = " INSERT INTO Live (id_canal, titulo_live, descricao, status) VALUES (" + idCanalLive + ", '" + tituloLive + "', '" + descricaoLive + "', '" + statusLive + "')";
                                        stm.executeUpdate(sqlInsertLive);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir live: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Atualizar
                                    try {
                                        String tituloLiveNovo = lerStringObrigatorio("Informe o titulo da live deseja atualizar");
                                        String tituloLiveAtualizar = lerStringObrigatorio("Informe o novo título da live");
                                        String descricaoLiveAtualizar = lerStringObrigatorio("Informe o nova descrição da live ");
                                        String statusLiveAtualizar = lerStringObrigatorio("Informe o novo status da live");
                                        String sqlUpdateLive = " UPDATE Live set titulo_live = '" + tituloLiveAtualizar + "', " + "descricao = '" + descricaoLiveAtualizar + "', status = '" + statusLiveAtualizar + "' " + "where titulo_live = '" + tituloLiveNovo + "'";
                                        sqlVerificador(stm.executeUpdate(sqlUpdateLive));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao atualizar live: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Deletar
                                    try {
                                        String tituloLiveDeletar = lerStringObrigatorio("Informe o titulo da live que deseja remover");
                                        String sqlDeletarLive = " DELETE FROM Live where titulo_live = '" + tituloLiveDeletar + "'";
                                        sqlVerificador(stm.executeUpdate(sqlDeletarLive));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar live: " + e.getMessage());
                                    }
                                    break;
                                case 4:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Live ORDER BY id_live ");
                                    while (rs.next()) {
                                        int id_live = rs.getInt("id_live");
                                        String tituloLives = rs.getString("titulo_live");
                                        String statusLives = rs.getString("status");
                                        String descricaoLive1 = rs.getString("descricao");
                                        System.out.println("Id: " + id_live + " Título: " + tituloLives + " Descrição: " + descricaoLive1 + " Status: " + statusLives);
                                    }
                                    break;
                                case 5:
                                    // Buscar a quantidade de views em uma live
                                    int idLive = lerIntObrigatorio("Informe algum id de live que deseja ver a quantidade de views");
                                    String SqlConsultaLive1 = " SELECT l.titulo_live, COUNT(h.id_usuario) AS views FROM Historico_Visualizacao h JOIN Live l ON l.id_live = h.id_live WHERE h.id_live = " + idLive + " GROUP BY l.titulo_live";
                                    ResultSet rs2 = stm.executeQuery(SqlConsultaLive1);
                                    sqlVerificador(rs2);
                                    while (rs2.next()) {
                                        String tituloLives = rs2.getString("titulo_live");
                                        String views = rs2.getString("views");
                                        System.out.println("Título: " + tituloLives + " views: " + views);
                                    }
                                    break;
                                case 6:
                                    // Buscar a quantidade de views em uma live
                                    int idLive1 = lerIntObrigatorio("Informe algum id de live que deseja ver os viewers");
                                    String SqlConsultaLive2 = " SELECT u.nickname FROM Historico_Visualizacao h JOIN Usuario u ON u.id_usuario = h.id_usuario WHERE h.id_live = " + idLive1;
                                    ResultSet rs3 = stm.executeQuery(SqlConsultaLive2);
                                    sqlVerificador(rs3);
                                    while (rs3.next()) {
                                        String nickNameViewer = rs3.getString("nickname");
                                        System.out.println("Nick: " + nickNameViewer);
                                    }
                                    break;
                                case 7:
                                    // Buscar Alguns
                                    String tituloBusca = lerStringObrigatorio("Informe algum título de live que deseja procurar");
                                    String SqlConsultaLive = " SELECT * FROM Live where titulo_live like '" + tituloBusca + "%'";
                                    ResultSet rs1 = stm.executeQuery(SqlConsultaLive);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int id_live = rs1.getInt("id_live");
                                        String tituloLives = rs1.getString("titulo_live");
                                        String statusLives = rs1.getString("status");
                                        System.out.println("Id: " + id_live + " Título: " + tituloLives + " Status: " + statusLives);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 8);
                        break;
                    // Categorias ( INSERIR - ALTERAR - REMOVER - CONSULTA - PROCURAR - SAIR )
                    case 4:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Categoria");
                            System.out.println("2 - Alterar Categoria");
                            System.out.println("3 - Remover Categoria");
                            System.out.println("4 - Consultar Categorias");
                            System.out.println("5 - Procurar Categoria");
                            System.out.println("6 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        String tituloCategoria = lerStringObrigatorio("Informe o título da categoria");
                                        String descricaoCategoria = lerStringObrigatorio("Informe a descrição da categoria");
                                        String sqlInsertCategoria = " INSERT INTO Categoria (titulo, descricao) VALUES ('" + tituloCategoria + "', '" + descricaoCategoria + "')";
                                        stm.executeUpdate(sqlInsertCategoria);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir categoria: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Atualizar
                                    try {
                                        String tituloCategoriaAtualizar = lerStringObrigatorio("Informe o titulo da categoria que deseja atualizar");
                                        String tituloNovo = lerStringObrigatorio("Informe o novo título da categoria");
                                        String descricaoNova = lerStringObrigatorio("Informe a nova descrição da categoria");
                                        String sqlUpdateCategoria = " UPDATE Categoria set titulo = '" + tituloNovo + "', descricao = '" + descricaoNova + "' where titulo = '" + tituloCategoriaAtualizar + "'";
                                        sqlVerificador(stm.executeUpdate(sqlUpdateCategoria));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao atualizar categoria: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Deletar
                                    try {
                                        String tituloCategoriaDeletar = lerStringObrigatorio("Informe o titulo da categoria que deseja remover");
                                        String sqlDeletarCategoria = " DELETE FROM Categoria where titulo = '" + tituloCategoriaDeletar + "'";
                                        sqlVerificador(stm.executeUpdate(sqlDeletarCategoria));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar categoria: " + e.getMessage());
                                    }
                                    break;
                                case 4:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Categoria ORDER BY id_categoria ");
                                    while (rs.next()) {
                                        int id_categoria = rs.getInt("id_categoria");
                                        String tituloCategorias = rs.getString("titulo");
                                        String descricaoCategorias = rs.getString("descricao");
                                        System.out.println("Id: " + id_categoria + " Título: " + tituloCategorias + " Descrição: " + descricaoCategorias);
                                    }
                                    break;
                                case 5:
                                    // Buscar Alguns
                                    String tituloBuscaCategoria = lerStringObrigatorio("Informe algum título de categoria que deseja procurar");
                                    String SqlConsultaCategoria = " SELECT * FROM Categoria where titulo like '" + tituloBuscaCategoria + "%'";
                                    ResultSet rs1 = stm.executeQuery(SqlConsultaCategoria);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int id_categoria = rs1.getInt("id_categoria");
                                        String tituloCategorias = rs1.getString("titulo");
                                        String descricaoCategorias = rs1.getString("descricao");
                                        System.out.println("Id: " + id_categoria + " Título: " + tituloCategorias + " Descrição: " + descricaoCategorias);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 6);
                        break;
                    // Inscrição ( INSERIR - REMOVER - CONSULTA - PROCURAR - SAIR )
                    case 5:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Inscrição");
                            System.out.println("2 - Remover Inscrição");
                            System.out.println("3 - Consultar Inscrições");
                            System.out.println("4 - Procurar Inscrição");
                            System.out.println("5 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        int idUsuarioInscricao = lerIntObrigatorio("Informe o id do usuário");
                                        int idCanalInscricao = lerIntObrigatorio("Informe o id do canal");
                                        String sqlInsertInscricao = " INSERT INTO Inscricao (id_usuario, id_canal) VALUES (" + idUsuarioInscricao + ", " + idCanalInscricao + ")";
                                        stm.executeUpdate(sqlInsertInscricao);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir inscrição: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Deletar
                                    try {
                                        int idUsuarioInscricaoDeletar = lerIntObrigatorio("Informe o id do usuário que deseja remover a inscrição");
                                        int idCanalInscricaoDeletar = lerIntObrigatorio("Informe o id do canal que deseja remover a inscrição");
                                        String sqlDeletarInscricao = " DELETE FROM Inscricao where id_usuario = " + idUsuarioInscricaoDeletar + " and id_canal = " + idCanalInscricaoDeletar;
                                        sqlVerificador(stm.executeUpdate(sqlDeletarInscricao));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar inscrição: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Inscricao ORDER BY id_usuario ");
                                    while (rs.next()) {
                                        int id_usuario = rs.getInt("id_usuario");
                                        int id_canal = rs.getInt("id_canal");
                                        String dataInscricao = rs.getString("data_inscricao");
                                        System.out.println("Id Usuario: " + id_usuario + " Id Canal: " + id_canal + " Data: " + dataInscricao);
                                    }
                                    break;
                                case 4:
                                    // Buscar Alguns
                                    int idUsuarioBuscaInscricao = lerIntObrigatorio("Informe o id do usuário que deseja procurar");
                                    String SqlConsultaInscricao = " SELECT * FROM Inscricao where id_usuario = " + idUsuarioBuscaInscricao;
                                    ResultSet rs1 = stm.executeQuery(SqlConsultaInscricao);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int id_usuario = rs1.getInt("id_usuario");
                                        int id_canal = rs1.getInt("id_canal");
                                        String dataInscricao = rs1.getString("data_inscricao");
                                        System.out.println("Id Usuario: " + id_usuario + " Id Canal: " + id_canal + " Data: " + dataInscricao);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 5);
                        break;
                    // Mensagem de chat ( INSERIR - ALTERAR - REMOVER - CONSULTA - CONSULTE SUAS MENSAGENS - PROCURAR USUÁRIO - SAIR )
                    case 6:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Mensagem de Chat");
                            System.out.println("2 - Alterar Mensagem de Chat");
                            System.out.println("3 - Remover Mensagem de Chat");
                            System.out.println("4 - Consultar Mensagens de Chat");
                            System.out.println("5 - Consulte suas Mensagens");
                            System.out.println("6 - Procurar Mensagem de Live");
                            System.out.println("7 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        int idUsuarioChat = lerIntObrigatorio("Informe o id do usuário");
                                        int idLiveChat = lerIntObrigatorio("Informe o id da live");
                                        String conteudoChat = lerStringObrigatorio("Informe o conteúdo da mensagem");
                                        String sqlInsertChat = " INSERT INTO Chat_Mensagem (id_usuario, id_live, conteudo) VALUES (" + idUsuarioChat + ", " + idLiveChat + ", '" + conteudoChat + "')";
                                        stm.executeUpdate(sqlInsertChat);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir mensagem de chat: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Atualizar
                                    try {
                                        int idChatAtualizar = lerIntObrigatorio("Informe o id do chat");
                                        String conteudoChatNovo = lerStringObrigatorio("Informe o novo conteúdo da mensagem que deseja atualizar");
                                        String sqlUpdateChat = " UPDATE Chat_Mensagem set conteudo = '" + conteudoChatNovo + "' where id_chat = " + idChatAtualizar;
                                        sqlVerificador(stm.executeUpdate(sqlUpdateChat));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao atualizar mensagem de chat: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Deletar
                                    try {
                                        int idChatDeletar = lerIntObrigatorio("Informe o id do chat que deseja remover");
                                        String sqlDeletarChat = " DELETE FROM Chat_Mensagem where id_chat = " + idChatDeletar;
                                        sqlVerificador(stm.executeUpdate(sqlDeletarChat));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar mensagem de chat: " + e.getMessage());
                                    }
                                    break;
                                case 4:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Chat_Mensagem ORDER BY data_mensagem ");
                                    while (rs.next()) {
                                        int id_chat = rs.getInt("id_chat");
                                        int id_usuario = rs.getInt("id_usuario");
                                        int id_live = rs.getInt("id_live");
                                        String conteudoChats = rs.getString("conteudo");
                                        System.out.println("Id Mensagem: " + id_chat + " Id Live " + id_live + " Id Usuario: " + id_usuario + " Mensagem: " + conteudoChats);
                                    }
                                    break;
                                case 5:
                                    // Buscar mensagens de um usuario
                                    int idUsario = lerIntObrigatorio("Informe o id do usuário que deseja consultar os chats");
                                    String sqlBuscarChats = " SELECT * FROM Chat_Mensagem WHERE id_usuario = '" + idUsario + "' ORDER BY data_mensagem ";
                                    ResultSet rs4 = stm.executeQuery(sqlBuscarChats);
                                    sqlVerificador(rs4);
                                    while (rs4.next()) {
                                        int id_chat = rs4.getInt("id_chat");
                                        int id_usuario = rs4.getInt("id_usuario");
                                        String conteudoChats = rs4.getString("conteudo");
                                        System.out.println("Id: " + id_chat + " Id Usuario: " + id_usuario + " Mensagem: " + conteudoChats);
                                    }
                                    break;
                                case 6:
                                    // Buscar mensagens de lives
                                    int idLiveBuscaChat = lerIntObrigatorio("Informe o id da live que deseja procurar mensagens");
                                    String SqlConsultaChat = " SELECT * FROM Chat_Mensagem where id_live = " + idLiveBuscaChat;
                                    ResultSet rs1 = stm.executeQuery(SqlConsultaChat);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int id_chat = rs1.getInt("id_chat");
                                        int id_usuario = rs1.getInt("id_usuario");
                                        String conteudoChats = rs1.getString("conteudo");
                                        System.out.println("Id Mensagem: " + id_chat + " Id Usuario: " + id_usuario + " Mensagem: " + conteudoChats);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 7);
                        break;
                    // Notificações ( INSERIR - ALTERAR - REMOVER - CONSULTA - CONSULTE SUAS NOTIFICAÇÕES - PROCURAR USUÁRIO - SAIR )
                    case 7:
                        do {
                            System.out.println("Menu de Opcões");
                            System.out.println("1 - Inserir Notificação");
                            System.out.println("2 - Alterar Notificação");
                            System.out.println("3 - Remover Notificação");
                            System.out.println("4 - Consultar Notificações");
                            System.out.println("5 - Consulte suas Notificações");
                            System.out.println("6 - Procurar Notificação");
                            System.out.println("7 - Sair");

                            System.out.println();
                            opcao = lerIntObrigatorio("Digite a opção desejada");

                            switch (opcao) {
                                case 1:
                                    // Inserir
                                    try {
                                        int idUsuarioNotificacao = lerIntObrigatorio("Informe o id do usuário");
                                        String conteudoNotificacao = lerStringObrigatorio("Informe o conteúdo da notificação");
                                        String sqlInsertNotificacao = " INSERT INTO Notificacao (id_usuario, conteudo) VALUES (" + idUsuarioNotificacao + ", '" + conteudoNotificacao + "')";
                                        stm.executeUpdate(sqlInsertNotificacao);
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao inserir notificação: " + e.getMessage());
                                    }
                                    break;
                                case 2:
                                    // Atualizar
                                    try {
                                        int idNotificacaoAtualizar = lerIntObrigatorio("Informe o id da notificação");
                                        String conteudoNotificacaoNovo = lerStringObrigatorio("Informe o novo conteúdo da notificação que deseja atualizar");
                                        String sqlUpdateNotificacao = " UPDATE Notificacao set conteudo = '" + conteudoNotificacaoNovo + "' where id_notificacao = " + idNotificacaoAtualizar;
                                        sqlVerificador(stm.executeUpdate(sqlUpdateNotificacao));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao atualizar notificação: " + e.getMessage());
                                    }
                                    break;
                                case 3:
                                    // Deletar
                                    try {
                                        int idNotificacaoDeletar = lerIntObrigatorio("Informe o id da notificação que deseja remover");
                                        String sqlDeletarNotificacao = " DELETE FROM Notificacao where id_notificacao = " + idNotificacaoDeletar;
                                        sqlVerificador(stm.executeUpdate(sqlDeletarNotificacao));
                                    } catch (SQLException e) {
                                        System.out.println("Erro ao deletar notificação: " + e.getMessage());
                                    }
                                    break;
                                case 4:
                                    // Buscar todos
                                    ResultSet rs = stm.executeQuery(" SELECT * FROM Notificacao ORDER BY data_envio ");
                                    while (rs.next()) {
                                        int id_notificacao = rs.getInt("id_notificacao");
                                        int id_usuario = rs.getInt("id_usuario");
                                        String conteudoNotificacoes = rs.getString("conteudo");
                                        System.out.println("Id: " + id_notificacao + " Id Usuario: " + id_usuario + " Conteúdo: " + conteudoNotificacoes);
                                    }
                                    break;
                                case 5:
                                    // Buscar todas notificações de usuario
                                    int idUsario = lerIntObrigatorio("Informe o id do usuário que deseja consultar as notificações");
                                    String sqlBuscarTodasNotificacoes1 = " SELECT * FROM Notificacao WHERE id_usuario = '" + idUsario + "' ORDER BY data_envio ";
                                    ResultSet rs4 = stm.executeQuery(sqlBuscarTodasNotificacoes1);
                                    sqlVerificador(rs4);
                                    while (rs4.next()) {
                                        int id_notificacao = rs4.getInt("id_notificacao");
                                        int id_usuario = rs4.getInt("id_usuario");
                                        String conteudoNotificacoes = rs4.getString("conteudo");
                                        System.out.println("Id: " + id_notificacao + " Id Usuario: " + id_usuario + " Conteúdo: " + conteudoNotificacoes);
                                    }
                                    break;
                                case 6:
                                    // Buscar Alguns
                                    String conteudoNotificacao1 = lerStringObrigatorio("Informe o conteudo da notificação deseja procurar");
                                    String SqlConsultaNotificacao = " SELECT * FROM Notificacao where conteudo like '" + conteudoNotificacao1 + "%'";
                                    ResultSet rs1 = stm.executeQuery(SqlConsultaNotificacao);
                                    sqlVerificador(rs1);
                                    while (rs1.next()) {
                                        int id_notificacao = rs1.getInt("id_notificacao");
                                        int id_usuario = rs1.getInt("id_usuario");
                                        String conteudoNotificacoes = rs1.getString("conteudo");
                                        System.out.println("Id: " + id_notificacao + " Id Usuario: " + id_usuario + " Conteúdo: " + conteudoNotificacoes);
                                    }
                                    break;
                            }
                            System.out.println();
                        } while (opcao != 7);
                        break;
                }
            } while (entrada != 8);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public static void sqlVerificador(Integer linhas) {
        if (linhas == 0) {
            System.out.println("Registro não encontrado");
        } else {
            System.out.println("Operação realizada com sucesso");
        }
    }

    public static void sqlVerificador(ResultSet rs) throws SQLException {
        if (!rs.isBeforeFirst()) {
            System.out.println("Erro conteúdo não encontrado");
        }
    }


    public static String lerStringObrigatorio(String mensagem) {
        String valor = "";
        while (valor == null || valor.trim().isEmpty()) {
            System.out.println(mensagem + ": ");
            valor = sc.nextLine();
            if (valor.trim().isEmpty()) {
                System.out.println("Campo Obrigatório! Preencha o campo");
            }
        }
        return valor.trim();
    }

    public static Integer lerIntObrigatorio(String mensagem) {
        while (true) {
            System.out.println(mensagem + ": ");
            try {
                return Integer.parseInt(sc.nextLine());
            } catch (NumberFormatException e) {
                System.out.println("Insira um valor válido");
            }
        }
    }
}
