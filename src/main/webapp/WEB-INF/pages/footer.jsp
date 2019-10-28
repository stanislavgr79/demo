<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<script src="<c:url value="/resource/js/jquery.js"/>"></script>
<script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet"
          href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">

    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resource/css/overall.css"/>">
</head>

<body>

<footer class="footer-distributed" >

    <div class="footer-right" >

        <a href="<c:url value="/index"/>">
            <span class="glyphicon glyphicon-home"></span>
        </a>

        <security:authorize access="hasRole('ROLE_ADMIN')">
            <a href="<spring:url value="/admin/getAllCustomers" />">
                <span class="glyphicon glyphicon-user"></span>
            </a>
        </security:authorize>

        <security:authorize access="hasRole('ROLE_ADMIN')">
            <a href="<spring:url value="/order/getOrderById" />">
                <span class="glyphicon glyphicon-shopping-cart"></span>
            </a>
        </security:authorize>

        <a href="<c:url value="/login"/>">
            <span class="glyphicon glyphicon-log-in"></span>
        </a>

    </div>

    <div class="footer-left">

        <p class="footer-links">
            <a href="<c:url value="/index"/>">Home</a> ·
            <a href="<c:url value="/getAllProducts" />">Product List</a>
        </p>

    </div>

</footer>

</body>
</html>