<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<style>
</style>
<title>Cadastro de Contato</title>
<link
	href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"
	rel="stylesheet">
<link href="${pageContext.request.contextPath}/css/memoriam.css"
	rel="stylesheet">
</head>
<body>
	<div class="container">
		<div class="jumbotron">
			<h2>
				Memori<i class="glyphicon glyphicon-phone"></i>m
			</h2>
			<h3>Dados da Operadora</h3>

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
				
				<input type="hidden" name="op" value="cadopr">
				<input type="hidden" name="id" value="${operadora.id}">  
				<input id="nome" value="${operadora.nome}" name="nome" type="text"
					class="form-control" placeholder="Nome" />
				<input id="prefixo" value="${operadora.prefixo}" name="prefixo" type="text"
					class="form-control" placeholder="Prefixo" />  
				
				<input type="submit"
					class="form-control btn btn-primary" value="Salvar">
			</form>
		</div>
	</div>
	<c:set var="endofconversation" value="true" scope="request" />
	<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js" />
	<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js" />
</body>
</html>