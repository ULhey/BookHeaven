<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<link rel="stylesheet" type="text/css" href='<c:url value="./resources/css/styles.css"/>'>
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<title>BookHeaven</title>
</head>
<body style="background: #e2e3e5;">
<nav class="navbar navbar-expand-lg" style="background: #385a64;">
		<div class="container-fluid ">
			<a class="navbar-brand" aria-current="page" href="index"> 
			<img src="https://images-ext-2.discordapp.net/external/hVEzhQnNsZSIyyI_K5VXuRLDlr7hrZJQCr06zUXmBZw/https/i.pinimg.com/originals/d3/8d/09/d38d095f49c32da7b69171fc379cdacf.png?width=676&height=676"
				alt="Logo" width="70" height="64" class="d-inline-block align-text-top">
			</a>
			<button class="navbar-toggler" type="button" data-bs-toggle="collapse" data-bs-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarSupportedContent">
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<form action="detalhes" method="post">
						<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
						<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
						<ul style="padding: 0">
							<li class="list-group-item">
								<label class="btn btn-lg text-white" style="background: #385a64;">
										<i class="fas fa-home"></i> Home<input type="submit" name="botao" style="display: none;" value="Home">
								</label>
							</li>
						</ul>
					</form>
					</li>
				</ul>
				<form action="pesquisalivro" method="post" role="search" style="width: 70%">
					<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
					<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
					
					<div class="input-group">
						<input class="form-control flex-grow-1" type="search" aria-label="Search" name="pesquisa"> 
						<label class="btn text-white" style="background: #fb735d; border-radius: 0px 5px 5px 0px">
						<i class="fas fa-search text-white"></i><input type="submit" name="botao" style="display: none;" value="Pesquisar">
						</label>
					</div>
				</form>
				<ul class="navbar-nav me-auto mb-2 mb-lg-0">
					<li class="nav-item">
						<form action="detalhes" method="post">
							<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
							<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>">
		            		<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
		            		<input type="hidden" name="tituloLivro" value="<c:out value="${detalhes.titulo}"></c:out>">
		            		<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
		            		
							<ul style="padding: 0">
								<li class="list-group-item"><label class="btn btn-lg text-white" style="background: #385a64;">
										<i class="fas fa-user text-white"></i> <c:if test="${empty login }">
											Login
											<input type="submit" name="botao" style="display: none;" value="Logar"></c:if> 
											<c:if test="${not empty login }">
											<c:out value="${login}"></c:out>
										</c:if>
								</label></li>
							</ul>
						</form>
					</li>
					<li class="nav-item">
						<form action="detalhes" method="post">
							<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
							<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>">
		            		<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
		            		<input type="hidden" name="tituloLivro" value="<c:out value="${detalhes.titulo}"></c:out>">
		            		<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
		            		
							<ul style="padding: 0">
								<li class="list-group-item">
									<label class="btn btn-lg text-white position-relative" style="background: #385a64;">
									  <i class="fas fa-shopping-cart text-white"></i> Carrinho 
									  <c:if test="${not empty login }">
										<span class="position-absolute top-0 start-100 translate-middle badge rounded-pill bg-danger">
										    <c:out value="${qtdPedido}"></c:out>
										    <span class="visually-hidden">itens no carrinho</span>
										</span>
									</c:if>
									  <input type="submit" style="display: none" name="botao" value="Carrinho">
									</label>
								</li>
							</ul>
						</form>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<br>
	<br>
	<div class="container">
		<form action="detalhes" method="post">
			<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
			<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>">
          	<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
          	<input type="hidden" name="tituloLivro" value="<c:out value="${detalhes.titulo}"></c:out>">
          	<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
          	
			<div class="row justify-content-center align-items-center">
				<div class="col-md-6">
					<img src="<c:out value="${detalhes.linkImagem}"></c:out>" class="img-fluid" width="500" height="464">
				</div>
				<div class="col-md-6">
					<main class="form-container">
						<ul class="list-group list-group-flush" style="list-style-type: none">
							<li> 
								<label class="btn text-white" style="background: #385a64;">
									<i class="fas fa-arrow-left"></i> Voltar<input type="submit"name="botao" style="display: none;" value="Voltar"> 
								</label> 
							</li>
						</ul>
						<h1 class="h3 mb-3 fw-normal">
							<br> <b><c:out value="${detalhes.titulo}"></c:out></b>
							<input type="hidden" name="idLivro" value="<c:out value="${detalhes.cod}"></c:out>">
						</h1>
						<p class="card-text" style="font-size: 20px;">
							<b>Autor:</b>
							<c:out value="${detalhes.nomeAutor}"></c:out>
						</p>
						<p class="card-text" style="font-size: 20px;">
							<c:out value="${detalhes.genero}"></c:out> ·
							<c:out value="${detalhes.numPaginas}"></c:out>paginas
						</p>
						<p class="card-text" style="font-size: 26px;">
							<b>Sinopse</b>
						</p>
						<p class="card-text" style="font-size: 15px;">
							<c:out value="${detalhes.sinopse}"></c:out>
						</p>
						<div align="left">
							<b class="card-text" style="font-size: 25px;">R$ <c:out value="${detalhes.preco}"></c:out>0
							</b>
						</div>
						<br>
						<div
							class="col-md-3 d-flex justify-content-between align-items-center">
							<label for="quantity" class="mb-0">Quantidade:</label>
							<div class="input-group">
								<input type="number" name="quantidade" class="form-control ml-2" min="1" value="1" style="margin-left: .5em;">
							</div>
						</div>
						<br> 
						<label class="btn w-100 py-2 btn text-white" style="background: #fb735d;">
							<i class="fas fa-shopping-cart"></i> Comprar
							<input type="submit" name="botao" style="display: none;" value="Comprar">
						</label> 
					</main>
				</div>
			</div>
		</form>
	</div>
</body>
</html>