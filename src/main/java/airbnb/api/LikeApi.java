package airbnb.api;

import airbnb.dto.response.LikeResponse;
import airbnb.dto.response.SimpleResponse;
import airbnb.service.LikeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/api/like")
@RequiredArgsConstructor
public class LikeApi {
    private final LikeService likeService;

    @GetMapping("/get/count/{feedbackId}")
    public LikeResponse getLikeAndDislikeByFeedback(@PathVariable Long feedbackId) {
        return likeService.getLikeAndDislikeByFeedback(feedbackId);
    }

    @PreAuthorize("hasAnyAuthority('VENDOR', 'USER')")
    @Operation(description = "Like or dislike feedback")
    @PutMapping("/{feedbackId}")
    public SimpleResponse likeOrDislikeFeedback(@PathVariable Long feedbackId, @RequestParam String likeOrDis, Principal principal) {
        return likeService.likeOrDislikeFeedback(principal, likeOrDis, feedbackId);
    }

}
