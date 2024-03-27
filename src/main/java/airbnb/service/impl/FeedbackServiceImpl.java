package airbnb.service.impl;

import airbnb.dto.request.FeedbackRequest;
import airbnb.dto.response.FeedbackResponse;
import airbnb.dto.response.FeedbackUserResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.entities.Announcement;
import airbnb.entities.Feedback;
import airbnb.entities.User;
import airbnb.entities.enums.Role;
import airbnb.exception.BadRequestException;
import airbnb.exception.ForbiddenException;
import airbnb.exception.NotFoundException;
import airbnb.repository.AnnouncementRepository;
import airbnb.repository.FeedbackRepository;
import airbnb.repository.UserRepository;
import airbnb.service.FeedbackService;
import airbnb.service.LikeService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class FeedbackServiceImpl implements FeedbackService {
    private final FeedbackRepository feedbackRepository;
    private final AnnouncementRepository announcementRepository;
    private final LikeService likeService;
    private final UserRepository userRepository;

    @Override
    public List<FeedbackResponse> getAllFeedbacksByAnnouncement(Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("Announcement with id " + announcementId + " not found!"));
        User currentUser = getCurrentUser();
        return feedbackRepository.findAllByAnnouncement(announcement)
                .stream()
                .map(feedback -> FeedbackResponse.builder()
                        .id(feedback.getId())
                        .images(feedback.getImages())
                        .feedback(feedback.getFeedback())
                        .createdAt(feedback.getCreatedAt())
                        .myOrNot(currentUser.getId().equals(feedback.getUser().getId()))
                        .updatedAt(feedback.getUpdatedAt())
                        .rating(feedback.getRating())
                        .likes(likeService.getLikeAndDislikeByFeedback(feedback.getId()))
                        .user(FeedbackUserResponse.builder()
                                .image(feedback.getUser().getImage())
                                .fullName(feedback.getUser().getFullName())
                                .id(feedback.getUser().getId())
                                .build())
                        .build())
                .collect(Collectors.toList());
    }

    private User getCurrentUser() {
        String email = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.getByEmail(email);
    }

    @Override
    public SimpleResponse saveFeedback(FeedbackRequest feedbackRequest, Long announcementId) {
        Announcement announcement = announcementRepository.findById(announcementId)
                .orElseThrow(() -> new NotFoundException("Announcement with id " + announcementId + " not found!"));
        User currentUser = getCurrentUser();

        if (currentUser.getRole().equals(Role.ADMIN)) throw new ForbiddenException("Admin cant leave feedback!");

        Feedback feedback = new Feedback();
        feedback.setFeedback(feedbackRequest.feedback());
        feedback.setImages(feedbackRequest.images());
        feedback.setRating(feedbackRequest.rating());
        feedback.setAnnouncement(announcement);
        feedback.setUser(currentUser);
        feedbackRepository.save(feedback);
        return SimpleResponse.builder().message("Successfully saved!").httpStatus(HttpStatus.OK).build();
    }

    @Override
    @Transactional
    public SimpleResponse updateFeedback(FeedbackRequest feedbackRequest, Long feedbackId) {
        Feedback foundFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException("Announcement with id " + feedbackId + " not found!"));
        User currentUser = getCurrentUser();
        if (!foundFeedback.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException("You cant edit feedback that you didnt wrote!");
        }
        foundFeedback.setFeedback(feedbackRequest.feedback());
        foundFeedback.setImages(feedbackRequest.images());
        foundFeedback.setRating(feedbackRequest.rating());
        return SimpleResponse.builder().message("Successfully updated!").httpStatus(HttpStatus.OK).build();
    }

    @Override
    public SimpleResponse deleteFeedback(Long feedbackId) {
        Feedback foundFeedback = feedbackRepository.findById(feedbackId)
                .orElseThrow(() -> new NotFoundException("Announcement with id " + feedbackId + " not found!"));
        User currentUser = getCurrentUser();
        if (!foundFeedback.getUser().getId().equals(currentUser.getId())) {
            throw new BadRequestException("You cant delete feedback that you didnt wrote!");
        }
        feedbackRepository.delete(foundFeedback);
        return SimpleResponse.builder().message("Successfully deleted!").httpStatus(HttpStatus.OK).build();

    }
}
