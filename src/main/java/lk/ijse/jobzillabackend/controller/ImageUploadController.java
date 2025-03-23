package lk.ijse.jobzillabackend.controller;


import ch.qos.logback.core.util.StringUtil;
import lk.ijse.jobzillabackend.dto.ResponseDTO;
import lk.ijse.jobzillabackend.util.FileUploadUtil;
import lk.ijse.jobzillabackend.util.VarList;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

@RestController
@RequestMapping("api/v1/img")
public class ImageUploadController {


    @PostMapping("/uploads")
    public ResponseEntity<ResponseDTO> saveImage(@RequestParam("files") MultipartFile[] files) {
        String uploadDir = "imageFolder";

        try {
            for (MultipartFile file : files) {
                String fileName = StringUtils.cleanPath(
                        Objects.requireNonNullElse(file.getOriginalFilename(), ""));
                System.out.println("Uploading file: " + fileName);

                FileUploadUtil.saveFile(uploadDir, fileName, file);
            }

            return ResponseEntity.ok(
                    new ResponseDTO(VarList.Created, "Files uploaded successfully", null));

        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ResponseDTO(VarList.Internal_Server_Error, "Internal Server Error", null));
        }
    }
}

