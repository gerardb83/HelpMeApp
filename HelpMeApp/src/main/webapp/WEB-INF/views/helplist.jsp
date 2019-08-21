<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html>
<html>
<head>
<link rel="stylesheet" href="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/css/bootstrap.min.css" 
integrity="sha384-ggOyR0iXCbMQv3Xipma34MD+dH/1fQ784/j6cY/iJTQUOhcWr7x9JvoRxT2MZw1T" crossorigin="anonymous">
<meta charset="ISO-8859-1">
<title>Help List </title>
</head>
<header>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<a class="navbar-brand" href="/">Help me App</a>
		<button class="navbar-toggler" type="button" data-toggle="collapse"
			data-target="#navbarColor03" aria-controls="navbarColor03"
			aria-expanded="false" aria-label="Toggle navigation">
			<span class="navbar-toggler-icon"></span>
		</button>

		<div class="collapse navbar-collapse" id="navbarColor03">
			<ul class="navbar-nav mr-auto">
				<li class="nav-item active"><a class="nav-link" href="/">Home
						<span class="sr-only">(current)</span>
				</a></li>
				<li class="nav-item"><a class="nav-link" href="/userpro">Profile</a>
				</li>
				<li class="nav-item"><a class="nav-link" href="/logout" id="logout">Log out</a>
				</li>

			</ul>
		</div>
	</nav>

	<nav class="navbar navbar-expand-lg navbar-dark bg-primary"
		id="navbarColor01">


		<c:if test="${ not empty user }">
		Welcome ${ user.firstName }
		<div id="usernav" style="float:right;">
		<p>
				
			</p>
			<p>
				
			</p>
			</div>
		</c:if>
	</nav>
</header>
<body>
<h2 style="padding-top: 2%; text-align: center;"> Here are the services near you  </h2>
<c:forEach items="${organizations}" var="org">
<ul >

<li ><a href="/autorepo?id=${org.agcid} " >${org.nme}</a> <br> 
<p>${org.city} </p>
<p>Address: ${org.adr1} ${org.adr2}</p>
<p>Phone: ${org.phone1} </p>


<a href="/autorepo?id=${org.agcid} "><button   type="button" class="btn btn-outline-info"> Help</button ></a></li>


</ul>
</c:forEach>



</body>
<footer>

</footer>
</html>