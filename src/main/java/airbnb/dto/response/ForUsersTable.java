package airbnb.dto.response;

import lombok.Builder;

@Builder
public record ForUsersTable(
        long id,
        String fullName,
        String email,
        int bookings,
        int home
) {
}
