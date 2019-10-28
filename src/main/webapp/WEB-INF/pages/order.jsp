<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ taglib prefix="fmt" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cart</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <script
            src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js">
    </script>
    <script
            src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js">
    </script>

</head>

<body>
<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp"%>

<h2>BASKET</h2>
<p>The List Products for purchase</p>

<div class="description" >
        Total quantity: ${order.totalQuantity}
</div>

<div class="description">
        Total price: ${order.totalPrice}</div>
</div>



<c:url value="/order/getCurrentOrder" var="url"></c:url>
<form:form method="post" action="${url}" modelAttribute="order">



    <table class="table table" width="99%" style="width: available">
        <thead>
        <tr>
            <th>OrderDetail Id</th>
            <th>Product Name</th>
            <th>Product Price</th>
            <th>Products Quantity</th>
            <th>Products TotalPrice</th>
            <th>Delete</th>
        </tr>
        </thead>

        <tbody>
        <c:forEach items="${order.orderDetail}" var="orderD"
                   varStatus="varStatus" >
        <div class="product-preview-container">
            <tr>
                <td>${orderD.id}</td>
                <td>${orderD.product.productName}</td>
                <td>${orderD.product.productPrice}</td>
                <td>${orderD.quantity}</td>
<%--                <td><form:input path="[${tagStatus.index}].quantity" value="${orderD.quantity}"/>--%>
<%--                <td><form:input path="orderdetail[${varStatus.index}].quantity"--%>
<%--                                value="${orderD.quantity}" readonly="false"/></td>--%>
<%--                <td><form:input path="${orderD.quantity}"></form:input></td>--%>
                <td>${orderD.subTotalPrice}</td>
                <td>
                    <a href="<c:url value="/order/removeOrderDetail/${orderD.id}" />"
                         class="btn btn-danger" style="margin-left: 15px">
                    <span class="glyphicon glyphicon-remove"></span>
                </a>
                </td>
            </tr>
        </div>
        </c:forEach>
        </tbody>
    </table>
    <div class="form-actions">
        <button type="submit" class="btn btn-lg btn-info">Update</button>
    </div>

<%--        <div>--%>
<%--            <input class="button-update-sc" type="submit" value="Update order" />--%>
<%--            <a class="navi-item"--%>
<%--               href="${url}"></a>--%>
<%--        </div>--%>

</form:form>


</body>

</div>
<c:url value="/order/removeAllDetails/${order.id}" var="url2"></c:url>
<a href="${url2}" class="btn btn-default" style="margin-left: 20px">Clear Basket</a>
</div>

</div>
<c:url value="/getAllProducts" var="url3"></c:url>
<a href="${url3}" class="btn btn-default" style="margin-left: 20px">Continue Shopping</a>
</div>

</html>



