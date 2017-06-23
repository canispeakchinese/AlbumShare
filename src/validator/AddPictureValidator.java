package validator;

import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import org.springframework.web.multipart.MultipartFile;

import domain.UploadedPhoto;

public class AddPictureValidator implements Validator{
	@Override
	public boolean supports(Class<?> klass) {
		return UploadedPhoto.class.isAssignableFrom(klass);
	}

	@Override
	public void validate(Object target, Errors errors) {
		UploadedPhoto uploadedPhoto = (UploadedPhoto) target;
		MultipartFile file = uploadedPhoto.getFile();
		if(file.getOriginalFilename().isEmpty()) {
			errors.rejectValue("file", "picture.required");
		}
		String photoName = uploadedPhoto.getPhotoName();
		String description = uploadedPhoto.getDescription();
		if(file != null && (photoName == null || photoName.isEmpty())) {
			uploadedPhoto.setPhotoName(file.getOriginalFilename());
		}
		if(description == null) {
			uploadedPhoto.setDescription("");
		}
	}
}
