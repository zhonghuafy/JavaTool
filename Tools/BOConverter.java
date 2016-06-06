package com.csc.mail.jsh.util.sys;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/****************
 * byte array and object converter
 * @author Joshua
 *
 */
public class BOConverter {

	public static byte[] toByte(Object object) throws IOException {
		ByteArrayOutputStream bos = new ByteArrayOutputStream();
		ObjectOutputStream oos = new ObjectOutputStream(bos);
		oos.writeObject(object);
		oos.flush();
		oos.close();
		bos.close();
		return bos.toByteArray();
	}
	
	public static Object toObject(byte[] bytes) throws IOException, ClassNotFoundException {
		ByteArrayInputStream bis = new ByteArrayInputStream(bytes);
		ObjectInputStream ois = new ObjectInputStream(bis);
		return ois.readObject();
	}
	
//	public static void main(String[] args){
//		try {
//			String ss = "测试测试boconverter!^-^";
//			byte[] a = toByte(ss);
//			System.out.println(toObject(a));
//		} catch (Exception e) {
//			// TODO: handle exception
//			e.printStackTrace();
//		}
//	}
}
