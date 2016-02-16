package ru.service.gallery.util;

import org.springframework.security.authentication.encoding.ShaPasswordEncoder;

public class Util {

	public static String encodePassword(String source){
		ShaPasswordEncoder encoder = new ShaPasswordEncoder(256);
		return encoder.encodePassword(source, null);
	}
}
