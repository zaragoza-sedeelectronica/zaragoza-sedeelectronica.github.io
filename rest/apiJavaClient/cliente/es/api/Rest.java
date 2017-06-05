package es.api;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.methods.DeleteMethod;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.commons.httpclient.methods.PutMethod;
import org.apache.commons.httpclient.methods.StringRequestEntity;



public class Rest {
	private final static String SERVER = Propiedades.getString("server");
	private final static String AGENT = Propiedades.getString("agent");

	
	public final static String FORMATO_XML = "application/xml";
	private final static String FORMATO_JSON = "application/json";
	private final static String FORMATO_JSONLD = "application/ld+json";
	
	public final static String USERADMIN = Propiedades.getString("user_admin");
	public final static String USERADMINPK = Propiedades.getString("user_admin_pk");
	
	protected static Rest servicio;
	
	private Rest() {
		
	}
	public static Rest getInstance() {
		if (servicio == null) {
			servicio = new Rest();
		}
		return servicio;
	}

	
	public Respuesta getLD(String uri) {
		return getLD(uri, "", "", null);
	}
	public Respuesta getLD(String uri, String clientId, String clavePrivada) {
		return getLD(uri, clientId, clavePrivada, null);
	}
	public Respuesta getLD(String uri, HashMap<String, String> headers) {
		return getLD(uri, "", "", headers);
	}
	
	public Respuesta getLD(String uri, String clientId, String clavePrivada, HashMap<String, String> headers) {
		return get(uri, clientId, clavePrivada, headers, FORMATO_JSONLD);
	}
	
	
	public Respuesta get(String uri) {
		return get(uri, "", "", null);
	}
	public Respuesta get(String uri, String clientId, String clavePrivada) {
		return get(uri, clientId, clavePrivada, null);
	}
	public Respuesta get(String uri, HashMap<String, String> headers) {
		return get(uri, "", "", headers);
	}
	
	public Respuesta get(String uri, String clientId, String clavePrivada, HashMap<String, String> headers) {
		return get(uri, clientId, clavePrivada, headers, FORMATO_JSON);
	}
	
	public Respuesta get(String uri, String clientId, String clavePrivada, HashMap<String, String> headers, String accept) {
		return get(SERVER, uri, clientId, clavePrivada, headers, accept);
	}
	
	public Respuesta get(String server, String uri, String clientId, String clavePrivada, HashMap<String, String> headers, String accept) {
		HttpClient client = new HttpClient();
		GetMethod method = new GetMethod(server + uri);
		method.addRequestHeader("User-Agent", AGENT);
		method.addRequestHeader("Accept", accept);
		
		method.addRequestHeader("Referer", SERVER + "/ciudad/");
		
		if (Propiedades.getString("proxy.host").length() >0) {
			client.getHostConfiguration().setProxy(Propiedades.getString("proxy.host"), Integer.parseInt(Propiedades.getString("proxy.port")));
		}
        
		if (headers != null) {
			Iterator it = headers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				method.addRequestHeader(e.getKey().toString(), e.getValue().toString());
			}
		}
		
		if (!"".equals(clientId) && !"".equals(clavePrivada)) {
			method.addRequestHeader("clientId", clientId);
			method.addRequestHeader("HmacSHA1", Hmac.calcular(clientId + "GET" + uri, clavePrivada));
		}

		Respuesta resp = new Respuesta();
		try {
			resp.setStatus(client.executeMethod(method));
			resp.setContenido(new String(method.getResponseBody(), "UTF-8"));
		} catch (HttpException e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
		} catch (IOException e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
		} finally {
			// Release the connection.
			method.releaseConnection();
		}
		return resp;

	}
	
	
	public Respuesta delete(String uri, String clientId, String clavePrivada) {
		HttpClient client = new HttpClient();
		DeleteMethod method = new DeleteMethod(SERVER + uri);

		System.out.println(SERVER +  uri);
		
		method.addRequestHeader("User-Agent", AGENT);
		method.addRequestHeader("Accept", FORMATO_JSON);
		method.addRequestHeader("Referer", SERVER + "/ciudad/");
		
		method.addRequestHeader("clientId", clientId);
		method.addRequestHeader("HmacSHA1", Hmac.calcular(clientId + "DELETE" + uri, clavePrivada));
		
		if (Propiedades.getString("proxy.host").length() >0) {
			client.getHostConfiguration().setProxy(Propiedades.getString("proxy.host"), Integer.parseInt(Propiedades.getString("proxy.port")));
		}
		
		Respuesta resp = new Respuesta();
		try {
			resp.setStatus(client.executeMethod(method));
			resp.setContenido(new String(method.getResponseBody(), "UTF-8"));
		} catch (HttpException e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
		} catch (IOException e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return resp;

	}
	public Respuesta post(String uri, String json,  String clientId, String clavePrivada) {
		return post(uri, json, clientId, clavePrivada, null);
	}
	public Respuesta post(String uri, String json,  String clientId, String clavePrivada, HashMap<String, String> headers) {
		HttpClient client = new HttpClient();
		
		PostMethod method = new PostMethod(SERVER + uri);
		System.out.println(SERVER + uri);
		method.addRequestHeader("User-Agent", AGENT);
		method.addRequestHeader("Accept", FORMATO_JSON);
		method.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
		method.addRequestHeader("Referer", SERVER + "/app/");
		
		if (Propiedades.getString("proxy.host").length() >0) {
			client.getHostConfiguration().setProxy(Propiedades.getString("proxy.host"), Integer.parseInt(Propiedades.getString("proxy.port")));
		}
		
		if (headers != null) {
			Iterator it = headers.entrySet().iterator();
			while (it.hasNext()) {
				Map.Entry e = (Map.Entry)it.next();
				method.addRequestHeader(e.getKey().toString(), e.getValue().toString());
			}
		}
		
		method.addRequestHeader("clientId", clientId);
		method.addRequestHeader("HmacSHA1", Hmac.calcular(clientId + "POST" + uri + json, clavePrivada));
		System.out.println("HMAC:" + Hmac.calcular(clientId + "POST" + uri + json, clavePrivada));
		method.setRequestEntity( new StringRequestEntity(json) );
		Respuesta resp = new Respuesta();
		try {
			resp.setStatus(client.executeMethod(method));
			resp.setContenido(new String(method.getResponseBody(), "UTF-8"));
		} catch (HttpException e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
		} catch (IOException e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
		} finally {
			method.releaseConnection();
		}
		return resp;

	}
	public Respuesta put(String uri, String json,  String clientId, String clavePrivada) {
		return put(uri, json, clientId, clavePrivada, null, FORMATO_JSON);
	}
	
	public Respuesta put(String uri, String json,  String clientId, String clavePrivada, String accept) {
		return put(uri, json, clientId, clavePrivada, null, accept);
	}
	
	public Respuesta put(String uri, String json,  String clientId, String clavePrivada, HashMap<String, String> headers, String accept) {
		Respuesta resp = new Respuesta();
		try {
			HttpClient client = new HttpClient();
			System.out.println(SERVER + uri);
			PutMethod method = new PutMethod(SERVER + uri);
			method.addRequestHeader("User-Agent", AGENT);
			method.addRequestHeader("Accept", accept);
			method.addRequestHeader("Content-Type", "application/json;charset=UTF-8");
			method.addRequestHeader("Referer", SERVER + "/app/");
			
			if (Propiedades.getString("proxy.host").length() >0) {
				client.getHostConfiguration().setProxy(Propiedades.getString("proxy.host"), Integer.parseInt(Propiedades.getString("proxy.port")));
			}
			
			if (headers != null) {
				Iterator it = headers.entrySet().iterator();
				while (it.hasNext()) {
					Map.Entry e = (Map.Entry)it.next();
					method.addRequestHeader(e.getKey().toString(), e.getValue().toString());
				}
			}
			method.addRequestHeader("clientId", clientId);
			System.out.println("HMAC:" + Hmac.calcular(clientId + "PUT" + uri + json, clavePrivada));
			method.addRequestHeader("HmacSHA1", Hmac.calcular(clientId + "PUT" + uri + json, clavePrivada));
			method.setRequestEntity( new StringRequestEntity(json));
			try {
				resp.setStatus(client.executeMethod(method));
				resp.setContenido(new String(method.getResponseBody(), "UTF-8"));
			} catch (HttpException e) {
				resp.setStatus(400);
				resp.setContenido(e.getMessage());
			} catch (IOException e) {
				resp.setStatus(400);
				resp.setContenido(e.getMessage());
			} finally {
				method.releaseConnection();
			}
			return resp;
		} catch (Exception e) {
			resp.setStatus(400);
			resp.setContenido(e.getMessage());
			return resp;
		}

	}
	
	
	public static String addExtension(String uri, String extension) {
		
		if (uri.indexOf("?") > 0) {
			String tmp = uri.substring(0, uri.indexOf("?")); 
			if (tmp.lastIndexOf("/") + 1 == tmp.length()) {
				tmp = tmp.substring(0, tmp.length() - 1); 
			}
			
			return tmp + "." + extension + (uri.substring(uri.indexOf("?"), uri.length()).replaceAll("&", "&amp;"));
		} else {
			if (uri.lastIndexOf("/") + 1 == uri.length()) {
				uri = uri.substring(0, uri.length() - 1); 
			}
			return uri + "." + extension;
		}
	}
}
