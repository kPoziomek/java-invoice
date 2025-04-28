package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;
import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class Main {
    
    public static void main(String[] args) {

        Invoice.resetInvoiceCounter();
        
        System.out.println("1. TWORZENIE FAKTURY\n");
        Invoice invoice1 = createSampleInvoice();
        System.out.println(invoice1);
        
        System.out.println("2. SEKWENCYJNA NUMERACJA FAKTUR\n");
        Invoice invoice2 = new Invoice();
        invoice2.addProduct(new OtherProduct("Laptop", new BigDecimal("3500.00")));
        invoice2.addProduct(new OtherProduct("Mysz bezprzewodowa", new BigDecimal("120.00")), 2);
        
        System.out.println("Faktura 1 - numer: " + invoice1.getInvoiceNumber());
        System.out.println("Faktura 2 - numer: " + invoice2.getInvoiceNumber());
        System.out.println("Różnica numerów: " + (invoice2.getInvoiceNumber() - invoice1.getInvoiceNumber()));
        System.out.println();
        
        System.out.println("3. TWORZENIE DUPLIKATU FAKTURY\n");
        Invoice duplicate = invoice1.createDuplicate();
        System.out.println("ORYGINALNA FAKTURA:");
        System.out.println(invoice1);
        System.out.println("DUPLIKAT FAKTURY:");
        System.out.println(duplicate);
        
        System.out.println("4. PORÓWNANIE ZAWARTOŚCI\n");
        System.out.println("Wartość netto oryginału: " + invoice1.getNetTotal());
        System.out.println("Wartość netto duplikatu: " + duplicate.getNetTotal());
        System.out.println("Wartości są równe: " + invoice1.getNetTotal().equals(duplicate.getNetTotal()));
        System.out.println("Numery faktur są różne: " + (invoice1.getInvoiceNumber() != duplicate.getInvoiceNumber()));
        System.out.println();
        
        System.out.println("5. USUWANIE DUPLIKATU\n");
        boolean removed = invoice1.removeDuplicate(duplicate);
        System.out.println("Duplikat został usunięty: " + removed);
        System.out.println();
        
        System.out.println("6. PODSUMOWANIE FUNKCJONALNOŚCI\n");
        System.out.println("✓ Drukowanie faktur (metoda toString)");
        System.out.println("✓ Tworzenie duplikatów faktur (metoda createDuplicate)");
        System.out.println("✓ Usuwanie duplikatów faktur (metoda removeDuplicate)");
        System.out.println("✓ Sekwencyjna numeracja faktur");
        System.out.println();
        
    }
    

    private static Invoice createSampleInvoice() {
        Invoice invoice = new Invoice();
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5.50")), 2);
        invoice.addProduct(new DairyProduct("Masło", new BigDecimal("6.99")));
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("3.49")), 3);
        invoice.addProduct(new OtherProduct("Kawa", new BigDecimal("24.99")));
        invoice.addProduct(new OtherProduct("Czekolada", new BigDecimal("5.99")), 5);
        return invoice;
    }
}
