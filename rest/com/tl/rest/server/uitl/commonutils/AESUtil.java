package com.tl.rest.server.uitl.commonutils;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import org.bouncycastle.jce.provider.BouncyCastleProvider;

import Decoder.BASE64Decoder;
import Decoder.BASE64Encoder;
/**
 * AES对称加密
 * @author yangcheng
 * @date 2017年12月12日 
 * @version V1.0
 */
public class AESUtil {
	/**
	 * 
	 * @Title: Encrypt 
	 * @author yangcheng
	 * @param msg 需要加密的东西
	 * @param sKey
	 * @return
	 * @throws Exception
	 */
	private static final String IV_KEY="b7r8i8a9n067ql==";
    public static String Encrypt(String msg) throws Exception  {  
    	//这里是随机生成消息密钥  或者手动给定消息密钥也可以的——密钥字符串长度必须为16
    	byte[] password =getKey();//new byte[16]; 
        SecretKeySpec skeySpec = new SecretKeySpec(password, "AES");  
        Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");//"算法/模式/补码方式"  
        IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());//使用CBC模式，需要一个向量iv，可增加加密算法的强度  
        cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);  
        byte[] encrypted = cipher.doFinal(msg.getBytes());  
        BASE64Encoder encoder=new BASE64Encoder();
        System.out.println("随机密钥："+encoder.encode(password));
        return encoder.encode(password).concat(encoder.encode(encrypted));//此处使用BASE64做转码功能，同时能起到2次加密的作用。  
    }  
    /**
     * 解密
     * @Title: Decrypt 
     * @author yangcheng
     * @param sMsg 加密后的密文
     * @return 
     * @throws IOException 
     * @throws Exception
     */
    public static String Decrypt(String sMsg) throws Exception {  
        	BASE64Decoder decoder = new BASE64Decoder(); 
        	//截取出密钥（消息密钥）
    		String passwordString = sMsg.substring(0, 24); 
    		//截取出被加密的内容
    		String cipherString = sMsg.substring(24, sMsg.length()); 
    		
    		byte[] password = decoder.decodeBuffer(passwordString);
    		//当为字符串时使用下面方法转成byte数组
            SecretKeySpec skeySpec = new SecretKeySpec(password, "AES");  
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");  
            IvParameterSpec iv = new IvParameterSpec(IV_KEY.getBytes());  
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);  
            byte[] encrypted1 = new BASE64Decoder().decodeBuffer(cipherString);//先用base64解密  
            try {  
                byte[] original = cipher.doFinal(encrypted1);  
                String originalString = new String(original);  
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;  
            }
    }
    /**
     * 获取随机密钥
     * @Title: getKey 
     * @author yangcheng
     * @return
     * @throws NoSuchAlgorithmException
     */
    public static byte[] getKey() throws NoSuchAlgorithmException{
        Security.addProvider(new BouncyCastleProvider());//加入BCProvider
        KeyGenerator keyGenerator = KeyGenerator.getInstance("AES");
        keyGenerator.init(128);//初始化密钥长度,128,192,256（选用192和256的时候需要配置无政策限制权限文件--JDK6）
        SecretKey key =keyGenerator.generateKey();//产生密钥
        System.out.println("key长度："+key.getEncoded().length);
        return key.getEncoded();
    }
}
