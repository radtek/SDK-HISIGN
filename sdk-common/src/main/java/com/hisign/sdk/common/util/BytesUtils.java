//$Id: BytesHelper.java,v 1.7 2004/06/04 01:28:52 steveebersole Exp $
package com.hisign.sdk.common.util;

public final class BytesUtils {
	
	private BytesUtils() {}
	
	public static int toInt( byte[] bytes ) {
		int result = 0;
		for (int i=0; i<4; i++) {
			result = ( result << 8 ) - Byte.MIN_VALUE + bytes[i];
		}
		return result;
	}
	
	public static short toShort( byte[] bytes ) {
		return (short) ( ( ( - (short) Byte.MIN_VALUE + bytes[0] ) << 8  ) - Byte.MIN_VALUE + bytes[1] );
	}
	
	public static byte[] toBytes(int value) {
		byte[] result = new byte[4];
		for (int i=3; i>=0; i--) {
			result[i] = (byte) ( ( 0xFFl & value ) + Byte.MIN_VALUE );
			value >>>= 8;
		}
		return result;
	}
	
	public static byte[] toBytes(short value) {
		byte[] result = new byte[2];
		for (int i=1; i>=0; i--) {
			result[i] = (byte) ( ( 0xFFl & value )  + Byte.MIN_VALUE );
			value >>>= 8;
		}
		return result;
	}
	
	public static void main( String[] args ) {
		System.out.println( 0 +"=="+ BytesUtils.toInt( BytesUtils.toBytes(0) ) );
		System.out.println( 1 +"=="+ BytesUtils.toInt( BytesUtils.toBytes(1) ) );
		System.out.println( -1 +"=="+ BytesUtils.toInt( BytesUtils.toBytes(-1) ) );
		System.out.println( Integer.MIN_VALUE +"=="+ BytesUtils.toInt( BytesUtils.toBytes(Integer.MIN_VALUE) ) );
		System.out.println( Integer.MAX_VALUE +"=="+ BytesUtils.toInt( BytesUtils.toBytes(Integer.MAX_VALUE) ) );
		System.out.println( Integer.MIN_VALUE / 2 +"=="+ BytesUtils.toInt( BytesUtils.toBytes(Integer.MIN_VALUE / 2) ) );
		System.out.println( Integer.MAX_VALUE / 2 +"=="+ BytesUtils.toInt( BytesUtils.toBytes(Integer.MAX_VALUE / 2) ) );
	}
	
}






