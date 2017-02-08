package com.bkash.washfi.controller;

import com.bkash.washfi.entity.FileInformation;
import com.bkash.washfi.entity.UploadModel;
import com.bkash.washfi.service.UploadService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Created by syed.ahmad on 2/6/2017.
 */
@Controller
public class RestUploadController {

    private final Logger logger = LoggerFactory.getLogger(RestUploadController.class);
    private static String UPLOADED_FOLDER = "E:\\DemoDump\\";

    @Autowired
    private UploadService uploadService;

    @GetMapping("/")
    public String index() {
        return "index";
    }


    @PostMapping("/api/upload/multi/model")
    public ResponseEntity<?> multiUploadFileModel(@ModelAttribute UploadModel model) {

        logger.debug("Multiple file upload! With UploadModel");
        FileInformation fileInformation=new FileInformation();
        List<String> filePathList=new ArrayList<>();
        try {

            filePathList=saveUploadedFiles(Arrays.asList(model.getFiles()));

        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }
        logger.info(" multiUploadFileModel : text :  "+model.getExtraField());
        logger.info("multiUploadFileModel : List : size {0}"+filePathList.size());

        fileInformation.setName(model.getExtraField());
        fileInformation.setUrl(filePathList.toString());

        uploadService.addInfo(fileInformation);
        return new ResponseEntity("Successfully uploaded!", HttpStatus.OK);

    }

    private List<String> saveUploadedFiles(List<MultipartFile> files) throws IOException {
        List<String> urlList=new ArrayList<>();
        String url="";
        for (MultipartFile file : files) {

            if (file.isEmpty()) {
                continue;
            }

            byte[] bytes = file.getBytes();
            Path path = Paths.get(UPLOADED_FOLDER + file.getOriginalFilename());
            Files.write(path, bytes);

            url=UPLOADED_FOLDER+path.getFileName();
            logger.info("saveUploadedFiles : Path : "+url);
            urlList.add(url);
        }
        return urlList ;
    }



}
