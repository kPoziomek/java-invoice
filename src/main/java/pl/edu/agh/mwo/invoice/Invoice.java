package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
//    private Collection<Product> products = new ArrayList<Product>();
private Map<Product, Integer> products = new HashMap<>();

    public void addProduct(Product product) {
        if(product == null) {
            throw new IllegalArgumentException( "Product cannot be null" );
        }
        this.products.put(product,1);
    }

    public void addProduct(Product product, Integer quantity) {
        if(quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be greater than 0");
        }
        products.put(product, quantity);
    }

    public BigDecimal getNetPrice() {
        if(products == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            sum = sum.add(entry.getKey().getPrice().multiply(new BigDecimal(entry.getValue())));
        }

        return sum;
    }

    public BigDecimal getTax() {
        if(products == null) {
            return BigDecimal.ZERO;
        }
        return getGrossPrice().subtract(getNetPrice());
    }

    public BigDecimal getGrossPrice() {
        if(products == null) {
            return BigDecimal.ZERO;
        }

        if(products == null) {
            return BigDecimal.ZERO;
        }
        BigDecimal sum = BigDecimal.ZERO;

        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            sum = sum.add(entry.getKey().getPriceWithTax().multiply(new BigDecimal(entry.getValue())));
        }

        return sum;

    }
}
