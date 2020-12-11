package gov.gsa.form.service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Login {

    @JsonProperty("data")
    private Data data;

    public Login(Data data) {
        this.data = data;
    }
    @JsonProperty("data")
    public Data getData() {
        return data;
    }
    @JsonProperty("data")
    public void setData(Data data) {
        this.data = data;
    }
}
