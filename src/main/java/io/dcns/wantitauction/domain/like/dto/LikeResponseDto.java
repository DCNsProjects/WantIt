package io.dcns.wantitauction.domain.like.dto;


import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
public class LikeResponseDto {

    private Long userId;
    private Long auctionId;
    private boolean liked;

    public LikeResponseDto(Long userId, Long auctionId, boolean liked) {
        this.userId = userId;
        this.auctionId = auctionId;
        this.liked = liked;
    }
}
