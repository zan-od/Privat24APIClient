package zan.api.privat24.builder;

import javax.xml.bind.DatatypeConverter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class SignedRequestBuilder {
    private String id;
    private String password;
    private String requestTemplate;
    private String data;

    public SignedRequestBuilder withId(String id) {
        this.id = id;
        return this;
    }

    public SignedRequestBuilder withPassword(String password) {
        this.password = password;
        return this;
    }

    public SignedRequestBuilder withRequestTemplate(String requestTemplate) {
        this.requestTemplate = requestTemplate;
        return this;
    }

    public SignedRequestBuilder withData(String data) {
        this.data = data;
        return this;
    }

    private String getSignature(String body, String password) throws NoSuchAlgorithmException {
        return sha1(md5(body.trim()+password));
    }

    static String sha1(String value) throws NoSuchAlgorithmException {
        return DatatypeConverter.printHexBinary(MessageDigest.getInstance("SHA-1").digest(value.getBytes())).toLowerCase();
    }

    static String md5(String value) throws NoSuchAlgorithmException {
        return DatatypeConverter.printHexBinary(MessageDigest.getInstance("MD5").digest(value.getBytes())).toLowerCase();
    }

    public String build() throws NoSuchAlgorithmException {
        return new TemplateBuilder()
                .withTemplate(requestTemplate)
                .withParameter("id", id)
                .withParameter("signature", getSignature(data, password))
                .withParameter("data", data)
                .build();
    }
}
