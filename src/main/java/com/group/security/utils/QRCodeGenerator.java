package com.group.security.utils;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.WriterException;
import com.google.zxing.client.j2se.MatrixToImageWriter;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.group.security.entity.Order;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.colors.ColorConstants;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;

import java.io.IOException;
import java.nio.file.FileSystems;
import java.nio.file.Path;

public class QRCodeGenerator {
    public static String generateQRCode(Order order) throws WriterException, IOException {
        String qrCodePath = "E:\\ENGINEERING\\FOE-UOR";
        String qrCodeName = qrCodePath + "Order-" + order.getOrderId() + "-QRCODE.png";

        // Generate QR code
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(
                "NIC: " + order.getUserInfo().getNic() + "\n" +
                        "Phone: " + order.getUserInfo().getPhone() + "\n" +
                        "Email: " + order.getUserInfo().getEmail() + "\n" +
                        "Order ID: " + order.getOrderId(),
                BarcodeFormat.QR_CODE, 400, 400);
        Path path = FileSystems.getDefault().getPath(qrCodeName);
        MatrixToImageWriter.writeToPath(bitMatrix, "PNG", path);

        // Generate PDF
        String pdfPath = qrCodePath + "Order-" + order.getOrderId() + "-QRCODE.pdf";
        PdfWriter writer = new PdfWriter(pdfPath);
        PdfDocument pdfDoc = new PdfDocument(writer);
        pdfDoc.setDefaultPageSize(PageSize.A4);
        Document document = new Document(pdfDoc);

        // Add fonts
        PdfFont bold = PdfFontFactory.createFont("Helvetica-Bold");
        PdfFont regular = PdfFontFactory.createFont("Helvetica");

        // Add header
        document.add(new Paragraph("THIS IS YOUR E-TICKET")
                .setFont(bold)
                .setFontSize(24)
                .setTextAlignment(TextAlignment.CENTER)
                .setFontColor(ColorConstants.WHITE)
                .setBackgroundColor(ColorConstants.BLACK)
                .setMarginBottom(10));

        document.add(new Paragraph("Please Show This QR Code On Your Mobile When You Arrive At The Event Location")
                .setFont(regular)
                .setFontSize(12)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Add EasyTicket.LK text
        document.add(new Paragraph("EasyTicket.LK")
                .setFont(bold)
                .setFontSize(18)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));

//        // Add check-in code
//        document.add(new Paragraph("ORDER CODE")
//                .setFont(bold)
//                .setFontSize(10)
//                .setTextAlignment(TextAlignment.RIGHT));
//
//        document.add(new Paragraph(String.valueOf(order.getOrderId()))
//                .setFont(bold)
//                .setFontSize(14)
//                .setTextAlignment(TextAlignment.RIGHT)
//                .setMarginBottom(20));

        // Add QR code
        Image qrCodeImage = new Image(ImageDataFactory.create(qrCodeName))
                .setWidth(400)
                .setHeight(400)
                .setHorizontalAlignment(HorizontalAlignment.CENTER);
        document.add(qrCodeImage.setMarginBottom(10));


        document.add(new Paragraph("Event Name: " + order.getEventCards().getEventName())
                .setFont(regular)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));


        document.add(new Paragraph("Event Date: "+order.getEventCards().getEventDate() + "  At: " + order.getEventCards().getEventTime())
                .setFont(regular)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));


        document.add(new Paragraph("Customer Name: " + order.getUserInfo().getFirst_name())
                .setFont(regular)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(10));


        document.add(new Paragraph("Ticket Price: " + order.getAmount() + " LKR")
                .setFont(regular)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER)
                .setMarginBottom(20));

        // Add seat number
//        document.add(new Paragraph("SEAT NUMBER")
//                .setFont(bold)
//                .setFontSize(10));
//
//        document.add(new Paragraph("A6")  // Replace "A6" with actual seat number if available
//                .setFont(bold)
//                .setFontSize(36)
//                .setMarginBottom(20));

        // Add footer
        document.add(new Paragraph("Enjoy Your Event | EasyTicket.LK")
                .setFont(bold)
                .setFontSize(14)
                .setTextAlignment(TextAlignment.CENTER));

        document.close();

        return pdfPath;
    }
}
