<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
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
<script src="https://code.jquery.com/jquery-3.6.4.min.js"></script>
<title>Pagamento</title>
</head>
<body class="d-flex align-items-center py-4" onload="load()"
	style="background: #e2e3e5; min-height: 100vh;">
	<div class="container">
		<div class="row">
			<div class="col-md-6">
				<div align="center">
					<img src="https://images-ext-2.discordapp.net/external/hVEzhQnNsZSIyyI_K5VXuRLDlr7hrZJQCr06zUXmBZw/https/i.pinimg.com/originals/d3/8d/09/d38d095f49c32da7b69171fc379cdacf.png?width=676&height=676"
						class="mb-4" height="145" width="150">
				</div>
				<br>
				<form action="pagamento" method="post">
					<input type="hidden" name="pagAnt" value="<c:out value="${pagAnt}"></c:out>"> 
					<input
						type="hidden" name="login"
						value="<c:out value="${login}"></c:out>"> 
						<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>"> 
						<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>"> 
						<input type="hidden" name="tituloLivro" value="<c:out value="${titulo}"></c:out>">
						<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
					<label class="btn text-white" style="background: #fe8e7e;">
						<i class="fas fa-arrow-left"></i> Voltar<input type="submit" name="botao" style="display: none" value="Voltar">
					</label>
				</form>
				<br> <br>
				<h2 class="mb-4">
					<b>Carrinho de Compras</b>
				</h2>

				<div class="form-group">
					<p class="h3">
						Nome do Usuário:
						<c:out value="${login}"></c:out>
					</p>
				</div>
				<br>
				<ul class="list-group">
					<li class="list-group-item text-center">
						<div class="row">
							<div class="table-responsive"
								style="height: 200px; overflow-y: scroll">
								<table class="table table-striped table">
									<thead>
										<tr>
											<th>Título do Livro</th>
											<th>Quantidade</th>
											<th>Preço</th>
											<th></th>
										</tr>
									</thead>
									<tbody>
										<c:forEach var="p" items="${pedidos}">
											<tr>
												<form action="pagamento" method="post">
													<input type="hidden" name="pagAnt" value="<c:out value="${pagAnt}"></c:out>"> 
													<input type="hidden" name="login" value="<c:out value="${login}"></c:out>"> 
													<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>"> 
													<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>">
													<input type="hidden" name="tituloLivro" value="<c:out value="${titulo}"></c:out>"> 
													<input type="hidden" name="idPedido" value="<c:out value="${p.pedido.id}"></c:out>">
													<input type="hidden" name="qtdPedido" value="<c:out value="${qtdPedido}"></c:out>">
													<td><c:out value="${p.livro.titulo}"></c:out></td>
													<td><c:out value="${p.qtd}"></c:out></td>
													<td>R$ <c:out value="${p.livro.preco}"></c:out>0</td>
													<td><input class="btn btn-danger" type="submit" id="botao" name="botao" value="Excluir"></td>
												</form>
											</tr>
										</c:forEach>
									</tbody>
								</table>
							</div>
						</div>
				</ul>
				<br> <br>
				<div class="form-check">
					<input type="checkbox" class="form-check-input"
						id="paymentMethodPIX"> 
						<label class="form-check-label h4" for="paymentMethodPIX">Método de Pagamento: PIX</label>
				</div>
				<form action="pagamento" method="post">
					<input type="hidden" name="pagAnt"
						value="<c:out value="${pagAnt}"></c:out>"> 
						<input type="hidden" name="login" value="<c:out value="${login}"></c:out>"> 
						<input type="hidden" name="pagAntBt" value="<c:out value="${pagAntBt}"></c:out>"> 
						<input type="hidden" name="pesquisa" value="<c:out value="${tituloPesquisa}"></c:out>"> 
						<input type="hidden" name="tituloLivro" value="<c:out value="${titulo}"></c:out>">

					<c:forEach var="p" items="${idsPedido}">
						<input type="hidden" name="idPedido" value="<c:out value="${p}"></c:out>">
					</c:forEach>

					<h3 class="mt-4"> Total: R$
						<c:out value="${total}"></c:out>0 <input type="hidden" name="total" value="<c:out value="${total}"></c:out>">
					</h3>
					<br>
					<div class="text-center">
						<input type="submit" class="btn w-100 py-2 text-white" id="finalizeButton" name="botao" style="background: #ca5c4d;" value="Finalizar Compra">
					</div>
					<br>
				</form>
				<div align="center" id="qrcode"></div>
			</div>
			<c:if test="${not empty teste}">
				<script src="https://cdn.rawgit.com/davidshimjs/qrcodejs/gh-pages/qrcode.min.js"></script>
				<script>
					function load() {
						var textoParaQRCode = "https://r.mtdv.me/ZL1cu8V4z9U3a49n";

				        var qrcode = new QRCode(document.getElementById("qrcode"), {
				            text: textoParaQRCode,
				            width : 250,
							height : 250
				        });
				        
				        setTimeout(function () {
			                $("#pagamentoRealizadoModal").modal("show");
			            }, 8000);
					}
				</script>
			</c:if>
			<div class="modal fade" id="pagamentoRealizadoModal" tabindex="-1" role="dialog" aria-labelledby="exampleModalLabel" aria-hidden="true">
			    <div class="modal-dialog" role="document">
			        <div class="modal-content">
			            <div class="modal-header">
			                <h3 class="modal-title text-success" id="exampleModalLabel">Sucesso</h3>
			            </div>
			            <div align="center" class="modal-body">
			            <img src="https://media.tenor.com/YgYpuNhzF8gAAAAi/rift_donald-duck_anim.gif" style="width: 30%" class="img-fluid">
			            <br> <br>
			                <b> O pagamento foi realizado com sucesso! </b>
			            </div>
			            <div class="modal-footer">
			                <form action="pagamento" method="post">
								<input type="hidden" name="login" value="<c:out value="${login}"></c:out>"> 
								<input type="hidden" name="total" value="<c:out value="${total}"></c:out>">
								<c:forEach var="p" items="${idsPedido}">
									<input type="hidden" name="idPedido" value="<c:out value="${p}"></c:out>">
								</c:forEach>
								<ul style="padding: 0">
									<li class="list-group-item">
										<label class="btn text-white" style="background: #385a64;"> Fechar <input type="submit" name="botao" style="display: none" value="Pagar">
										</label>
									</li>
								</ul>
							</form>
			            </div>
			        </div>
			    </div>
			</div>
			<div class="col-md-6">
				<img src="https://a.imagem.app/oKmRGE.png" class="img-fluid"
					style="width: 150%;">
			</div>
		</div>
	</div>
</body>
</html>