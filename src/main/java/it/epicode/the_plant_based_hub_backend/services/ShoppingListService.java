package it.epicode.the_plant_based_hub_backend.services;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfWriter;
import org.springframework.stereotype.Service;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@Service
public class ShoppingListService {
    public ByteArrayOutputStream generateShoppingListPdf (List<String> items) throws DocumentException, IOException {
        Document document = new Document();
        ByteArrayOutputStream output = new ByteArrayOutputStream();

        PdfWriter.getInstance(document, output);
        document.open();

        Paragraph title = new Paragraph("Shopping List");
        title.setAlignment(Element.ALIGN_CENTER);
        document.add(title);

        for (String item : items) {
            document.add(new Paragraph(item));
        }

        document.close();
        return output;
    }

}
