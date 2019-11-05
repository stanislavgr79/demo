<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Shop</title>
</head>
<body>
<%@ include file="navbar.jsp"%>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp"%>




<div class="account-container">

    <div class="page-title">.</div>
    <ul>

        <li>User Name: ${pageContext.request.userPrincipal.name}</li>

        <li>Role:
            <ul>
                <c:forEach items="${userDetails.authorities}" var="auth">
                    <li>${auth.authority}</li>
                </c:forEach>
            </ul>
        </li>

    </ul>

    <div class="container"
         style="width: 1145px; margin-bottom: 180px;">
        <h2>The List of Orders</h2>

        <form:form method="get" modelAttribute="customerDetail">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>TotalQuantity</th>
                    <th>TotalPrice</th>
                    <th>OrderDetail</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${customerDetail.ordersList}" var="order">

                    <tr>
                        <td>${order.id}</td>
                        <td>${order.totalQuantity}</td>
                        <td>${order.totalPrice}</td>
                        <td>
                            <a href="<c:url value="order/${order.id}" />"
                               class="btn btn-success" style="margin-left: 15px">
                                <span class="glyphicon glyphicon-edit"></span>
                            </a>
                        </td>
                    </tr>

                </c:forEach>

                </tbody>

            </table>
        </form:form>

    </div>

</div>


</body>
</html>