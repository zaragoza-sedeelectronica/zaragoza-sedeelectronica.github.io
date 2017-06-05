package es.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.NumberFormat;

import org.apache.commons.codec.binary.Base64;

public class Utils {
	public static String tratarCadena(String object, boolean enForm) {
    	String retorno = (String) object;
    	if (enForm) {
    		retorno = retorno.replaceAll("&amp;", "&");
    		retorno = retorno.replaceAll("&", "&amp;");
    		retorno = retorno.replaceAll("<", "&lt;");
    		retorno = retorno.replaceAll(">", "&gt;");
    		retorno = retorno.replaceAll("\"", "&quot;");
    	} else {
    		retorno = retorno.replaceAll("&amp;", "&");
    		retorno = retorno.replaceAll("&", "&amp;");
    		//retorno = retorno.replaceAll("<", "&lt;");
    		//retorno = retorno.replaceAll(">", "&gt;");
    		retorno = retorno.replaceAll("\"", "&quot;");
    	}
    	return retorno;
	}
	public static String pasarAMoneda(String object) {
		return NumberFormat.getCurrencyInstance().format(Double.valueOf(object)).replace("\u20AC", "");
	}
	public static String encodeToString(String path) throws IOException {
		File file = new File(path);
		FileInputStream imageInFile = null;
		try {
			// Reading a Image file from file system
			imageInFile = new FileInputStream(file);
			byte imageData[] = new byte[(int) file.length()];
			imageInFile.read(imageData);

			// Converting Image byte array into Base64 String
			String base64 = Base64.encodeBase64String(imageData);
			return base64;
		} finally {
			if (imageInFile != null) {
				imageInFile.close();
			}
		}
	}
}
