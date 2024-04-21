package de.mydomain.photos.clone.controller;

import de.mydomain.photos.clone.model.Photo;
import de.mydomain.photos.clone.service.PhotosService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import java.io.IOException;

@RestController
public class PhotosController {

    @Autowired
    private PhotosService photosService;

    @GetMapping("/")
    public String helloWorld() {
        return "Willkommen zu meiner Photo-Applikation...";
    }

    @GetMapping("/photos")
    public Iterable<Photo> getPhotos() {
        return photosService.getPhotos();
    }

    @GetMapping("/photos/{id}")
    public Photo getPhoto(@PathVariable Integer id) {

        Photo photo = photosService.getPhoto(id);

        if (photo == null) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND);
        }

        return photo;
    }

    @DeleteMapping("/photos/{id}")
    public void deletePhoto(@PathVariable Integer id) {
        photosService.remove(id);
    }

    /*
    @PostMapping("/photos")
    public Photo createPhoto(@RequestBody @Valid Photo photo) {
        photo.setId(UUID.randomUUID().toString());
        db.put(photo.getId(), photo);
        return photo;
    }
    */

    @PostMapping("/photos")
    public Photo uploadImage(@RequestParam("file") MultipartFile file) throws IOException {
        return photosService.save(file.getOriginalFilename(), file.getContentType(), file.getBytes());
    }
}
