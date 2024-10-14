package com.tallerwebi.infraestructura;
import com.tallerwebi.dominio.IUploadFilesService;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.File;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;


@Service
public class UploadFilesServiceImpl implements IUploadFilesService {

    @Override
    public String handleFileUpload(MultipartFile file) throws Exception{

        try {
            byte[] bytes = file.getBytes();
            String fileOriginalName = file.getOriginalFilename();

            long fileSize = file.getSize();
            long maxFileSize = 1024 * 1024 * 10;

            if (fileSize > maxFileSize) {
                return "El archivo es demasiado pesado";
            }

            if (!fileOriginalName.endsWith(".jpg")
                && !fileOriginalName.endsWith(".png")
                && !fileOriginalName.endsWith(".jpeg")) {
                return "Solo archivos jpg, jpeg y png";
            }

            String newFileName = fileOriginalName; 

            File folder = new File("uploads/imagenesSubidas");

            if (!folder.exists()) {
                folder.mkdirs();
            }

            Path path = Paths.get("uploads/imagenesSubidas/" + newFileName);
            Files.write(path, bytes);

            return "/imagenesSubidas/" + newFileName;

        } catch (Exception e) {
            throw new Exception(e.getMessage());
        }
    }
}

