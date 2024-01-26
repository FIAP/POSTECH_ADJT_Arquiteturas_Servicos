<picture>
  <img alt="Shows an illustrated sun in light mode and a moon with stars in dark mode." src="https://on.fiap.com.br/theme/fiap/postech/pos-tech.png">
</picture>

# Discipina Arquiteturas de Serviços


Repositório da disciplina Arquiteturas de Serviços do curso Pós Tech em Arquitetura e Desenvolvimento em Java da FIAP


### AULAS:

<br>

**Microsserviço Cidade**


<br>

### Projeto: FIAP SHOP

#### Microsserviço 1: Gerenciamento de Produtos (Utilizando MySQL)

**Funções Principais:**

1. Cadastrar produtos
2. Listar produtos
3. Atualizar detalhes do produto
4. Excluir produtos

<br>

**Modelo de Dados** (MySQL):

* ProdutoID (Chave primária)
* Nome
* Descrição
* Preço
* Estoque

<br>

**API Endpoints:**

1. POST `/produtos` - para adicionar um novo produto
2. GET `/produtos` - para listar todos os produtos
3. GET `/produtos/{ProdutoID}` - para ver detalhes de um produto
4. PUT `/produtos/{ProdutoID}` - para atualizar um produto
5. DELETE `/produtos/{ProdutoID}` - para excluir um produto

<br>

#### Microsserviço 2: Gerenciamento de Pedidos (Utilizando MongoDB)

**Funções Principais:**

1. Criar novos pedidos
2. Listar pedidos
3. Visualizar detalhes do pedido
4. Atualizar status do pedido
5. Excluir pedidos

<br>

**Modelo de Dados** (MongoDB):

* PedidoID (Chave primária)
* ClienteID
* Lista de Produtos (Array com ProdutoID, Quantidade)
* Status (ex: pendente, em processamento, entregue)
* Data do Pedido

<br>

**API Endpoints:**

1. POST `/pedidos` - para criar um novo pedido
2. GET `/pedidos` - para listar todos os pedidos
3. GET `/pedidos/{PedidoID}` - para ver detalhes de um pedido
4. PUT `/pedidos/{PedidoID}` - para atualizar um pedido
5. DELETE `/pedidos/{PedidoID}` - para excluir um pedido

<br>

**Comunicação entre os Microsserviços:**

* Quando um novo pedido é criado no microsserviço de pedidos, ele comunica-se com o microsserviço de produtos para verificar a disponibilidade dos produtos e para atualizar o estoque.
* É possível usar HTTP RESTful APIs ou gRPC para a comunicação entre os serviços.

<br>

**Ferramentas e Tecnologias:**

1. Linguagens de Programação: Java.
2. Banco de dados: MySQL para o serviço de produtos e MongoDB para o serviço de pedidos.
3. DataGrip: Gerenciamento de Banco de Dados
4. Ferramentas de API: Swagger, Postman, etc.
5. Controle de Versão: Git
6. Docker


<br>

**Observação:**

Os arquivos docker-compose-mysql.yaml e docker-compose-mongodb.yaml são para criar uma instância dos respectivos bancos de dados.  É necessário ter o docker instalado na máquina para executá-los.
