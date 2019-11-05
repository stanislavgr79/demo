<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Shop</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css" />
    <link rel="stylesheet" href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>

</head>
<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp"%>
<body>



<h2>ORDER</h2>
<form:input path="Order.id" value="${Order.id}" readonly="true"/>

<div class="description" >
    Total quantity: ${Order.totalQuantity}
</div>

<div class="description">
    Total price: ${Order.totalPrice}
</div>

<%--<div>--%>
    <%--<a href="<c:url value="/order/removeAllDetails" />"--%>
       <%--class="btn btn-success" style="margin-left: 15px"  title="deleteAllRows" >--%>
        <%--<input class="button-update-sc" type="submit" value="Clear basket" style="color: #0f0f0f"/>--%>
    <%--</a>--%>
<%--</div>--%>

<div class="container"
     style="width: 1145px; margin-bottom: 180px;">
    <h2>The List of Products in Order</h2>

    <c:url value="/admin/editOrder/update" var="url"></c:url>
<form:form action="${url}" method="post" modelAttribute="Order"  >

<table class="table table" width="99%" style="width: available">

    <form:hidden path="id" value="${Order.id}" readonly="true"/>
    <thead>
    <tr>
        <th>OrderDetail id</th>
        <th>Product Name</th>
        <th>Product Price</th>
        <th>Products Quantity</th>
        <th>Products TotalPrice</th>
        <%--<th>Delete</th>--%>
    </tr>
    </thead>

    <tbody>

    <c:forEach items="${Order.orderDetail}" var="od" varStatus="tagStatus">
        <tr>
            <td><form:input path="orderDetail[${tagStatus.index}].id" value="${od.id}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].product.productName" value="${od.product.productName}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].product.productPrice" value="${od.product.productPrice}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].quantity" value="${od.quantity}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].price" value="${od.price}" readonly="true"/></td>

            <%--<td>--%>
                <%--<a href="<c:url value="/order/removeOrderDetail/${od.product.id}" />"--%>
                   <%--class="btn btn-danger" style="margin-left: 15px">--%>
                    <%--<span class="glyphicon glyphicon-remove"></span>--%>
                <%--</a>--%>
            <%--</td>--%>
        </tr>
    </c:forEach>

    </tbody>

</table>


<%--<a class="btn btn-success" style="margin-left: 15px">--%>
    <%--<input class="button-update-sc" type="submit" name="action1" value="Update quantity." style="color: #0f0f0f"/>--%>
    <%--<a/>--%>

    <a class="btn btn-success" style="margin-left: 15px">
        <input class="button-update-sc" type="submit" value="Success order." style="color: #0f0f0f"/>
    </a>

    </form:form>
</div>
</body>


<%--<div>--%>
    <%--<a href="<c:url value="/getAllProducts" />"--%>
       <%--class="btn btn-success" style="margin-left: 15px"  title="Continue Shopping..." >--%>
        <%--<input class="button-update-sc" type="submit" value="Continue Shopping..." style="color: #0f0f0f"/>--%>
    <%--</a>--%>
<%--</div>--%>

</html>


