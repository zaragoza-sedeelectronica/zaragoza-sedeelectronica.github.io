package es.azar;

import es.api.Rest;

public class Agenda {
	private static final String API = "/sede/servicio/cultura/evento";

	public static void main(String[] args) {
		
		String body = "{"
				+ "    \"title\": \"texto de prueba\""
				+ "	   ,\"novisible\":\"S\""
				+ "    ,\"web\": \"http://www.google.com\""
				+ "    ,\"description\": \"descripcion\""
				+ "    ,\"poblacion\": ["
				+ "        {"
				+ "            \"id\": 2"
				+ "        }"
				+ "    ]"
				+ "    ,\"temas\": ["
				+ "        {"
				+ "            \"id\": 37"
				+ "        }"
				+ "    ]"
				+ "    ,\"subEvent\": ["
				+ "        {"
				+ "            \"lugar\": {"
				+ "                \"id\": \"rec-916\""
				+ "            },"
				+ "            \"fechaFinal\": \"2015-12-31T00:00:00Z\","
				+ "            \"fechaFinal\": \"2015-12-31T00:00:00Z\","
				+ "            \"horaInicio\": \"09:00\","
				+ "            \"horaFinal\": \"10:00\""
				+ "        }"
				+ "    ]"
				+ "}";
		System.out.println(body);
		System.out.println(Rest.getInstance().post(API, body, Rest.USERADMIN, Rest.USERADMINPK));
		
		
		
	}
}
