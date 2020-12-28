package com.example.demo.services;

import com.example.demo.persistences.DbFile;
import com.example.demo.repository.DbFileRepository;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.Date;

@Service
public class DbFileStorageService {

    @Autowired
    private DbFileRepository dbFileRepository;

    public DbFile storeFile(MultipartFile file, int task_id, Date upload_date) throws IOException {
        String fileName = StringUtils.cleanPath(file.getOriginalFilename());

            DbFile dbFile = new DbFile(fileName, file.getContentType(), file.getBytes(), task_id, upload_date);

            return dbFileRepository.save(dbFile);

    }

    public DbFile getFile(String fileId) {
        return dbFileRepository.findById(fileId).orElse(null);
    }
}
