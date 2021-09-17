<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>일괄추가</title>
    <link rel="stylesheet" type="text/css" 
        th:href="@{/css/bootstrap.min.css}"/>
	<script th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
    <div style="padding: 10px;">
        <form th:action="@{/board/insert_all}" method="post" enctype="multipart/form-data">

            <th:block th:each="i : ${#numbers.sequence(1,3)}"> 
             <input type="text" name="title" th:value="|제목 ${i}|"> 
             <input type="text" name="content" th:value="|내용 ${i}|"> 
             <input type="text" name="writer" th:value="|작성자 ${i}|"> 
             <input type="file" name="file"> <br />
        </th:block>

            <input type="submit" value="일괄추가" />
        </form>
    </div>


    
</body>
</html>