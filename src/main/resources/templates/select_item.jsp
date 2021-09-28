<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>item_select_all.jsp</title>
</head>

<body>
    <div style="padding:10px">
		<table border="1">
			<tr>
				<th>번호(idx)</th>
                <th>물품코드</th>
				<th>물품명</th>
				<th>물품가격</th>
				<th>재고수량</th>
				<th>등록일자</th>
				<th>판매자아이디</th>
				<th>판매자이름</th>
				<th>이미지등록</th>
			</tr>
           
			<tr th:each="itm, idx : ${list}">
               <td th:text="${idx.count}"></td>
               <td th:text="${itm.no}"></td>
               <td th:text="${itm.name}"></td>
               <td th:text="${itm.price}"></td>
               <td th:text="${itm.quantity}"></td>
               <td th:text="${itm.regdate}"></td>
			   <td th:text="${itm.memberUserid}"></td>
			   <td th:text="${itm.memberUsername}"></td>
               <td><a th:href="@{/seller/insert_item_image(no=${itm.no})}">이미지등록</a></td>
           </tr>
       </table>
    </div>
</body>
</html>


