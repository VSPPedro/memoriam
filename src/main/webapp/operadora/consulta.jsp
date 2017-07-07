<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<%@ taglib prefix="tt" tagdir="/WEB-INF/tags/templating"%>
<%@ taglib prefix="mm" tagdir="/WEB-INF/tags/messages"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<tt:template title="Consulta a operadora">
	<jsp:attribute name="tscript">
		<script>
			function showDeleteIcon (box) {
				var chboxs = document.getElementsByName("delids");
				var vis = "none";
				for(var i=0;i<chboxs.length;i++) { 
					if(chboxs[i].checked){
						vis = "block";
						break;
					}
				}
				document.getElementById(box).style.display = vis;
			}
		</script>
		<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>
		<script src="${pageContext.request.contextPath}/bootstrap/js/bootstrap.min.js"></script>
	</jsp:attribute>
	
	<jsp:body>
		<div class="container">
			<div class="jumbotron">
				<h2>Memori<i class="glyphicon glyphicon-phone"></i>m</h2>
				<!--Mensagens de erro do formulario -->
				<mm:messages value="${msgs}" erroStyle="color:red" infoStyle="color:blue"/>
				<form action="controller.do?op=delopr" method="POST">
					<table class="table">
						<tr align="left">
							<th></th>
							<th>Nome da operadora</th>
						</tr>
						<c:forEach var="operadora" items="${operadoras}">
							<tr align="left">
								<td>
									<input type="checkbox" name="delids" value="${operadora.id}" onclick="showDeleteIcon('div1')"/>
								</td>
								<td>
									<a href="controller.do?op=edtopr&id=${operadora.id}">${operadora.nome}</a>
								</td>
								<td>${contato.fone}</td>
							</tr>
						</c:forEach>
					</table>
					<div id="div1" style="display:none">
						<input type="submit" value="Excluir" class="form-control btn btn-danger" onclick="return confirm('Quer remover mesmo a(s) operadora(s)?');"/>
					</div>
				</form>
				<a href="operadora/cadastro.jsp" class="form-control btn btn-primary">Nova Operadora</a>
			</div>
		</div>
		<c:remove var="msgs" scope="application"/>
	</jsp:body>
</tt:template>