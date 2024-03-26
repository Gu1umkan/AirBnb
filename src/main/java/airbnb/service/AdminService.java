package airbnb.service;

import airbnb.dto.response.ForHomeProfile;
import airbnb.dto.response.ForUsersTable;
import airbnb.dto.response.PaginationResponse;
import airbnb.dto.response.SimpleResponse;

import java.util.List;

public interface AdminService {
    PaginationResponse getAllHome(int page, int size);

    SimpleResponse reject(long id, String cause);

    SimpleResponse delete(long id);

    SimpleResponse accept(long id);

    ForHomeProfile find(long id);

    List<ForUsersTable> getAll();
}
