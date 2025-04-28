package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

import pl.edu.agh.mwo.invoice.product.Product;

public class Invoice {
    private Map<Product, Integer> products = new HashMap<Product, Integer>();
    private int invoiceNumber;
    private static List<Invoice> duplicates = new ArrayList<>();
    
    public static final int BASE_INVOICE_NUMBER = 10000000;
    
    private static int invoiceCounter = BASE_INVOICE_NUMBER;

    public Invoice() {
        this.invoiceNumber = generateInvoiceNumber();
    }
    
    public static void resetInvoiceCounter() {
        invoiceCounter = BASE_INVOICE_NUMBER;
    }
    
    public static void resetDuplicates() {
        duplicates.clear();
    }

    public static int getCurrentInvoiceNumber() {
        return invoiceCounter;
    }
    
    private int generateInvoiceNumber() {
        return invoiceCounter++;
    }


    public void addProduct(Product product) {
        addProduct(product, 1);
    }

    public void addProduct(Product product, Integer quantity) {
        if (product == null || quantity <= 0) {
            throw new IllegalArgumentException();
        }
        products.put(product, quantity);
    }

    public BigDecimal getNetTotal() {
        BigDecimal totalNet = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalNet = totalNet.add(product.getPrice().multiply(quantity));
        }
        return totalNet;
    }

    public BigDecimal getTaxTotal() {
        return getGrossTotal().subtract(getNetTotal());
    }

    public BigDecimal getGrossTotal() {
        BigDecimal totalGross = BigDecimal.ZERO;
        for (Product product : products.keySet()) {
            BigDecimal quantity = new BigDecimal(products.get(product));
            totalGross = totalGross.add(product.getPriceWithTax().multiply(quantity));
        }
        return totalGross;
    }

    public int getInvoiceNumber() {
        return this.invoiceNumber;
    }

    public Invoice createDuplicate() {
        Invoice duplicate = new Invoice();
        
        for (Map.Entry<Product, Integer> entry : this.products.entrySet()) {
            duplicate.addProduct(entry.getKey(), entry.getValue());
        }
        
        duplicates.add(duplicate);
        return duplicate;
    }
    
    public boolean removeDuplicate(Invoice duplicate) {
        if (duplicates.contains(duplicate)) {
            duplicates.remove(duplicate);
            return true;
        }
        return false;
    }
    
    public boolean removeDuplicate() {
        if (duplicates.isEmpty()) {
            return false;
        }
        duplicates.clear();
        return true;
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        
        Invoice invoice = (Invoice) obj;
        return products.equals(invoice.products);
    }
    
    @Override
    public int hashCode() {
        return Objects.hash(products);
    }
    
    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("Invoice number: ").append(this.invoiceNumber).append("\n");
        sb.append("==========================================\n");
        sb.append(String.format(Locale.US, "%-20s %-10s %-10s %-10s %-10s\n", "Product", "Quantity", "Price", "Tax", "Total"));
        sb.append("------------------------------------------\n");
        
        for (Map.Entry<Product, Integer> entry : products.entrySet()) {
            Product product = entry.getKey();
            Integer quantity = entry.getValue();
            BigDecimal price = product.getPrice();
            BigDecimal tax = product.getPriceWithTax().subtract(price);
            BigDecimal total = product.getPriceWithTax().multiply(new BigDecimal(quantity));
            
            sb.append(String.format(Locale.US, "%-20s %-10d %-10.1f %-10.1f %-10.1f\n", 
                product.getName(), 
                quantity, 
                price, 
                tax, 
                total));
        }
        
        sb.append("==========================================\n");
        sb.append("Total net: ").append(getNetTotal().setScale(2, RoundingMode.HALF_UP)).append("\n");
        sb.append("Total tax: ").append(getTaxTotal().setScale(2, RoundingMode.HALF_UP)).append("\n");
        sb.append("Total gross: ").append(getGrossTotal().setScale(2, RoundingMode.HALF_UP)).append("\n");
        
        return sb.toString();
    }
}
