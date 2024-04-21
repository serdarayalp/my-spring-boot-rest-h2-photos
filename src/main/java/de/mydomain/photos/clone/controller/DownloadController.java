package de.mydomain.photos.clone.controller;

import de.mydomain.photos.clone.model.Photo;
import de.mydomain.photos.clone.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

@RestController
public class DownloadController {

    @Autowired
    private PhotosService photosService;

    @GetMapping("/download/{id}")
    public ResponseEntity<byte[]> downloadPhoto(@PathVariable Integer id) {

        Photo photo = photosService.getPhoto(id);

        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        byte[] data = photo.getData();

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.valueOf(photo.getContentType()));

        /**
         * Das Content-Disposition-Headerfeld wird verwendet, um Anweisungen zu geben, wie der Browser mit dem Inhalt
         * umgehen soll. In diesem Fall wird "attachment" als Wert für den Typ des Inhalts festgelegt, was bedeutet,
         * dass der Browser das angehängte Inhaltsstück als Dateianhang behandeln soll.
         * Die Methode filename(photo.getFileName()) legt den Dateinamen fest, den der Browser verwenden soll,
         * wenn das Foto als Anhang heruntergeladen wird.
         */
        ContentDisposition contentDisposition = ContentDisposition
                .builder("attachment")
                .filename(photo.getFileName())
                .build();
        headers.setContentDisposition(contentDisposition);

        return new ResponseEntity<>(data, headers, HttpStatus.OK);
    }
}
