package com.example.demo.controller;

import com.example.demo.file.UploadFileResponse;
import com.example.demo.persistences.DbFile;
import com.example.demo.services.DbFileStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.io.IOException;
import java.sql.Date;

@CrossOrigin(origins = "http://localhost:3000")
@RestController
@RequestMapping("fyp/api")
public class DbFileController {
    @Autowired
    private DbFileStorageService dbFileStorageService;

    @PostMapping("/uploadFile")
    public UploadFileResponse uploadFile(@RequestParam("file") MultipartFile file,
                                         @RequestParam("upload_date") Date upload_date,
                                         @RequestParam("task_id") int task_id) throws IOException {

        DbFile dbFile = dbFileStorageService.storeFile(file, task_id, upload_date);

        String fileDownloadUri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/fyp/api/downloadFile/")
                .path(dbFile.getId())
                .toUriString();
        UploadFileResponse response = new UploadFileResponse();
        response.setId(dbFile.getId());
        response.setFileName(dbFile.getFileName());
        response.setFileType(dbFile.getFileType());
        response.setTask_id(dbFile.getTask_id());
        response.setUploadDate(dbFile.getUploadDate());
        return response;
    }

    @GetMapping("/downloadFile/{fileId}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String fileId) {
        // Load file from database
        DbFile dbFile = dbFileStorageService.getFile(fileId);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("/downloadFile/task/{task_id}")
    public ResponseEntity<Resource> downLoadFileByTaskId(@PathVariable int task_id) {
        DbFile dbFile = dbFileStorageService.getFileByTask(task_id);

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(dbFile.getFileType()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + dbFile.getFileName() + "\"")
                .body(new ByteArrayResource(dbFile.getData()));
    }

    @GetMapping("checkFileAttachedCount/task/{task_id}")
    public ResponseEntity<Integer> checkFileAttachedCount (@PathVariable int task_id) {
        Integer result = dbFileStorageService.getFileCountbyTask(task_id);

        return ResponseEntity.ok().body(result);
    }
}
