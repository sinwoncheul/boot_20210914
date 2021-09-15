<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>게시판 글쓰기</title>
    <link rel="stylesheet" type="text/css" 
        th:href="@{/css/bootstrap.min.css}"/>
	<script th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <form th:action="@{/board/insert}" method="post">
        제목 : <input type="text" name="title"> <br />
        내용 : <input type="text" name="content"> <br />
        작성자 : <input type="text" name="writer"> <br />
        <input type="submit" value="글쓰기" />
    
    </form>
    
</body>
</html>