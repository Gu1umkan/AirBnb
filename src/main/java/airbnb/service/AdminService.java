package airbnb.service;

import airbnb.dto.response.PaginationResponse;
import airbnb.dto.response.SimpleResponse;

public interface AdminService {
    PaginationResponse getAllHome(int page, int size);

    SimpleResponse reject(long id, String cause);

    SimpleResponse delete(long id);

    SimpleResponse accept(long id);
}
