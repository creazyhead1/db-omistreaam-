# omniStream Database 

![MySQL](https://img.shields.io/badge/MySQL-005C84?style=for-the-badge&logo=mysql&logoColor=white)
![Aiven](https://img.shields.io/badge/Aiven-FF4F00?style=for-the-badge&logo=aiven&logoColor=white)
![MIT License](https://img.shields.io/badge/License-MIT-green.svg?style=for-the-badge)

Modelagem e implementação de um banco de dados relacional completo para uma plataforma de streaming e transmissões ao vivo (estilo Twitch/YouTube), focado em escalabilidade, integridade de dados e alta disponibilidade.

---

##  Arquitetura em Nuvem

Para garantir que o sistema funcione de forma independente e profissional (sem depender de uma máquina local ligada), o banco de dados foi provisionado e hospedado na nuvem utilizando a plataforma **Aiven Cloud**, garantindo acesso simultâneo e seguro para múltiplos colaboradores e integrações de microsserviços.

---

##  Estrutura do Banco de Dados (DER)

O modelo de dados foi projetado para suportar o fluxo em tempo real de uma plataforma de streaming, dividindo-se em:

* **Core de Usuários:** Gerenciamento de contas (`Usuario`), canais personalizados (`Canal`) e sistema de inscrições (`Inscricao`).
* **Transmissões e Conteúdo:** Controle de transmissões ao vivo (`Live`), categorias e tags (`Categoria`, `Live_Categoria`) e integração com catálogos externos de VOD (`Conteudo_VOD`).
* **Interação em Tempo Real:** Histórico de visualização dinâmico (`Historico_Visualizacao`), sistema de chat para as transmissões (`Chat_mensagem`) e disparos de alertas (`Notificacao`).

`Untitled.png`.*

---

##  Tecnologias Utilizadas

* **SGBD:** MySQL 8.0+
* **Hospedagem Cloud:** Aiven (Managed MySQL)
* **Ferramenta de Modelagem:** dbdiagram.io / MySQL Workbench
* **Segurança:** Conexão encriptada via protocolo SSL (Certificado CA)

---

## 🚀 Como Executar o Projeto Localmente

Se quiser replicar este banco de dados na sua máquina ou conectá-lo à sua própria instância, siga os passos abaixo:

1. Clone o repositório:
   ```bash
   git clone [https://github.com/seu-usuario/omnistream-database.git](https://github.com/seu-usuario/omnistream-database.git)
