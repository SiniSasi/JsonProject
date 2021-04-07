package model;

import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;

@JsonIgnoreProperties(ignoreUnknown = true)
@JsonInclude(JsonInclude.Include.NON_NULL)

public class WeatherResponse {

    private String cnt;

    public String getCnt() {
        return cnt;
    }

    public void setCnt(String cnt) {
        this.cnt = cnt;
    }


    public List<WeatherDetails> getList() {
        return list;
    }

    public void setList(List<WeatherDetails> list) {
        this.list = list;
    }

    private List<WeatherDetails> list;
}
