package es.api;

import java.util.MissingResourceException;
import java.util.ResourceBundle;
/**
 * 
 * @author UsuarioAyto
 *
 */
public class Propiedades {
	/**
	 * Nombre del fichero de propiedades
	 */
	private static final String BUNDLE_NAME = "propiedades"; //$NON-NLS-1$
	/**
	 * Recurso
	 */
	private static final ResourceBundle RESOURCE_BUNDLE = ResourceBundle
			.getBundle(BUNDLE_NAME);
	/**
	 * Constructor
	 *
	 */
	private Propiedades() {
		super();
	}
	/**
	 * 
	 * @param key Elemento a recoger
	 * @return valor del elemento en el fichero
	 */
	public static String getString(final String key) {
		String retorno;
		try {
			retorno = RESOURCE_BUNDLE.getString(key);
		} catch (MissingResourceException e) {
			retorno = '!' + key + '!';
		}
		return retorno;
	}
}
