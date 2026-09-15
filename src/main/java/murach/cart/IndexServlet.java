package murach.cart;

import jakarta.servlet.*;
import jakarta.servlet.http.*;
import jakarta.servlet.annotation.*;
import murach.business.Product;
import murach.data.ProductDB;
import java.io.IOException;
import java.util.List;

// Servlet xu ly trang chu - hien thi danh sach san pham
@WebServlet("")
public class IndexServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        // Lay danh sach san pham tu ProductDB
        List<Product> products = ProductDB.getAll();
        request.setAttribute("products", products);

        // Chuyen den trang index.jsp
        RequestDispatcher dispatcher = request.getRequestDispatcher("/index.jsp");
        dispatcher.forward(request, response);
    }
}
