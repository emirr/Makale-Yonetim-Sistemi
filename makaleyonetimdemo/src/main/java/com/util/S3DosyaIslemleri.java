package com.util;

import java.io.File;
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
			System.out.println("in null çýktý");
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
				//gerçekte burada s3 file gönderme kodu olacak
				System.out.println("S3'e dosya yükleniyor");
			} catch (IOException e) {
				System.out.println(e.getMessage());
			}
		}
		
	}

	public static void s3DosyaSilme(String makalePath) {
		fileName = folderPath + makalePath;
		System.out.println("S3'den dosya siliniyor");

	}
}
