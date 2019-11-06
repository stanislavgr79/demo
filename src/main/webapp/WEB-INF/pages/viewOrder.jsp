<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Cart</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
    <link rel="stylesheet" href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>

</head>
<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp"%>
<body>



<h2>ORDER</h2>
<p>The List Products in Order</p>

<div class="description" >
    Total quantity: ${Order.totalQuantity}
</div>

<div class="description">
    Total price: ${Order.totalPrice}
</div>


<form:form  method="get" modelAttribute="Order"  >

<table class="table table" width="99%" style="width: available">

    <thead>
    <tr>
        <th>Product Name</th>
        <th>Product Price</th>
        <th>Products Quantity</th>
        <th>Products TotalPrice</th>
    </tr>
    </thead>

    <tbody>

    <c:forEach items="${Order.orderDetail}" var="od" varStatus="tagStatus">
        <tr>
            <form:hidden path="orderDetail[${tagStatus.index}].product.id" value="${od.product.id}" readonly="true"/>
            <td><form:input path="orderDetail[${tagStatus.index}].product.productName" value="${od.product.productName}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].product.productPrice" value="${od.product.productPrice}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].quantity" value="${od.quantity}" readonly="true"/></td>
            <td><form:input path="orderDetail[${tagStatus.index}].price" value="${od.price}" readonly="true"/></td>
        </tr>
    </c:forEach>

    </tbody>

</table>

    </form:form>

</body>


<div>
    <a href="<c:url value="/accountInfo" />"
       class="btn btn-success" style="margin-left: 15px"  title="Back to My Orders..." >
        <input class="button-update-sc" type="submit" value="Back to My Orders..." style="color: #0f0f0f"/>
    </a>
</div>

<div>
    <a href="<c:url value="/getAllProducts" />"
       class="btn btn-success" style="margin-left: 15px"  title="Continue Shopping..." >
        <input class="button-update-sc" type="submit" value="Continue Shopping..." style="color: #0f0f0f"/>
    </a>
</div>

</html>



