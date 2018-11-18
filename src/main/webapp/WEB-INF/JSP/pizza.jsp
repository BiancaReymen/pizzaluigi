<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url='/WEB-INF/JSP/head.jsp'>
	<c:param name='title' value='${pizza.naam}' />
</c:import>
</head>
<body>
	<c:import url='/WEB-INF/JSP/menu.jsp' />
	<c:if test='${empty pizza}'>
		<h1>Pizza niet gevonden</h1>
	</c:if>
	<c:if test='${not empty pizza}'>
		<h1>${pizza.naam}</h1>
		<dl>
			<dt>Nummer</dt>
			<dd>${pizza.id}</dd>
			<dt>Naam</dt>
			<dd>${pizza.naam}</dd>
			<dt>Prijs</dt>
			<dd><spring:eval expression='pizza.prijs'/></dd>
			<dt>In dollar</dt><dd><spring:eval expression='inDollar.waarde'/></dd>
			<dt>Pikant</dt>
			<dd>${pizza.pikant ? 'ja' : 'nee'}</dd>
		</dl>
	</c:if>
</body>
</html>