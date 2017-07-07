<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating"%>
<%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tt:template title="Cadastrar contato">
	<jsp:body>
		<div class="container">
			<div class="jumbotron">
				<h2>Memori<i class="glyphicon glyphicon-phone"></i>m</h2>
				<!--Mensagens de erro do formulario -->
				<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue"/>
				
				<form action="${pageContext.request.contextPath}/controller.do?op=cadctt" method="POST">
					
					<input type="hidden" name="id" value="${contato.id}">  
					<input id="nome" value="${contato.nome}" name="nome" type="text"
						class="form-control" placeholder="Nome" /> 
					<input id="fone" value="${contato.fone}" name="fone" class="form-control" 
						type="text" placeholder="Fone" />
					
					<fmt:formatDate var="dataAniv" value="${contato.dataAniversario}"
						pattern="dd/MM/yyyy" />
					<input id="dataaniv" value="${dataAniv}" name="dataaniv"
						class="form-control" type="date"
						placeholder="Data de criação (dd/mm/aaaa)" /> 
					
					<select class="form-control" id="operadora" name="operadora">
						<option value="${null}" label="Selecione a operadora"> 
							Selecione a operadora
						</option>
						<c:forEach var="operadora" items="${utilBean.operadoras}">
							<c:if test="${operadora.id eq contato.operadora.id}">
								<option value="${operadora.id}" label="${operadora.nome}" selected>
									${operadora.nome}
								</option>
							</c:if>
							<c:if test="${operadora.id ne contato.operadora.id}">
								<option value="${operadora.id}" label="${operadora.nome}">
									${operadora.nome}
								</option>
							</c:if>
						</c:forEach>
					</select>

					<input type="submit" value="Salvar" class="form-control btn btn-primary"/>
				</form>

			</div>
		</div>
		<c:remove var="msgs" scope="application"/>
	</jsp:body>
</tt:template>