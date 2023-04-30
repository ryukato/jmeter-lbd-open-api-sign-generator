package com.linecorp.lbd.signature;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import org.apache.commons.codec.binary.Base64;

public class DefaultSignatureGenerator {
    private static final String HMAC_512_SECRET_ALGORITHM = "HmacSHA512";

    public String generate(
            String secret,
            String httpMethod,
            String path,
            Long timestamp,
            String nonce,
            String queryString,
            String serializedBody
    ) {
        String data = signatureTarget(httpMethod, path, timestamp, nonce, queryString, serializedBody);
        // create hash for signature
        Mac mac = null;
        try {
            SecretKeySpec signingKey = new SecretKeySpec(secret.getBytes(), HMAC_512_SECRET_ALGORITHM);
            mac = Mac.getInstance(HMAC_512_SECRET_ALGORITHM);
            mac.init(signingKey);
        } catch (Exception e) {
            throw new RuntimeException("fail to generate signature", e);
        }
        return Base64.encodeBase64String(mac.doFinal(data.getBytes()));
    }

    private String signatureTarget(
            String httpMethod,
            String path,
            Long timestamp,
            String nonce,
            String queryString,
            String serializedBody) {
        StringBuilder sb = new StringBuilder();
        sb.append(nonce)
          .append(timestamp)
          .append(httpMethod)
          .append(path);
        if (!queryString.isEmpty()) {
            if (queryString.contains("?")) {
                sb.append(queryString);
            } else {
                sb.append("?").append(queryString);
            }
        }

        if (!serializedBody.isEmpty()) {
            if (serializedBody.contains("?")) {
                sb.append("&").append(serializedBody);
            } else {
                sb.append("?").append(serializedBody);
            }
        }

        return sb.toString();
    }

    public static DefaultSignatureGenerator createInstance() {
        return new DefaultSignatureGenerator();
    }
}
