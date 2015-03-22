package es.api;

import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;



public class Hmac {
	public static String calcular(String texto, String clave) {
		SecretKeySpec keySpec = new SecretKeySpec(clave.getBytes(), "HmacSHA1");
		StringBuffer retorno = new StringBuffer();
		Mac mac;
		try {
			mac = Mac.getInstance("HmacSHA1");

			mac.init(keySpec);
			byte[] result = mac.doFinal(texto.getBytes());
			
//			Java 1.3.1
//			for (int i = 0; i < result.length; i++) {
//				String hexString = Integer.toHexString(result[i]);
//				if (hexString.length() < 2) {
//				    hexString = "0" + hexString;
//				}
//				if (hexString.length() > 2) {
//					hexString = hexString.substring(hexString.length() - 2, hexString.length());
//				}
//				retorno.append(hexString);
//			}
			for (byte b : result) {
				retorno.append(String.format("%02x", b));
			}
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		} catch (InvalidKeyException e) {
			e.printStackTrace();
		}
		return retorno.toString();
	}
}
