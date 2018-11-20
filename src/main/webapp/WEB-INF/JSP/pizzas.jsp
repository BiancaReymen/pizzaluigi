<%@page contentType='text/html' pageEncoding='UTF-8' session='false'%>
<%@taglib prefix='vdab' uri='http://vdab.be/tags'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<%@taglib prefix='spring' uri='http://www.springframework.org/tags'%>
<!doctype html>
<html lang='nl'>
<head>
<vdab:head title='Pizza's'/>
</head>
<script>
document.getElementById('pizzaform').onsubmit = function(){
	document.getElementById('toevoegknop').disabled = true;
};
</script>
<body>
	<vdab:menu/>
	<c:if test='${not empty param.boodschap }'>
		<div class='boodschap'>${param.boodschap}</div>
	</c:if>
	<h1>
		Pizza's
		<c:forEach begin="1" end="5">
		&#9733;
		</c:forEach>
	</h1>
	<ul class='zebra'>
		<c:forEach var='pizza' items='${pizzas}'>

			<li>${pizza.id}: <c:out value="${pizza.naam}"/> ${pizza.prijs}
				&euro; <c:choose>
					<c:when test="${pizza.pikant}"> pikant </c:when>
					<c:otherwise>niet pikant</c:otherwise>
				</c:choose> <spring:url value="/pizzas/{id}" var="url">
					<spring:param name="id" value="${pizza.id}" />
				</spring:url> <a href="${url}">Detail</a>
			</li>
		</c:forEach>
	</ul>
</body>
</html>