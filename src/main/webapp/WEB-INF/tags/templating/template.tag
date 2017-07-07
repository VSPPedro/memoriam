<%@ tag description="Template para JSP" pageEncoding="UTF-8"%>
<%@ attribute name="title" required="true"%>
<%@ attribute name="theader" fragment="true" required="false"%>
<%@ attribute name="tscript" fragment="true" required="false"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="t" tagdir="/WEB-INF/tags/templating"%>

<html>
<head>
<title>${title}</title>
<link href="${pageContext.request.contextPath}/css/memoriam.css"rel="stylesheet">
<link href="${pageContext.request.contextPath}/bootstrap/css/bootstrap.min.css"rel="stylesheet">
</head>
<body>
	<div class="pageheader">
		<c:if test="${empty theader}">
			<t:header/>
		</c:if>
		<c:if test="${not empty theader}">
			<jsp:invoke fragment="theader"/>
		</c:if>
	</div>
	<div id="body">
		<jsp:doBody/>
	</div>
	<jsp:invoke fragment="tscript"/>
</body>
</html>