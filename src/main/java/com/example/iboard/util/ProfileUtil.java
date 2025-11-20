package com.example.iboard.util;

import org.springframework.http.*;
import org.springframework.web.multipart.*;

import java.io.*;
import java.util.*;

public class ProfileUtil {
  private static final String PROFLILE_FOLDER = System.getProperty("user.dir") + File.separator + "upload"
      + File.separator + "profile" + File.separator;
  private static final String PROFILE_NAME = "default.jpg";

  // MultipartFile을 파라미터로 받아 base64 문자열 주소 형식로 인코딩하는 함수
  public static String getBase64Profile(MultipartFile file) throws IOException {
    byte[] fileBytes =  file.getBytes();
    return "data:" + file.getContentType() + ";base64," + Base64.getEncoder().encodeToString(fileBytes);
  }

  public static String getDefaultBase64Profile()   {
    try {
      // 1. 폴더와 파일명으로 파일 객체를 생성
      File file = new File(PROFLILE_FOLDER, PROFILE_NAME);
      // 2. FileInputStream을 이용해 open한 파일을 byte로 읽어온다
      FileInputStream fis = new FileInputStream(file);
      byte[] fileBytes = fis.readAllBytes();
      // 3. base64로 리턴
      return "data:" + MediaType.IMAGE_JPEG_VALUE + ";base64," + Base64.getEncoder().encodeToString(fileBytes);
    } catch(IOException e) {
      e.printStackTrace();
    }
    return null;
  }
}
