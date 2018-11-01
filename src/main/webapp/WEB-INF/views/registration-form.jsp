<%@ taglib prefix = "c" uri = "http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<html>
    <head></head>
    <body>
        <h3>Register</h3>

        <form:form action="${pageContext.request.contextPath}/processRegistration"
                modelAttribute="newuser" method="POST">

                <c:if test="${registrationError != null}">
                    <p style="color:red;"><c:out value="${registrationError}" /></p>
                </c:if>

                <form:input path="userName" placeholder="username" />

                <form:password path="password" placeholder="password"  />

                <form:select path="role" items="${roles}" />

                <input type="submit" value="Submit" />

        </form:form>

        <br/>
                <a href="${pageContext.request.contextPath}">Login</a>

    </body>
</html>