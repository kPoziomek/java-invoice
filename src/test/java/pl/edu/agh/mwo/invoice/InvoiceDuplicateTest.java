package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class InvoiceDuplicateTest {
    private Invoice invoice;

    @Before
    public void createInvoiceForTheTest() {
        invoice = new Invoice();
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5.0")), 2);
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("3.5")), 3);
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("35.0")), 1);
    }

    @Test
    public void testCreateDuplicateWithSameProducts() {
        Invoice duplicate = invoice.createDuplicate();
        
        Assert.assertNotNull(duplicate);
        Assert.assertNotSame(invoice, duplicate);
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(duplicate.getNetTotal()));
        Assert.assertThat(invoice.getTaxTotal(), Matchers.comparesEqualTo(duplicate.getTaxTotal()));
        Assert.assertThat(invoice.getGrossTotal(), Matchers.comparesEqualTo(duplicate.getGrossTotal()));
    }
    
    @Test
    public void testDuplicateHasDifferentNumber() {
        Invoice duplicate = invoice.createDuplicate();
        
        Assert.assertNotEquals(invoice.getInvoiceNumber(), duplicate.getInvoiceNumber());
    }
    
    @Test
    public void testCreateEmptyDuplicate() {
        Invoice emptyInvoice = new Invoice();
        Invoice duplicate = emptyInvoice.createDuplicate();
        
        Assert.assertNotNull(duplicate);
        Assert.assertThat(BigDecimal.ZERO, Matchers.comparesEqualTo(duplicate.getNetTotal()));
    }
    
    @Test
    public void testModifyingDuplicateDoesNotAffectOriginal() {
        Invoice duplicate = invoice.createDuplicate();
        duplicate.addProduct(new TaxFreeProduct("Nowy produkt", new BigDecimal("100.0")));
        
        Assert.assertNotEquals(invoice.getNetTotal(), duplicate.getNetTotal());
    }
    
    @Test
    public void testRemoveDuplicatesEmpty() {
        Invoice.resetDuplicates();
        
        Invoice emptyInvoice = new Invoice();
        boolean result = emptyInvoice.removeDuplicate();
        
        Assert.assertFalse(result);
    }
    
    @Test
    public void testRemoveDuplicatesSuccess() {
        Invoice original = new Invoice();
        original.addProduct(new TaxFreeProduct("Test", new BigDecimal("10.0")));
        
        Invoice duplicate = original.createDuplicate();
        boolean result = original.removeDuplicate(duplicate);
        
        Assert.assertTrue(result);
    }
    
    @Test
    public void testRemoveDuplicatesNonDuplicate() {
        Invoice original = new Invoice();
        original.addProduct(new TaxFreeProduct("Test1", new BigDecimal("10.0")));
        
        Invoice nonDuplicate = new Invoice();
        nonDuplicate.addProduct(new TaxFreeProduct("Test2", new BigDecimal("20.0")));
        
        boolean result = original.removeDuplicate(nonDuplicate);
        
        Assert.assertFalse(result);
    }
}
