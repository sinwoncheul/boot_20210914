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
        <a th:href="@{/board/insert_all}">일괄추가</a>
		<hr />
		
		<form th:action="@{/board/update_all}" method="post" name="form"> 
	        <table border="1">
	            <tr>
	                <th>체크</th>
	                <th>글번호</th>
	                <th>글제목</th>
	                <th>작성자</th>
	                <th>조회수</th>
	                <th>날짜</th>
	            </tr>
	            <tr th:each="brd, idx : ${list}">
	                <td><input type="checkbox" name="chks" th:value="${brd.no}" /></td>
	                <td th:text="${brd.no}"></td>
	                <td th:text="${brd.title}"></td>
	                <td th:text="${brd.writer}"></td>
	                <td th:text="${brd.hit}"></td>
	                <td th:text="${brd.regdate}"></td>
	            </tr>
	        </table>
	        <input type="submit" value="수정" />
	        <input type="button" value="삭제" th:onclick="|javascript:handleDelete()|" />
        </form>
    </div>

	
    <script th:inline="javascript">
    	function handleDelete(){
    		var theForm = document.form;

    		theForm.action = "/ROOT/board/delete_all";
    		theForm.submit();
    	}
    </script>

</body>
</html>
