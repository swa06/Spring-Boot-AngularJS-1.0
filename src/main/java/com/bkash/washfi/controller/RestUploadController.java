package com.bkash.washfi.controller;

import com.bkash.washfi.entity.FileInformation;
import com.bkash.washfi.entity.FileList;
import com.bkash.washfi.entity.UploadModel;
import com.bkash.washfi.service.UploadService;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;

import javax.servlet.http.HttpServletResponse;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
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
        logger.info("Files in controller:"+model.getFiles());
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
    @GetMapping("/result")
    public ResponseEntity<?> showInitPage(Model model) {

        List<FileInformation> fileInformationList=uploadService.findAll();
        FileInformation fileInformation=new FileInformation();
        Gson gson = new GsonBuilder().create();
        String jsonInput = "";

        for(FileInformation f :fileInformationList){
            jsonInput=jsonInput+gson.toJson(f);
        }
        FileList fileList=new FileList();
        fileList.setFileList(fileInformationList);
        logger.info(" showInitPage : text :  "+jsonInput);
        logger.info(" showInitPageList : text :  "+fileList.getFileList().get(0).getName());

        return new ResponseEntity(fileList, HttpStatus.OK);


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

            url=path.getFileName().toString();

            urlList.add(url);
        }
        return urlList ;
    }

    @GetMapping(value = "/download")
    public void downloadFile(@RequestParam(value="fileName") String fileName,
                             HttpServletResponse response) {


        response.setContentType("application/octet-stream");
        response.setHeader("Content-Disposition", "attachment;filename=\"" + fileName + "\"");
        try {
            FileInputStream inputStream = new FileInputStream(UPLOADED_FOLDER+fileName);
            int read =0;
            byte [] bytes = new byte[4096];
            OutputStream os = response.getOutputStream();
            logger.info(" total bytes length {0}" , inputStream.available());
            while((read = inputStream.read(bytes))> -1){
                os.write(bytes, 0, read);
            }
            os.flush();
            os.close();
            logger.info( "downloadFileServlet is completed!!!");
        } catch (IOException ex) {
            logger.info("Error writing file to output stream. Filename was '{}'", fileName, ex);
            throw new RuntimeException("IOError writing file to output stream");
        }

    }

    @GetMapping(value ="/api/delete")
    public ResponseEntity<?> deltedId(@RequestParam(value="id") String id){
        int deleteId=-1;
        try {
            logger.info("ID in controller: "+id);
            deleteId=Integer.parseInt(id);
            uploadService.removeUser(deleteId);
        }catch (Exception e){
            e.printStackTrace();
        }

        return  new ResponseEntity(deleteId, HttpStatus.OK);
    }



}
