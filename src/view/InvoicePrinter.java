package view;

import com.itextpdf.io.font.constants.StandardFonts;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.*;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import model.entity.Appointment;
import respository.dao.AppointmentDAO;
import respository.dao.ServiceDAO;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class InvoicePrinter {
    private final ServiceDAO serviceDAO;

    public InvoicePrinter() {
        this.serviceDAO = new ServiceDAO();
    }

    public boolean printInvoice(CheckDonView.OrderDetailDialog.PrintData data) throws IOException {
        try {
            if (data.getServices() == null || data.getServices().length == 0) {
                throw new IOException("Không tìm thấy thông tin dịch vụ");
            }

            // Create PDF file
            String fileName = String.format("Invoice_%d.pdf", data.getAppointmentId());
            PdfWriter writer = new PdfWriter(fileName);
            PdfDocument pdf = new PdfDocument(writer);
            Document document = new Document(pdf);

            // Create fonts
            PdfFont normalFont = PdfFontFactory.createFont(StandardFonts.HELVETICA);
            PdfFont boldFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_BOLD);
            PdfFont italicFont = PdfFontFactory.createFont(StandardFonts.HELVETICA_OBLIQUE);

            // Header
            document.add(new Paragraph("HÓA ĐƠN DỊCH VỤ THÚ CƯNG")
                    .setFont(boldFont)
                    .setFontSize(18)
                    .setTextAlignment(TextAlignment.CENTER));

            // Store info
            document.add(new Paragraph("PET CARE SERVICE")
                    .setFont(normalFont)
                    .setTextAlignment(TextAlignment.CENTER));
            document.add(new Paragraph("Địa chỉ: 123 ABC Street, District X, HCM City")
                    .setFont(normalFont)
                    .setTextAlignment(TextAlignment.CENTER));

            // Invoice details
            document.add(new Paragraph(String.format("Số hóa đơn: #%d", data.getAppointmentId()))
                    .setFont(boldFont));
            document.add(new Paragraph(String.format("Khách hàng: %s", data.getCustomerName()))
                    .setFont(normalFont));
            document.add(new Paragraph(String.format("Thú cưng: %s", data.getPetName()))
                    .setFont(normalFont));
            document.add(new Paragraph(String.format("Ngày: %s", data.getAppointmentDate()))
                    .setFont(normalFont));

            // Services table
            Table table = new Table(UnitValue.createPercentArray(new float[]{70, 30}))
                    .useAllAvailableWidth();

            table.addHeaderCell(new Cell().add(new Paragraph("Dịch vụ").setFont(boldFont)));
            table.addHeaderCell(new Cell().add(new Paragraph("Giá").setFont(boldFont)));

            // Calculate total
            double totalAmount = 0;
            DecimalFormat priceFormat = new DecimalFormat("#,### VNĐ");

            for (String serviceName : data.getServices()) {
                double price = serviceDAO.getPriceByName(serviceName);
                totalAmount += price;

                table.addCell(new Cell().add(new Paragraph(serviceName).setFont(normalFont)));
                table.addCell(new Cell().add(new Paragraph(priceFormat.format(price))
                        .setFont(normalFont)
                        .setTextAlignment(TextAlignment.RIGHT)));
            }

            document.add(table);

            // Total amount
            document.add(new Paragraph(String.format("\nTổng cộng: %s", priceFormat.format(totalAmount)))
                    .setFont(boldFont)
                    .setTextAlignment(TextAlignment.RIGHT));

            // Footer
            document.add(new Paragraph("\nCảm ơn quý khách!")
                    .setFont(italicFont)
                    .setTextAlignment(TextAlignment.CENTER));

            document.close();

            // Open PDF file
            if (Desktop.isDesktopSupported()) {
                Desktop.getDesktop().open(new File(fileName));
            }
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
}