package com.bkash.washfi.repository;

import com.bkash.washfi.entity.FileInformation;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * Created by syed.ahmad on 2/8/2017.
 */
@Repository
public interface UploadRepository extends CrudRepository<FileInformation,Integer> {


}
