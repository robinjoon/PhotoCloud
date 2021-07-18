<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,dto.*"%>
<%@ taglib uri="http://java.sun.com/jstl/core" prefix="c" %>
<!DOCTYPE html>
<html>
<head>
<link
	href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css"
	rel="stylesheet"
	integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC"
	crossorigin="anonymous">
<script
	src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
	integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
	crossorigin="anonymous"></script>
<meta charset="UTF-8">
<title>사진백업시스템</title>
</head>
<%List<Content> contents = (List<Content>)request.getAttribute("contents"); %>
<%List<String> srcs = (List<String>)request.getAttribute("srcs"); %>
<%String device = contents.get(0).getDevice(); String album = contents.get(0).getAlbum(); int nowPage = (int)request.getAttribute("nowPage");%>
<body>
	<nav class="navbar navbar-expand-lg navbar-light bg-light">
		<div class="container-fluid">
			<a class="navbar-brand" href="#">PhotoCloud</a>
			<button class="navbar-toggler" type="button"
				data-bs-toggle="collapse" data-bs-target="#navbarNav"
				aria-controls="navbarNav" aria-expanded="false"
				aria-label="Toggle navigation">
				<span class="navbar-toggler-icon"></span>
			</button>
			<div class="collapse navbar-collapse" id="navbarNav">
				<ul class="navbar-nav">
					<li class="nav-item"><a class="nav-link" href="/PhotoCloud/devices/list">Devices</a>
					</li>
				</ul>
			</div>
		</div>
	</nav>
	<div class ="container-fluid">
	<%for(int i=0;i<2;i++){ %>
		<div class="row">
		<%for(int j=0;j<3;j++){ %>
			<div class="col">
				<a href="<%=i*3+j>=contents.size()?"#":"/PhotoCloud/contents/"+device+"/"+album+"/"+contents.get(i*3+j).getContentId()+"/info"%>">
					<img class="<%=i*3+j>=contents.size()?"":"img-thumbnail"%>" src ="<%=i*3+j>=contents.size()?"":srcs.get(i*3+j)%>">
				</a>
			</div>
		<%} %>
		</div>
	<%} %>
	</div>
	<nav aria-label="Page navigation example">
		<ul class="pagination justify-content-center">
			<li class="page-item">
				<a class="page-link" href="#"aria-label="Previous">
					<span aria-hidden="true">&laquo;</span>
				</a>
			</li>
			<%int contentsCount = (int)request.getAttribute("contentCount"); %>
			<%for(int i=1;i<=contentsCount/6+1;i++) {%>
			<li class="page-item <%=nowPage==i?"active":""%>">
				<a class="page-link" href="/PhotoCloud/contents/<%=device %>/<%=album %>/list/<%=i%>"><%=i%></a>
			</li>
			<%} %>
			<li class="page-item">
				<a class="page-link" href="#"aria-label="Next">
					<span aria-hidden="true">&raquo;</span>
				</a>
			</li>
		</ul>
	</nav>
</body>
</html>