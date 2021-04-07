package jsonProject.jsonProject;

import java.io.File;
import java.util.*;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.JSONValue;

import com.fasterxml.jackson.databind.ObjectMapper;
import model.*;

public class ReadJson {
	static Map<String, City> weatherByCityID;

	public static void main(String[] args) throws Exception {
		weatherByCityID = new HashMap<String, City>();
		ObjectMapper objectMapper = new ObjectMapper();
		List<City> cityList = Arrays.asList(objectMapper.readValue(new File("./jsonFiles/cityID.json"), City[].class));

		int countIN = 1;
		int countUS = 1;
		int countPK = 1;
		List<String> list = new ArrayList<String>();
		List<String> in = new ArrayList<String>();
		List<String> us = new ArrayList<String>();
		List<String> pk = new ArrayList<String>();

		for (City city : cityList) {
			if (city.getCountry().equals("IN") && countIN <= 10) {
				weatherByCityID.put(city.getId(), city);
				in.add(city.getId());
				countIN++;
			}
			if (city.getCountry().equals("US") && countUS <= 10) {
				weatherByCityID.put(city.getId(), city);
				us.add(city.getId());
				countUS++;
			}
			if (city.getCountry().equals("PK") && countPK <= 10) {
				weatherByCityID.put(city.getId(), city);
				pk.add(city.getId());
				countPK++;
			}
		}
		for (Map.Entry<String, City> weatherByCity : weatherByCityID.entrySet()) {
			list.add(weatherByCity.getKey());
		}
		ReadJson readSon = new ReadJson();
		Map<String, List<City>> values = (readSon.groupByCountry(list));

		System.out.println(objectMapper.writeValueAsString(values));
		System.out.println("\n\n");
		System.out.println("For IN: " + readSon.groupByWeatherCondition(in));
		System.out.println("For US: " + readSon.groupByWeatherCondition(us));
		System.out.println("For PK: " + readSon.groupByWeatherCondition(pk));
	}

	public Map<String, List<City>> groupByCountry(List<String> cityList) {
		Map<String, List<City>> resp = new HashMap<String, List<City>>();
		for (String city : cityList) {
			City cityDetails = weatherByCityID.get(city);
			if (null != cityDetails) {
				City cityResponse = new City();
				Coord coord = new Coord();
				cityResponse.setCoord(coord);
				cityResponse.setId(cityDetails.getId());
				cityResponse.setName(cityDetails.getName());
				coord.setLat(cityDetails.getCoord().getLat());
				coord.setLon(cityDetails.getCoord().getLon());
				List<City> citys = resp.get(cityDetails.getCountry());
				if (null != citys) {
					citys.add(cityResponse);
				} else {
					List<City> list = new ArrayList<City>();
					list.add(cityResponse);
					resp.put(cityDetails.getCountry(), list);
				}
			}
		}
		return resp;
	}

	public Map<String, List<String>> groupByWeatherCondition(List<String> cityList) {
		Map<String, List<String>> resp = new HashMap<String, List<String>>();
		WeatherProvider weatherProvider = new WeatherProvider();
		WeatherResponse weatherResponse = weatherProvider.getWeatherDetails(cityList);

		if (weatherResponse != null) {
			for (WeatherDetails weatherDetails : weatherResponse.getList()) {
				if (weatherDetails != null) {
					for (Weather weather : weatherDetails.getWeather()) {
						List<String> names = resp.get(weather.getMain());
						if (names != null) {
							names.add(weatherDetails.getName());
						} else {
							List<String> list = new ArrayList<String>();
							list.add(weatherDetails.getName());
							resp.put(weather.getMain(), list);
						}
					}
				}
			}
		}
		return resp;
	}

}
