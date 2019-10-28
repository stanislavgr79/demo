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

<div class="container"
     style="width: 1145px; margin-bottom: 180px;">
    <h2>Customer Management</h2>
    <p>The List of Customers</p>

    <%--<c:url value="/admin/getAllCustomers" var="url"></c:url>--%>
    <form:form method="post" action="saveCustomers" modelAttribute="Customers">
        <table class="table table-hover">
            <thead>
            <tr>
                <th>Id</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Edit</th>
                <th>Delete</th>
            </tr>
            </thead>

            <tbody>
                <%--        <c:forEach items="${movie.actors}" var="actor" varStatus="counter">--%>
            <c:forEach items="${Customers}" var="customer" varStatus="tagStatus">

                <tr>
                    <td>${customer.firstName}</td>

<%--    <td><form:input path="customer.firstName" value="${customer.firstName}"--%>
<%--                                                readonly="false"/></td>--%>
                    <td><form:input path="" value="${customer.lastName}"
                                    readonly="false"/></td>
<%--                    <td>${customer.lastName}</td>--%>
                    <td>${customer.user.email}</td>

<%--                    <td>${customer.id}</td>--%>
<%--



<%--                    <td><form:input path="customers[${tagStatus.index}].user.email" value="${customer.user.email}"--%>
<%--                                    readonly="false"/></td>--%>
                        <%--                <td>${person.firstName}</td>--%>
                        <%--                <td>${person.lastName}</td>--%>
                        <%--                <td>--%>
                        <%--                    <label>--%>
                        <%--                        <input type="text" name="email${varStatus.index}" value="${person.user.email}" />--%>
                        <%--                    </label>--%>
                        <%--                        &lt;%&ndash;  <form:input path="" value="${person.user.email}"/></td>&ndash;%&gt;--%>
                        <%--                </td>--%>
                    <td>
                        <a href="<c:url value="customer/update/${customer.id}" />"
                           class="btn btn-success" style="margin-left: 15px">
                            <span class="glyphicon glyphicon-edit"></span>
                        </a>
                    </td>
                    <td>
                        <a href="<c:url value="customer/delete/${customer.id}" />"
                           class="btn btn-danger" style="margin-left: 15px">
                            <span class="glyphicon glyphicon-remove-sign"></span>
                        </a>
                    </td>

                </tr>

            </c:forEach>

            </tbody>

        </table>
        <input type="submit" value="Save"/>
    </form:form>

</div>

<%@ include file="footer.jsp" %>

</body>
</html>
