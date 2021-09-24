<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>member_address_update.jsp</title>
</head>

<body>
    <div style="padding:10px">
		<form th:action="@{/member/address_update}" method="post">
			<input type="text" name="no" th:value="${address.no}" readonly />
			<input type="text" name="addr" th:value="${address.addr}" placeholder="주소입력" />
			<input type="submit" value="주소변경" />
		</form>
    </div>
</body>
</html>
