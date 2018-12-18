package top.ccxh.reptile.weather.common;

import javax.crypto.Cipher;
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
     * @param
     */
    public static  KeyPair  generatorKey(byte[] publicKeyBytes) {
        try {
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(publicKeyBytes);
            PublicKey publicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec params = ((DHPublicKey) publicKey).getParams();
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance("DH");
            keyPairGenerator.initialize(params);
            KeyPair keyPair = keyPairGenerator.generateKeyPair();
            return keyPair;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static void main(String[] args) {
        try {
            // 发送方密钥
            KeyPairGenerator senderKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            senderKeyPairGenerator.initialize(512);
            KeyPair senderKeyPair = senderKeyPairGenerator.generateKeyPair();
            byte[] senderPublicKeyEnc = senderKeyPair.getPublic().getEncoded();
            /**
             * 构建密钥
             */
            KeyFactory keyFactory = KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec = new X509EncodedKeySpec(senderPublicKeyEnc);
            PublicKey receiverPublicKey = keyFactory.generatePublic(x509EncodedKeySpec);
            DHParameterSpec params = ((DHPublicKey) receiverPublicKey).getParams();
            KeyPairGenerator receiverKeyPairGenerator = KeyPairGenerator.getInstance("DH");
            receiverKeyPairGenerator.initialize(params);
            KeyPair receiverKeyPair = receiverKeyPairGenerator.generateKeyPair();

            /**
             * 生成本地密钥
             */
            KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
            keyAgreement.init(receiverKeyPair.getPrivate());
            keyAgreement.doPhase(receiverPublicKey, true);
            SecretKey secretKey = keyAgreement.generateSecret("DES");

            KeyFactory senderKeyFactory=KeyFactory.getInstance("DH");
            X509EncodedKeySpec x509EncodedKeySpec2 = new X509EncodedKeySpec(receiverKeyPair.getPublic().getEncoded());
            PublicKey publicKey = senderKeyFactory.generatePublic(x509EncodedKeySpec2);
            KeyAgreement keyAgreement2  = KeyAgreement.getInstance("DH");
            keyAgreement2.init(senderKeyPair.getPrivate());
            keyAgreement2.doPhase(publicKey,true);
            SecretKey des = keyAgreement2.generateSecret("DES");
            if (des.equals(secretKey)){
                System.out.println("des = " + des);
            }
            Cipher cipher=Cipher.getInstance("DES");
            cipher.init(Cipher.ENCRYPT_MODE,secretKey);
            byte[] bytes = cipher.doFinal("ccxh".getBytes());
            cipher.init(Cipher.DECRYPT_MODE,des);
            byte[] bytes1 = cipher.doFinal(bytes);
            System.out.println(new String(bytes1));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static byte[] local(RSAKey rsaKey){
  /*      KeyAgreement keyAgreement = KeyAgreement.getInstance("DH");
        keyAgreement.init();
        keyAgreement.doPhase(myPublicKey, true);
        SecretKey secretKey = keyAgreement.generateSecret("AES");*/
        return null;
    }

    public static class RSAKey{
        private byte[] privateKey;
        private byte[] publicvateKey;

        public RSAKey(KeyPair keyPair) {
            this.privateKey = keyPair.getPrivate().getEncoded();
            this.publicvateKey = keyPair.getPublic().getEncoded();
        }

        public byte[] getPrivateKey() {
            return privateKey;
        }

        public void setPrivateKey(byte[] privateKey) {
            this.privateKey = privateKey;
        }

        public byte[] getPublicvateKey() {
            return publicvateKey;
        }

        public void setPublicvateKey(byte[] publicvateKey) {
            this.publicvateKey = publicvateKey;
        }
    }
}
