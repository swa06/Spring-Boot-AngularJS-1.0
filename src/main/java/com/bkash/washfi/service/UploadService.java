package com.bkash.washfi.service;

import com.bkash.washfi.controller.RestUploadController;
import com.bkash.washfi.entity.FileInformation;
import com.bkash.washfi.repository.UploadRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * Created by syed.ahmad on 2/8/2017.
 */
@Service
public class UploadService {
    private final Logger logger = LoggerFactory.getLogger(UploadService.class);
    @Autowired
    private UploadRepository uploadRepository;

    public UploadService() {
    }


    public void addInfo(FileInformation fileInformation) {
        if(fileInformation!=null) {
            uploadRepository.save(fileInformation);
        }else{
            logger.error("Student data cannot be null.");
        }
    }

    public List<FileInformation> findAll() {
        List<FileInformation> fileInformationList=new ArrayList<>();
        Iterator<FileInformation> iterator=uploadRepository.findAll().iterator();
        while(iterator.hasNext()){
            fileInformationList.add(iterator.next());
        }
        return fileInformationList;
    }

    public void removeUser(int id) {
        uploadRepository.delete(id);
    }


}
