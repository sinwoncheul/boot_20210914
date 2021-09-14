<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
    <link rel="stylesheet" type="text/css" 
        th:href="@{/css/bootstrap.min.css}"/>
	<script th:src="@{/js/bootstrap.min.js}"></script>
</head>

<body>
	<div class="container">
    	home 한글
    	<h1 th:text="테스트"></h1>

        <div th:text="${ti}"/>
        <div th:text="${nu}"/>
        <hr />

        <div th:text="${obj.no}"/>
        <div th:text="${obj.title}"/>
        <div th:text="${obj.content}"/>
        <hr />
        <table border="1">
            <tr th:each="brd, idx : ${li}">
                <td th:text="${idx}"></td>
                <td th:text="${brd.no}"></td>
                <td th:text="${brd.title}"></td>
                <td th:text="${brd.content}"></td>
            </tr>
        </table>
        <hr />

        <a th:href="@{/board/list(no=1)}">게시판 목록</a>
        <a th:href="@{/board/list(no=1, name='b')}">게시판 목록</a>

    	<input type="button" class="btn btn-success" value="테스트" />
    </div>
</body>
</html>

