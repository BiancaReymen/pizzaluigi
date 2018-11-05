<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url='/WEB-INF/JSP/head.jsp'>
	<c:param name='title' value='Van tot prijs' />
</c:import>
</head>
<body>
	<c:import url='/WEB-INF/JSP/menu.jsp' />
	<h1>Van tot prijs</h1>
	<c:url value='/pizzas' var='url' />
	<form:form action='${url}' modelAttribute='vanTotPrijsForm' method='get'>
		<form:label path='van'>Van:</form:label>
		<form:input path='van' autofocus='autofocus' />
		<form:errors path='van'/>
		<form:label path='tot'>Tot:</form:label>
		<form:input path='tot' />
		<form:errors path='tot'/>
		<input type='submit' value='Zoeken'>
		<form:errors cssClass='fout'/>
	</form:form>
	<c:if test='${not empty pizzas}'>
		<ul>
			<c:forEach items='${pizzas}' var='pizza'>
				<spring:url var='url' value='/pizzas/{id}'>
					<spring:param name='id' value='${pizza.id}' />
				</spring:url>
				<li><a href='${url}'><c:out value='${pizza.naam}' /></a>(${pizza.prijs})</li>
			</c:forEach>
		</ul>
	</c:if>
</body>
</html>