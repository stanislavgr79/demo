<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@ page isELIgnored="false" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customers Management</title>
    <link rel="stylesheet"
          href="<c:url value="/resource/bootstrap/css/bootstrap.min.css"/>">
    <script src="<c:url value="/resource/js/jquery.js"/>"></script>
    <script src="<c:url value="/resource/bootstrap/js/bootstrap.min.js"/>"></script>

</head>

<body>

<%@ include file="navbar.jsp" %>
<%@ include file="fon.jsp" %>
<%@ include file="footer.jsp" %>

<div class="container"
     style="width: 1145px; margin-bottom: 180px;">
    <h2>Customer Management</h2>
    <p>The List of Customers</p>

    <form:form method="get" modelAttribute="Customers">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Id</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Enabled</th>
                <th>AccountNonExpired</th>
                <th>CredentialsNonExpired</th>
                <th>AccountNonLocked</th>
                <th>Edit</th>

<%--                <security:authorize access="hasRole({'ROLE_ADMIN'})">--%>
                <th>View</th>
<%--                </security:authorize>--%>

            </tr>
            </thead>

            <tbody>
            <c:forEach items="${Customers}" var="customer" varStatus="tagStatus">

                <tr>
                    <td>${customer.id}</td>
                    <td>${customer.firstName}</td>
                    <td>${customer.lastName}</td>
                    <td>${customer.user.email}</td>
                    <td>${customer.user.enabled}</td>
                    <td>${customer.user.accountNonExpired}</td>
                    <td>${customer.user.credentialsNonExpired}</td>
                    <td>${customer.user.accountNonLocked}</td>

                    <td>
                        <a href="<c:url value="customer/update/${customer.id}" />"
                           class="btn btn-success" style="margin-left: 15px">
                            <span class="glyphicon glyphicon-edit"></span>
                        </a>
                    </td>
<%--                    <security:authorize access="hasRole({'ROLE_ADMIN'})">--%>
<%--                    <td>--%>
<%--                        <a href="<c:url value="customer/delete/${customer.id}" />"--%>
<%--                           class="btn btn-danger" style="margin-left: 15px">--%>
<%--                            <span class="glyphicon glyphicon-remove-sign"></span>--%>
<%--                        </a>--%>
<%--                    </td>--%>
<%--                    </security:authorize>--%>
                    <td>
                        <a href="<c:url value="getCustomer/${customer.id}" />"
                           class="btn btn-success" style="margin-left: 15px">
                            <span class="glyphicon glyphicon-info-sign"></span>
                        </a>
                    </td>
                </tr>

            </c:forEach>

            </tbody>

        </table>
    </form:form>

</div>


</body>
</html>
