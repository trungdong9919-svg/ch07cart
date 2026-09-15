<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<%@ taglib prefix="fmt" uri="jakarta.tags.fmt" %>
<%-- Dat locale en_US de format tien te dung kieu $14.95 --%>
<fmt:setLocale value="en_US"/>
<%--
    Trang gio hang (cart.jsp)
    - Hien thi danh sach san pham da them vao gio
    - Cho phep cap nhat so luong, xoa san pham
    - Tiep tuc mua sam hoac thanh toan
--%>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Murach's Java Servlets and JSP - Your Cart</title>
    <link rel="stylesheet" href="styles/main.css">
</head>
<body>
<div class="container">

    <h1>Your cart</h1>

    <c:choose>
        <c:when test="${empty sessionScope.cart}">
            <p>Your cart is empty.</p>
            <form action="." method="get">
                <input type="submit" value="Continue Shopping">
            </form>
        </c:when>
        <c:otherwise>
            <table>
                <tr>
                    <th>Quantity</th>
                    <th>Description</th>
                    <th class="price">Price</th>
                    <th class="amount">Amount</th>
                    <th></th>
                </tr>
                <c:forEach var="entry" items="${sessionScope.cart}">
                    <c:set var="item" value="${entry.value}"/>
                    <tr>
                        <td class="qty-cell">
                            <%-- Moi hang co form Update rieng --%>
                            <%-- Moi hang chi gui qty cua chinh no, servlet tu giu nguyen cac item khac --%>
                            <form action="cart" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="update">
                                <input type="text" name="qty_${item.product.code}"
                                       value="${item.quantity}" size="3">
                                <input type="submit" value="Update">
                            </form>
                        </td>
                        <td class="desc-cell">${item.product.description}</td>
                        <td class="price">
                            <fmt:formatNumber value="${item.product.price}" type="currency"/>
                        </td>
                        <td class="amount">
                            <fmt:formatNumber value="${item.total}" type="currency"/>
                        </td>
                        <td>
                            <%-- Form xoa san pham khoi gio --%>
                            <form action="cart" method="post" style="display:inline;">
                                <input type="hidden" name="action" value="remove">
                                <input type="hidden" name="productCode" value="${item.product.code}">
                                <input type="submit" value="Remove Item">
                            </form>
                        </td>
                    </tr>
                </c:forEach>
            </table>

            <p class="note">
                <b>To change the quantity</b>, enter the new quantity and click on the Update button.
            </p>

            <%-- Nut Continue Shopping va Checkout --%>
            <div class="buttons">
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="continueShopping">
                    <input type="submit" value="Continue Shopping">
                </form>
                <form action="cart" method="post">
                    <input type="hidden" name="action" value="checkout">
                    <input type="submit" value="Checkout">
                </form>
            </div>
        </c:otherwise>
    </c:choose>

</div>
</body>
</html>
