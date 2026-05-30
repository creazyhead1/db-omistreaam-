import mysql.connector
import os   
from dotenv import load_dotenv
load_dotenv()
caminho_da_pasta = os.path.dirname(os.path.abspath(__file__))
nome_do_pem = os.getenv('DB_SSL_CA')
caminho_do_pem = os.path.join(caminho_da_pasta, nome_do_pem) if nome_do_pem else None

try:
    connection = mysql.connector.connect(
        host=os.getenv('DB_HOST'),
        port=int(os.getenv('DB_PORT')),
        user=os.getenv('DB_USER'),
        password=os.getenv('DB_PASSWORD'),
        database=os.getenv('DB_NAME'),
        ssl_ca=caminho_do_pem
    )
    print("Connected to MySQL database")

except mysql.connector.Error as err:
    print(f"Error: {err}")
    exit()
def clear_screen():
    os.system('cls' if os.name == 'nt' else 'clear')
def mostrar_tabelas_do_banco(connection):
    cursor = connection.cursor()
    try:
        cursor.execute("SHOW TABLES")
        tabelas_encontradas = cursor.fetchall()
        
        print("\n=======================================")
        print("        TABELAS NO SEU BANCO")
        print("=======================================")
        for tab in tabelas_encontradas:
            print(f" 🔹 {tab[0]}")  
        print("=======================================\n")
        tabela_escolhida = input("Digite o nome de uma das tabelas acima: ").strip()
        if not tabela_escolhida:
            print("\n⚠ Operação cancelada: Nenhum nome foi digitado.")
            return None
        return tabela_escolhida
    except mysql.connector.Error as err:
        print(f"Erro ao buscar tabelas: {err}")
        return None
    finally:
        cursor.close()
def comados_MySQL_Creat_insert(connection):
    tabelas =  mostrar_tabelas_do_banco(connection)
    if tabelas is None:
        return
    cursor = connection.cursor()
    try:
        cursor.execute(f"DESCRIBE {tabelas}")
        colunas_completas = cursor.fetchall()
        colunas = [col[0] for col in colunas_completas if col[0] != 'id']
        valores = []
        for coluna in colunas:
            dado = input(f"Digite o valor para {coluna}: ")
            valores.append(dado)
        lista_de_colunas = ", ".join(colunas)
        marcadores = ", ".join(["%s"] * len(valores))
        comando_sql = f"INSERT INTO {tabelas} ({lista_de_colunas}) VALUES ({marcadores})"
        cursor.execute(comando_sql, valores)
        connection.commit()
        print("\nDados inseridos com sucesso!")
    except mysql.connector.errors.ProgrammingError:
        print(f"\n⚠ Erro: A tabela '{tabelas}' não existe no banco de dados.")
    finally:
        cursor.close()
def comandos_MySQL_SELECT_Read(connection):
    tabela = mostrar_tabelas_do_banco(connection)
    if tabela is None:
        return
    cursor = connection.cursor()
    try:
        cursor.execute(f"SELECT * FROM {tabela}")
        dados = cursor.fetchall()
        
        if len(dados) == 0:
            print(f"\nA tabela '{tabela}' está vazia ou não possui registros.")
        else:
            print(f"\n--- REGISTROS DA TABELA: {tabela} ---")
            for linha in dados:
                linha_formatada = " | ".join(str(item) for item in linha)
                print(linha_formatada)
    except mysql.connector.Error as err:
        print(f"Erro ao ler os dados: {err}")
    finally:
        cursor.close()
def comandos_MySQL_UPDATE_editar(connection):
    tabela = mostrar_tabelas_do_banco(connection)
    
    if tabela is None:
        return
    cursor = connection.cursor()
    try:
        cursor.execute(f"DESCRIBE {tabela}")
        colunas_completas = cursor.fetchall()
        coluna_id = [col[0] for col in colunas_completas if col[3] == 'PRI'][0]
        colunas = [
            col[0] for col in colunas_completas 
            if col[3] != 'PRI' and 'data' not in col[0]]
        alvo = input(f"Digite o {coluna_id} do registro que quer atualizar: ").strip()
        if not alvo:
            print("Operação cancelada: ID inválido.")
            return
        valores = []
        partes_do_set = []
        for coluna in colunas:
            dado = input(f"Digite o novo valor para {coluna}: ")
            valores.append(dado)
            partes_do_set.append(f"{coluna} = %s")
        juntar_campos = ", ".join(partes_do_set) 
        comando_sql = f"UPDATE {tabela} SET {juntar_campos} WHERE {coluna_id} = %s"
        valores.append(alvo)
        
        cursor.execute(comando_sql, valores)
        connection.commit()
        if cursor.rowcount == 0:
            print(f"\n⚠ Aviso: Nenhum registro encontrado com o {coluna_id} '{alvo}'. Nenhuma alteração foi feita.")
        else:
            print(f"\n✅ Sucesso: Registro com {coluna_id} '{alvo}' atualizado com sucesso!")
    except mysql.connector.Error as err:
        print(f"Erro ao atualizar registro: {err}")
    finally:
        cursor.close()
def comandos_MySQL_delete(connection):
    tabela = mostrar_tabelas_do_banco(connection)
    if tabela is None:
        return
    cursor = connection.cursor()
    try:
        cursor.execute(f"DESCRIBE {tabela}") 
        colunas_completas = cursor.fetchall()
        coluna_id = [col[0] for col in colunas_completas if col[3] == 'PRI'][0]
        print(f"\n--- Colunas disponíveis em {tabela} ---")
        for col in colunas_completas:
            print(f"-> {col[0]}")    
        alvo = input(f"\nDigite o {coluna_id} do registro que quer deletar: ").strip()
        if not alvo:
            print("Operação cancelada: ID inválido.")
            return   
        comando_sql = f"DELETE FROM {tabela} WHERE {coluna_id} = %s"
        valores = [alvo]
        cursor.execute(comando_sql, valores)
        connection.commit()
        if cursor.rowcount == 0:
            print(f"\n⚠ Aviso: Nenhum registro encontrado com o {coluna_id} '{alvo}'. Nada foi deletado.")
        else:
            print(f"\n✅ Sucesso: Registro {alvo} deletado com sucesso da tabela {tabela}!")
    except mysql.connector.Error as err:
        print(f"Erro ao deletar registro: {err}")
    finally:
        cursor.close()
rodando = True
while rodando:
    print("-"*30)
    print("1 - Inserir Registro (Create)")
    print("2 - Listar Registros (Read)")
    print("3 - Atualizar Registro (Update)")
    print("4 - Deletar Registro (Delete)")
    print("5 - Sair do Programa")
    print("-"*30)
    opcao = input("escolha uma das opçoes")
    clear_screen()
    if opcao == "1":
         comados_MySQL_Creat_insert(connection)
    elif opcao == "2":
         comandos_MySQL_SELECT_Read (connection)
    elif opcao == "3":
         comandos_MySQL_UPDATE_editar(connection)
    elif opcao == "4":
         comandos_MySQL_delete (connection)
    elif opcao == "5":
        print("\nSaindo do programa... Até mais!")
        rodando = False
    else:
         print("Opção inválida! Digite um número de 1 a 5.")
connection.close()
print("Sistema encerrado.")