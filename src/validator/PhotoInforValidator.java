package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.ValidationUtils;
import org.springframework.validation.Validator;

import domain.PhotoInfor;

public class PhotoInforValidator implements Validator {
	@Override
	public boolean supports(Class<?> klass) {
		return PhotoInfor.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		PhotoInfor photoInfor = (PhotoInfor) target;
		ValidationUtils.rejectIfEmptyOrWhitespace(errors, "photoName", "photoname.required");
		if(photoInfor.getDescription() == null) {
			photoInfor.setDescription("");
		}
	}
}
