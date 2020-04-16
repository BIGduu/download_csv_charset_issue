package com.example.inputstreamresourcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;

@RestController
@SpringBootApplication
public class InputStreamResourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InputStreamResourceDemoApplication.class, args);
    }

    @GetMapping(path = "/test")
    public ResponseEntity<InputStreamResource> test() throws IOException {
        InputStream resourceAsStream = ClassLoader.getSystemClassLoader().getResourceAsStream("test.txt");
        byte[] bytes1;
        bytes1 = new byte[resourceAsStream.available()];
        resourceAsStream.read(bytes1);
//        String strjson = new String(bytes1, StandardCharsets.UTF_8);

//        byte[] bytes0 = new Byte{new Byte('\uFEFF')}
//		byte[] bytes1 = "abcdefg中文测试".getBytes();
        InputStream inputStream = new ByteArrayInputStream(bytes1);
        HttpHeaders header = new HttpHeaders();
        header.add("content-disposition", "attachment; filename=test.csv; charset=utf-8");
//        header.add("Content-Type", "text/csv");
        header.add("Cache-Control", "no-cache, no-store");
        header.add("Pragma", "no-cache");
        header.add("Expires", "0");
        header.add("Content-Encoding", "UTF-8");
        header.add("Character-Encoding", "UTF-8");
        return ResponseEntity.ok()
//            .contentType(MediaType.valueOf("text/csv;charset=UTF-8"))
            .headers(
                header
            ).body(
//                strjson
                new InputStreamResource(inputStream)
            );
    }
}
