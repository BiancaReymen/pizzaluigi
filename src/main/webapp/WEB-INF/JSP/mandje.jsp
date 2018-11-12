<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<!doctype html>
<html lang='nl'>
<head>
<c:import url='/WEB-INF/JSP/head.jsp'>
	<c:param name='title' value='Mandje' />
</c:import>
</head>
<body>
	<c:import url='/WEB-INF/JSP/menu.jsp' />
	<h1>Mandje</h1>
	<c:url value='/mandje' var='url' />
	<form:form action='${url}' modelAttribute='mandjeForm' method='post'
		id='mandjeform'>
		<form:label path='pizzaId'>Pizza: <form:errors
				path='pizzaId' />
		</form:label>
		<form:select path='pizzaId' items='${allePizzas}' itemLabel='naam'
			itemValue='id' />
		<input type='submit' value='Toevoegen' id='toevoegknop'>
	</form:form>
	<c:if test='${not empty pizzasInMandje}'>
		<h2>Pizza's in mandje</h2>
		<ul>
			<c:forEach items='${pizzasInMandje}' var='pizza'>
				<li><c:out value='${pizza.naam}' /></li>
			</c:forEach>
		</ul>
	</c:if>
	<script>
		document.getElementById('mandjeform').onsubmit = function() {
			document.getElementById('toevoegknop').disabled = true;
		};
	</script>
</body>
</html>