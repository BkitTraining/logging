package com.example.demo.logging.utils;

import com.example.demo.logging.annotation.LogMethod;
import org.springframework.util.StringUtils;

public class CommonUtils {

  @LogMethod
  public static void validateEmail(String email) {
    if (!StringUtils.hasText(email)) {
      throw new IllegalArgumentException("Invalid Email " + email);
    }
  }

}
