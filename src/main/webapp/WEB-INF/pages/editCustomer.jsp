<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Edit Product</title>
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
    <h1 class="well">Edit Customer</h1>
    <div class="col-lg-12 well">
        <div class="row">

<%--            <c:url value="admin/customer/update" var="url"></c:url>--%>
            <form:form method="post" modelAttribute="customer" action="/admin/customer/update">
                <div class="col-sm-12">
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="id">Customer Id</form:label>
                            <form:input type="text" placeholder="Customer Id"
                                        class="form-control" path="id" readonly="true"></form:input>
                        </div>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="firstName">First Name</form:label>
                            <form:input type="text" placeholder="Customer First Name"
                                        class="form-control" path="firstName" readonly="true"></form:input>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="lastName">First Name</form:label>
                            <form:input type="text" placeholder="Customer First Name"
                                        class="form-control" path="lastName" readonly="true"></form:input>
                        </div>
                    </div>
                    <form:hidden path="user.id" ></form:hidden>

                    <div class="form-group">
                        <form:label path="user.email">User detail</form:label>
                        <form:input type="text" placeholder="User email"
                                       class="form-control" path="user.email" readonly="true"></form:input>
                    </div>

                    <div class="col-sm-4 form-group">
                        <form:label path="user.enabled">Is enabled</form:label>
                        <form:checkbox placeholder="User is enabled"
                                       class="form-control" path="user.enabled"></form:checkbox>
                    </div>
                    <div class="col-sm-4 form-group">
                        <form:label path="user.accountNonExpired">Is AccountNonExpired</form:label>
                        <form:checkbox placeholder="User is AccountNonExpired"
                                       class="form-control" path="user.accountNonExpired"></form:checkbox>
                    </div>
                    <div class="col-sm-4 form-group">
                        <form:label path="user.credentialsNonExpired">Is CredentialsNonExpired</form:label>
                        <form:checkbox placeholder="User is CredentialsNonExpired"
                                       class="form-control" path="user.credentialsNonExpired"></form:checkbox>
                    </div>
                    <div class="col-sm-4 form-group">
                        <form:label path="user.accountNonLocked">Is isAccountNonLocked</form:label>
                        <form:checkbox placeholder="User AccountNonLocked"
                                       class="form-control" path="user.accountNonLocked"></form:checkbox>
                    </div>
                </div>

                    <div class="row">
                    <div class="form-actions">
                        <button type="submit" class="btn btn-lg btn-info">Update</button>
                    </div>
                    </div>

            </form:form>
        </div>
    </div>
</div>

</body>
</html>