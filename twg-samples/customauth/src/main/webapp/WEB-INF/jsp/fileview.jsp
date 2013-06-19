<%@ page isELIgnored="false" session="true"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt"%>
<%@ taglib prefix='security'
	uri='http://www.springframework.org/security/tags'%>

<html>

<head>

<title>File View - Custom Authentication Sample App</title>

</head>

<body>

	<%@ include file="/WEB-INF/jsp/userinfobar.jsp"%>
	<%@ include file="/WEB-INF/jsp/navigation.jsp"%>

	<h1>File View</h1>

	<h2>
		<c:out value="${message}" />
	</h2>
</body>

</html>
