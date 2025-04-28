package pl.edu.agh.mwo.invoice;

import org.junit.Assert;
import org.junit.Test;

public class InvoiceNumberTest {
    
    @Test
    public void testFirstInvoiceNumberStartsWithBaseNumber() {
        Invoice.resetInvoiceCounter();
        Invoice invoice = new Invoice();
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER, invoice.getInvoiceNumber());
    }

    @Test
    public void testTwoInvoicesHaveSequentialNumbers() {
        Invoice.resetInvoiceCounter();
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        
        Assert.assertEquals(invoice1.getInvoiceNumber() + 1, invoice2.getInvoiceNumber());
    }
    
    @Test
    public void testMultipleInvoicesHaveSequentialNumbers() {
        Invoice.resetInvoiceCounter();
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        Invoice invoice3 = new Invoice();
        Invoice invoice4 = new Invoice();
        Invoice invoice5 = new Invoice();
        
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER, invoice1.getInvoiceNumber());
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 1, invoice2.getInvoiceNumber());
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 2, invoice3.getInvoiceNumber());
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 3, invoice4.getInvoiceNumber());
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 4, invoice5.getInvoiceNumber());
    }
    
    @Test
    public void testInvoiceNumbersAfterReset() {
        Invoice.resetInvoiceCounter();
        Invoice invoice1 = new Invoice();
        Invoice invoice2 = new Invoice();
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 1, invoice2.getInvoiceNumber());
        
        Invoice.resetInvoiceCounter();
        Invoice invoice3 = new Invoice();
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER, invoice3.getInvoiceNumber());
    }
    
    @Test
    public void testGetCurrentInvoiceNumber() {
        Invoice.resetInvoiceCounter();
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER, Invoice.getCurrentInvoiceNumber());
        
        Invoice invoice1 = new Invoice();
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 1, Invoice.getCurrentInvoiceNumber());
        
        Invoice invoice2 = new Invoice();
        Assert.assertEquals(Invoice.BASE_INVOICE_NUMBER + 2, Invoice.getCurrentInvoiceNumber());
    }
    
    @Test
    public void testDuplicatesUseSequentialNumbering() {
        Invoice.resetInvoiceCounter();
        Invoice original = new Invoice();
        Invoice duplicate = original.createDuplicate();
        
        Assert.assertEquals(original.getInvoiceNumber() + 1, duplicate.getInvoiceNumber());
    }
}
