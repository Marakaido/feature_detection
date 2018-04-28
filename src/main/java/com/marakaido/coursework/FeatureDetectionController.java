package com.marakaido.coursework;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.BufferedInputStream;
import java.io.IOException;

@RestController
public class FeatureDetectionController {

    @RequestMapping("/harris")
    public int[] harris(@RequestParam("file") MultipartFile file) throws IOException {
        BufferedImage img = ImageIO.read(new BufferedInputStream(file.getInputStream()));

        int cols = img.getHeight();
        int rows = img.getWidth();
        double[][] data = new double[rows][cols];

        for( int i = 0; i < rows; i++ )
            for( int j = 0; j < cols; j++ ) {
                int value = img.getRGB(i,j);
                data[i][j] = (value&0xFF000000 + value&0x00FF0000 + value&0x0000FF00 + value&0x000000FF) / 4.0;
            }

        HarrisDetector detector = new HarrisDetector();
        detector.setRThreshold(1000);
        detector.setNonMaxSpan(5);

        return detector.apply(data);
    }
}
