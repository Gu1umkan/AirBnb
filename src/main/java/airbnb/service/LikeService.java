package airbnb.service;

import airbnb.dto.response.LikeResponse;
import airbnb.dto.response.SimpleResponse;

import java.security.Principal;

public interface LikeService {
    LikeResponse getLikeAndDislikeByFeedback(Long feedbackId);
    SimpleResponse likeOrDislikeFeedback(Principal principal, String likeOrDis, Long feedbackId);

}
