<!DOCTYPE html>
<html xmlns:th="http://www.thymeleaf.org"
      xmlns:sec="http://www.thymeleaf.org/extras/spring-security">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>home.jsp</title>
    <link rel="stylesheet" type="text/css" th:href="@{/css/bootstrap.min.css}"/>
	<script th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
	<div class="container">
        <div sec:authorize="isAuthenticated()">

	        <h3 sec:authorize="hasAuthority('ADMIN')">권한 ADMIN</h3>
	        <h3 sec:authorize="hasAuthority('SELLER')">권한 SELLER</h3>
	        <h3 sec:authorize="hasAuthority('CUSTOMER')">권한 CUSTOMER</h3> 
	        
	        인증정보 : <div sec:authentication="principal"></div>
	        사용자 ID : <div sec:authentication="name"></div>
        
			<form th:action="@{/member/logout}" method="post">
				<input type="submit" value="로그아웃" />
			</form>
        </div>

		<div sec:authorize="!isAuthenticated()">
			<a th:href="@{/member/login}">로그인</a>
			<a th:href="@{/member/join}">회원가입</a>
        </div>

		<hr />
		<th:block th:each="obj, idx : ${list}">
			<div th:text="${obj.name}" style="display:inline-block"></div>
			<div th:text="${obj.price}" style="display:inline-block"></div>
            <img th:src="@{/item_image_preview(itemno=${obj.no}, idx=0)}" width="50px" height="50px" />

			<th:block th:if="${idx.count % 3 ==0 }">
            	<hr />
            </th:block>
		</th:block>

    </div>
</body>
</html>