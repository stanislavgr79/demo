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



            <c:url var="add" value="/user/registration"></c:url>
            <form:form method="post" action="${add}" modelAttribute="registerForm"
                       enctype="multipart/form-data">

                <div class="col-sm-12">

                    <div class="row">
                        <div class="form-group">
<%--                            <form:label path="">User detail</form:label>--%>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="firstName">First Name</form:label>
                            <form:input type="text" placeholder="Enter First Name.."
                                        class="form-control" path="firstName"></form:input>
                            <form:errors path="firstName" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>

                        <div class="col-sm-6 form-group">
                            <form:label path="lastName">Last Name</form:label>
                            <form:input type="text" placeholder="Enter Last Name.."
                                        class="form-control" path="lastName"></form:input>
                            <form:errors path="lastName" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="email">Email</form:label>
                            <form:input type="text" placeholder="Enter Email.."
                                        class="form-control" path="email"></form:input>
                            <form:errors path="email" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                    </div>

                    <hr>

                    <div class="form-group">
<%--                        <form:label path="address">Address</form:label>--%>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="country">Country</form:label>
                            <form:input type="text" placeholder="Enter Country.."
                                        class="form-control" path="country"></form:input>
                            <form:errors path="country" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="city">City</form:label>
                            <form:input type="text" placeholder="Enter city"
                                        class="form-control" path="city"></form:input>
                            <form:errors path="city" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="street">Street</form:label>
                            <form:input type="text" placeholder="Enter street"
                                        class="form-control" path="street"></form:input>
                            <form:errors path="street" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="flat">Flat</form:label>
                            <form:input type="text" placeholder="Enter flat"
                                        class="form-control" path="flat"></form:input>
                            <form:errors path="flat" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="password">Password</form:label>
                            <form:input type="password" placeholder="********"
                                        class="form-control" path="password"></form:input>
                            <form:errors path="password" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="confirmPassword">Password</form:label>
                            <form:input type="password" placeholder="********"
                                        class="form-control" path="confirmPassword"></form:input>
                            <form:errors path="confirmPassword" cssClass="error" cssStyle="color: #761c19"></form:errors>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-lg btn-info" >Submit
<%--                                onclick="return Validate()">Submit--%>
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>



<%--<script type="text/javascript">--%>


<%--    /**--%>
<%--     * @return {boolean}--%>
<%--     */--%>
<%--    function Validate() {--%>
<%--        var password = document.getElementById("pass").value;--%>
<%--        var checkpass = document.getElementById("checkpass").value;--%>

<%--        if (password !== checkpass) {--%>
<%--            alert("Password does Not Match.");--%>
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

</body>
</html>
