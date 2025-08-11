package inge2.com.alquileres.backend.service;


import com.cloudinary.Cloudinary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.UncheckedIOException;
import java.time.LocalTime;
import java.util.Map;

@Service
public class FileStorageService {

    private final Cloudinary cloudinary;

    public FileStorageService(Cloudinary cloudinary) {
        this.cloudinary = cloudinary;
    }

    public String guardarImagen(MultipartFile imagen) {
        if(imagen.isEmpty()){
            throw new IllegalArgumentException("La imagen esta vacia");
        }
        try {
            String nombre = getNombre(imagen);
            Map uploadResult =cloudinary.uploader().upload(imagen.getBytes(), Map.of("public_id", nombre, "overwrite", true,"resource_type", "image"));
            return  uploadResult.get("secure_url").toString();
        }
        catch (IOException ex){
            throw  new UncheckedIOException("Ocurrio un error al cargar la imagen",ex);
        }
    }


    private static String getNombre(MultipartFile imagen) {
        String nombreImagen = imagen.getOriginalFilename();
        if (nombreImagen == null){
            throw new IllegalArgumentException("La imagen debe tener nombre");
        }

        int ultimoPunto = nombreImagen.lastIndexOf('.');
        if (ultimoPunto >= 0) {
            nombreImagen = nombreImagen.substring(0, ultimoPunto);
        }

        return nombreImagen + "_" + LocalTime.now();
    }

    public void deleteImage(String urlImagen) {
        try {
            this.cloudinary.uploader().destroy(getPublicIdFromUrl(urlImagen), Map.of("resource_type", "image"));
        }
        catch (IOException e){
            throw new UncheckedIOException("Ocurrio un error al borrar la imagen", e);
        }
    }

    public void checkImagen(MultipartFile imagen) {
        if (imagen == null || imagen.isEmpty()) {
            throw new IllegalArgumentException("Para crear un auto la imagen es obligatoria");
        }
    }
    private static String getPublicIdFromUrl(String url) {
        // Dividir la URL por "/upload/" y obtener la segunda parte
        String[] parts = url.split("/upload/");
        if (parts.length < 2) {
            throw new IllegalArgumentException("La URL no contiene '/upload/'");
        }

        String path = parts[1];

        // Remover versión (por ejemplo: v1693000000/)
        path = path.replaceFirst("^v\\d+/", "");

        // Remover la extensión (por ejemplo: .jpg, .png, etc.)
        path = path.replaceFirst("\\.[^/.]+$", "");

        return path;
    }
}
