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
<title>Login - Cliente</title>
</head>
<body class="d-flex align-items-center py-4"
	style="background: #e2e3e5; min-height: 100vh;">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<main class="form-container text-center">
					<form action="logincliente" method="post">
						<input type="hidden" name="pagAnt" value="<c:out value="${pagAnt}"></c:out>">
						<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
						<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>">
            			<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
            			<input type="hidden" name="tituloLivro" value="<c:out value="${titulo}"></c:out>"> 
						<img src="https://images-ext-2.discordapp.net/external/hVEzhQnNsZSIyyI_K5VXuRLDlr7hrZJQCr06zUXmBZw/https/i.pinimg.com/originals/d3/8d/09/d38d095f49c32da7b69171fc379cdacf.png?width=676&height=676" class="mb-4" height="145" width="150">
						<h1 class="h3 mb-3 fw-normal">
							<b>Faça login</b>
						</h1>
						<div class="form-floating">
							<input type="email" class="form-control" id="floatingInput" name="email" placeholder="email.@bookheaven.com" /> <label for="floatingInput">Email:</label>
						</div>
						<br>
						<div class="form-floating">
							<input type="password" class="form-control" id="floatingInput" name="senha" placeholder="senha"> 
							<label for="floatingInput">Senha:</label>
						</div>
						<br> <input class="btn w-100 py-2 text-white" type="submit" value="Entrar" name="botao" class="btn w-100 py-2" style="background: #385a64;">
						<div class="d-flex justify-content-center">
							<p class="mt-3 mr-2">Não tem uma conta? </p>
							<input style="padding: 0" class="btn btn-link" type="submit" name="botao" value="Cadastre-se">
						</div>
					</form>
					<br>
					<c:if test="${not empty erro}">
						<p class="text-danger">
							<b><c:out value="${erro}"></c:out></b>
						</p>
					</c:if>
				</main>
			</div>
			<div
				class="col-md-6 d-flex align-items-center justify-content-center">
				<img src="https://a.imagem.app/oK9iZ8.png" class="img-fluid"
					style="width: 150%;">
			</div>
		</div>
	</div>
</body>
</html>