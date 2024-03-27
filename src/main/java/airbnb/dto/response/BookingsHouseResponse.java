package airbnb.dto.response;

import airbnb.entities.enums.HouseType;
import airbnb.entities.enums.Region;
import lombok.*;


import java.util.List;


@Builder
public record BookingsHouseResponse (

        Long id,
        HouseType houseType,
        List<String> images,
        String title,
        String description,
        String address,
        String town,
        Region region,
        int maxOfGuests,
        List<BookingResponse> booked,
        List<BookingInFavoritesResponse> inFavorites,
        List<FeedbackResponse>  feedback

    //////  booking  //////

//         Long ids,
//         String fullName,
//         String email,
//         String image,
//         LocalDate checkIn,
//         LocalDate checkOut,
//         BigDecimal totalPrice,


    /////  Feedback  /////

//        Long idd,
//        String feedback,
//        List<String> imagess,
//        LocalDate createdAt,
//        int rating,

    ///// like //////
//
//        Long id1,
//        Boolean isLike,
//        Boolean dislike
){

}
//    public BookingsHouseResponse(Long id, HouseType houseType,  String title, String description, String address,
//                                 String town, Region region, int maxOfGuests, Long ids, String fullName, String email, String image, LocalDate checkIn, LocalDate checkOut,
//                                 BigDecimal totalPrice, Long idd, String feedback,  LocalDate createdAt, int rating, Long id1, Boolean isLike, Boolean dislike) {
//        this.id = id;
//        this.houseType = houseType;
//
//        this.title = title;
//        this.description = description;
//        this.address = address;
//        this.town = town;
//        this.region = region;
//        this.maxOfGuests = maxOfGuests;
//        this.ids = ids;
//        this.fullName = fullName;
//        this.email = email;
//        this.image = image;
//        this.checkIn = checkIn;
//        this.checkOut = checkOut;
//        this.totalPrice = totalPrice;
//        this.idd = idd;
//        this.feedback = feedback;
//
//        this.createdAt = createdAt;
//        this.rating = rating;
//        this.id1 = id1;
//        this.isLike = isLike;
//        this.dislike = dislike;
//    }

