package io.github.tangmonkmeat.token;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.util.FileCopyUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.KeyFactory;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

/**
 * 读取 RSA 的公钥和私钥
 *
 * @author zwl
 * @version 1.0
 * @date 2021/7/14 下午2:04
 */
public class RsaKeysReader {

    private static final Logger LOGGER = LoggerFactory.getLogger(RsaKeysReader.class);

    public static RSAPrivateKey readRsaPvt(String fileName) {
        String data = getData(fileName);
        data = data.replace("-----BEGIN RSA PRIVATE KEY-----\n", "")
                .replace("\n-----END RSA PRIVATE KEY-----\n", "");


        byte[] bytes = Base64.getDecoder().decode(data);

        PKCS8EncodedKeySpec ks = new PKCS8EncodedKeySpec(bytes);
        KeyFactory kf = null;
        PrivateKey pvt = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            pvt = kf.generatePrivate(ks);
        } catch (Exception e) {
            LOGGER.info("私钥读取失败");
            e.printStackTrace();
        }

        return (RSAPrivateKey) pvt;
    }


    public static RSAPublicKey readRsaPub(String fileName) {
        String data = getData(fileName);
        data = data.replace("-----BEGIN RSA PUBLIC KEY-----\n", "")
                .replace("\n-----END RSA PUBLIC KEY-----\n", "");


        byte[] bytes = Base64.getDecoder().decode(data);

        X509EncodedKeySpec ks = new X509EncodedKeySpec(bytes);
        KeyFactory kf = null;
        PublicKey pub = null;
        try {
            kf = KeyFactory.getInstance("RSA");
            pub = kf.generatePublic(ks);
        } catch (Exception e) {
            LOGGER.info("公钥读取失败");
            e.printStackTrace();
        }

        return (RSAPublicKey) pub;
    }

    private static String getData(String fileName) {
        String data = "";
        ClassPathResource cpr = new ClassPathResource(fileName);
        try {
            byte[] bdata = FileCopyUtils.copyToByteArray(cpr.getInputStream());
            LOGGER.info("试图读取秘钥文件, 文件路径 : [{}]", cpr.getPath());
            data = new String(bdata, StandardCharsets.UTF_8);
        } catch (IOException e) {
            LOGGER.info("文件读取失败");
            e.printStackTrace();
        }
        return data;
    }

}
