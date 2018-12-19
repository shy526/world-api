package top.ccxh.reptile.weather.common;


import javax.crypto.Cipher;
import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密帮助类
 *
 * @author ccxh
 */
public class JdkDhUtil {
    /**
     * 算法名
     */
    private static final String ENCRYPT_DH ="DH";
    private static final String ENCRYPT_AES ="AES";
    public static void main(String[] args) {
        try {
            // 发送方密钥
            KeyPair senderKeyPair = initKey();

            KeyPair keyPair = generateReceiverKey(senderKeyPair.getPublic().getEncoded());
            SecretKey secretKey = localKey(senderKeyPair.getPublic().getEncoded(), keyPair.getPrivate().getEncoded());
            PublicKey initKey = restorePublicKey(keyPair.getPublic().getEncoded());
            PrivateKey privateKey = restorePrivateKey(senderKeyPair.getPrivate().getEncoded());
            SecretKey secretKey2 = localKey(initKey.getEncoded(), privateKey.getEncoded());
            if (secretKey.equals(secretKey2)){
                System.out.println("secretKey2 = " + secretKey2);
            }
            byte[] bytes = enCoder(secretKey, "ccxh".getBytes());
            byte[] bytes1 = deCoder(secretKey2, bytes);
            System.out.println(new java.lang.String(bytes1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 初始化key
     * @return KeyPair
     * @throws Exception
     */
    private static KeyPair initKey() throws Exception {
        KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance(ENCRYPT_DH);
        senderKeyPairGenerator.initialize(512);
        return senderKeyPairGenerator.generateKeyPair();
    }

    /**
     * 生成接收者的key
     * @param initPublicKey
     * @return
     */
    public static KeyPair generateReceiverKey(byte[] initPublicKey) throws Exception {
        PublicKey publicKey = restorePublicKey(initPublicKey);
        DHParameterSpec params = ((DHPublicKey) publicKey).getParams();
        KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance(ENCRYPT_DH);
        receiverKeyPairGenerator.initialize(params);
        return receiverKeyPairGenerator.generateKeyPair();
    }

    /**
     * 生成加/解 密钥
     * @param publicKey
     * @param privateKey
     * @return
     * @throws Exception
     */
    private static SecretKey localKey(byte[] publicKey, byte[] privateKey) throws Exception {
        KeyAgreement keyAgreement = KeyAgreement.getInstance(ENCRYPT_DH);
        keyAgreement.init(restorePrivateKey(privateKey));
        keyAgreement.doPhase(restorePublicKey(publicKey), true);
        return keyAgreement.generateSecret(ENCRYPT_AES);
    }


    /**
     * 还原公钥
     * @return PublicKey
     */
    public  static PublicKey restorePublicKey(byte[] publicKey) throws Exception {
        KeyFactory keyFactory =KeyFactory.getInstance(ENCRYPT_DH);
        X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(publicKey);
        return keyFactory.generatePublic(x509EncodedKeySpec2);
    }

    /**
     * 还原私钥
     * @param privateKey 初始化密钥
     * @return
     * @throws Exception
     */
    public static PrivateKey restorePrivateKey(byte[] privateKey)  throws Exception{
        KeyFactory keyFactory =KeyFactory.getInstance(ENCRYPT_DH);
        PKCS8EncodedKeySpec pkcs8EncodedKeySpec = new PKCS8EncodedKeySpec(privateKey);
        return keyFactory.generatePrivate(pkcs8EncodedKeySpec);
    }

    private static byte[] coder(SecretKey key,byte[] data,int mode) throws Exception {
        Cipher cipher=Cipher.getInstance(ENCRYPT_AES);
        cipher.init(mode,key);
        return cipher.doFinal(data);
    }

    private static byte[] enCoder(SecretKey key,byte[] data) throws Exception {
       return   coder(key,data,Cipher.ENCRYPT_MODE);
    }

    private static byte[] deCoder(SecretKey key,byte[] data) throws Exception {
        return   coder(key,data,Cipher.DECRYPT_MODE);
    }
}
