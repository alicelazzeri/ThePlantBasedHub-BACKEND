package it.epicode.the_plant_based_hub_backend.controllers;

import com.itextpdf.text.DocumentException;
import it.epicode.the_plant_based_hub_backend.services.ShoppingListService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/shopping-list")
@CrossOrigin
public class ShoppingListController {

    @Autowired
    private ShoppingListService shoppingListService;

    // GET http://localhost:8080/api/shopping-list?items=item1,item2,item3  + bearer token

    @GetMapping
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<String> getShoppingList(@RequestParam List<String> items, Model model) {
        StringBuilder htmlResponse = new StringBuilder("<h1>Shopping List</h1><ul>");
        for (String item : items) {
            htmlResponse.append("<li>").append(item).append("</li>");
        }
        htmlResponse.append("</ul>");
        return new ResponseEntity<>(htmlResponse.toString(), HttpStatus.OK);
    }

    // GET http://localhost:8080/api/shopping-list/pdf?items=item1,item2,item3  + bearer token

    @GetMapping("/pdf")
    @PreAuthorize("hasAnyAuthority('ADMIN', 'USER')")
    public ResponseEntity<byte[]> getShoppingListPdf(@RequestParam List<String> items) throws IOException, DocumentException {
        ByteArrayOutputStream output = shoppingListService. generateShoppingListPdf(items);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_PDF);
        headers.setContentDispositionFormData("attachment", "shopping_list.pdf");
        return new ResponseEntity<>(output.toByteArray(), headers, HttpStatus.OK);
    }
}
