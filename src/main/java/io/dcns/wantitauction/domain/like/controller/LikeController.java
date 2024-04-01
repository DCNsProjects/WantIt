package io.dcns.wantitauction.domain.like.controller;

import io.dcns.wantitauction.domain.like.dto.LikeResponseDto;
import io.dcns.wantitauction.domain.like.service.LikeService;
import io.dcns.wantitauction.global.dto.ResponseDto;
import io.dcns.wantitauction.global.impl.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("v1/auctions")
public class LikeController {

    private final LikeService likeService;

    @PostMapping("/{auctionitemId}/likes")
    public ResponseEntity<ResponseDto<LikeResponseDto>> likeAuctionItem(
        @PathVariable Long auctionitemId, @AuthenticationPrincipal UserDetailsImpl userDetails) {

        LikeResponseDto likeResponseDto = likeService.likeAuctionItem(auctionitemId,
            userDetails.getUser());
        return ResponseDto.of(HttpStatus.OK, likeResponseDto);
    }

//    @GetMapping("likes")
//    public ResponseEntity<ResponseDto<List<LikeResponseListDto>>> getlikeAuctionItem(@AuthenticationPrincipal UserDetailsImpl userDetails){
//        List<LikeResponseListDto> likeResponseDto = likeService.getlikedAuctionItem(userDetails.getUser().getUserId());
//
//        return ResponseDto.of(HttpStatus.OK, likeResponseDto);
//
//    }




}
