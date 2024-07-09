package it.epicode.the_plant_based_hub_backend.services;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ShoppingListService {

    public ByteArrayOutputStream generateShoppingListPdf(List<String> items) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, output);
        document.open();

        // Color configuration
        BaseColor black = new BaseColor(0, 0, 0);
        BaseColor green = new BaseColor(0, 96, 47);

        // Font configuration
        BaseFont raleway = BaseFont.createFont("src/main/resources/fonts/Raleway/Raleway-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font ralewayFont = new Font(raleway, 12, Font.NORMAL, black);
        BaseFont lora = BaseFont.createFont("src/main/resources/fonts/Lora/Lora-Regular.ttf", BaseFont.IDENTITY_H, BaseFont.EMBEDDED);
        Font loraFont = new Font(lora, 13, Font.NORMAL, green);

        // Add logo with text
        Image logo = Image.getInstance("src/main/resources/images/logo.png");
        logo.scaleToFit(80, 80);
        logo.setAlignment(Element.ALIGN_CENTER);
        document.add(logo);

        Paragraph logoText = new Paragraph("THE PLANT BASED HUB", new Font(lora, 16, Font.BOLD, green));
        logoText.setAlignment(Element.ALIGN_CENTER);
        logoText.setSpacingAfter(20);
        document.add(logoText);

        // Title
        Paragraph title = new Paragraph("Shopping List", new Font(lora, 16, Font.BOLD, green));
        title.setAlignment(Element.ALIGN_CENTER);
        title.setSpacingAfter(20);
        document.add(title);

        // List of items
        for (String item : items) {
            Paragraph itemParagraph = new Paragraph("\u2714 " + item, ralewayFont);
            itemParagraph.setAlignment(Element.ALIGN_CENTER);
            document.add(itemParagraph);
        }

        document.close();
        return output;
    }
}
