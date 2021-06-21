package br.com.univali.healthroutes.api.distanceMatrix.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import br.com.univali.healthroutes.api.adress.model.Adress;
import br.com.univali.healthroutes.api.distanceMatrix.model.Root;

@Service
public class DistanceMatrixService {
	
	public static final String keyVirtualEarth = "Aq4V7NLmwYcGudi-TI2XV_GGuHI11adm29Dv7ks96J56cZte9HXrZOaY5oPIQukx";
	
	public Root getMatrix(List<Adress> adresses) {
		
		String jsonsaida;
		String request = "https://dev.virtualearth.net/REST/v1/Routes/DistanceMatrix?travelMode=driving&key="+ keyVirtualEarth +"&origins=";
		for(int i=0;i<adresses.size();i++) {
			if(i<adresses.size()-1)
				request += String.valueOf(adresses.get(i).getLatitude()+","+String.valueOf(adresses.get(i).getLongitude()+ ";"));
			else
				request += String.valueOf(adresses.get(i).getLatitude()+","+String.valueOf(adresses.get(i).getLongitude()));
		}
		request += "&destinations=";
		
		for(int i=0;i<adresses.size();i++) {
			if(i<adresses.size()-1)
				request += String.valueOf(adresses.get(i).getLatitude()+","+String.valueOf(adresses.get(i).getLongitude()+ ";"));
			else
				request += String.valueOf(adresses.get(i).getLatitude()+","+String.valueOf(adresses.get(i).getLongitude()));
		}
		try {
			jsonsaida = openURLtoString(request);
		}catch (Exception e) {
			return null;
		}
		return getRouteFromJson(jsonsaida);
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
		
		
		
	}
	
	private static int calculateDaysBetween(LocalDate deadline) {
		
		return (int)ChronoUnit.DAYS.between(LocalDate.now(), deadline);
		
	}

}
