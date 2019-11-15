<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="security"
           uri="http://www.springframework.org/security/tags"%>
<%@ page isELIgnored="false"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <link rel="stylesheet"
          href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>" type="text/jsp"></script>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resource/css/overall.css"/>">

</head>
<body>

<nav class="navbar navbar-inverse">
    <div class="container-fluid">


        <div class="navbar-header">
            <button type="button" class="navbar-toggle" data-toggle="collapse"
                    data-target="#myNavbar">
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
                <span class="icon-bar"></span>
            </button>
        </div>


        <div class="collapse navbar-collapse" >
            <ul class="nav navbar-nav">
                <li><a href=" <c:url value="/index" />">Home</a></li>


                <li><a href=" <c:url value="/getAllProducts" />">Product List</a></li>

                <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_MANAGER'})">
                    <li>
                        <a href=" <c:url value="/admin/product/getProductsDisabled" />">
                            Products disabled</a>
                    </li>
                </security:authorize>

                <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_MANAGER'})">
                    <li>
                        <a href=" <c:url value="/admin/product/addProduct" />">
                            Add Product</a>
                    </li>
                </security:authorize>

                <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_MANAGER'})">
                    <li>
                        <a href=" <c:url value="/admin/getAllCustomers" />">
                            List Customers</a>
                    </li>
                </security:authorize>

                <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_MANAGER'})">
                    <li>
                        <a href=" <c:url value="/admin/getAllOrders" />">
                            List All Orders</a>
                    </li>
                </security:authorize>

            </ul>

            <ul class="nav navbar-nav navbar-right">

                <c:if test="${!empty pageContext.request.userPrincipal.name}">
                    <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_USER'})">
                    <li>
                        <a href="<c:url value="/accountInfo" />">
                        <span class="glyphicon glyphicon-shopping-user"> </span>
                            Welcome..${pageContext.request.userPrincipal.name}
                    </a>
                    </li>
                    </security:authorize>

                    <security:authorize access="hasAnyRole({'ROLE_USER'})">
                        <li><a href="<c:url value="basket/getCurrentBasket" />"><span
                                class="glyphicon glyphicon-shopping-cart"></span> Basket</a></li>
                    </security:authorize>
                    <li><a href="<c:url value="/logout" />"><span
                            class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </c:if>
            </ul>


            <ul class="nav navbar-nav navbar-right">

                <c:if test="${pageContext.request.userPrincipal.name==null}">
                    <li><a href="<c:url value="/login" />"><span
                            class="glyphicon glyphicon-shopping-cart"></span>My Basket</a></li>
                    <li><a href="<c:url value="/user/registration" />"><span
                            class="glyphicon glyphicon-log-user"></span> Register</a></li>
                    <li><a href="<c:url value="/login" />"><span
                            class="glyphicon glyphicon-log-in"></span> Login</a></li>
                </c:if>

            </ul>

        </div>
    </div>
</nav>



</body>
</html>