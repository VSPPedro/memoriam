<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE HTML>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Memoriam</title>
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"
	rel="stylesheet" />
<link href="${pageContext.request.contextPath}/css/memoriam.css"
	rel="stylesheet">
<script
	src="https://ajax.googleapis.com/ajax/libs/jquery/3.2.1/jquery.min.js"></script>
<script type="text/javascript"
	src="${pageContext.request.contextPath}/javascript/memoriam.js"></script>
</head>
<body>

	<nav class="navbar navbar-default navbar-fixed-top">
		<div class="container">
			<div id="navbar">
			
				<c:if test="${perfil eq 'ADMIN'}">
					<ul class="nav navbar-nav">
						<li>
							<a href="${pageContext.request.contextPath}/controller.do?op=conctt">Contatos</a>
						</li>
						<li>
							<a href="${pageContext.request.contextPath}/controller.do?op=conoper">Operadoras</a>
						</li>
					</ul>
				</c:if>
				
				<ul class="nav navbar-nav navbar-right">
					<li class="dropdown">
						 <a href="#" class="dropdown-toggle" data-toggle="dropdown"
						 	role="button" aria-haspopup="true" aria-expanded="false">
						 		${sessionScope.usuario.nome}
						 			<span class="caret"></span>
			 			</a>
						 <ul class="dropdown-menu">
							<li>
								<a href="#" id="link-submit">Sair</a>
							</li>
							<li role="separator"class="divider"></li>
							<li>
								<a href="#">${sessionScope.usuario.perfil}</a>
							</li>
						</ul>
					</li>
				</ul>
			</div>
		</div>
	</nav>

	<div class="container">
		<div class="jumbotron">
			<h2>
				Memori<i class="glyphicon glyphicon-phone"></i>m
			</h2>
			
			<c:if test="${not empty msgs}">
				<div align="left">
					<div style="color: red">
						<ul style="padding-left: 0px;">
							<c:forEach var="msg" items="${msgs}">
								<li style="list-style-type: none;">${msg}</li>
							</c:forEach>
						</ul>
					</div>
				</div>
			</c:if>
			
			<form action="${pageContext.request.contextPath}/controller.do"
				method="POST" class="form-horizontal">
				<input type="hidden" name="op" value="exlopr">
				<table>
					<tr align="left">
						<th></th>
						<th>Nome da operadora</th>
					</tr>
					<c:forEach var="operadora" items="${operadoras}">
						<tr align="left">
							<td><input name="selecionarOperadora" value="${operadora.id}"
								type="checkbox" onclick="showButton()" /></td>
							<td><a href="controller.do?op=edtopr&id=${operadora.id}">${operadora.nome}</a></td>
						</tr>
					</c:forEach>
				</table>
				<a href="operadora/cadastro.jsp" class="form-control btn btn-primary">Nova Operadora</a>
				<input id="btnExcluir" type="submit"
					class="form-control btn  btn-danger" style="display: none;" value="Excluir">
				<a href="controller.do?op=conctt" class="form-control btn btn-primary">Contatos</a>
			</form>
		</div>
	</div>
	
	<c:set var="endofconversation" value="true" scope="request" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	<script type="text/javascript">var form = document.getElementById("logout-form");
		document.getElementById("link-submit").addEventListener("click", function() {
			form.submit();
		});
	</script>
</body>
</html>