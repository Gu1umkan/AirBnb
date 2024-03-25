package airbnb.service;

import airbnb.dto.response.PaginationResponse;

public interface AdminService {
    PaginationResponse getAllHome(int page, int size);
}
