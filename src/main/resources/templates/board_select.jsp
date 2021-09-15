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

        <a th:href="@{/board/insert}">게시판글쓰기</a>
        <hr />

        <form th:action="@{/board/select}" method="get">
            <input type="hidden" value="1" name="page" />
            <input type="text" name="title" placeholder="검색어" />
            <input type="submit" value="검색" />
        </form>

        <table border="1">
            <tr>
                <th>번호</th>
                <th>글번호</th>
                <th>제목</th>
                <th>작성자</th>
                <th>조회수</th>
                <th>날짜</th>
            </tr>    
            <tr th:each="brd, idx : ${list}">
                <td th:text="${idx.count}"></td>
                <td th:text="${brd.no}"></td>
                <td>
                    <a th:href="@{/board/select_one(no=${brd.no})}" 
                    th:text="${brd.title}"></a>
                </td>
                <td th:text="${brd.writer}"></td>
                <td th:text="${brd.hit}"></td>
                <td th:text="${brd.regdate}"></td>
            </tr>
        </table>

        <th:block th:each="i : ${#numbers.sequence(1,cnt)}">
            <a th:href="@{/board/select(page=${i}, title=${param.title})}" th:text="${i}"></a>
        </th:block>
    </div>  
</body>
</html>