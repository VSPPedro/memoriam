<%@ tag description="Barra de navegação comum às páginas" body-content="empty" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:set var="perfil" value="${sessionScope.usuario.perfil}"/>
<c:set var="nome" value="${sessionScope.usuario.nome}"/>

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
					<li>
						<a href="${pageContext.request.contextPath}/controller.do?op=conuser">Usuarios</a>
					</li>
				</ul>
			</c:if>
			<ul class="nav navbar-nav navbar-right">
				<li class="dropdown">
					<a href="# "class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">
						${nome} (${perfil})<span class="caret"></span>
					</a>
					<ul class="dropdown-menu">
						<li>
							<a href="#"id="link-submit">
								<i class="glyphicon glyphicon-log-out"></i>Sair
							</a>
						</li>
					</ul>
				</li>
			</ul>
		</div>
	</div>
</nav>

<form id="logout-form"action="${pageContext.request.contextPath}/controller.do"method="POST">
	<input type="hidden"name="op"value="logout"/>
</form>

<script type="text/javascript">
	var form = document.getElementById("logout-form");
	document.getElementById("link-submit").addEventListener("click", function() {form.submit();});
</script>