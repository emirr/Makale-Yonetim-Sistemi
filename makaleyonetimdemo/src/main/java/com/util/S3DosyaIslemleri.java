package com.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class S3DosyaIslemleri {
	private static String folderPath = "A:\\makaleStore";
	private static String silinecekPath;
	private static String fileName;

	public static void s3DosyaYukleme(String makalePath, InputStream in) {
		fileName = folderPath + makalePath;
		if(in == null){
			System.out.println("in null çıktı");
		}
		else{
			try {

				// write the inputStream to a FileOutputStream
				OutputStream out = new FileOutputStream(new File(fileName));

				int read = 0;
				byte[] bytes = new byte[10485760];// 10 mb

				while ((read = in.read(bytes)) != -1) {
					out.write(bytes, 0, read);
				}

				in.close();
				out.flush();
				out.close();
				//ger�ekte burada s3 file g�nderme kodu olacak
				System.out.println("S3'e dosya y�kleniyor");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}

	public static void s3DosyaSilme(String makalePath) {
		fileName = folderPath + makalePath;
		System.out.println("S3'den dosya siliniyor");

	}
	
	public static FileInputStream s3DosyaOkuma(String makalePath){
		fileName = folderPath + makalePath;
		File file = new File(fileName);
		FileInputStream fis = null;
		try {
			fis = new FileInputStream(file);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		 return fis;

	}
}
