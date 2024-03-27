package airbnb.api;

import airbnb.dto.request.FeedbackRequest;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.FeedbackService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/feedback")
@RequiredArgsConstructor
public class FeedBackApi {
    private final FeedbackService feedbackService;

    @Secured({"USER", "VENDOR"})
    @PostMapping("/save/{announcementId}")
    public SimpleResponse saveFeedback(@RequestBody @Valid FeedbackRequest feedbackRequest, @PathVariable Long announcementId) {
        return feedbackService.saveFeedback(feedbackRequest, announcementId);
    }

    @Secured({"USER", "VENDOR"})
    @PutMapping("/{feedbackId}")

    public SimpleResponse updateFeedback(@RequestBody @Valid FeedbackRequest feedbackRequest, @PathVariable Long feedbackId) {
        return feedbackService.updateFeedback(feedbackRequest, feedbackId);
    }

    @Secured({"USER", "VENDOR"})
    @DeleteMapping("/{feedbackId}")
    public SimpleResponse deleteFeedback(@PathVariable Long feedbackId) {
        return feedbackService.deleteFeedback(feedbackId);
    }
}
