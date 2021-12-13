package com.demo.springmybatis.common.util;

import com.demo.springmybatis.common.security.jwt.constants.SecurityConstants;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jws;
import io.jsonwebtoken.Jwts;

public class AuthUtil {

  public static int getUserNo(String header) throws Exception {
    String token = header.substring(7);

    byte[] signingKey = SecurityConstants.JWT_SECRET.getBytes();

    Jws<Claims> parsedToken = Jwts.parser()
            .setSigningKey(signingKey)
            .parseClaimsJws(token);

    String subject = parsedToken.getBody().getSubject();

		return Integer.parseInt(subject);
  }
}
