// EncryptClasses.java
package com.hisign.sdk.common.util.security;

import java.security.SecureRandom;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;

public class Crypto {
	private static SecretKey key;
	private static Cipher cipher;

	static public void encrypt(String fileName, String encryptedFileName) throws Exception {

		// 生成密匙
		SecureRandom sr = new SecureRandom();
		SecretKey key = (SecretKey) (Util.readObject(Util.KEY_FILE_NAME));

		// 创建用于实际加密操作的Cipher对象
		Cipher ecipher = Cipher.getInstance(Util.CIPHER_ALGORITHM);
		ecipher.init(Cipher.ENCRYPT_MODE, key, sr);

		// 加密文件

		// 读入文件
		byte data[] = Util.readFile(fileName);

		// 加密
		byte encryptedData[] = ecipher.doFinal(data);

		// 保存加密后的内容
		Util.writeFile(encryptedFileName, encryptedData);

		System.out.println("Encrypted " + fileName);

	}

	/** decrypt file */
	static public void decrypt(String fileName, String decryptedFileName) throws Exception {

		// 读取密匙
		System.err.println("Decrypt Start: reading key");
		SecretKey key = (SecretKey) (Util.readObject(Util.KEY_FILE_NAME));

		// 解密
		SecureRandom sr = new SecureRandom();
		cipher = Cipher.getInstance(Util.CIPHER_ALGORITHM);
		cipher.init(Cipher.DECRYPT_MODE, key, sr);

		// 读入文件
		byte data[] = Util.readFile(fileName);

		// 加密
		byte decryptedData[] = cipher.doFinal(data);

		// 保存加密后的内容
		Util.writeFile(decryptedFileName, decryptedData);

		System.out.println("Decrypted " + fileName);

	}

	public static void main(String[] args) {
		try {
			System.out.println("Usage: java km.security.Crypto En| De fileName processedName");
			if (args.length == 3) {
				if (args[0].equalsIgnoreCase("En")) {
					Crypto.encrypt(args[1], args[2]);
				} else if (args[0].equalsIgnoreCase("De")) {
					Crypto.decrypt(args[1], args[2]);
				}
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}

}