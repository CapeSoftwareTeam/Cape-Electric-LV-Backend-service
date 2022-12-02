package com.capeelectric.service;

import com.capeelectric.exception.NotificationException;
import com.capeelectric.model.NotificationCommentList;

public interface NotificationService {

	public NotificationCommentList retrieveCommentsForUser(String userName) throws NotificationException;

}
