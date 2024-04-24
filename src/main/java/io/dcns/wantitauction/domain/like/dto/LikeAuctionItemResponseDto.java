package io.dcns.wantitauction.domain.like.dto;


import java.time.LocalDateTime;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeAuctionItemResponseDto {


    private String itemName;
    private String itemDescription;
    private LocalDateTime startDate;
    private LocalDateTime endDate;

    public LikeAuctionItemResponseDto(
        String itemName, String itemDescription, LocalDateTime startDate, LocalDateTime endDate) {
        this.itemName = itemName;
        this.itemDescription = itemDescription;
        this.startDate = startDate;
        this.endDate = endDate;
    }
}
