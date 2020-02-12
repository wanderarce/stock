#Aplicação de desafio MILTEC

##Configurações

1 - JDK 1.8
2 - spring boot: 2.2.4
3 - IDE: Utilizado Spring Tools Suite sendo assim a ide já possui as configurações do Maven para controle dos JAR's necessários.
4- Para baixar as libs descritas no pom.xml cliquei com o botão direito do mouse após a importação e selecione maven install ou vá até a pasta do
projeto e execute mvnw install (mvnw -h, para obter a lista de comandos)

## Estrutura do projeto

1- Foi utilizado a comunicação REST conforme solicitado separando o projeto nas seguintes camadas:
1.1 - Entities: Classes que represetam a entidade a ser persistida no banco de dados
1.2 - Respositories: criado a interface de cada entidade implementando o JpaRepository já que o mesmo já possibilita várias facilidades.
1.3 - Services:  Classe que representa o controle de regras de cada requisição;
1.4 - Rest: Controller responsável por realizar a requisição para o serviço e devolver somente o necessário.
1.5 - Utilizado apenas banco H2 em disco para persistência de dados;

## Regras

1 - Uma seção não pode ter dois ou mais tipos diferentes de bebidas (como já fora dito). 
	Mapeado a Classe Sections @ManyToOne(fetch = FetchType.LAZY, optional= false)
	@JoinColumn(name="type_id", nullable = false) para que cada Seção possa ter apenas um tipo de bebida

2 - Não há entrada ou saída de estoque sem respectivo registro no histórico.
	Os registros são controlados pela tabela de releases (lançamentos) com os respectivos dados solicitados no item 3 junto com o produto.

3 - Registro deve conter horário, tipo, volume, seção e responsável pela entrada.

4 -  O endpoint de consulta de histórico de entrada e saída de estoque, deve retornar os
resultados ordenados por data e seção, podendo alterar a ordenação via parâmetros.

5 - Para situações de erro, é necessário que a resposta da requisição seja coerente em
exibir uma mensagem condizente com o erro.

6 - Cadastro e consulta das bebidas armazenadas em cada seção com suas respectivas
queries.
7 - Consulta do volume total no estoque por cada tipo de bebida.

8 - Consulta das seções disponíveis de armazenamento de um determinado volume
de bebida. (calcular via algoritmo).
	Realizado cálculo (Entradas - Saídas) verificando se o resultado desse cálculo é maior ou igual a capacidade da seção.

9 - Consulta das seções disponíveis para venda de determinado tipo de bebida (calcular via
algoritmo).
	realizado cálculo dos item de movimento de (Entrada- Saída)

10 - Cadastro de histórico de entrada e saída de bebidas em caso de venda e recebimento.
	O controle de entrada/saída é feito pela coluna movement podendo ser cadastrado como ENTRADA/SAIDA para que possa ser filtrado.
	
11 - Consulta do histórico de entradas e saídas por tipo de bebida e seção.
	http://localhost:8080/releases/filter/1/1
	
# Observações

1 - Foram utilizados alguns recursos como o Optional do Java 8.
2 - As consultas são paginadas e a apresentação dos dados são do objeto Page<?> disponível do framework para possam ver
3 - Foi adicionado um arquivo do postman para que possam verificar os dados utilizados durante o desenvolvimento.
4-  Para demonstração do uso das possibilidades do Spring com Jpa foram utilizadas consultas com predicate, query native e as consulta padrões do Jpa.
 
