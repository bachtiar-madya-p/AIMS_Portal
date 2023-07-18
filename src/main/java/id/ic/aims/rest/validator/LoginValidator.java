package id.ic.aims.rest.validator;

import id.ic.aims.rest.model.LoginRequest;

public class LoginValidator extends BaseValidator{

    public LoginValidator() {
    }

    public boolean validate(LoginRequest request) {
        return notNull(request)
                && validate(request.getEmail())
                && validate(request.getPassword());
    }
}
