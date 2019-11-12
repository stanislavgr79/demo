<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Company Management</title>
    <link rel="stylesheet"
          href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>

</head>

<body>

<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp" %>

<label>List roles:</label>
<div>
    <c:forEach items="${Roles}" var="state">
        <option value="${state.id}" >${state.name}</option>
    </c:forEach>
</div>

<div>
    <a href="<c:url value="/secure/createUser" />"
       class="btn btn-success" style="margin-left: 15px"  title="CreateUser" >
        <input class="button-update-sc" type="submit" value="CreateUser" style="color: #0f0f0f"/>
    </a>
</div>




<div class="container"
     style="width: 1145px; margin-bottom: 180px;">
    <h2>Company Management</h2>
    <p>The List of Users</p>

    <form:form method="get" modelAttribute="users">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Id</th>
                <th>Email</th>
                <th>Enabled</th>
                <th>AccountNonExpired</th>
                <th>CredentialsNonExpired</th>
                <th>AccountNonLocked</th>
                <th>Edit</th>
                <th>Delete</th>

            </tr>
            </thead>

            <tbody>
            <c:forEach items="${users}" var="user" varStatus="tagStatus">

                <tr>
                    <td>${user.id}</td>
                    <td>${user.email}</td>
                    <td>${user.enabled}</td>
                    <td>${user.accountNonExpired}</td>
                    <td>${user.credentialsNonExpired}</td>
                    <td>${user.accountNonLocked}</td>

                    <td>
                        <a href="<c:url value="/secure/editUser/${user.id}" />"
                           class="btn btn-success" style="margin-left: 15px">
                            <span class="glyphicon glyphicon-edit"></span>
                        </a>
                    </td>
                        <td>
                            <a href="<c:url value="/secure/deleteUser/${user.id}" />"
                               class="btn btn-danger" style="margin-left: 15px">
                                <span class="glyphicon glyphicon-remove-sign"></span>
                            </a>
                        </td>
                </tr>

            </c:forEach>

            </tbody>

        </table>
    </form:form>

</div>

<%@ include file="footer.jsp" %>

</body>
</html>