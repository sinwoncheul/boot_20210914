<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>

<body>
<div style="padding:10px">
		
		<form th:action="@{/board/update_all_action}" method="post"> 
	        <table border="0">
	            <tr>
	                <th>글번호</th>
	                <th>글제목</th>
	                <th>작성자</th>
	                <th>조회수</th>
	                <th>날짜</th>
	            </tr>
	            <tr th:each="brd, idx : ${list}">
	                <td><input type="text" name="no" th:value="${brd.no}" readonly /></td>
	                <td><input type="text" name="title" th:value="${brd.title}" /></td>
	                <td><input type="text" name="writer" th:value="${brd.writer}" /></td>
	                <td th:text="${brd.hit}"></td>
	                <td th:text="${brd.regdate}"></td>
	            </tr>
	        </table>
	        <input type="submit" value="일괄수정" />
        </form>
    </div>

</body>
</html>