package airbnb.dto.request;


public record LoginRequest(
        String email,
        String password
) {

}
