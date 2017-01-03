// Util.java
package com.hisign.sdk.common.util.security;

import java.io.*;

public class Util {
    // 把文件读入byte数组
    public static String KEYGENERATOR_ALGORITHM = "Blowfish";
    public static String CIPHER_ALGORITHM = "Blowfish/ECB/PKCS5Padding";
    public static String KEY_FILE_NAME = "key.data";
    
    static public byte[] readFile( String fileName ) throws IOException {        
        
        File file = new File( fileName );
        long len = file.length();
        byte data[] = new byte[(int)len];
        BufferedInputStream fin = new BufferedInputStream(
        new FileInputStream(file));
        int r = fin.read( data );
        if (r != len) {
            throw new IOException( "Only read "+ r +" of " + len + " for " + file );
        }
        fin.close();
        return data;
    }
    
    static public Object readObject( String fileName ) throws Exception {        
        
        File file = new File( fileName );       
        ObjectInputStream fin = new ObjectInputStream(
        new FileInputStream(file));
        Object data =  fin.readObject();        
        fin.close();
        return data;
    }
    
    // 把byte数组写出到文件
    static public void writeFile( String fileName, byte data[] )
    throws IOException {
        
        BufferedOutputStream fout = new BufferedOutputStream(
        new FileOutputStream(fileName));
        fout.write( data );
        fout.flush();
        fout.close();
    }
    
    static public void writeObject( String fileName, Object object )
    throws Exception {
        
        ObjectOutputStream fout = new ObjectOutputStream(
        new FileOutputStream(fileName));
        fout.writeObject( object);
        fout.flush();
        fout.close();
    }
}



