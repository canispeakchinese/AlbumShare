package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;
import domain.User;

public class LoginValidator implements Validator{

	@Override
	public boolean supports(Class<?> klass) {
		return User.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "username", "username.required");
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "password", "password.required");
	}

}
