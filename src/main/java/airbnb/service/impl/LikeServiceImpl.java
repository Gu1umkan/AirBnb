package airbnb.service.impl;

import airbnb.dto.response.LikeResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Feedback;
import airbnb.entities.Like;
import airbnb.entities.User;
import airbnb.exception.ForbiddenException;
import airbnb.exception.NotFoundException;
import airbnb.repository.FeedbackRepository;
import airbnb.repository.LikeRepository;
import airbnb.repository.UserRepository;
import airbnb.service.LikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;

@Service
@RequiredArgsConstructor
public class LikeServiceImpl implements LikeService {
    private final FeedbackRepository feedbackRepository;
    private final UserRepository userRepository;
    private final LikeRepository likeRepository;

    @Override
    public LikeResponse getLikeAndDislikeByFeedback(Long feedbackId) {
        Feedback foundFeedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback with id " + feedbackId + " not found!"));
        List<Like> likes = foundFeedback.getLikes();
        int countOfLike = 0;
        int countOfDislike = 0;

        for (Like like : likes) {
            if (like.getIsLike() != null && like.getIsLike()) {
                countOfLike++;
            } else if (like.getDislike() != null && like.getDislike()) {
                countOfDislike++;
            }
        }

        return LikeResponse.builder()
                .countOfDislike(countOfDislike)
                .countOfLike(countOfLike)
                .build();
    }

    @Override
    @Transactional
    public SimpleResponse likeOrDislikeFeedback(Principal principal, String likeOrDis, Long feedbackId) {
        String email = principal.getName();
        User loginUser = userRepository.getByEmail(email);
        Feedback foundFeedback = feedbackRepository.findById(feedbackId).orElseThrow(() -> new NotFoundException("Feedback with id " + feedbackId + " not found!"));
        Like likeByUserAndFeedbackId = likeRepository.findByUserAndFeedback(loginUser, foundFeedback);

        boolean isLike = likeOrDis.equalsIgnoreCase("like");
        boolean isDislike = likeOrDis.equalsIgnoreCase("dislike");

        if (!isLike && !isDislike) {
            throw new ForbiddenException("Incorrect value for likeOrDis parameter!");
        }

        if (likeByUserAndFeedbackId != null && isLike) {
            likeByUserAndFeedbackId.setIsLike(!likeByUserAndFeedbackId.getIsLike());
            likeByUserAndFeedbackId.setDislike(false);
        }
        if (likeByUserAndFeedbackId != null && isDislike) {
            likeByUserAndFeedbackId.setIsLike(false);
            likeByUserAndFeedbackId.setDislike(!likeByUserAndFeedbackId.getDislike());
        }
        if (likeByUserAndFeedbackId == null) {
            Like newLike = new Like();
            newLike.setFeedback(foundFeedback);
            newLike.setUser(loginUser);
            if (isLike) {
                newLike.setIsLike(true);
                newLike.setDislike(false);
            }
            if (isDislike) {
                newLike.setIsLike(false);
                newLike.setDislike(true);
            }
            likeRepository.save(newLike);
        }
        return SimpleResponse.builder().httpStatus(HttpStatus.OK).message("Successfully changed!").build();
    }
}
