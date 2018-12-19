package top.ccxh.reptile.weather.common;

import org.apache.commons.codec.binary.Hex;

import javax.crypto.Cipher;
import java.io.ByteArrayOutputStream;
import java.io.UnsupportedEncodingException;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密 RSA帮助类
 *
 * @author ccxh
 */
public class RsaUtils {

    /** */
    /**
     * RSA最大加密明文大小
     */
    private static final int MAX_ENCODER = 117;

    /** */
    /**
     * RSA最大解密密文大小
     */
    private static final int MAX_DECODER = 128;
    /**
     * 算法名
     */
    private static final String ENCRYPT_RSA ="RSA";


    /**
     * 初始化秘钥
     *
     * @return
     */
    public KeyPair initKey() {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(ENCRYPT_RSA);
            keyPairGenerator.initialize(1024);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 私钥加密
     *
     * @param keyPair
     * @param data
     * @return
     */
    public byte[] privateEncoder(byte[] keyPair, byte[] data) {
        return privateCoder(keyPair, data, MAX_ENCODER, Cipher.ENCRYPT_MODE);
    }

    /**
     * 私钥解密
     *
     * @param keyPair
     * @param data
     * @return
     */
    public byte[] privateDecoder(byte[] keyPair, byte[] data) {
        return privateCoder(keyPair, data, MAX_DECODER, Cipher.DECRYPT_MODE);
    }

    /**
     * 私钥 加/解密
     *
     * @param keyPair 公钥
     * @param data    数据
     * @param max     分段加密 最大切割数
     * @param mode    加密/解密
     * @return byte[]
     */
    public byte[] privateCoder(byte[] keyPair, byte[] data, int max, int mode) {
        try {
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(keyPair);
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_RSA);
            PrivateKey privateKey = keyFactory.generatePrivate(pkcs8EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(mode, privateKey);
            return groupData(data, cipher, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥揭秘
     *
     * @param keyPair
     * @param data
     * @return
     */
    public byte[] publicDecoder(byte[] keyPair, byte[] data) {
        return publicCoder(keyPair, data, MAX_DECODER, Cipher.DECRYPT_MODE);
    }

    /**
     * 公钥 加/解密
     *
     * @param keyPair 公钥
     * @param data    数据
     * @param max     分段加密 最大切割数
     * @param mode    加密/解密
     * @return byte[]
     */
    public byte[] publicCoder(byte[] keyPair, byte[] data, int max, int mode) {
        try {
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(keyPair);
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_RSA);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            Cipher cipher = Cipher.getInstance(keyFactory.getAlgorithm());
            cipher.init(mode, publicKey);
            return groupData(data, cipher, max);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 公钥加密
     *
     * @param keyPair
     * @param data
     * @return
     */
    public byte[] publicEncoder(byte[] keyPair, byte[] data) {

        return publicCoder(keyPair, data, MAX_ENCODER, Cipher.ENCRYPT_MODE);

    }

    /**
     * 分组加/解密
     *
     * @param data   数据
     * @param cipher cipher
     * @param max    最大分组
     * @return byte[]
     * @throws Exception 错误
     */
    private byte[] groupData(byte[] data, Cipher cipher, int max) throws Exception {
        int inputLen = data.length;
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        int offSet = 0;
        byte[] cache;
        int i = 0;
        // 对数据分段解密
        while (inputLen - offSet > 0) {
            if (inputLen - offSet > max) {
                cache = cipher.doFinal(data, offSet, max);
            } else {
                cache = cipher.doFinal(data, offSet, inputLen - offSet);
            }
            out.write(cache, 0, cache.length);
            i++;
            offSet = i * max;
        }
        return out.toByteArray();
    }


    /**
     * 公钥加密=>私钥解密
     * 示例
     */
    public void publicEncoder2PrivatedeCoder(){
        System.out.println("公钥加密=>私钥解密");
        RsaUtils jdkRSAUtil = new RsaUtils();
        KeyPair keyPair = jdkRSAUtil.initKey();
        System.out.println(Hex.encodeHexString(keyPair.getPublic().getEncoded()));
        System.out.println(Hex.encodeHexString(keyPair.getPrivate().getEncoded()));
        String text="关关雎鸠，在河之洲。窈窕淑女，君子好逑。\n" +
                "参差荇菜，左右流之。窈窕淑女，寤寐求之。\n" +
                "求之不得，寤寐思服。悠哉悠哉，辗转反侧。\n" +
                "参差荇菜，左右采之。窈窕淑女，琴瑟友之。\n" +
                "参差荇菜，左右芼之。窈窕淑女，钟鼓乐之。 ";
        byte[] encoder = jdkRSAUtil.publicEncoder(keyPair.getPublic().getEncoded(), text.getBytes());
        System.out.println(Hex.encodeHexString(encoder));
        byte[] decoder = jdkRSAUtil.privateDecoder(keyPair.getPrivate().getEncoded(), encoder);
        System.out.println( new String(decoder));
    }

    /**
     * 私钥加密=>公钥解密
     * 示例
     */
    public  void privateEncoder2PublcideCoder(){
        System.out.println("私钥加密=>公钥解密");
        RsaUtils jdkRSAUtil = new RsaUtils();
        KeyPair keyPair = jdkRSAUtil.initKey();
        System.out.println(Hex.encodeHexString(keyPair.getPublic().getEncoded()));
        System.out.println(Hex.encodeHexString(keyPair.getPrivate().getEncoded()));
        String text="关关雎鸠，在河之洲。窈窕淑女，君子好逑。\n" +
                "参差荇菜，左右流之。窈窕淑女，寤寐求之。\n" +
                "求之不得，寤寐思服。悠哉悠哉，辗转反侧。\n" +
                "参差荇菜，左右采之。窈窕淑女，琴瑟友之。\n" +
                "参差荇菜，左右芼之。窈窕淑女，钟鼓乐之。 ";
        byte[] encode = jdkRSAUtil.privateEncoder(keyPair.getPrivate().getEncoded(), text.getBytes());
        System.out.println(Hex.encodeHexString(encode));
        byte[] decoder = jdkRSAUtil.publicDecoder(keyPair.getPublic().getEncoded(), encode);
        System.out.println( new String(decoder));
    }
}
