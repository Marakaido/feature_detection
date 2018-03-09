package com.marakaido.coursework;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.BufferedInputStream;
import java.io.IOException;

@RestController
public class FeatureDetectionController {

    @RequestMapping("/harris")
    public int[] harris(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedImage img = ImageIO.read(new BufferedInputStream(file.getInputStream()));
        byte[] data = ((DataBufferByte) img.getRaster().getDataBuffer()).getData();
        int rows = img.getHeight();
        int cols = img.getWidth();

        return new int[]{1, 2, 3, 4, 5};
    }
}
