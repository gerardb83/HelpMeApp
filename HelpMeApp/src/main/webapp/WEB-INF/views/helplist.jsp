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
<nav>

</nav> 
 </header>
<body>
<h2> Here are the services near you  </h2>
<c:forEach items="${organizations}" var="org">
<ul>

<li><a href="${org.weburl }" >${org.nme}</a> <br> 
<p>Address: ${org.adr1} ${org.adr2}</p>
<p>Phone: ${org.phone1} </p>
<button   style="margin-left:  1%;"> Help</button ></li>

</ul>
</c:forEach>



</body>
<footer>

</footer>
</html>