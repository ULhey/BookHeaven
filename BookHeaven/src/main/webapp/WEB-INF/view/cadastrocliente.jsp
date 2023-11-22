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
<title>Cadastro - Cliente</title>
</head>
<body class="d-flex align-items-center py-4" style="background: #e2e3e5; min-height: 100vh;">
	<div class="container">
		<div class="row">
			<div class="col-md-6 d-flex align-items-center justify-content-center">
				<img src="https://a.imagem.app/oKwycb.png" class="img-fluid" style="width: 150%;">
			</div>
			<div class="col-md-6">
				<h2>
					<b>Criar Conta</b>
				</h2>
				<br>
				<form action="cadastrocliente" method="post">
				  
					<input type="hidden" name="pagAnt" value="<c:out value="${pagAnt}"></c:out>">
					<input type="hidden" name="login" value="<c:out value="${login}"></c:out>">
					<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>">
            		<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
            		<input type="hidden" name="tituloLivro" value="<c:out value="${titulo}"></c:out>"> 
            			
					<div class="col-md-12 form-floating mb-3">
					<input type="text" class="form-control" name="nome" placeholder="Nome"> <label for="floatingNome">Nome:</label>
					</div>
					<div class="form-group">
						<div class="row">
							<div class="col-md-4 form-floating mb-3">
								<div class="form-floating" style="width: 100%;">
									<input type="text" class="form-control" name="cpf" placeholder="CPF"> <label for="floatingCPF">CPF:</label>
								</div>
							</div>
							<div class="col-md-4 form-floating mb-3">
								<div class="form-floating" style="width: 100%;">
									<input type="date" class="form-control" name="dataNasc" placeholder="Data de Nascimento">
									<label for="floatingDataNascimento">Data de Nascimento:</label>
								</div>
							</div>
							<div class="col-md-4 form-floating mb-3">
								<div class="form-floating" style="width: 100%;">
									<input type="text" class="form-control" name="telefone" placeholder="Telefone"> <label for="floatingTelefone">Telefone:</label>
								</div>
							</div>
						</div>
					</div>
					<div class="col-md-12 form-floating mb-3">
						<input type="text" class="form-control" name="endereco" placeholder="Endereço"> <label for="floatingEndereco">Endereço:</label>
					</div>
					<div class="form-floating mb-3">
						<input type="text" class="form-control" name="email" placeholder="email.@bookheaven.com"> <label for="floatingEmail">Email:</label>
					</div>
					<div class="form-floating mb-3">
						<input type="password" class="form-control" name="senha" placeholder="Senha"> <label for="floatingSenha">Senha:</label>
					</div>
					<br>
					<input class="btn w-100 py-2 text-white" type="submit" value="Cadastrar" name="botao" class="btn w-100 py-2 text-white" style="background: #385a64;">
					<div class="d-flex justify-content-center">
						<p class="mt-3 mr-2">Já tem uma conta? </p>
							<input style="padding: 0" class="btn btn-link" type="submit" name="botao" value="Entrar">
						</div>
				</form>
			</div>
		</div>
	</div>
</body>
</html>