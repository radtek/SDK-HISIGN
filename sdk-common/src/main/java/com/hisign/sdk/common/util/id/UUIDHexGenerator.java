//$Id: UUIDHexGenerator.java,v 1.11 2004/08/12 07:18:53 oneovthafew Exp $
package com.hisign.sdk.common.util.id;

import java.io.Serializable;

/**
 * <b>uuid.hex</b><br>
 * <br>
 * A <tt>UUIDGenerator</tt> that returns a string of length 32,
 * This string will consist of only hex digits. Optionally, 
 * the string may be generated with separators between each 
 * component of the UUID.
 *
 * @see UUIDStringGenerator
 * @author Gavin King
 */

public class UUIDHexGenerator extends UUIDGenerator{
	
	private String sep = "";
	
	protected String format(int intval) {
		String formatted = Integer.toHexString(intval);
		StringBuffer buf = new StringBuffer("00000000");
		buf.replace( 8-formatted.length(), 8, formatted );
		return buf.toString();
	}
	
	protected String format(short shortval) {
		String formatted = Integer.toHexString(shortval);
		StringBuffer buf = new StringBuffer("0000");
		buf.replace( 4-formatted.length(), 4, formatted );
		return buf.toString();
	}
	
	@Override
	public Serializable generate() {
		return new StringBuffer(36)
		.append( format( getIP() ) ).append(sep)
		.append( format( getJVM() ) ).append(sep)
		.append( format( getHiTime() ) ).append(sep)
		.append( format( getLoTime() ) ).append(sep)
		.append( format( getCount() ) )
		.toString();
	}
	
	public static void main( String[] args ) throws Exception {
		IdentifierGenerator gen = new UUIDHexGenerator();
		System.out.println("uuid="+gen.generate().toString());
	}
	
}






