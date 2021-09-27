<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>Document</title>
</head>
<body>

    <div style="padding: 5px;">
        <form th:action="@{/seller/insert_item}" method="post">
        <th:block th:each="i: ${#numbers.sequence(1,3)}">
            <input type="text" name="name" th:value="|물품명 ${i}|" />
            <input type="text" name="content" th:value="|물품내용 ${i}|" />
            <input type="text" name="price" th:value="| ${i}|" />
            <input type="text" name="quantity" th:value="| ${i}|" /><br />
        </th:block>
        <input type="submit" value="등록하기" />
        
        
        </form>
    
    </div>
    
</body>
</html>