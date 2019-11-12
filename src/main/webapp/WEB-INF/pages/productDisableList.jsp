<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product Management</title>
    <link rel="stylesheet" href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>
</head>

<body>

<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp"%>

<div class="container"
     style="width: 1145px; margin-bottom: 180px;">
    <h2>Product Management</h2>
    <p>The List of Disable Products</p>
    <table class="table table-hover" >
        <thead>
        <tr>
            <th>Product Id</th>
            <th>Product Name</th>
            <th>Products Price</th>
            <th>Description</th>
            <th>Status</th>
            <th>View</th>
            <!-- 	for admin -->
            <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_MANAGER'})">
                <th>Edit</th>
            </security:authorize>

        </tr>
        </thead>

        <tbody>

        <c:forEach items="${products}" var="item">
            <tr>
                <td>${item.id}</td>
                <td>${item.productName}</td>
                <td>${item.productPrice}</td>
                <td style="width: 160px">${item.description}</td>
                <td>${item.enabled}</td>
                <td>
                    <a href="<c:url value="/getProductById/${item.id}" />"
                       class="btn btn-info" role="button">
                        <span class="glyphicon glyphicon-info-sign"></span>
                    </a>
                </td>

                <security:authorize access="hasAnyRole({'ROLE_ADMIN','ROLE_MANAGER'})">
                    <td>
                        <a href="<c:url value="/admin/product/update/${item.id}" />"
                           class="btn btn-success" style="margin-left: 15px">
                            <span class="glyphicon glyphicon-edit"></span>
                        </a>
                    </td>
                </security:authorize>

            </tr>

        </c:forEach>

        </tbody>
    </table>
</div>

</body>
</html>
