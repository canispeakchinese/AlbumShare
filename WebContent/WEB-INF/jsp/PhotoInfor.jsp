<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>

<!DOCTYPE HTML>
<html>
<head>
<title>个人信息</title>
<style type="text/css">
@import url("<c:url value="/css/bootstrap.css"/>");
</style>
<style type="text/css">@import url("<c:url value="/css/main.css"/>");</style>
</head>
<body>

<nav class="navbar navbar-default">
  <div class="container-fluid">
    <!-- Brand and toggle get grouped for better mobile display -->
    <div class="navbar-header">
      <button type="button" class="navbar-toggle collapsed" data-toggle="collapse" data-target="#bs-example-navbar-collapse-1" aria-expanded="false">
        <span class="sr-only">Toggle navigation</span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
        <span class="icon-bar"></span>
      </button>
      <a class="navbar-brand" href="/AlbumShare/check-login">主页</a>
    </div>

    <!-- Collect the nav links, forms, and other content for toggling -->
    <div class="collapse navbar-collapse" id="bs-example-navbar-collapse-1">
      <ul class="nav navbar-nav">
        <li class="active"><a href="#">Link <span class="sr-only">(current)</span></a></li>
        <li><a href="#">Link</a></li>
        <li class="dropdown">
          <a href="#" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">Dropdown <span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">One more separated link</a></li>
          </ul>
        </li>
      </ul>
      <form class="navbar-form navbar-left">
        <div class="form-group">
          <input type="text" class="form-control" placeholder="Search">
        </div>
        <button type="submit" class="btn btn-default">Submit</button>
      </form>
      <ul class="nav navbar-nav navbar-right">
        <li><a href="/AlbumShare/infor"><c:out value="${user.username}" /></a></li>
        <li class="dropdown">
          <a href="/AlbumShare/logout" class="dropdown-toggle" data-toggle="dropdown" role="button" aria-haspopup="true" aria-expanded="false">注销<span class="caret"></span></a>
          <ul class="dropdown-menu">
            <li><a href="#">Action</a></li>
            <li><a href="#">Another action</a></li>
            <li><a href="#">Something else here</a></li>
            <li role="separator" class="divider"></li>
            <li><a href="#">Separated link</a></li>
          </ul>
        </li>
      </ul>
    </div><!-- /.navbar-collapse -->
  </div><!-- /.container-fluid -->
</nav>

	<h3 align="center"><c:out value="${error}" /></h3>
	
	<div class="container-fluid">
	
		<div class="row">
			<div class="col-sm-8 col-sm-offset-2 col-md-8 col-md-offset-2 main">
				<h2 class="page-header">图片信息</h2>
				<form:form class="form-horizontal" commandName="photoInfor"
					action="/AlbumShare/change-photo" method="post">
					<div class="form-group">
						<label for="photoName" class="col-sm-2 control-label">图片名称</label>
						<div class="col-sm-8">
							<form:input class="form-control" id="photoName" value="${photoInfor.photoName}" path="photoName" />
						</div>
					</div>
					
					<p class="errorLine">
           				<form:errors path="photoName" cssClass="error"/>
       				</p>

					<div class="form-group">
						<label for="description" class="col-sm-2 control-label">描述</label>
						<div class="col-sm-8">
							<form:input class="form-control" id="description" value="${photoInfor.description}" path="description" />
						</div>
					</div>
					
					<p class="errorLine">
           				<form:errors path="description" cssClass="error"/>
       				</p>
					
					<div class="form-group">
						<label for="isOpen" class="col-sm-2 control-label">是否开放</label>
						<div class="col-sm-8">
							<form:input class="form-control" id="isOpen" value="${photoInfor.isOpen}" path="isOpen" />
						</div>
					</div>
					
					<p class="errorLine">
           				<form:errors path="isOpen" cssClass="error"/>
       				</p>
					
					<div class="form-group">
						<div class="col-sm-offset-2 col-sm-10">
							<input class="btn btn-danger" id="reset" type="reset"
								value="重置"> <input class="btn btn-primary"
								id="submit" type="submit" value="更新">
						</div>
					</div>
				</form:form>
			</div>
		</div>
	</div>
</body>
</html>
