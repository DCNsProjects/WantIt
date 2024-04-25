package io.dcns.wantitauction.domain.auctionItem.dto;

import io.dcns.wantitauction.domain.auctionItem.entity.AuctionItemEnum;
import java.time.LocalDateTime;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Builder
public class CreateProductRequestDto {

    private String itemName;
    private String itemDescription;
    private Long minPrice;
    private String category;
    private LocalDateTime startDate;
    private LocalDateTime endDate;
    private final AuctionItemEnum status = AuctionItemEnum.READY;
    private String imageUrl;
}
