package top.ccxh.reptile.weather.common;


import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * DH帮助类
 * 秘钥分配
 * 在Java 8 update 161版本以后会出现 Unsupported secret key algorithm: AES
 * 添加 jvm 启动参数 -Djdk.crypto.KeyAgreement.legacyKDF=true
 *
 * @author ccxh
 */
public class JdkDhUtil {
    /**
     * 算法名
     */
    private static final String ENCRYPT_DH = "DH";
    private static final String ENCRYPT_AES = "AES";


    /**
     * 初始化key
     *
     * @return KeyPair
     */
    private static KeyPair initKey() {
        try {
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance(ENCRYPT_DH);
            senderKeyPairGenerator.initialize(512);
            return senderKeyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成接收者的key
     *
     * @param initPublicKey
     * @return KeyPair
     */
    public static KeyPair generateReceiverKey(byte[] initPublicKey) {
        try {
            PublicKey publicKey = restorePublicKey(initPublicKey);
            DHParameterSpec params = ((DHPublicKey) publicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance(ENCRYPT_DH);
            receiverKeyPairGenerator.initialize(params);
            return receiverKeyPairGenerator.generateKeyPair();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成加/解  AES密钥
     *
     * @param publicKey  公钥
     * @param privateKey 私钥
     * @return SecretKey
     */
    public static SecretKey localKey(byte[] publicKey, byte[] privateKey) {
        try {
            KeyAgreement keyAgreement = KeyAgreement.getInstance(ENCRYPT_DH);
            keyAgreement.init(restorePrivateKey(privateKey));
            keyAgreement.doPhase(restorePublicKey(publicKey), true);
            return keyAgreement.generateSecret(ENCRYPT_AES);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }


    /**
     * 还原公钥
     *
     * @param publicKey 公钥 byte[]
     * @return PublicKey
     */
    public static PublicKey restorePublicKey(byte[] publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_DH);
            X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(publicKey);
            return keyFactory.generatePublic(x509EncodedKeySpec2);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 还原私钥
     *
     * @param privateKey 私钥 byte[]
     * @return PrivateKey
     */
    public static PrivateKey restorePrivateKey(byte[] privateKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance(ENCRYPT_DH);
            PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
            return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 加/解密
     *
     * @param key  key
     * @param data 数据
     * @param mode 加解密 模式选择
     * @return byte[]
     */
    private static byte[] coder(SecretKey key, byte[] data, int mode) {
        try {
            Cipher cipher = Cipher.getInstance(ENCRYPT_AES);
            cipher.init(mode, key);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;

    }

    /**
     * 加密
     *
     * @param key  key
     * @param data 数据
     * @return byte[]
     */
    public static byte[] enCoder(SecretKey key, byte[] data) {
        return coder(key, data, Cipher.ENCRYPT_MODE);
    }

    /**
     * 解密
     *
     * @param key  key
     * @param data 数据
     * @return byte[]
     */
    public static byte[] deCoder(SecretKey key, byte[] data) {
        return coder(key, data, Cipher.DECRYPT_MODE);
    }

    public void example() {
        //1.初始化秘钥
        KeyPair senderKeyPair = initKey();
        //2. 生成公钥和私钥
        KeyPair keyPair = generateReceiverKey(senderKeyPair.getPublic().getEncoded());
        //3. 生成 本地key
        SecretKey secretKey = localKey(senderKeyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded());
        //4. 生成key
        SecretKey secretKey2 = localKey(keyPair.getPublic().getEncoded(), senderKeyPair.getPrivate().getEncoded());
        if (secretKey.equals(secretKey2)) {
            System.out.println("secretKey2 = " + secretKey2);
        }
        byte[] bytes = enCoder(secretKey, "ccxh".getBytes());
        byte[] bytes1 = deCoder(secretKey2, bytes);
        System.out.println(new java.lang.String(bytes1));
    }
}
