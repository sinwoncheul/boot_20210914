<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판수정</title>
</head>
<body>
    <div style="padding:5px">
        <a th:href="@{/board/select}">게시판목록</a>
        <a th:href="@{/board/select_one(no=${board.no})}">내용</a>
	    <hr />
	    
	    <form th:action="@{/board/update}" method="post">
            <input type="hidden" name="no" th:value="${board.no}"/><br />
	        제목 : <input type="text" name="title" th:value="${board.title}"/><br />
	        내용 : <input type="text" name="content" th:value="${board.content}"/><br />
	        작성자 : <input type="text" name="writer" th:value="${board.writer}" /><br />
	        <input type="submit" value="글수정" />
	    </form>
    </div>
</body>
</html>
