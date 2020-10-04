package zan.api.privat24.builder;

import junit.framework.TestCase;
import org.junit.Test;

import java.security.NoSuchAlgorithmException;

public class SignedRequestBuilderTest extends TestCase {

    @Test
    public void testGetSignature() throws NoSuchAlgorithmException {

        String result = new SignedRequestBuilder()
                .withId("id")
                .withPassword("")
                .withData("1")
                .withRequestTemplate("[id] [data] [signature]")
                .build();

        assertEquals("id 1 0937afa17f4dc08f3c0e5dc908158370ce64df86", result);
    }

    @Test
    public void testMD5() throws NoSuchAlgorithmException {
        assertEquals("c4ca4238a0b923820dcc509a6f75849b", SignedRequestBuilder.md5("1"));
    }

    @Test
    public void testSHA1() throws NoSuchAlgorithmException {
        assertEquals("356a192b7913b04c54574d18c28d46e6395428ab", SignedRequestBuilder.sha1("1"));
    }

    @Test
    public void testMD5andSHA1() throws NoSuchAlgorithmException {
        assertEquals("0937afa17f4dc08f3c0e5dc908158370ce64df86", SignedRequestBuilder.sha1(SignedRequestBuilder.md5("1")));
    }
}