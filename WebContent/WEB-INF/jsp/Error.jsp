<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE html>
<html>
<head>
<title>主页</title>
<style type="text/css">
@import url("<c:url value="/css/bootstrap.css"/>");
</style>
</head>
<body>
	
<a href="/AlbumShare/login">登录</a>
	
	<h3 align="center"><c:out value="${error}" /></h3>

</body>
</html>
