package com.marakaido.coursework;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.annotation.MultipartConfig;

@RestController
@MultipartConfig(fileSizeThreshold = 20971520)
public class FeatureDetectionController {

    @RequestMapping("/harris")
    public byte[] harris(@RequestParam("file") MultipartFile file) {
        return new byte[]{};
    }
}
