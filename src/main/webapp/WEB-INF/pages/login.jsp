<%@ page contentType="text/html;charset=UTF-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Login</title>
    <link rel="stylesheet" href="bootstrap/css/bootstrap.min.css">
    <script src="js/jquery-3.1.0.min.js"></script>
    <script src="bootstrap/js/bootstrap.min.js"></script>
    <link rel="stylesheet" type="text/css" href="css/header.css">
    <style type="text/css">
        @media ( min-width : 1200px){
            .container {
                width: 1300px;}
        }  @media ( min-width : 992px){
            .col-md-4 {
                margin-left: 330px;}
            }
    </style>
</head>

<body>
<%@ include file="navbar.jsp"%>
<%@ include file="fon.jsp"%>

<div class="container" style="margin-top: 30px; margin-bottom: 180px;">
    <div class="col-md-4">
        <div class="login-panel panel panel-default">
            <div class="panel-heading">
                <h3 class="panel-title" align="center">Sign In</h3>
            </div>
            <div class="panel-body">

                <!-- will display after registration form registered successfully -->
                <c:if test="${not empty registrationSuccess}">
                    <div class="error" style="color: #ff0000;">${registrationSuccess}</div>
                </c:if>
                <!-- will display after logged out successfully -->
                <c:if test="${not empty logout}">
                    <div class="error" style="color: #ff0000;">${logout}</div>
                </c:if>
                <form name="loginForm"
                        method="POST"  action="${pageContext.request.contextPath}/j_spring_security_check">
                    <fieldset>
                        <div class="form-group">
                            <input class="form-control" placeholder="Text"
                                   name="j_username" type="text">
                        </div>
                        <div class="form-group">
                            <input class="form-control" placeholder="Password"
                                   name="j_password" type="password">
                        </div>
<%--                        <c:if test="${not empty error}">--%>
                        <div class="checkbox">
                            <c:if test="${not empty error}">
                                <div class="error" style="color: #ff0000">${error}</div>
                            </c:if>
                        </div>
                        <div class="form-actions">
                            <button type="submit" class="btn btn-sm btn-success"
                                    style="margin-right: 100px; margin-left: 60px"
<%--                                    onclick="return Validate()"--%>
                            >Login</button>

                            <a href="<c:url value="/user/registration"/>"
                               class="btn btn-sm btn-success">Register</a>
                        </div>
                    </fieldset>
                </form>
            </div>
        </div>
    </div>
</div>

<%@ include file="footer.jsp"%>
</body>
</html>

<%--<script type="text/javascript">--%>


<%--    /**--%>
<%--     * @return {boolean}--%>
<%--     */--%>
<%--    function Validate() {--%>
<%--        var customer = document.getElementById("customer").value;--%>
<%--        // var checkpass = document.getElementById("checkpass").value;--%>

<%--        if (${!customer.user.enabled}) {--%>
<%--            alert("customer disabled");--%>
<%--            return false;--%>
<%--        }--%>
<%--        // if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(document.getElementById("email").value))--%>
<%--        // {--%>
<%--        //     return true;--%>
<%--        // } else {--%>
<%--        //     alert("Invalid email address!");--%>
<%--        //     return false;--%>
<%--        // }--%>

<%--        return true;--%>
<%--    }--%>

<%--</script>--%>
