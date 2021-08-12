package io.github.tangmonkmeat.dto;


import java.io.Serializable;

/**
 * Description:
 *
 * @author zwl
 * @date 2021/7/15 上午11:08
 * @version 1.0
 */
public class DetailAuthDTO implements Serializable {
    public String signature;
    public String rawData;
    public String encryptedData;
    public String iv;

    public DetailAuthDTO(){}

    public DetailAuthDTO(String signature, String rawData, String encryptedData, String iv) {
        this.signature = signature;
        this.rawData = rawData;
        this.encryptedData = encryptedData;
        this.iv = iv;
    }

    @Override
    public String toString() {
        return "DetailAuthDTO{" +
                "signature='" + signature + '\'' +
                ", rawData='" + rawData + '\'' +
                ", encryptedData='" + encryptedData + '\'' +
                ", iv='" + iv + '\'' +
                '}';
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public String getRawData() {
        return rawData;
    }

    public void setRawData(String rawData) {
        this.rawData = rawData;
    }

    public String getEncryptedData() {
        return encryptedData;
    }

    public void setEncryptedData(String encryptedData) {
        this.encryptedData = encryptedData;
    }

    public String getIv() {
        return iv;
    }

    public void setIv(String iv) {
        this.iv = iv;
    }
}
