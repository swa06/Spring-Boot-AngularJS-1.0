package com.bkash.washfi.entity;

import org.springframework.web.multipart.MultipartFile;

/**
 * Created by syed.ahmad on 2/6/2017.
 */
public class UploadModel {
    private String extraField;
    private MultipartFile[] files;

    public String getExtraField() {
        return extraField;
    }

    public void setExtraField(String extraField) {
        this.extraField = extraField;
    }

    public MultipartFile[] getFiles() {
        return files;
    }

    public void setFiles(MultipartFile[] files) {
        this.files = files;
    }
}
