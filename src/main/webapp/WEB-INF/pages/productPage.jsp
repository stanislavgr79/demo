<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Product View</title>
    <link rel="stylesheet"
          href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resource/css/register.css"/>">

</head>

<body>
<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp" %>

<div class="container" style="margin-bottom: 19px">
    <h1 class="well">Product Info</h1>
    <div class="col-lg-12 well">
        <div class="row">

            <form:form method="get" modelAttribute="productEntity" >
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="id">Product Id</form:label>
                            <form:textarea type="text" placeholder="Product Id"
                                        class="form-control" path="id" readonly="true"></form:textarea>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="productName">Product Name</form:label>
                            <form:textarea type="text" placeholder="Product Name.."
                                        class="form-control" path="productName" readonly="true"></form:textarea>
                        </div>
                    </div>
                    <div class="form-group">
                        <form:label path="description">Product Description</form:label>
                        <form:textarea type="text" placeholder="Description"
                                       class="form-control" path="description" readonly="true"></form:textarea>
                    </div>
                    <div class="col-sm-4 form-group">
                        <form:label path="productPrice">Product Price</form:label>
                        <form:textarea type="text" placeholder="Product Price.."
                                    class="form-control" path="productPrice" readonly="true"></form:textarea>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>

</body>
</html>