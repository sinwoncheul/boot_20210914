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
	        <table border="2">
	            <tr>
					<th>물분번호</th>
	                <th>물품명</th>
	                <!-- <th>물품내용</th> -->
	                <th>가격</th>
	                <th>수량</th>
					<th>등록일자</th>
					<th>이미지등록</th>
	            </tr>
	            <tr th:each="list, idx : ${list}">
	                <td><input type="text" name="no" th:value="${list.no}" readonly /></td>
					<td><input type="text" name="name" th:value="${list.name}" /></td>
	                <!-- <td><input type="text" name="content" th:value="${list.content}" /></td> -->
	                <td><input type="text" name="price" th:value="${list.price}" /></td>
                    <td><input type="text" name="quantity" th:value="${list.quantity}" /></td>
					<td th:text="${list.regdate}"></td>
					<td><a th:href="@{/seller/insert_item_image(no=${list.no})}">이미지등록</a></td>
	            </tr>
	        </table>
        </form>
    </div>
</body>
</html>