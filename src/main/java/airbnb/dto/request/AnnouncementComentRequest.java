package airbnb.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Builder
public class AnnouncementComentRequest {
    @NotNull(message = "not null !! ")
    private String rejectAnnouncement;
}
