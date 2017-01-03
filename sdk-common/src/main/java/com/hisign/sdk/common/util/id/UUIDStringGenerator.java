//$Id: UUIDStringGenerator.java,v 1.10 2004/08/12 07:18:53 oneovthafew Exp $
package com.hisign.sdk.common.util.id;

import java.io.Serializable;

import com.hisign.sdk.common.util.BytesUtils;

/**
 * <b>uuid.string</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 16,
 * This string will NOT consist of only alphanumeric
 * characters. Use this only if you don't mind unreadable
 * identifiers.<br>
 * <br>
 * This implementation is known to be incompatible with
 * Postgres.
 *
 * @see UUIDHexGenerator
 * @author Gavin King
 */

public class UUIDStringGenerator extends UUIDGenerator{
	
	private String sep = "";
	
	@Override
	public Serializable generate() {
		return new StringBuffer(20)
		.append( toString( getIP() ) ).append(sep)
		.append( toString( getJVM() ) ).append(sep)
		.append( toString( getHiTime() ) ).append(sep)
		.append( toString( getLoTime() ) ).append(sep)
		.append( toString( getCount() ) )
		.toString();
	}
	
	public static void main( String[] args ) throws Exception {
		IdentifierGenerator gen = new UUIDStringGenerator();
		for ( int i=0; i<5; i++) {
			String id = (String) gen.generate();
			System.out.println( id + ": " +  id.length() );
		}
	}
	
	private static String toString(int value) {
		return new String ( BytesUtils.toBytes(value) );
	}
	
	private static String toString(short value) {
		return new String ( BytesUtils.toBytes(value) );
	}
}






