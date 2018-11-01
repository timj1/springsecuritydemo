<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head></head>
    <body>
        <h3>Login</h3>
        <hr>
        <form:form action="${pageContext.request.contextPath}/authenticateUser" method="POST">

            <c:if test="${registrationComplete != null}">
                <p style="color:green;"><c:out value="${registrationComplete}" /></p>
            </c:if>

            <c:if test="${param.error != nul}">
                <p style="color:red;">Invalid username or password</p>
            </c:if>

            <label>Username: </label><input type="text" name="username" />
            <label>Password: </label><input type="password" name="password" />
            <input type="submit" value"Submit" />

        </form:form>

        <br/>
        <a href="${pageContext.request.contextPath}/register">Register</a>

    </body>
</html>