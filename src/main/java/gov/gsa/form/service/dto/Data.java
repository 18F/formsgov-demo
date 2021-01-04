package gov.gsa.form.service.dto;

import java.io.Serializable;

public class Data implements Serializable {
    private static final long serialVersionUID = -911443577919980123L;
    private String email = "service@gsa.gov";
    private String password = "vBEJbMK6DAydFjBitmLbB4ndBhHZpm";

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
