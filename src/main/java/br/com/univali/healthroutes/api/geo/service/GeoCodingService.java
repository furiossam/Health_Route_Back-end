package br.com.univali.healthroutes.api.geo.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;

import org.springframework.stereotype.Service;

import com.fasterxml.jackson.databind.ObjectMapper; // version 2.11.1

import br.com.univali.healthroutes.api.geo.model.Root;

import com.fasterxml.jackson.core.JsonProcessingException;

@Service
public class GeoCodingService {

	//API de busca de latitude e longitude
	public static final String keyVirtualEarth = "Aqz7fy3UKeAIGyrBfNvsR70QRP7UjH_RQxH_kac2DVMADC1TuKvc1LHKjnh0OY6M";
	
	
	
	public Root getBasicLatLog(String address) {
		address = address == null ? null : address.trim();
		
		String jsonsaida;
		try {
			jsonsaida = openURLtoString("http://dev.virtualearth.net/REST/v1/Locations?q="+URLEncoder.encode(address, "UTF-8")+"&key="+  keyVirtualEarth);
			//System.out.println(("http://dev.virtualearth.net/REST/v1/Locations?q="+URLEncoder.encode(address, "UTF-8")+"&key="+  keyVirtualEarth));
			//System.out.println(jsonsaida);
			return getPointFromJson(jsonsaida);
		} catch (Exception e) {
			return null;
		}
	}

	public Root getPointFromJson(String jsonRaw) {
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

	

	
	/**
	 * Se conecta nos fornecedores através de um link
	 * 
	 * @param String caminho do fornecedor
	 * @return String retorno
	 * @throws IOException
	 */
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
		Root root = geo.getBasicLatLog("Rua Zélia Maria dos Santos, 70, São Sebastião, Palhoça, SC");
		Root root2 = geo.getBasicLatLog("Rua Tomaz Domingos da Silveira, 803, São Sebastião, Palhoça, SC");
		System.out.println(root.getLat());
		System.out.println(root.getLong());
		System.out.println(root2.getLat());
		System.out.println(root2.getLong());
		
	}
}