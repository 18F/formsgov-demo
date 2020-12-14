package gov.gsa.form.service.security;

import gov.gsa.form.service.config.Constants;
import gov.gsa.form.service.dto.User;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.saml.SAMLCredential;
import org.springframework.security.saml.userdetails.SAMLUserDetailsService;
import org.opensaml.saml2.core.Attribute;
import org.opensaml.xml.XMLObject;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;

@Named
public class UserDetails  implements SAMLUserDetailsService {

    @Override
    public User loadUserBySAML(SAMLCredential credential)  {
         HttpServletRequest request =
            ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes())
                .getRequest();
        String firstName = credential.getAttributeAsString("first_name");
        String lastName = credential.getAttributeAsString("last_name");
        User user =  new User();
        if (StringUtils.isNotBlank(firstName) ) {
            user.setFirstName(firstName);
        }
        if (StringUtils.isNotBlank(lastName)) {
            user.setLastName(lastName);
        }
        request.getSession().setAttribute("user", user);
        return user;
    }
}
