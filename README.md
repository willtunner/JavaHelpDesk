<h4 align="center">
 <img src="https://user-images.githubusercontent.com/957189/82269499-46a2f080-9948-11ea-84ee-020ed0310fb7.jpg" />
</h4>



<h4 align="center">
 Essa aplicação foi feita para resolver os problemas do suporte de uma empresa que devido as altas demandas de chamados e não se tinha um controle dos chamados atendidos , se foi resolvido ou se ficou em aberto.
</h4>

<h1 align="center"> :: Screens do sistema :: </h1>

<h3 align="center"> :: Tela de login :: </h3>

<h4 align="center">
 <img src="https://user-images.githubusercontent.com/957189/82269219-54a44180-9947-11ea-8395-3f77c97b3a72.jpg"/>

<h3  ::Tela de Login ::</h3>

</h4>

- Busca o Ip e Mac do computador

<h3 align="center"> :: Tela Principal :: </h3>
<h4 align="center">
 <img src="https://user-images.githubusercontent.com/957189/82269374-e744e080-9947-11ea-885e-a537ecc0533c.jpg"/>
</h4>

- Tela principal Nomal exibe um modo reduzido de informações


<h3 align="center"> :: Tela Principal Expandida :: </h3>
<h4 align="center">
 <img src="https://user-images.githubusercontent.com/957189/82269819-33445500-9949-11ea-9bb8-92f6b4631ad5.jpg"/>
</h4>

- Exibe funções adcionais ao maximimar a tela



<h3 align="center"> :: Tela de Chamado :: </h3>
 <img src="https://user-images.githubusercontent.com/957189/82271014-faa67a80-994c-11ea-916c-76598af68983.jpg"/>


<h1 align="center"> :: Funcionalidades do sistema :: </h1>

- Sistema de login 
- Exibe chamados por dia, semana e mes.
- Exibe chamados em aberto (se zerado ele fica verde se não fica laranja).
- Sistema conta com um contador que ao escolher a empresa e o cliente ele conta o tempo do atendimento e se o chamado ficar em aberto ele conta os dias em aberto.
- Integração com AnyDesk, passando a string de conexão basta clicar no botão do anydesk que ele executa o o programa instalado com o acesso remoto no cliente direto
- Conta com um sistema de PrintScreen que salva dentro da pasta usuario/empresa organizando os prints para gerar relatório de erro
- Salva documentos em pdf no banco para assim reportar a equipe ou algum manual para a equipe consultar.
- Imagem de perfil de usuario
- Sistema de Agenda telefonica
- Cadastro de empresas salvando a conexão de acesso anydesk dos servidores, cnpj, endereço etc..
- Cadastro de usuarios e clientes
- Botão com link externo para sites de consulta sintegra, DANFE, NFCE, NFE
- Cadastro de versões de aplicativos 
- Pega informações de espaço HD e memoria do computador
- Identifica qual versão do windows está rodando o sistema 
- Banco de dados MySqL


<h1 align="center"> :: MODO DE USAR :: </h1>

1. Instale o MySqL, JDK, Netbeans 

2. Use o arquivo .sql do projeto e deixe rodando

3. Configure o ip do localhost em  connection/ConnectionFactory para comunicar com o MySqL
