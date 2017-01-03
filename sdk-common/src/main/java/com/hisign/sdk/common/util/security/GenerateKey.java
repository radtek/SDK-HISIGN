// GenerateKey.java
package com.hisign.sdk.common.util.security;

import java.security.SecureRandom;

import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
/**
 * 生成一个安全密匙。在命令行，利用GenerateKey工具（参见GenerateKey.java）
 * 把密匙写入一个文件： % java GenerateKey key.data
 */
public class GenerateKey {
    static public void main( String args[] ) throws Exception {
       
        // 生成密匙
        SecureRandom sr = SecureRandom.getInstance("SHA1PRNG");
        KeyGenerator kg = KeyGenerator.getInstance(Util.KEYGENERATOR_ALGORITHM);
        kg.init(sr);
        SecretKey key = kg.generateKey();
        // 把密匙数据保存到文件
        Util.writeObject(Util.KEY_FILE_NAME, key);
    }
}

