<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%-- Dat locale en_US de format tien te dung kieu $14.95 --%>
<fmt:setLocale value="en_US"/>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Murach's Java Servlets and JSP - CD List</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<div class="container">

    <h1>CD list</h1>

    <table>
        <tr>
            <th>Description</th>
            <th class="price">Price</th>
            <th></th>
        </tr>
        <c:forEach var="product" items="${products}">
        <tr>
            <td>${product.description}</td>
            <td class="price">
                <%-- Format gia tien kieu $14.95 --%>
                <fmt:formatNumber value="${product.price}" type="currency"/>
            </td>
            <td>
                <%-- Moi san pham co mot form post rieng --%>
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="add">
                    <input type="hidden" name="productCode" value="${product.code}">
                    <input type="submit" value="Add To Cart">
                </form>
            </td>
        </tr>
        </c:forEach>
    </table>

</div>
</body>
</html>
