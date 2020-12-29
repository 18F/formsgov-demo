package gov.gsa.form.service.web.rest.vm;

import gov.gsa.form.service.dto.User;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;

@RestController
@RequestMapping("/api")
public class UserResource {

    @GetMapping(value = "/user")
    public User getCurrentUser(HttpServletRequest request){
        return (User) request.getSession().getAttribute("user");
    }
}

