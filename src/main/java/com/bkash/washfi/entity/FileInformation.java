package com.bkash.washfi.entity;

import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * Created by syed.ahmad on 2/8/2017.
 */
@Entity
@Table(name = "BOOT_USER_FILE")
public class FileInformation {

    @Id
    @Basic(optional = false)
    @NotNull
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "FILE_TABLE_TEST_BOOT_SEQ")
    @SequenceGenerator(name = "FILE_TABLE_TEST_BOOT_SEQ", sequenceName = "FILE_TABLE_TEST_BOOT_SEQ", allocationSize = 1)
    private int id;

    @NotNull
    @Length(min = 1,max = 50)
    private String name;

    @NotNull
    @Length(min = 1, max = 100)
    private String url;


    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}
