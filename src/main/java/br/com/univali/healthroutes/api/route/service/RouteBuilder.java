package br.com.univali.healthroutes.api.route.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.univali.healthroutes.api.geo.service.GeoCodingService;
import br.com.univali.healthroutes.api.route.model.Root;

public class RouteBuilder {

	//Chave da API de rotas
	public static final String keyHere = "5bm33hhpFY-R1cNjfDbKoNhS2mT3F_JLle1y0ad1-0Y";
	
	public Root getRoute(Double lat1, Double lon1, Double lat2, Double lon2) {
		System.out.println("https://router.hereapi.com/v8/routes?transportMode=car&origin="+String.valueOf(lat1)+","+String.valueOf(lon1)+
				"&destination="+String.valueOf(lat2)+","+String.valueOf(lon2)+"&return=summary&apiKey="+keyHere);
		String jsonsaida;
		try {
			jsonsaida = openURLtoString("https://router.hereapi.com/v8/routes?transportMode=car&origin="+String.valueOf(lat1)+","+String.valueOf(lon1)+
					"&destination="+String.valueOf(lat2)+","+String.valueOf(lon2)+"&return=summary&apiKey="+keyHere);
			System.out.println("https://router.hereapi.com/v8/routes?transportMode=car&origin="+String.valueOf(lat1)+","+String.valueOf(lon1)+
					"&destination="+String.valueOf(lat2)+","+String.valueOf(lon2)+"&return=summary&apiKey="+keyHere);
			//System.out.println(jsonsaida);
			return getRouteFromJson(jsonsaida);
		} catch (Exception e) {
			return null;
		}
	}
	
	public Root getRouteFromJson(String jsonRaw) {
		ObjectMapper om = new ObjectMapper();
		Root root;
		try {
			root = om.readValue((jsonRaw), Root.class);
			return root;
		} catch (JsonProcessingException e) {
			e.printStackTrace();
			System.out.println("Erro");
			return null;
		} 
					
	}
	
	public static String openURLtoString(String providerPath) throws IOException {
		String enc = "UTF8";
		URLEncoder.encode(providerPath, enc);
		URL url = new URL(providerPath);
		HttpURLConnection connection = (HttpURLConnection) url.openConnection();

		BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream(), enc));
		String inputLine;

		StringBuilder result = new StringBuilder();
		while ((inputLine = in.readLine()) != null) {
			result.append(inputLine);
		}
		in.close();

		return result.toString();
	}
	
	public static void main(String[] args) {	
		GeoCodingService geo = new GeoCodingService();
		br.com.univali.healthroutes.api.geo.model.Root root = geo.getBasicLatLog("Rua Zélia Maria dos Santos, 70, São Sebastião, Palhoça, SC");
		br.com.univali.healthroutes.api.geo.model.Root root2 = geo.getBasicLatLog("R. José Cosme Pamplona, 1929, Bela Vista, Palhoça, SC,");
		System.out.println(root.getLat()+",");
		System.out.println(root.getLong());
		System.out.println(root2.getLat());
		System.out.println(root2.getLong());
		
		RouteBuilder route = new RouteBuilder();
		Root rootRoute = route.getRoute(root.getLat(), root.getLong(), root2.getLat(), root2.getLong());
		//System.out.println((rootRoute.getRoutes().get(0).getSections().get(0).getArrival().getTime().getTime()/60000)-(rootRoute.getRoutes().get(0).getSections().get(0).getDeparture().getTime().getTime()/60000));
		//Duração da Rota
		System.out.println(rootRoute.getRoutes().get(0).getSections().get(0).getSummary().getDuration()/60);
		//Tamanho da Rota em metros
		System.out.println(rootRoute.getRoutes().get(0).getSections().get(0).getSummary().getLength());
		
	}
}
