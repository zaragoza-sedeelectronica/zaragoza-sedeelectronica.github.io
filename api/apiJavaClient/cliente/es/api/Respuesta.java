package es.api;

import es.api.json.JSONArray;
import es.api.json.JSONException;
import es.api.json.JSONObject;

public class Respuesta {
	private int status;
	private String contenido;
	public int getStatus() {
		return status;
	}
	public void setStatus(int status) {
		this.status = status;
	}
	public String getContenido() {
		return contenido;
	}
	public void setContenido(String contenido) {
		this.contenido = contenido;
	}
	public JSONObject getJson() throws JSONException {
		return new JSONObject(contenido);
	}
	public JSONArray getJsonArray() throws JSONException {
		return new JSONArray(contenido);
	}
	public String getMensajeError() {
		String mensaje = ((JSONObject) this.getJson()).has("mensaje") ? ((JSONObject) this.getJson()).getCadena("mensaje")
				: ((JSONObject) this.getJson()).has("error")
						&& this.getJson().get("error") instanceof String ? 
								((JSONObject) this.getJson()).getCadena("error") 
								: "";
		if ("".equals(mensaje) && ((JSONObject) this.getJson()).has("error")) {
			JSONArray errores = ((JSONObject) this.getJson()).getJSONArray("error");
			StringBuffer err = new StringBuffer();
			if (errores != null) {
				err.append("<div class=\"alert alert-error error\" id=\"msg\">Errores:<ul>");
				for (int i = 0; i < errores.length(); i++) {
					JSONObject obj = errores.getJSONObject(i);
					err.append("<li><strong>" + JSONObject.getNames(obj)[0] + "</strong>: " + trataError(obj.getString(JSONObject.getNames(obj)[0])) + "</li>");
				}
				err.append("</ul></div>");
			}
			mensaje = err.toString();
		} else {
			if (!"".equals(mensaje)) {
				mensaje = "<div class=\"alert alert-error error\" id=\"msg\">" + mensaje + "</div>";
			}
		}
		return mensaje;
	
	}
	private String trataError(String mensaje) {
		return "<![CDATA[" + mensaje + "]]>";
	}
	@Override
	public String toString() {
		return "Respuesta [status=" + status + ", contenido=" + contenido + "]";
	}
	
}

