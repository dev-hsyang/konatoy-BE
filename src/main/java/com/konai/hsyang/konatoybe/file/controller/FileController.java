package com.konai.hsyang.konatoybe.file.controller;

import com.konai.hsyang.konatoybe.file.service.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.servlet.http.HttpServletResponse;

@RequiredArgsConstructor
@Controller
public class FileController {

    private final FileService fileService;

    @GetMapping("/file/download/{fileID}")
    public void download(@PathVariable Long fileID, HttpServletResponse response){

        fileService.downloadFile(fileID, response);
    }
}
