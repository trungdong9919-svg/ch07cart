package murach.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import murach.business.CartItem;
import murach.business.Product;
import murach.data.ProductDB;
import java.io.IOException;
import java.util.LinkedHashMap;

// Servlet xu ly gio hang - session tracking voi HttpSession
@WebServlet("/cart")
public class CartServlet extends HttpServlet {

    @SuppressWarnings("unchecked")
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        // Lay gio hang tu session, neu chua co thi tao moi
        LinkedHashMap<String, CartItem> cart =
            (LinkedHashMap<String, CartItem>) session.getAttribute("cart");
        if (cart == null) {
            cart = new LinkedHashMap<>();
        }

        String action = request.getParameter("action");
        if (action == null) action = "";

        switch (action) {

            case "add":
                // Them san pham vao gio hang
                String productCode = request.getParameter("productCode");
                Product product = ProductDB.getProductByCode(productCode);
                if (product != null) {
                    if (cart.containsKey(productCode)) {
                        // San pham da co trong gio, tang so luong len 1
                        CartItem existing = cart.get(productCode);
                        existing.setQuantity(existing.getQuantity() + 1);
                    } else {
                        // Them moi vao gio
                        cart.put(productCode, new CartItem(product));
                    }
                }
                session.setAttribute("cart", cart);
                forwardToCart(request, response);
                break;

            case "update":
                // Cap nhat so luong cac san pham trong gio
                for (String key : cart.keySet()) {
                    String qtyParam = request.getParameter("qty_" + key);
                    if (qtyParam != null && !qtyParam.trim().isEmpty()) {
                        try {
                            int qty = Integer.parseInt(qtyParam.trim());
                            if (qty > 0) {
                                cart.get(key).setQuantity(qty);
                            }
                        } catch (NumberFormatException e) {
                            // Bo qua neu nguoi dung nhap sai
                        }
                    }
                }
                session.setAttribute("cart", cart);
                forwardToCart(request, response);
                break;

            case "remove":
                // Xoa san pham khoi gio hang theo productCode
                String removeCode = request.getParameter("productCode");
                cart.remove(removeCode);
                session.setAttribute("cart", cart);
                forwardToCart(request, response);
                break;

            case "checkout":
                // Thanh toan: xoa toan bo gio hang, chuyen ve trang chu
                session.removeAttribute("cart");
                response.sendRedirect(request.getContextPath() + "/");
                break;

            case "continueShopping":
                // Luu gio hang, quay ve trang danh sach san pham
                session.setAttribute("cart", cart);
                response.sendRedirect(request.getContextPath() + "/");
                break;

            default:
                response.sendRedirect(request.getContextPath() + "/");
                break;
        }
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        forwardToCart(request, response);
    }

    // Phuong thuc tien ich chuyen huong den cart.jsp
    private void forwardToCart(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        RequestDispatcher dispatcher = request.getRequestDispatcher("/cart.jsp");
        dispatcher.forward(request, response);
    }
}
