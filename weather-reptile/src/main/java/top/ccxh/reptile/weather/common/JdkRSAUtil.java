package top.ccxh.reptile.weather.common;

import javax.crypto.KeyAgreement;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHPublicKey;
import javax.crypto.spec.DHParameterSpec;
import java.security.*;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.X509EncodedKeySpec;

/**
 * 非对称加密帮助类
 *
 * @author ccxh
 */
public class JdkRSAUtil {
    /**
     * 初始化key
     *
     * @return byte[]
     */
    public static KeyPair initKey(AlgorithmParameterSpec algorithmParameterSpec) {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            if (algorithmParameterSpec == null) {
                keyPairGenerator.initialize(512);
            } else {
                keyPairGenerator.initialize(512);
            }
            return keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static byte[] initKey() {
        return initKey(null).getPublic().getEncoded();
    }

    /**
     * 根据生成本地秘钥秘钥
     *
     * @param publicKey
     */
    public static  SecretKey  generatorKey(byte[] publicKey) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKey);
            DHPublicKey myPublicKey = (DHPublicKey) keyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec params = myPublicKey.getParams();
            KeyPair keyPair = initKey(params);
            PrivateKey privateKey = keyPair.getPrivate();
            return
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        //发送生成秘钥
        byte[] send = initKey();
        //接收方key
        SecretKey receiverKey = generatorKey(send);
        generatorKey(receiverKey.ge)
    }

    public static byte[] local(){
        KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init(privateKey);
        keyAgreement.doPhase(myPublicKey, true);
        SecretKey secretKey = keyAgreement.generateSecret("AES");
    }

    public class RSAKey{
        private byte[]
    }
}
