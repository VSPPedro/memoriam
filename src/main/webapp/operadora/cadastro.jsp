<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating"%>
<%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tt:template title="Cadastrar operadora">	
	<jsp:body>
		<div class="container">
			<div class="jumbotron">
				<h2>Memori<i class="glyphicon glyphicon-phone"></i>m</h2>
				<!--Mensagens de erro do formulario -->
				<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue"/>
				
				<form action="${pageContext.request.contextPath}/controller.do?op=cadopr" method="POST">
					
					<input type="hidden" name="id" value="${operadora.id}">  
					<input id="nome" value="${operadora.nome}" name="nome" type="text"
						class="form-control" placeholder="Nome" />
					<input id="prefixo" value="${operadora.prefixo}" name="prefixo" type="text"
						class="form-control" placeholder="Prefixo" />

					<input type="submit" value="Salvar" class="form-control btn btn-primary"/>
				</form>

			</div>
		</div>
		<c:remove var="msgs" scope="application"/>
	</jsp:body>
</tt:template>