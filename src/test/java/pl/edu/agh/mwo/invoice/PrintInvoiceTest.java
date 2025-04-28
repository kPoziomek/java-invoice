package pl.edu.agh.mwo.invoice;

import java.math.BigDecimal;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import pl.edu.agh.mwo.invoice.product.DairyProduct;
import pl.edu.agh.mwo.invoice.product.OtherProduct;
import pl.edu.agh.mwo.invoice.product.TaxFreeProduct;

public class PrintInvoiceTest {
    private Invoice invoice;

    @Before
    public void createInvoiceForTheTest() {
        invoice = new Invoice();
    }

    @Test
    public void testEmptyInvoicePrinting() {
        String printedInvoice = invoice.toString();
        Assert.assertTrue(printedInvoice.contains("Invoice number:"));
        Assert.assertTrue(printedInvoice.contains("Total net:"));
        Assert.assertTrue(printedInvoice.contains("Total tax:"));
        Assert.assertTrue(printedInvoice.contains("Total gross:"));
        
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        Assert.assertThat(invoice.getTaxTotal(), Matchers.comparesEqualTo(BigDecimal.ZERO));
        Assert.assertThat(invoice.getGrossTotal(), Matchers.comparesEqualTo(BigDecimal.ZERO));
    }

    @Test
    public void testInvoicePrintingWithOneProduct() {
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5.0")));
        String printedInvoice = invoice.toString();
        
        Assert.assertTrue(printedInvoice.contains("Chleb"));
        BigDecimal expectedNet = new BigDecimal("5.0");
        BigDecimal expectedTax = new BigDecimal("0.0");
        BigDecimal expectedGross = new BigDecimal("5.0");
        
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(expectedNet));
        Assert.assertThat(invoice.getTaxTotal(), Matchers.comparesEqualTo(expectedTax));
        Assert.assertThat(invoice.getGrossTotal(), Matchers.comparesEqualTo(expectedGross));
    }

    @Test
    public void testInvoicePrintingWithMultipleProducts() {
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5.0")));
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("3.5")));
        invoice.addProduct(new OtherProduct("Wino", new BigDecimal("35.0")));
        
        String printedInvoice = invoice.toString();
        
        Assert.assertTrue(printedInvoice.contains("Chleb"));
        Assert.assertTrue(printedInvoice.contains("Mleko"));
        Assert.assertTrue(printedInvoice.contains("Wino"));
        
        System.out.println("Net total: " + invoice.getNetTotal());
        System.out.println("Gross total: " + invoice.getGrossTotal());
        
        BigDecimal expectedNet = new BigDecimal("43.5");
        BigDecimal expectedGross = new BigDecimal("51.83");
        
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(expectedNet));
        Assert.assertThat(invoice.getGrossTotal(), Matchers.comparesEqualTo(expectedGross));
    }

    @Test
    public void testInvoicePrintingWithMultipleQuantities() {
        invoice.addProduct(new TaxFreeProduct("Chleb", new BigDecimal("5.0")), 2);
        invoice.addProduct(new DairyProduct("Mleko", new BigDecimal("3.5")), 3);
        
        String printedInvoice = invoice.toString();
        
        Assert.assertTrue(printedInvoice.contains("Chleb"));
        Assert.assertTrue(printedInvoice.contains("Mleko"));
        
        BigDecimal expectedNet = new BigDecimal("20.5");
        BigDecimal expectedTax = new BigDecimal("0.84");
        BigDecimal expectedGross = new BigDecimal("21.34");
        
        Assert.assertThat(invoice.getNetTotal(), Matchers.comparesEqualTo(expectedNet));
        Assert.assertThat(invoice.getTaxTotal(), Matchers.comparesEqualTo(expectedTax));
        Assert.assertThat(invoice.getGrossTotal(), Matchers.comparesEqualTo(expectedGross));
    }

    @Test
    public void testInvoiceContainsNumberInPrinting() {
        String printedInvoice = invoice.toString();
        
        Assert.assertTrue(printedInvoice.contains("Invoice number:"));
        Assert.assertTrue(printedInvoice.matches("(?s).*Invoice number:\\s*\\d+.*"));
    }
}
