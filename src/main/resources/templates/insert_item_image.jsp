<!DOCTYPE html>
<html lang="ko" xmlns:th="http://www.thymeleaf.org">
<head>
    <meta charset="UTF-8">
    <meta http-equiv="X-UA-Compatible" content="IE=edge">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>insert_item_image.jsp</title>
</head>


<body>
    <div style="padding:5px">
    
    	<th:block th:each="i : ${#numbers.sequence(0,2)}">
    		<div th:text="${i}"></div>
			<img th:src="@{/seller/item_image_preview(itemno=${param.no}, idx=${i})}" width="50px" height="50px" />
	    </th:block>
	    <hr />
    
    	<form th:action="@{/seller/insert_item_image}" method="post" 
            enctype="multipart/form-data">
	    	<input type="text" name="no" th:value="${param.no}" /><br />
	    	<th:block th:each="i : ${#numbers.sequence(1,3)}">
	            <input type="file" name="file" /><br />
	        </th:block>
	        <input type="submit" value="이미지등록" />
        </form>
    </div>
</body>
</html>
