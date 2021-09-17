<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>회원가입</title>
</head>

<body>
    <div style="padding:10px">
		<form th:action="@{/member/join}" method="post">
			<input type="text" name="userid" placeholder="아이디입력" /><br />
			<input type="password" name="userpw" placeholder="암호입력" /><br />
			<input type="password" name="userpw1" placeholder="암호확인" /><br />
			<input type="text" name="username" placeholder="이름"/><br />
			<input type="text" name="usertel" placeholder="아이디연락처" /><br />
			<select name="userrole">
				<option value="CUSTOMER">고객</option>
				<option value="SELLER">판매자</option>
				<option value="ADMIN">관리자</option>
			</select><br />
			<input type="submit" value="회원가입" />
		</form>    
    </div>
</body>
</html>
