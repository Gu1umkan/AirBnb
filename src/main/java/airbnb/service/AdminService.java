package airbnb.service;

import airbnb.dto.response.*;

import java.util.List;

public interface AdminService {
    PaginationResponse getAllHome(int page, int size);

    SimpleResponse reject(long id, String cause);

    SimpleResponse delete(long id);

    SimpleResponse accept(long id);

    ForHomeProfile find(long id);

    List<ForUsersTable> getAll();

    ForUserProfileWithBookings getWithBookings(long id);

    ForUserProfileWithBookings getWithAnnouncements(long id);

    SimpleResponse remove(long id);

    SimpleResponse blockAll(long id);
}
