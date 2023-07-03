package pt.amado.camelsamples._01.processor;

import org.apache.camel.Exchange;
import org.apache.http.HttpStatus;
import org.springframework.stereotype.Component;
import pt.amado.camelsamples._01.model.ResponseModel;
import pt.amado.camelsamples._01.model.User;

import java.util.List;

/**
 * The UserTransformerCamel class provides methods for transforming user data and generating a response model.
 */
@Component
public class UserTransformerCamel {

    /**
     * Modifies the email address of the User object in the given exchange.
     * @param exchange the Camel exchange containing the User object to be modified
     */
    public void doSomething(Exchange exchange) {
        User user = (User) exchange.getMessage().getBody();
        user.setEmail("replaced@email.com");
    }

    /**
     * Generates a response model based on the list of User objects in the given exchange.
     * @param exchange the Camel exchange containing the list of User objects
     * @return the ResponseModel object
     */
    public ResponseModel generateResponse(Exchange exchange) {

        @SuppressWarnings("unchecked")
        List<User> users = (List<User>) exchange.getMessage().getBody();

        return ResponseModel.builder()
                .users(users)
                .timeRunning(System.currentTimeMillis() - exchange.getCreated())
                .statusCode(HttpStatus.SC_CREATED)
                .build();
    }

}
