CREATE DATABASE BookHeaven

USE BookHeaven

--TABELAs
CREATE TABLE livro(
    ID INT IDENTITY(1, 1) NOT NULL,
    titulo VARCHAR(100) NOT NULL,
    numPagina INT NOT NULL,
    preco DECIMAL(10, 2) NOT NULL,
    genero VARCHAR(100) NOT NULL,
    dataPublicacao DATE NOT NULL,
    nomeAutor VARCHAR(100) NOT NULL,
    sinopse VARCHAR(600) NOT NULL,
    linkImagem VARCHAR(200) NOT NULL,
    numVendas INT,
    PRIMARY KEY(ID)
)

CREATE TABLE funcionario(
    ID INT IDENTITY(1, 1) NOT NULL,
    loginFunc VARCHAR(50) NOT NULL,
    senha VARCHAR(30) NOT NULL,
    PRIMARY KEY(ID)
)

INSERT INTO funcionario VALUES ('admin', '123')

CREATE TABLE cliente(
	nome VARCHAR(100) NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	dataNasc DATE NOT NULL,
	telefone VARCHAR(9) NOT NULL,
	endereco VARCHAR(100) NOT NULL,
	email VARCHAR(30) NOT NULL,
	senha VARCHAR(20) NOT NULL,
	PRIMARY KEY(CPF)
)

CREATE TABLE pedido(
	idPedido INT IDENTITY(1001, 1) NOT NULL,
	dataPedido DATE NOT NULL,
	cpf VARCHAR(11) NOT NULL,
	situacao VARCHAR(20) NOT NULL,
	PRIMARY KEY (idPedido),
	FOREIGN KEY (cpf) REFERENCES cliente(cpf)
) 

CREATE TABLE itemPedido(
	idItemPedido INT IDENTITY(51, 1) NOT NULL,
	idLivro INT NOT NULL,
	idPedido INT NOT NULL,
	valorUnitario DECIMAL (10,2) NOT NULL,
	quantidade INT NOT NULL,
	PRIMARY KEY (idItemPedido),
	FOREIGN KEY (idPedido) REFERENCES pedido(idPedido),
	FOREIGN KEY (idLivro) REFERENCES livro(ID)
)

CREATE TABLE pagamento(
	idPagamento INT IDENTITY (61,1) NOT NULL,
	idPedido INT NOT NULL,
	dataPagamento DATE NOT NULL,
	subTotal DECIMAL (10,2) NOT NULL,
	PRIMARY KEY (idPagamento),
	FOREIGN KEY (idPedido) REFERENCES pedido(idPedido),
)

--PROCEDUREs
CREATE PROCEDURE crudLivro(@acao AS VARCHAR(1), @ID AS INT, @titulo AS VARCHAR(100), @numPagina as varchar(100), @preco AS DECIMAL(10, 2), @genero AS VARCHAR(100), 
@dataPublicacao AS DATE, @nomeAutor AS varchar(100), @sinopse AS VARCHAR(600), @linkImagem AS varchar(200), @saida AS BIT OUTPUT)  AS
    IF(@acao = 'i') BEGIN
        DECLARE @numVenda AS INT = CAST((RAND() * 100) AS INT)
        INSERT INTO livro VALUES (@titulo, @numPagina, @preco, @genero, @dataPublicacao, @nomeAutor, @sinopse, @linkImagem, @numVenda)
    END

    IF(@acao = 'u') BEGIN
        UPDATE livro SET titulo = @titulo, numPagina = @numPagina, preco = @preco, genero = @genero, dataPublicacao = @dataPublicacao, sinopse = @sinopse, linkImagem = @linkImagem WHERE ID = @ID
    END

    IF(@acao  = 'd') BEGIN
        DELETE livro WHERE ID = @ID
    END

CREATE PROCEDURE validarCPF(@cpf VARCHAR(11), @valido BIT OUTPUT) AS BEGIN
    DECLARE @soma1 INT,
					 @soma2 INT,
					 @i INT,
					 @cpfValido BIT

    SET @soma1 = 0
    SET @soma2 = 0
    SET @cpfValido = 1 

    IF (LEN(@cpf) = 11) BEGIN
        SET @i = 1

        WHILE (@i <= 9) BEGIN
            SET @soma1 = @soma1 + CAST(SUBSTRING(@cpf, @i, 1) AS INT) * (11 - @i)
            SET @soma2 = @soma2 + CAST(SUBSTRING(@cpf, @i, 1) AS INT) * (12 - @i)
            SET @i = @i + 1
        END

        SET @soma1 = 11 - (@soma1 % 11)
        IF (@soma1 >= 10)
		SET @soma1 = 0

        SET @soma2 = @soma2 + @soma1 * 2
        SET @soma2 = 11 - (@soma2 % 11)

        IF (@soma2 >= 10)
		SET @soma2 = 0

        IF (@soma1 <> CAST(SUBSTRING(@cpf, 10, 1) AS INT) OR @soma2 <> CAST(SUBSTRING(@cpf, 11, 1) AS INT))
            SET @cpfValido = 0
		END
     ELSE BEGIN
        SET @cpfValido = 0
    END

    SET @valido = @cpfValido
END

CREATE PROCEDURE crudCliente(@acao AS VARCHAR(1), @nome AS VARCHAR(100), @cpf AS VARCHAR(11), @dataNasc AS DATE, @telefone AS VARCHAR(9), 
@endereco AS VARCHAR(100), @email AS VARCHAR(30), @senha AS VARCHAR(20), @saida AS BIT OUTPUT) AS
    DECLARE @validarCPF INT = 0
	EXEC validarCPF @cpf, @validarCPF OUTPUT
	IF(@acao = 'i'  AND @validarCPF = 1) BEGIN
        INSERT INTO cliente VALUES (@nome, @cpf, @dataNasc, @telefone, @endereco, @email, @senha)
    END

CREATE PROCEDURE crudItemPedido(@acao AS VARCHAR(1), @idPedido AS INT, @cpf AS VARCHAR(11), @idLivro AS INT, @valorUnitario AS DECIMAL (10,2), 
@quantidade AS INT, @saida AS BIT OUTPUT) AS
    IF(@acao = 'i') BEGIN
			INSERT INTO pedido VALUES (GETDATE(), @cpf, 'pendente')
			INSERT INTO itemPedido VALUES (@idLivro, @idPedido, @valorUnitario,  @quantidade)
			SET @saida = 1
    END
	 ELSE BEGIN
        SET @saida = 0 
    END

	IF(@acao = 'd') BEGIN
			UPDATE pedido SET situacao = 'cancelado' WHERE idPedido = @idPedido
    END

CREATE PROCEDURE crudPagamento(@acao AS VARCHAR(1), @idPag AS INT, @idPedido AS INT,  @total AS DECIMAL, @saida AS BIT OUTPUT) AS
	IF (@acao = 'I') BEGIN
		INSERT INTO pagamento VALUES(@idPedido, GETDATE(), @total)
		UPDATE pedido SET situacao = 'pago' WHERE idPedido = @idPedido
	END

--VIEWs
CREATE VIEW viewPedido AS
SELECT  l.ID AS ID, l.titulo AS titulo, l.numPagina AS numPagina, l.preco AS preco, l.genero AS genero, l.dataPublicacao AS dataPublicacao, l.nomeAutor AS nomeAutor, l.sinopse AS sinopse, l.linkImagem AS linkImagem, l.numVendas AS numVendas,
			  c.nome AS nome, c.cpf AS cpf, c.dataNasc AS dataNasc, c.telefone AS telefone, c.endereco AS endereco, c.email AS email, c.senha AS senha,
			  p.idPedido AS idPedido, p.dataPedido AS dataPedido, p.situacao AS situacao,  i.valorUnitario as  valorUnitario, I.quantidade AS quantidade
FROM livro l INNER JOIN itempedido i ON l.ID = i.idLivro
				    INNER JOIN pedido p ON p.idPedido = i.idpedido
					INNER JOIN cliente c ON c.cpf = p.cpf

CREATE VIEW viewPagamento AS
SELECT l.ID AS ID, l.titulo AS titulo, l.numPagina AS numPagina, l.preco AS preco, l.genero AS genero, l.dataPublicacao AS dataPublicacao, l.nomeAutor AS nomeAutor, l.sinopse AS sinopse, l.linkImagem AS linkImagem, l.numVendas AS numVendas,
			  c.nome AS nome, c.cpf AS cpf, c.dataNasc AS dataNasc, c.telefone AS telefone, c.endereco AS endereco, c.email AS email, c.senha AS senha,
			  p.idPedido AS idPedido, p.dataPedido AS dataPedido, p.situacao AS situacao,  i.valorUnitario as  valorUnitario, I.quantidade AS quantidade,
			  pag.idPagamento AS idPag, pag.subTotal AS TOTAL, pag.dataPagamento AS dataPagamento
FROM livro l INNER JOIN itempedido i ON l.ID = i.idLivro
					INNER JOIN pedido p ON p.idPedido = i.idpedido
					INNER JOIN cliente c ON c.cpf = p.cpf
					INNER JOIN pagamento pag ON pag.idPedido = p.idPedido

CREATE VIEW viewCalculaTotal AS
SELECT p.cpf as cpf, p.situacao AS situacao, sum(i.quantidade * i.valorUnitario) as subTotal
FROM pedido p INNER JOIN itempedido i ON p.idPedido = i.idpedido
GROUP BY p.cpf, p.situacao 

CREATE VIEW viewQtdPedido AS
SELECT cpf, situacao, COUNT(cpf) AS pedidos 
FROM pedido
GROUP BY cpf, situacao

--BADGE
SELECT COUNT(cpf) AS pedidos
FROM pedido
WHERE cpf = '22288889994' AND situacao = 'pendente'

--SELECTs
SELECT * FROM livro
SELECT * FROM funcionario
SELECT * FROM cliente
SELECT * FROM pedido
SELECT * FROM itemPedido
SELECT * FROM pagamento

--SELECTs VIEWs
SELECT * FROM viewPedido 
SELECT * FROM viewPagamento 
SELECT * FROM viewCalculaTotal WHERE cpf = '22233366638' AND situacao = 'pendente'
SELECT * FROM viewQtdPedido