package com.drinksleo.util;


import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Slf4j
public class UploadUtil {


    //Logger log = LoggerFactory.getLogger("SampleLogger");

    public static boolean uploadImage(MultipartFile image, String uploadImageFolder) {
        boolean sucess = false;

        if (image != null) {
            if (!image.isEmpty()) {
                String fileName = image.getOriginalFilename();
                try {

                    //Creating directory to store the image
                    //String uploadImageFolder1 = "C:\\Users\\PC\\OneDrive\\Estudo\\Programacao\\Projetos\\drinksleo\\src\\main\\resources\\static\\image-upload";
                    File dir = new File(uploadImageFolder);
                    if (!dir.exists()) { //create the directory if it not exists.
                        dir.mkdir();
                    }

                    //Creating the file in the directory
                    File file = new File(dir.getAbsolutePath() + File.separator+ fileName);

                    BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));

                    stream.write(image.getBytes());
                    stream.close();
                    log.info("File {}, has been uploaded sucessfully!", fileName);
                    sucess = true;
                } catch (Exception e) {
                    log.error("File {}, was not uploaded sucessfully! \n Error: {}", fileName, e);
                }
            } else {
                log.error("Upload fail: The file is empty!");
            }
        } else {
            log.error("Upload fail: The file is null!");
        }

        return sucess;
    }
}
