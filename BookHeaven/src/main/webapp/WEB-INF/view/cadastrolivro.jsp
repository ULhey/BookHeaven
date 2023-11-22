<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html class="h-100">
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css"/>'>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<title>Cadastro - Livro</title>
</head>
<body class="d-flex align-items-center py-4" style="background: #e2e3e5; min-height: 100vh;">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<form action="cadastrolivro" method="post">
					<div class="container">
						<div class="d-flex justify-content-between">
							<h2>
								<b>Cadastrar - Livro</b>
							</h2>
							<ul class="col-md-3">
								<li class="list-group-item">
									<label class="btn text-white" style="width: 100%; background: #fe8e7e;">
										<i class="fas fa-sign-out-alt"></i> Logoff 
										<input type="submit" name="botao" style="display: none;" value="Logoff">
									</label>
								</li>
							</ul>
						</div>
						<br>
						<div class="row">
							<div class="col-md-3 form-floating mb-3">
								<div class="form-floating" style="width: 100%;">
									<input type="text" class="form-control" name="cod" placeholder="Codigo Livro" value='<c:out value="${cadastrolivro.cod}"></c:out>'>
									<label for="floatingCod">Codigo Livro</label>
								</div>
							</div>
							<div class="col-md-3">
								<input class="btn py-2" style="background: #ffc09d; height: 75%" type="submit" id="botao" name="botao" value="Buscar">
							</div>
						</div>
						<div class="form-group">
							<div class="row">
								<div class="col-md-9 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="text" class="form-control" name="titulo" placeholder="Titulo Livro" value='<c:out value="${cadastrolivro.titulo}"></c:out>'>
										<label for="floatingTitulo">Titulo do Livro</label>
									</div>
								</div>

								<div class="col-md-3 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="text" class="form-control" name="numpag" placeholder="N° de Paginas:" value='<c:out value="${cadastrolivro.numPaginas}"></c:out>'>
										<label for="floatingNumpag">N° de Pag.</label>
									</div>
								</div>
								<div class="col-md-6 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="text" class="form-control" name="preco" placeholder="Preço:" value='<c:out value="${cadastrolivro.preco}"></c:out>'>
										<label for="floatingTelefone">Preço</label>
									</div>
								</div>
								<div class="col-md-6 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="text" class="form-control" name="genero" placeholder="Gêneros:" value='<c:out value="${cadastrolivro.genero}"></c:out>'>
										<label for="floatingGenero">Gêneros</label>
									</div>
								</div>
								<div class="col-md-8 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="text" class="form-control" id="floatingAutor" placeholder="Nome Autor:" name="autor" value='<c:out value="${cadastrolivro.nomeAutor}"></c:out>'>
										<label for="floatingAutor">Nome Autor</label>
									</div>
								</div>
								<div class="col-md-4 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="date" class="form-control" name="dataPub" placeholder="Data de Publicacao" value='<c:out value="${cadastrolivro.dataPublicao}"></c:out>'>
										<label for="floatingDataPubli">Data de Publicação</label>
									</div>
								</div>
								<div class="col-md-12 form-floating mb-3">
									<div class="form-floating" style="width: 100%;">
										<input type="text" class="form-control" name="sinopse" placeholder="Sinopse:" value='<c:out value="${cadastrolivro.sinopse}"></c:out>'>
										<label for="floatingSinopse">Sinopse</label>
									</div>
								</div>
							</div>
							<div class="col-md-12 form-floating mb-3">
								<div class="form-floating" style="width: 100%;">
									<input type="text" class="form-control" name="link" placeholder="Link imagem" value='<c:out value="${cadastrolivro.linkImagem}"></c:out>'>
									<label for="floatingTelefone">Link Imagem</label>
								</div>
							</div>
							<br>
							<div class="text-center">
								<div class="d-flex justify-content-between">
									<input class="btn py-2" style="background: #ffc09d; width: 30%;" type="submit" id="botao" name="botao" value="Cadastrar"> 
									<input class="btn py-2" style="background: #ffc09d; width: 30%;" type="submit" id="botao" name="botao" value="Alterar">
									<input class="btn py-2" style="background: #ffc09d; width: 30%;" type="submit" id="botao" name="botao" value="Listar">
								</div>
							</div>
							<br> <br>
						</div>
					</div>
				</form>
				<c:if test="${not empty livros}">
					<div class="table-responsive"
						style="height: 200px; overflow-y: scroll">
						<table class="table table-striped table">
							<thead>
								<tr>
									<th>Codigo do Livro</th>
									<th>Título do Livro</th>
									<th>Preço de Venda</th>
									<th>Nome do Autor</th>
									<th></th>
								</tr>
							</thead>
							<tbody>
								<c:forEach var="l" items="${livros}">
									<form action="cadastrolivro" method="post">
										<tr>
											<td><input type="hidden" name="cod" value='<c:out value="${l.cod}"></c:out>'><c:out value="${l.cod}"></c:out></td>
											<td><c:out value="${l.titulo}"></c:out></td>
											<td>R$ <c:out value="${l.preco}"></c:out>0</td>
											<td><c:out value="${l.nomeAutor}"></c:out></td>
											<td><input class="btn py-2" style="background: #ca5c4d;" type="submit" id="botao" name="botao" value="Excluir">
											</td>
										</tr>
									</form>
								</c:forEach>
							</tbody>
						</table>
					</div>
				</c:if>
			</div>
			<div class="col-md-6 d-flex align-items-center justify-content-center">
				<img src="https://a.imagem.app/oKC9nm.png" class="img-fluid" style="width: 150%;">
			</div>
		</div>
	</div>
</body>
</html>