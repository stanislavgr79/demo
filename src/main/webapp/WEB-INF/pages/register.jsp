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
            <form:form method="post" action="${add}" modelAttribute="customer"
                       enctype="multipart/form-data">
                <div class="col-sm-12">

                    <div class="row">
                        <div class="form-group">
                            <form:label path="">User detail</form:label>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="firstName">First Name</form:label>
                            <form:input type="text" placeholder="Enter First Name.."
                                        class="form-control" path="firstName"></form:input>
                            <form:errors path="firstName"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="lastName">Last Name</form:label>
                            <form:input type="text" placeholder="Enter Last Name.."
                                        class="form-control" path="lastName"></form:input>
                            <form:errors path="lastName"></form:errors>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="user.email">Email</form:label>
                            <form:input type="text" placeholder="Enter Email.."
                                        class="form-control" path="user.email" id="email"></form:input>
                            <form:errors path="user.email"></form:errors>
                        </div>
                    </div>

                    <hr>
                    <div class="form-group">
                        <form:label path="address">Address</form:label>
                    </div>
                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="address.country">Country</form:label>
                            <form:input type="text" placeholder="Enter Country.."
                                        class="form-control" path="address.country"></form:input>
                            <form:errors path="address.country"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="address.city">City</form:label>
                            <form:input type="text" placeholder="Enter city"
                                        class="form-control" path="address.city"></form:input>
                            <form:errors path="address.city"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="address.street">Street</form:label>
                            <form:input type="text" placeholder="Enter street"
                                        class="form-control" path="address.street"></form:input>
                            <form:errors path="address.street"></form:errors>
                        </div>
                        <div class="col-sm-6 form-group">
                            <form:label path="address.flat">Flat</form:label>
                            <form:input type="text" placeholder="Enter flat"
                                        class="form-control" path="address.flat"></form:input>
                            <form:errors path="address.flat"></form:errors>
                        </div>
                    </div>

                    <div class="row">
                        <div class="col-sm-6 form-group">
                            <form:label path="user.password">Password</form:label>
                            <form:input type="password" placeholder="********"
                                        class="form-control" path="user.password" id="pass"></form:input>
                        </div>
                        <div class="col-sm-6 form-group">
                            <label>Confirm Password</label>
                            <input type="password"
                                   placeholder="********" class="form-control"
                                   id="confirmpass"/>
                        </div>
                    </div>

                    <div class="form-actions">
                        <button type="submit" class="btn btn-lg btn-info"
                                onclick="return Validate()">Submit
                        </button>
                    </div>
                </div>
            </form:form>
        </div>
    </div>
</div>



<script type="text/javascript">


    /**
     * @return {boolean}
     */
    function Validate() {
        var password = document.getElementById("pass").value;
        var checkpass = document.getElementById("checkpass").value;

        if (password !== checkpass) {
            alert("Password does Not Match.");
            return false;
        }
        // if (/^\w+([\.-]?\w+)*@\w+([\.-]?\w+)*(\.\w{2,3})+$/.test(document.getElementById("email").value))
        // {
        //     return true;
        // } else {
        //     alert("Invalid email address!");
        //     return false;
        // }

        return true;
    }

</script>

</body>
</html>