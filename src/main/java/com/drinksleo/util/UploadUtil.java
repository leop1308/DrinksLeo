package com.drinksleo.util;


import com.drinksleo.config.ImageConfigs;
import com.drinksleo.exception.CustomException;
import com.drinksleo.exception.ExceptionEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;


@Slf4j
public class UploadUtil {


    //Logger log = LoggerFactory.getLogger("SampleLogger");

    public static boolean uploadImage(MultipartFile image, ImageConfigs imageConfigs) throws Exception {
        boolean sucess = false;

        if (image != null) {
            if (!image.isEmpty()) {
                String fileName = image.getOriginalFilename();
                try {

                    //Creating directory to store the image
                    //String uploadImageFolder1 = "C:\\Users\\PC\\OneDrive\\Estudo\\Programacao\\Projetos\\drinksleo\\src\\main\\resources\\static\\image-upload";
                    File dir = new File(imageConfigs.getPath());
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

    public void imageIsRequired(ImageConfigs imageConfigs) throws Exception {
        if(imageConfigs.getRequired()){
            throw new CustomException(ExceptionEnum.IMAGE_REQUIRED.getMessage());
        }
    }

    public static boolean deleteFile (String fileUrl){
        File file = new File (fileUrl);
        if(file.exists()){
            if(file.delete()){
                log.info("File deleted sucessfully!");
                return true;
            }else{
                log.error("File deletion could not be deleted!");
            }
        }else{
            log.error("File or Folder does not exists!");
        }
        return false;

    }
}
