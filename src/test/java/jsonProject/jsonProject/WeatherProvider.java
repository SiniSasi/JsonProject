package jsonProject.jsonProject;

import model.WeatherResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import java.util.ArrayList;
import java.util.List;

public class WeatherProvider {


    private String prefix = "http://api.openweathermap.org/data/2.5/group?id=";
    private String suffix = "&units=metric&appid=6d4c202370ae8d7c76ee8edefc512a4f";

    private RestTemplate restTemplate = new RestTemplate();


    public WeatherResponse getWeatherDetails(List<String> pinCodes) {
        try {
            ResponseEntity<WeatherResponse> weatherResponse = restTemplate.exchange(getUrl(pinCodes), HttpMethod.GET, null, WeatherResponse.class);
            if (weatherResponse.getStatusCode() == HttpStatus.OK)
               return weatherResponse.getBody();

        } catch (Exception exp) {
        	System.out.println(exp);
            throw new RuntimeException("exception while calling api");
        }
        throw new RuntimeException("invalid response while calling api");
    }


    private String getUrl(List<String> pinCodes) {
        StringBuilder sb = new StringBuilder(prefix);
        sb.append(StringUtils.join(pinCodes, ','));
        sb.append(suffix);
        return sb.toString();
    }

}
