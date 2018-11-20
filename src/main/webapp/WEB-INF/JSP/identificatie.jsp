<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<%@taglib prefix='form' uri='http://www.springframework.org/tags/form'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title='Identificatie'/>
</head>
<body>
	<vdab:menu/>
	<h1>Identificatie</h1>
	<c:url value='/identificatie' var='url' />
	<form:form action='${url}' modelAttribute='identificatie' method='post'
		id='identificatieForm'>
		<form:label path='emailAdres'>Email adres:
<form:errors path='emailAdres' />
		</form:label>
		<form:input path='emailAdres' autofocus='autofocus'
			required='required' type='email' />
		<input type='submit' value='OK' id='okKnop'>
	</form:form>
	<script>
		document.getElementById('identificatieForm').onsubmit = function() {
			document.getElementById('okKnop').disabled = true;
		};
	</script>
</body>
</html>