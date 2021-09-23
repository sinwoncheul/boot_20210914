<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>member_mypage.jsp</title>
</head>

<body>
    <div style="padding:10px">
    	<a th:href="@{/member/mypage(menu=1)}">회원정보수정</a>
    	<a th:href="@{/member/mypage(menu=2)}">비밀번호변경</a>
    	<a th:href="@{/member/mypage(menu=3)}">회원탈퇴</a>
    	<hr />
    	
		<span th:text="${param.menu}"></span>

		<div th:if="${#strings.toString(param.menu)=='1'}">
			<h4>회원정보수정</h4>	
			<form th:action="@{/member/mypage(menu=1)}" method="post">
				<input type="text" th:value="${member.username}" /><br />
				<input type="text" th:value="${member.usertel}" /><br />
				<input type="submit" value="회원정보수정" />
			</form>
		</div>

		<div th:if="${#strings.toString(param.menu)=='2'}">
			<h4>비밀번호변경</h4>	
		</div>
		
		<div th:if="${#strings.toString(param.menu)=='3'}">
			<h4>회원탈퇴</h4>	
		</div>	
    </div>
</body>
</html>