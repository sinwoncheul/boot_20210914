<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>
    <div style="padding: 10px;">
    <a th:href="@{/board/select}">게시판목록</a>
    <hr />

    글번호 : <div style="display:inline-block" th:text="${board.no}"></div> <br />
    글제목 : <div style="display:inline-block" th:text="${board.title}"></div> <br />
    글내용 : <div style="display:inline-block" th:text="${board.content}"></div> <br />
    작성자 : <div style="display:inline-block" th:text="${board.writer}"></div> <br />

    <hr />

	<a th:href="@{/board/update(no=${board.no})}"><input type="button" value="글수정" /></a>
    	<a th:href="@{/board/delete(no=${board.no})}"><input type="button" value="글삭제" /></a>
 <a th:if="${board.no != 0}" th:href="@{/board/select_one(no=${prev})}"><input type="button" value="이전글"> </a>
 <a th:if="${board.no != 0}" th:href="@{/board/select_one(no=${next})}"><input type="button" value="다음글"> </a>

</div>
</body>
</html>