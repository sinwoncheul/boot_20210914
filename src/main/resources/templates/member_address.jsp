<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>주소페이지</title>
</head>

<body>
    <div style="padding:10px">
        <table border="1">
            <tr>
                <th>번호</th>
                <th>주소</th>
                <th>등록일</th>
                <th>버튼</th>
            </tr>

            <tr th:each="obj, idx : ${list}">
                <td th:text="${obj.no}"></td>
                <td th:text="${obj.addr}"></td>
                <td th:text="${obj.regdate}"></td>
                <td>  <a th:href="@{/member/address_delete(no=${obj.no})}">삭제</a> 
                      <a th:href="@{/member/address_update(no=${obj.no})}">수정</a> </td>
            </tr>
      
        </table>
        <hr />

        <form th:action="@{/member/address}" method="post">
            <input type="text" name="addr" placeholder="등록할 주소 입력">
            <input type="submit" value="주소등록" />
        
        </form>
	  
    </div>
</body>
</html>


