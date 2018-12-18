package top.ccxh.reptile.weather.common;

import org.apache.commons.codec.binary.Hex;
import org.junit.Test;
import top.ccxh.BaseTest;

import java.security.Key;

public class JdkEncryptUtilsTest extends BaseTest {

    @Test
    public void base64() {
        String now="ccxh";
        String baseEncode = JdkEncryptUtils.encoderBase64(now);
        System.out.println(baseEncode);
        String baseDecode = JdkEncryptUtils.decoderBase64ToString(baseEncode);
        System.out.println(baseDecode);
    }

    @Test
    public void digest() {
        String now="ccxh";
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.MD5, now));
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.SHA, now));
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.SHA_256, now));
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.MD5, now));
        System.out.println(JdkEncryptUtils.enCoderHmacMd5(now));
    }

    @Test
    public void encrypt() {
        JdkEncryptUtils.EncryptMode tt = JdkEncryptUtils.EncryptMode.DES_EDE;
        byte[] bytes = JdkEncryptUtils.generatorKey(tt);
        Key key = JdkEncryptUtils.switchKey(tt, bytes);
        byte[] encoder = JdkEncryptUtils.encoder(tt, key, "ccxh".getBytes());
        System.out.println(Hex.encodeHexString(encoder));
        byte[] decoder = JdkEncryptUtils.decoder(tt, key, encoder);
        System.out.println(new String(decoder));
    }

    @Test
    public void PBETest() {
        String password = "12456789";
        String now = "ccxh";
        Key pbeKey = JdkEncryptUtils.getPBEKey(password);
        byte[] salt = JdkEncryptUtils.randomSalt();
        byte[] encoder = JdkEncryptUtils.encoderPBE(pbeKey, salt, 5, now.getBytes());
        System.out.println(Hex.encodeHexString(encoder));
        byte[] decoder = JdkEncryptUtils.decoderPBE(pbeKey, salt, 5, encoder);
        System.out.println(new String(decoder));
    }

}