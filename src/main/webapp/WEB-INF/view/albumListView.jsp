<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*,dto.*"%>
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
<body>
<%List<String> albums = (List<String>)request.getAttribute("albums"); String device = (String)request.getAttribute("device");%>
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
	<div class="container-fluid">
		<%for(int i=0;i<4;i++){ %>
		<div class="row">
		<%for(int j=0;j<3;j++){ %>
			<div class="col">
				<a href="<%=i*3+j>=albums.size()?"#":"/PhotoCloud/contents/"+device+"/"+albums.get(i*3+j)+"/list/1"%>">
					<%=i*3+j>=albums.size()?"":albums.get(i*3+j)%>
				</a>
			</div>
		<%} %>
		</div>
	<%} %>
	</div>
</body>
</html>