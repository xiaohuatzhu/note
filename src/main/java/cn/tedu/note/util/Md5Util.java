package cn.tedu.note.util;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Md5Util {

	private static String salt;

	public static String saltString(String str) {
		// System.out.println(salt);
		return DigestUtils.md5Hex(salt + str);
	}

	public String getSalt() {
		return salt;
	}

	@SuppressWarnings("static-access")
	@Value("#{const.salt}")
	public void setSalt(String salt) {
		this.salt = salt;
	}

}
