package top.ccxh.reptile.weather.common;

import org.apache.commons.codec.binary.Hex;
import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;
import javax.crypto.*;
import javax.crypto.spec.*;
import java.io.IOException;
import java.security.Key;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 *加密算法
 * @author ccxh
 */
public class JdkEncryptUtils {
    private final static   String  PLUS_WOR_FILL="%s/ECB/PKCS5Padding";
    private final static BASE64Decoder BASE64_DECODER =new BASE64Decoder();
    private final static BASE64Encoder BASE64_ENCODER =new BASE64Encoder();

    /**
     * 生成key
     * @param mode
     * @return byte[]
     */
    public static byte[] generatorKey(EncryptMode mode)   {
        KeyGenerator keyGenerator= null;
        try {
            keyGenerator = KeyGenerator.getInstance(mode.getMode());
            keyGenerator.init( new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            return secretKey.getEncoded();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 转换为可用的key
     * @param mode
     * @param keyData
     * @return key
     */
    public static  Key  switchKey(EncryptMode mode,byte[] keyData)  {
        Key key=null;
        try {
            //转换key
            switch (mode){
                case AES:
                    key=new SecretKeySpec(keyData,mode.getMode());
                    break;
                case DES_EDE:
                    DESedeKeySpec deSedeKeySpec = new DESedeKeySpec(keyData);
                    SecretKeyFactory plant=SecretKeyFactory.getInstance(mode.getMode());
                     key= plant.generateSecret(deSedeKeySpec);
                    break;
                case DES:
                    DESKeySpec descKey=new DESKeySpec(keyData);
                    SecretKeyFactory factory=SecretKeyFactory.getInstance(mode.getMode());
                    key= factory.generateSecret(descKey);
                    break;
                default:
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return key;

    }

    /**
     * 转换秘钥
     * @param password  口令
     * @return key
     */
    public static Key  getPBEKey(String password){
        try {
            PBEKeySpec pbeKeySpec = new PBEKeySpec(password.toCharArray());
            SecretKeyFactory factory=SecretKeyFactory.getInstance(EncryptMode.PBE.getMode());
            return factory.generateSecret(pbeKeySpec);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 生成盐
     * @return  byte[]
     */
    public static byte[] randomSalt(){
       SecureRandom random=new SecureRandom();
       return random.generateSeed(8);
    }

    /**
     * pbe 加密
     * @param key 秘钥
     * @param salt 盐
     * @param saltNumber 迭代次数
     * @param data 处理数据
     * @return byte[]
     */
    public static byte[] encoderPBE(Key key,byte[] salt,int saltNumber,byte[] data){
        return doFinalPBE(key,salt,saltNumber,data,Cipher.ENCRYPT_MODE);
    }

    /**
     * pbe 解密
     * @param key 秘钥
     * @param salt 盐
     * @param saltNumber 迭代次数
     * @param data 处理数据
     * @return byte[]
     */
    public static byte[] decoderPBE(Key key,byte[] salt,int saltNumber,byte[] data){
       return doFinalPBE(key,salt,saltNumber,data,Cipher.DECRYPT_MODE);
    }

    /**
     * pbe
     * @param key 秘钥
     * @param salt 盐
     * @param saltNumber 迭代次数
     * @param data 处理数据
     * @param mode 加/解密模式
     * @return byte[]
     */
    public static byte[] doFinalPBE(Key key,byte[] salt,int saltNumber,byte[] data,int mode){
        try {
            PBEParameterSpec pbeParameterSpec = new PBEParameterSpec(salt, saltNumber);
            Cipher cipher=Cipher.getInstance(EncryptMode.PBE.getMode());
            cipher.init(mode,key,pbeParameterSpec);
            return cipher.doFinal(data);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 解密/假面
     * @param mode 解密模式
     * @param cipherMod 加密模式或者解密模式
     * @param key  秘钥
     * @param data 数据
     * @return
     */
    public  static byte[] doFinal(EncryptMode mode,int cipherMod,Key key,byte[] data){
        try {
            Cipher cipher = Cipher.getInstance(getEncryptWorFill(mode));
            cipher.init(cipherMod,key);
            byte[] result = cipher.doFinal(data);
            return  result;
        } catch (Exception e) {
            e.printStackTrace();
        }
       return null;
    }

    /**
     * 解密
     * @param mode 解密模式
     * @param key  秘钥
     * @param data 数据
     * @return
     */
    public static  byte[] decoder(EncryptMode mode,Key key,byte[] data){
        return doFinal(mode,Cipher.DECRYPT_MODE,key,data);
    }

    /**
     * 加密
     * @param mode 解密模式
     * @param key  秘钥
     * @param data 数据
     * @return  byte[]
     */
    public static  byte[] encoder(EncryptMode mode, Key key, byte[] data){
        return doFinal(mode,Cipher.ENCRYPT_MODE,key,data);
    }


    /**
     * 组装mode工作方式和填充
     * @param mode
     * @return String
     */
    private static String getEncryptWorFill(EncryptMode mode){
        return String.format(PLUS_WOR_FILL,mode.getMode());
    }

    /**
     * 加密类型
     */
    public enum EncryptMode{
        /**
         * aes
         */
        AES("AES"),
        /**
         * 三重des
         */
        DES_EDE("DESede"),
        /**
         * des
         */
        DES("DES"),
        /**
         * PBE
         */
        PBE("PBEWITHMD5andDES"),
        /**
         * 仅支持内部方法
         * HmacMD5
         */
        H_MAC_MD5("HmacMD5"),
        ;

        EncryptMode(String mode) {
            this.mode = mode;
        }

        private String mode;

        public String getMode() {
            return mode;
        }
    }

    /**
     * 解密数据
     * @param str
     * @return
     */
    public static  String encoderBase64(String str){
        return encoderBase64(str.getBytes());
    }

    /**
     * 加密
     * @param bytes 加密数据
     * @return String
     */
    public static String encoderBase64(byte[] bytes){
        return BASE64_ENCODER.encode(bytes);
    }

    /**
     * 界面
     * @param str 字符串
     * @return byte[]
     */
    public static  byte[] decoderBase64(String str){
        try {
            return BASE64_DECODER.decodeBuffer(str);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * base64解密
     * @param str 字符串
     * @param charsetName 编码方式
     * @return String
     */
    public static String decoderBase64ToString(String str,String charsetName){
        try {
            return new String(BASE64_DECODER.decodeBuffer(str),charsetName);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     *
     * @param str
     * @return String
     */
    public static String enCoderHmacMd5(String str){
        try {
            byte[] bytes = generatorKey(EncryptMode.H_MAC_MD5);
            SecretKeySpec secretKeySpec = new SecretKeySpec(bytes,EncryptMode.H_MAC_MD5.getMode());
            Mac mac = Mac.getInstance(secretKeySpec.getAlgorithm());
            mac.init(secretKeySpec);
            byte[] result = mac.doFinal(str.getBytes());
            return Hex.encodeHexString(result);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * base64解密
     * @param str 字符串
     * @return String utf8的字符串
     */
    public static String decoderBase64ToString(String str){
        return  decoderBase64ToString(str,"utf-8");
    }

    /**
     *摘要算法
     * @param digestMode 摘要
     * @param str  str
     * @return String
     */
    public static String encoderDigest(DigestMode digestMode,String str){
        try {
            MessageDigest messageDigest=MessageDigest.getInstance(digestMode.getMode());
            byte[] md5Bytes = messageDigest.digest(str.getBytes());
            return Hex.encodeHexString(md5Bytes);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public enum DigestMode{
        /**
         * sha
         */
        SHA("SHA"),
        /**
         * md5
         */
        MD5 ("MD5"),
        /**
         * sha_256
         */
        SHA_256("SHA-256");
        private String mode;
        DigestMode(String mode) {
            this.mode = mode;
        }

        public String getMode() {
            return mode;
        }

    }


    /**
     * base64 示例
     */
    public void base64() {
        String now="ccxh";
        String baseEncode = JdkEncryptUtils.encoderBase64(now);
        System.out.println(baseEncode);
        String baseDecode = JdkEncryptUtils.decoderBase64ToString(baseEncode);
        System.out.println(baseDecode);
    }

    /**
     * 常用摘要算法 示例
     */
    public void digest() {
        String now="ccxh";
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.MD5, now));
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.SHA, now));
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.SHA_256, now));
        System.out.println(JdkEncryptUtils.encoderDigest(JdkEncryptUtils.DigestMode.MD5, now));
        System.out.println(JdkEncryptUtils.enCoderHmacMd5(now));
    }

    /**
     * 常用对称算法 示例
     */
    public void encrypt() {
        JdkEncryptUtils.EncryptMode tt = JdkEncryptUtils.EncryptMode.DES_EDE;
        byte[] bytes = JdkEncryptUtils.generatorKey(tt);
        Key key = JdkEncryptUtils.switchKey(tt, bytes);
        byte[] encoder = JdkEncryptUtils.encoder(tt, key, "ccxh".getBytes());
        System.out.println(Hex.encodeHexString(encoder));
        byte[] decoder = JdkEncryptUtils.decoder(tt, key, encoder);
        System.out.println(new String(decoder));
    }


    /**
     * PBE对称算法 示例
     */
    public void pbe() {
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
