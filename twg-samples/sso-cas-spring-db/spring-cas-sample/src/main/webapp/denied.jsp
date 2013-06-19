<%@ page import="org.springframework.security.core.AuthenticationException" %>
<%@ page import="org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter" %>

<html>
<head>
    <title>Access Denied!</title>
</head>

<body>
<h2>Access Denied!</h2>

<font color="red">
    You don't have enough privileges to access this resource.<br/><br/>
</font>
<p><a href="/spring-cas-sample">Home</a>
<p><a href="/spring-cas-sample/j_spring_security_logout">Logout</a>

</body>
</html>
