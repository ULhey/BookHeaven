<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/css/bootstrap.min.css" rel="stylesheet">
<link href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.3/css/all.min.css" rel="stylesheet">
<script src="https://cdn.jsdelivr.net/npm/@popperjs/core@2.11.8/dist/umd/popper.min.js"></script>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.3.2/dist/js/bootstrap.min.js"></script>
<title>BookHeaven</title>
<style>
.sinopse {
	height: 80px;
	overflow-y: scroll;
	scrollbar-width: none;
}

.sinopse::-webkit-scrollbar {
	width: 1px;
}

.lista {
	width: 100%;
	display: flex;
}

.sublinhado {
	border-bottom: 2px solid black;
}

.formPai {
	margin: auto;
	width: 100%;
}

@media screen and (min-width: 500px) {
	.formPai {
		width: 70%;
	}
}

@media screen and (min-width: 768px) {
	.formPai {
		margin: none;
		width: 80%;
	}
	.cartao {
		float: left;
		margin-left: .5%;
		margin-right: .5%;
		width: 49%;
	}
}

@media screen and (min-width: 1280px) {
	.formPai {
		margin: none;
		width: 100%;
	}
	.cartao {
		float: left;
		margin-left: .5%;
		margin-right: .5%;
		width: 24%;
	}
}
</style>
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
				<ul  class="navbar-nav me-auto mb-2 mb-lg-0">
				<li class="nav-item">
					<form action="index" method="post">
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
						<form action="pesquisalivro" method="post">
							<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
							<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
							<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
							<ul style="padding: 0">
								<li class="list-group-item">
								<label class="btn btn-lg text-white" style="background: #385a64;">
								<i class="fas fa-user text-white"></i> <c:if test="${empty login }"> Login
									<input type="submit" name="botao" style="display: none;" value="Logar">
										</c:if> 
										<c:if test="${not empty login }">
											<c:out value="${login}"></c:out>
										</c:if>
								</label></li>
							</ul>
						</form>
					</li>
					<li class="nav-item">
						<form action="pesquisalivro" method="post">
							<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
							<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
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
								  <input type="submit" name="botao" style="display: none;" value="Carrinho">
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
	<div class="container">
		<div class="row">
			<div class="col-md-6 col-sm-12">
				<p class="fs-3 sublinhado">
					<b>Resultado da busca: <c:out value="${tituloPesquisa}"></c:out></b>
				</p>
			</div>
		</div>
	</div>
	<div class="container">
		<div class="row formPai">
			<c:forEach var="l" items="${livros}">
				<form action="pesquisalivro" method="post" class="cartao">
					<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
					<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
					<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
					<div class="card mb-3">
						<img src="${l.linkImagem}" style="height: 447px;" class="card-img-top" style="width: 100%;">
						<div class="card-body">
							<h6 class="card-title">
								<c:out value="${l.titulo}"></c:out>
								<input type="hidden" name="titulo" value="<c:out value="${l.titulo}"></c:out>">
							</h6>
							<p class="card-text sinopse" style="font-size: 13px;">
								<c:out value="${l.sinopse}"></c:out>
							</p>
						</div>
						<ul class="list-group list-group-flush">
							<li class="list-group-item">
							<label class="btn btn-outline-success">
							<i class="fas fa-shopping-cart"></i> Comprar<input type="submit" name="botao" style="display: none;" value="Comprar">
							</label> <b> R$ <c:out value="${l.preco}"></c:out>0
							</b></li>
						</ul>
					</div>
				</form>
			</c:forEach>
		</div>
	</div>
</body>
</html>