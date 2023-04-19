package com.apps.ecom.services.impl;

import com.apps.ecom.services.FileService;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

@Service
public class FileServiceImpl implements FileService {
    @Override
    public String uploadImage(String path, MultipartFile file) throws IOException {
        //Filename
        String name = file.getOriginalFilename();

        // generate the random ID
        String randomID = UUID.randomUUID().toString();

        // generate filename
        String fileName1 = randomID.concat(name.substring(name.lastIndexOf(".")));

        //full path
        String filePath = path+ File.separator+fileName1;

        System.out.println(filePath);

        //create folder if not created
        File f = new File(path);
        if(!f.exists()){
            f.mkdir();
        }

        //file copy
        Files.copy(file.getInputStream(), Paths.get(filePath));

        return fileName1;
    }

    @Override
    public InputStream getResource(String path, String fileName) throws FileNotFoundException {
        String fullPath = path+File.separator+fileName;
        InputStream is  = new FileInputStream(fullPath);
        return is;
    }
}
