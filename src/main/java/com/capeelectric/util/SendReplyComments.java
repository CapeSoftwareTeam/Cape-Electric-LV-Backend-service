package com.capeelectric.util;

import java.net.URI;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import com.capeelectric.controller.SummaryController;
import com.capeelectric.exception.RegistrationException;
import com.capeelectric.model.Register;
import com.capeelectric.repository.RegistrationRepository;
import com.capeelectric.service.AwsEmailService;

@Component
public class SendReplyComments {
	
	private static final Logger logger = LoggerFactory.getLogger(SummaryController.class);

	@Autowired
	private AwsEmailService awsEmailService;

	@Autowired
	private RegistrationRepository registrationRepo;
	
	@Value("${app.web.domain}")
	private String webUrl;

	public void sendComments(String userName) throws RegistrationException, Exception {
		Optional<Register> registerRepo = registrationRepo.findByUsername(userName);
		
		if (registerRepo.isPresent() && registerRepo.get().getAssignedBy() !=null) {
			logger.debug(Constants.EMAIL_SEND_COMMENT_MSG + " for this content sending Email service started");
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(registerRepo.get().getRegisterId()).toUri();
			String resetUrl = Utility.getSiteURL(uri.toURL());
			awsEmailService.sendEmailForComments(registerRepo.get().getAssignedBy(), userName,
					Constants.EMAIL_SEND_COMMENT_MSG + "\n" + "\n"
							+ (resetUrl.contains("localhost:5000")
									? resetUrl.replace("http://localhost:5000", "http://localhost:4200")
									: "https://www."+webUrl)
							+ "/login");
		} else {
			logger.debug("Email Id doesn't exist!");
			throw new Exception("Email Id doesn't exist!");
		}
	}

	public void replyComments(String inspectoUserName, String ViewerUserName) throws RegistrationException, Exception {

		Optional<Register> registerRepo = registrationRepo.findByUsername(inspectoUserName);
		if (registerRepo.isPresent() && registerRepo.get().getUsername().equalsIgnoreCase(inspectoUserName)) {
			logger.debug(Constants.EMAIL_REPLY_COMMENT_MSG + " for this content sending Email service started");
			URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
					.buildAndExpand(registerRepo.get().getRegisterId()).toUri();
			String resetUrl = Utility.getSiteURL(uri.toURL());
			awsEmailService.sendEmailForComments(ViewerUserName, inspectoUserName,
					Constants.EMAIL_REPLY_COMMENT_MSG + "\n" + "\n"
							+ (resetUrl.contains("localhost:5000")
									? resetUrl.replace("http://localhost:5000", "http://localhost:4200")
									: "https://www."+webUrl)
							+ "/login");
		} else {
			logger.debug("Given Inspector UserName MisMatched");
			throw new Exception("Given Inspector UserName MisMatched");
		}
	}
	
	public void approveComments(String userName, String approveOrReject) throws RegistrationException, Exception {
		Optional<Register> registerRepo = registrationRepo.findByUsername(userName);

		if (registerRepo.isPresent() && registerRepo.get().getAssignedBy() != null) {

			if (approveOrReject.equalsIgnoreCase("APPROVED")) {
				awsEmailService.sendEmailForComments(registerRepo.get().getAssignedBy(), userName, Constants.EMAIL_APPROVE_COMMENT_MSG);
			} else {
				logger.debug(Constants.EMAIL_REJECT_COMMENT_MSG + " for this content sending Email service started");
				URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}")
						.buildAndExpand(registerRepo.get().getRegisterId()).toUri();
				String resetUrl = Utility.getSiteURL(uri.toURL());
				awsEmailService.sendEmailForComments(registerRepo.get().getAssignedBy(), userName,
						Constants.EMAIL_REJECT_COMMENT_MSG + "\n"
								+ (resetUrl.contains("localhost:5000")
										? resetUrl.replace("http://localhost:5000", "http://localhost:4200")
										: "https://www."+webUrl)
								+ "/login");
			}

		} else {
			logger.debug("Email Id doesn't exist!");
			throw new Exception("Email Id doesn't exist!");
		}
	}
}
