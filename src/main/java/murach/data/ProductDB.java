package murach.data;

import murach.business.Product;
import java.util.ArrayList;
import java.util.List;

// Lop gia lap co so du lieu san pham
public class ProductDB {

    public static List<Product> getAll() {
        List<Product> products = new ArrayList<>();
        products.add(new Product("p1", "86 (the band) - True Life Songs and Pictures", 14.95));
        products.add(new Product("p2", "Paddlefoot - The first CD", 12.95));
        products.add(new Product("p3", "Paddlefoot - The second CD", 14.95));
        products.add(new Product("p4", "Joe Rut - Genuine Wood Grained Finish", 14.95));
        return products;
    }

    public static Product getProductByCode(String code) {
        for (Product p : getAll()) {
            if (p.getCode().equals(code)) {
                return p;
            }
        }
        return null;
    }
}
