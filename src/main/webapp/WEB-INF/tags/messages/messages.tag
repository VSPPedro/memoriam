<%@ tag description="Tag apra exibir mensagens diversas" pageEncoding="UTF-8"%>
<%@ tag import="br.edu.ifpb.memoriam.facade.Categoria"%>
<%@ tag import="br.edu.ifpb.memoriam.facade.Mensagem"%>
<%@ attribute name="value" required="true" rtexprvalue="true" type="java.util.List"%>
<%@ attribute name="erroStyle" required="false" rtexprvalue="true"%>
<%@ attribute name="infoStyle" required="false" rtexprvalue="true"%>
<%@ attribute name="avisoStyle" required="false" rtexprvalue="true"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>

<c:if test="${not empty value}">
	<div align="left">
		<div>
			<ul style="padding-left:0px;">
				<c:forEach var="msg" items="${value}">
					<c:if test="${msg.categoria eq 'INFO'}">
						<c:set var="estilo" value="${infoStyle}"/>
					</c:if>
					<c:if test="${msg.categoria eq 'ERRO'}">
						<c:set var="estilo" value="${erroStyle}"/>
					</c:if>
					<c:if test="${msg.categoria eq 'AVISO'}">
						<c:set var="estilo" value="${avisoStyle}"/>
					</c:if>
					<li style="list-style-type: none; ${estilo}">${msg.mensagem}</li>
				</c:forEach>
			</ul>
		</div>
	</div>
</c:if>