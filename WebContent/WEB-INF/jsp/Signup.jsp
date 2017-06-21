<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>注册</title>
<style type="text/css">
@import url(css/bootstrap.css);
</style>
<style type="text/css">@import url("<c:url value="/css/main.css"/>");</style>
</head>
<body>
	<h3 align="center"><c:out value="${error}" /></h3>

	<div class="container-fluid">
	
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 main">
				<h2 class="page-header">注册</h2>
				<form:form class="form-horizontal" commandName="user"
					action="/AlbumShare/check-signup" method="post">
					<div class="form-group">
						<label for="username" class="col-sm-2 control-label">用户名</label>
						<div class="col-sm-8">
							<form:input class="form-control" id="username" path="username" />
						</div>
					</div>
					
					<p class="errorLine">
           				<form:errors path="username" cssClass="error"/>
       				</p>

					<div class="form-group">
						<label for="password" class="col-sm-2 control-label">密码</label>
						<div class="col-sm-8">
							<form:password class="form-control" id="password" path="password" />
						</div>
					</div>
					
					<p class="errorLine">
           				<form:errors path="password" cssClass="error"/>
       				</p>
					
					<div class="form-group">
						<label for="email" class="col-sm-2 control-label">邮箱</label>
						<div class="col-sm-8">
							<form:input class="form-control" id="email" path="email" />
						</div>
					</div>
					
					<p class="errorLine">
           				<form:errors path="email" cssClass="error"/>
       				</p>
					
					<div class="form-group">
						<label for="facephoto" class="col-sm-2 control-label">头像</label>
						<div class="col-sm-8">
							<form:input class="form-control" id="facephoto" path="facephoto" />
						</div>
					</div>
					
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input class="btn btn-danger" id="reset" type="reset"
								value="重置"> <input class="btn btn-primary"
								id="submit" type="submit" value="注册">
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
