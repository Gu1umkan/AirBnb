package airbnb.service;

import airbnb.dto.request.FeedbackRequest;
import airbnb.dto.response.FeedbackResponse;
import airbnb.dto.response.SimpleResponse;

import java.util.List;

public interface FeedbackService {
  List<FeedbackResponse> getAllFeedbacksByAnnouncement(Long announcementId);
  SimpleResponse saveFeedback(FeedbackRequest feedbackRequest, Long announcementId);
  SimpleResponse updateFeedback(FeedbackRequest feedbackRequest, Long feedbackId);
  SimpleResponse deleteFeedback(Long feedbackId);
}
