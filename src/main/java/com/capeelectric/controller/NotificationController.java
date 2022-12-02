package com.capeelectric.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.capeelectric.exception.NotificationException;
import com.capeelectric.model.NotificationCommentList;
import com.capeelectric.service.NotificationService;

/**
 * 
 * @author capeelectricsoftware
 *
 */

@RestController
@RequestMapping("/api/v2")
public class NotificationController {

	private static final Logger logger = LoggerFactory.getLogger(NotificationController.class);

	@Autowired
	private NotificationService notificationService;

	@GetMapping("/retrieveComments/{userName}")
	public ResponseEntity<NotificationCommentList> retrieveCommentsForUser(@PathVariable String userName)
			throws NotificationException {
		logger.debug("Retrieve Comments Starts UserName: {}", userName);
		NotificationCommentList commentsForUser = notificationService.retrieveCommentsForUser(userName);
		logger.debug("Retrieve Comments End");
		return new ResponseEntity<NotificationCommentList>(commentsForUser, HttpStatus.OK);
	}
}
