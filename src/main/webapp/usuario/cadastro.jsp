<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating"%>
<%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<tt:template title="Cadastrar usuario">
	<jsp:body>
		<div class="container">
			<div class="jumbotron">
				<h2>Memori<i class="glyphicon glyphicon-phone"></i>m</h2>
				<!--Mensagens de erro do formulario -->
				<mm:messages value="${msgs}" erroStyle="color:red"
					infoStyle="color:blue" />
				
				<form
					action="${pageContext.request.contextPath}/controller.do?op=caduser"
					method="POST">
					
					<input type="hidden" id="id" name="id" value="${usuarioEdit.id}">  
					<input id="nome" value="${usuarioEdit.nome}" name="nome" type="text"
						class="form-control" placeholder="Nome" />
					<input id="email" value="${usuarioEdit.email}" name="email" type="text"
						class="form-control" placeholder="Email" />
					<input id="senha"  name="senha"
						type="password" class="form-control"
						placeholder="Nova Senha(Opcional)" />
					<input id="senhaConf" name="senhaConf"
						type="password" class="form-control"
						placeholder="Confirmar Nova Senha(Opcional)" />
						
					<select class="form-control" id="perfil" name="perfil">
						<option value="${null}" label="Selecione o perfil da conta:"> 
							Selecione a operadora
						</option>
						<c:forEach var="perfil" items="${utilBean.perfis}">
								<c:if test="${perfil eq usuarioEdit.perfil.nome}">
									<option value="${perfil}" label="${usuarioEdit.perfil.nome}"
									selected>
										${perfil}
									</option>
								</c:if>
								<c:if test="${perfil ne usuarioEdit.perfil.nome}">
									<option value="${perfil}" label="${usuarioEdit.perfil}">
										${perfil}
									</option>
								</c:if>
						</c:forEach>
					</select>
						
					<input type="submit" value="Salvar"
						class="form-control btn btn-primary" />
				</form>

			</div>
		</div>
		<c:remove var="msgs" scope="application" />
	</jsp:body>
</tt:template>