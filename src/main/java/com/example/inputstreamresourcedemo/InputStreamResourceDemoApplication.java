package com.example.inputstreamresourcedemo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.StandardCharsets;
import java.util.Objects;

@RestController
@SpringBootApplication
public class InputStreamResourceDemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(InputStreamResourceDemoApplication.class, args);
    }

    @GetMapping(path = "/test")
    public void test(HttpServletResponse response) throws IOException {
        char[] chars = new char[1024];
        InputStream inputStream = Objects.requireNonNull(ClassLoader.getSystemClassLoader().getResourceAsStream("test.txt"));
        InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
        StringBuilder stringBuilder = new StringBuilder();
        int read;
        for (;;){
            read = inputStreamReader.read(chars);
            if (read != -1){
                stringBuilder.append(chars);
            } else {
                break;
            }
        }

        response.addHeader("content-disposition", "attachment; filename=test.csv; charset=utf-8");
        ServletOutputStream outputStream = response.getOutputStream();
        OutputStreamWriter outputStreamWriter = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);
        // add BOM info jianshu.com/p/928661e27588
        outputStreamWriter.write(new String(new byte[] { (byte) 0xEF, (byte) 0xBB,(byte) 0xBF }));
        outputStreamWriter.write(stringBuilder.toString());
        outputStreamWriter.flush();
    }
}
