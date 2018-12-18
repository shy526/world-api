package top.ccxh.reptile.weather.timed;

import com.alibaba.druid.sql.visitor.functions.Hex;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import top.ccxh.BaseTest;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.DESedeKeySpec;
import javax.crypto.spec.SecretKeySpec;


import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.Security;

import static org.junit.Assert.*;

public class CatchWeatherInfoTest extends BaseTest {
    @Autowired
    CatchWeatherInfo catchWeatherInfo;
    @Test
    public void run() {
        catchWeatherInfo.run();
    }

    @Test
    public void jdkDesc(){
        //98年后没有可用性而言
        try {
            //生成key 默认56
            byte[] encoded = generatorKey("DES");

            //转换key
            DESKeySpec descKey=new DESKeySpec(encoded);
            SecretKeyFactory factory=SecretKeyFactory.getInstance("DES");
            Key key= factory.generateSecret(descKey);
            //加密类型/工作类型/填充方式
            Cipher cipher=Cipher.getInstance("DES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] bytes = cipher.doFinal("ccxh".getBytes());
            String result = new String(bytes, "utf-8");
            System.out.println(result);

            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] de = cipher.doFinal(bytes);
            System.out.println(new String(de,"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private byte[] generatorKey(String mode) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator=KeyGenerator.getInstance(mode);
        keyGenerator.init( new SecureRandom());
        SecretKey secretKey = keyGenerator.generateKey();
        return secretKey.getEncoded();
    }

    @Test
    public void jdk3Desc(){
        //3desc 秘钥强度增强
        try {
            //生成key
            KeyGenerator keyGenerator=KeyGenerator.getInstance("DESede");
            //默认168
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();

            //转换key
            DESedeKeySpec descKey=new DESedeKeySpec(encoded);
            SecretKeyFactory factory=SecretKeyFactory.getInstance("DESede");
            Key key= factory.generateSecret(descKey);
            //加密类型/工作类型/填充方式
            Cipher cipher=Cipher.getInstance("DESede/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] bytes = cipher.doFinal("ccxh".getBytes());
            String result = new String(bytes, "utf-8");
            System.out.println(result);

            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] de = cipher.doFinal(bytes);
            System.out.println(new String(de,"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Test
    public void aes(){
        //主流
        //默认128 位 ,256 需要改文件
        try {
            //生成key
            KeyGenerator keyGenerator=KeyGenerator.getInstance("AES");
            //默认168
            keyGenerator.init(new SecureRandom());
            SecretKey secretKey = keyGenerator.generateKey();
            byte[] encoded = secretKey.getEncoded();

            //转换key
            Key key=new SecretKeySpec(encoded,"AES");
            //加密类型/工作类型/填充方式
            Cipher cipher=Cipher.getInstance("AES/ECB/PKCS5Padding");
            cipher.init(Cipher.ENCRYPT_MODE,key);
            byte[] bytes = cipher.doFinal("ccxh".getBytes());
            String result = new String(bytes, "utf-8");
            System.out.println(result);

            cipher.init(Cipher.DECRYPT_MODE,key);
            byte[] de = cipher.doFinal(bytes);
            System.out.println(new String(de,"utf-8"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}