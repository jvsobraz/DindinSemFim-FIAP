# Dindin Sem Fim

Uma API para o app de controle de gastos pessoais.

## Vídeo explicativo no Youtube:

https://youtu.be/WpuwCXxmS-w

Este é um projeto que demonstra a criação de um serviço de aplicativo Java com integração contínua no Microsoft Azure. O projeto inclui a criação de tabelas em um banco de dados Oracle, a implantação de uma API e testes usando o software Insomnia. Siga as etapas abaixo para configurar o ambiente e executar o projeto.

## Configuração Inicial
### Banco de Dados Oracle

Primeiro, crie as tabelas no banco de dados Oracle usando o SQL Developer com o arquivo DDL.txt disponível no diretório do GitHub (https://github.com/jvsobraz/DindinSemFim-FIAP.git).

### Configuração do Projeto

1. Faça o download ou o Fork deste repositório: DindinSemFim-FIAP.
2. Realize as alterações necessárias no arquivo application.properties, especificando o username e password do seu banco de dados Oracle.

## Implantação no Microsoft Azure

1. Crie um Serviço de Aplicativo no Portal da Azure seguindo as configurações abaixo:
- Nome da instância
- Linguagem: Java 17
- Sistema Operacional: Windows
- Região: East US ou Brazil South
- Plano de preço: Gratuito (F1)

2. Na aba "Implantação", habilite a Implantação Contínua e conecte sua conta do GitHub.

3. Selecione a organização, o repositório e a branch que serão usados para o projeto.

4. Na aba "Rede", deixe o Acesso Público ativado.

5. Na aba "Monitoramento", desabilite o Application Insights para este projeto.

6. Crie um Grupo de Recursos e aguarde a conclusão da implantação.

## Testando a API

1. Após a implantação, acesse o Repositório no GitHub, clique em "Actions" e depois no "Workflow" em execução.

2. Aguarde o build e o deploy da aplicação.

3. Como a aplicação é uma API e não tem uma página inicial padrão, não é possível acessar o link diretamente.

4. Insira o sufixo /contas na URL gerada para realizar os testes no Insomnia.

5. Abra o Insomnia e realize testes GET, POST, PUT e DELETE na API de acordo com as instruções no arquivo de texto.

## Limpeza

Após concluir os testes, exclua o Grupo de Recursos e todos os recursos criados no Microsoft Azure. Além disso, remova os objetos do banco de dados no SQL Developer.

Esse é um guia básico para configurar e testar o projeto DindinSemFim-FIAP. Sinta-se à vontade para personalizá-lo de acordo com as necessidades do seu projeto e adicionar informações adicionais, como a estrutura de diretórios, documentação da API, ou qualquer outra informação relevante.

## Endpoint

- Despesas
    - [Cadastrar](#cadastrar-despesa)
    - [Mostrar detalhes](#detalhar-despesa)
    - Apagar
    - Atualizar
    - Listar todas
- Contas
- Categorias

---

### Cadastrar Despesa

`POST` /api/despesa

**Campos da requisição**

| campo | tipo | obrigatório | descrição
|-------|------|:-------------:|---
|valor | decimal | sim | o valor da despesa, deve ser maior que zero
|data|data|sim| a data da despesa
|categoria_id | int | sim | código de uma categoria previamente cadastrada
|conta_id |int |int | o código de uma conta previamente cadastrada
|descricao|texto|não| um texto sobre a despesa com no máximo de 255 caracteres

**Exemplo de corpo de requisição**

```js
{
    "valor": 100.00,
    "data": "2023-02-28",
    "categoria_id": 1,
    "conta_id": 1,
    "descricao": "cinema"
}
```

**Códigos de Resposta**

| código | descrição
|-|-
| 201 | despesa cadastrada com sucesso
| 400 | os campos enviados são inválidos

---

### Detalhar Despesa

`GET` /api/despesa/{id}

**Exemplo de Corpo da Resposta**

```js
{
    valor: 100.00,
    data: '2023-02-28',
    categoria: {
        categoria_id: 1,
        nome: 'lazer',
    },
    conta: {
        conta_id: 1,
        nome: 'itaú',
    }
    descricao: 'cinema'
}
```

**Códigos de Resposta**

| código | descrição
|-|-
| 200 | dados retornados com sucesso
| 404 | não existe despesa com o id informado
