<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
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

        <li>Customer : ${customer.user.email}</li>

    </ul>

    <div class="container"
         style="width: 1145px; margin-bottom: 180px;">
        <h2>The List of Orders</h2>

        <form:form method="get" modelAttribute="customer">
            <table class="table table-hover">
                <thead>
                <tr>
                    <th>Id</th>
                    <th>Create Date</th>
                    <th>TotalQuantity</th>
                    <th>TotalPrice</th>
                    <th>Status order</th>
                    <th>OrderDetail</th>
                </tr>
                </thead>

                <tbody>
                <c:forEach items="${customer.ordersList}" var="order">

                    <tr>
                        <td>${order.id}</td>
                        <td>
                            <fmt:formatDate value="${order.orderCreateDate}" pattern="dd-MM-yyyy HH:mm"/>
                        </td>
                        <td>${order.totalQuantity}</td>
                        <td>${order.totalPrice}</td>
                        <td>${order.statusOrder.STATUS}</td>
                        <td>
                            <a href="<c:url value="/admin/editOrder/${order.id}" />"
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