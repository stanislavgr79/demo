<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Register Form</title>
    <link rel="stylesheet"
          href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>
    <link rel="stylesheet" type="text/css"
          href="<c:url value="/resource/css/register.css"/>">
</head>
<body>

<%@ include file="navbar.jsp"%>
<%@ include file="fon.jsp"%>
<%@ include file="footer.jsp"%>

<div class="container" style="margin-bottom: 19px">
    <h1 class="well">Register Form</h1>
    <div class="col-lg-12 well">
        <div class="row">



            <c:url var="add" value="/secure/createUser"></c:url>
            <form:form method="post" action="${add}" modelAttribute="UserDto"
                       enctype="multipart/form-data" id="kkk">

                <div class="col-sm-12">

                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="email">Email</form:label>
                            <form:input type="text" placeholder="Enter Email.."
                                        class="form-control" path="email"></form:input>
                        </div>
                    </div>

<%--                    <hr>--%>
                    <div class="form-group">


                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="password">Password</form:label>
                            <form:input type="password" placeholder="********"
                                        class="form-control" path="password" id="pass" ></form:input>
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Confirm Password</label>
                            <input type="password"
                                   placeholder="********" class="form-control" id="confirmpass" />
                        </div>
                    </div>
                </div>

                                <form:select  path="roleSet">
                                    <c:forEach items="${Roles}" var="state">
                                        <option value="${state.id}" >${state.name}</option>
                                    </c:forEach>
                                </form:select>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-lg btn-info"
                                onclick="return Validate()">Submit</button>
                    </div>

                </div>
            </form:form>
        </div>
    </div>
</div>

<!-- Validating Password -->
<script type="text/javascript">
    function Validate() {
        var password = document.getElementById("pass").value;
        var confirmpass = document.getElementById("confirmpass").value;
        if (password != confirmpass) {
            alert("Password does Not Match.");
            return false;
        }
        return true;
    }
</script>