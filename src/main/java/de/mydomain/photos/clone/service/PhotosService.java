package de.mydomain.photos.clone.service;

import de.mydomain.photos.clone.model.Photo;
import de.mydomain.photos.clone.repository.PhotosRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PhotosService {

    @Autowired
    private PhotosRepository photosRepository;

    public Iterable<Photo> getPhotos() {
        return photosRepository.findAll();
    }

    public Photo getPhoto(Integer id) {
        return photosRepository.findById(id).orElse(null);
    }

    public void remove(Integer id) {
        photosRepository.deleteById(id);
    }

    public Photo save(String fileName, String contentType, byte[] data) {

        Photo photo = new Photo();

        photo.setFileName(fileName);
        photo.setContentType(contentType);
        photo.setData(data);

        photosRepository.save(photo);

        return photo;
    }
}
