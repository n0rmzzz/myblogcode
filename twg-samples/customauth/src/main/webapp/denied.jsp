<%@ page isELIgnored="false" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>

<html>

<head>

<title>Access Denied - Custom Authentication Sample App</title>

<style TYPE="text/css">
.errormessage {
	color: red;
}
</style>

</head>

<body onload='document.loginForm.j_username.focus();'>

	<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

	<h1 class="errormessage">Access Denied</h1>

</body>

</html>
