package com.project.billingSoftware.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.project.billingSoftware.model.Bill;
import com.project.billingSoftware.model.Product;

import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;

@Service
public class PdfService {

    public byte[] generateBillPdf(Bill bill) {
        try {
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            Document document = new Document();
            PdfWriter.getInstance(document, outputStream);
            document.open();

         // Inside the service method, after generating the PDF:
            File file = new File("path/to/save/bills/" + bill.getBillNumber() + ".pdf");
            try (FileOutputStream fos = new FileOutputStream(file)) {
                fos.write(outputStream.toByteArray());
            } catch (Exception e) {
                e.printStackTrace();
            }
            
            // 🛒 **Add Bill Header**
            Font titleFont = new Font(Font.FontFamily.HELVETICA, 18, Font.BOLD);
            Paragraph title = new Paragraph("🛒 Grocery Store Bill", titleFont);
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // 📝 **Customer Details**
            document.add(new Paragraph("\nBill Number: " + bill.getBillNumber()));
            document.add(new Paragraph("Customer Name: " + bill.getCustomerName()));
            document.add(new Paragraph("Email: " + bill.getCustomerEmail()));
            document.add(new Paragraph("Payment Method: " + bill.getPaymentMethod())); // ✅ Fixed error
            document.add(new Paragraph("Date: " + bill.getCreatedAt()));

            // 📌 **Product Details**
            document.add(new Paragraph("\nProducts Purchased:\n" + bill.getProductDetails())); // ✅ Fixed error

//         // 📌 **Product Details**
//            PdfPTable table = new PdfPTable(3); // 3 columns: Product, Quantity, Price
//            table.addCell("Product");
//            table.addCell("Quantity");
//            table.addCell("Price");
//
//            for (Product product : bill.getProductList()) { // Assuming Bill has a list of Product objects
//                table.addCell(product.getName());
//                table.addCell(String.valueOf(product.getStock()));
//                table.addCell("₹" + product.getPrice());
//            }

//            document.add(table);
            
            // 💰 **Total Amount**
            document.add(new Paragraph("\nTotal Amount: ₹" + bill.getTotalAmount(), titleFont));

            document.add(new Paragraph("\nThank you for shopping with us!"));

            document.close();
            return outputStream.toByteArray();

        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }
}
