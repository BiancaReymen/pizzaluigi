<%@tag description='menu' pageEncoding='UTF-8'%>
<%@taglib prefix='c' uri='http://java.sun.com/jsp/jstl/core'%>
<header>
	<nav>
		<ul>
			<li><a href="<c:url value='/'/>">Welkom</a></li>
			<li><a href="<c:url value='/pizzas'/>">Pizza's</a></li>
			<li><a href="<c:url value='/pizzas/vantotprijs'/>">Van tot
					prijs</a></li>
			<li><a href="<c:url value='/pizzas/prijzen'/>">Prijzen</a></li>
			<li><a href="<c:url value='/pizzas/toevoegen'/>">Toevoegen</a></li>
			<li><a href="<c:url value='/mandje'/>">Mandje</a></li>
			<li><a href="<c:url value='/identificatie'/>">Identificatie</a></li>
			<li><a href="<c:url value='/headers'/>">Headers</a></li>
		</ul>
	</nav>
</header>
