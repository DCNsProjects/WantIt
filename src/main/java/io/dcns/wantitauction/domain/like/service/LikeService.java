package io.dcns.wantitauction.domain.like.service;

import io.dcns.wantitauction.domain.auctionItem.entity.AuctionItem;
import io.dcns.wantitauction.domain.auctionItem.repository.AuctionItemRepository;
import io.dcns.wantitauction.domain.like.dto.LikeResponseDto;
import io.dcns.wantitauction.domain.like.entity.Like;
import io.dcns.wantitauction.domain.like.repository.LikeRepository;
import io.dcns.wantitauction.domain.user.entity.User;
import io.dcns.wantitauction.domain.user.repository.UserRepository;
import jakarta.persistence.EntityNotFoundException;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class LikeService {

    private final LikeRepository likeRepository;
    private final AuctionItemRepository auctionItemRepository;
    private final UserRepository userRepository;

    public LikeResponseDto likeAuctionItem(Long auctionitemId, User user) {
        AuctionItem auctionItem = validateAuctionItem(auctionitemId);
        Optional<Like> alreadyLiked = likeRepository.findByAuctionIdAndUserId(auctionitemId,
            user.getUserId());

        if (alreadyLiked.isPresent()) {
            Like like = alreadyLiked.get();
            likeRepository.delete(like);
            return new LikeResponseDto(alreadyLiked.get().getLikeId(), user.getUserId(),
                auctionItem.getAuctionItemId(), false);
        } else {
            Like like = new Like(user, auctionitemId);
            likeRepository.save(like);
            return new LikeResponseDto(like.getLikeId(), user.getUserId(),
                auctionItem.getAuctionItemId(), true);
        }
    }

    public List<LikeResponseDto> getlikedAuctionItem(Long userId) {
        User user = getUser(userId);
        List<Like> likedAuctionItemList = likeRepository.findAllByUserId(user.getUserId())
            .orElseThrow((() -> new NoSuchElementException("찜한 경매상품을 찾을 수 없습니다.")));

        return likedAuctionItemList.stream().map(
                like -> new LikeResponseDto(
                    like.getLikeId(), user.getUserId(), like.getAuctionId(), true))
            .collect(Collectors.toList());
    }

    private AuctionItem validateAuctionItem(Long auctionitemId) {
        AuctionItem auctionItem = auctionItemRepository.findById(auctionitemId).orElseThrow(
            () -> new NoSuchElementException("해당 경매상품을 찾을 수 없습니다."));
        return auctionItem;
    }

    private User getUser(Long userId) {
        return userRepository.findByUserId(userId)
            .orElseThrow(() -> new EntityNotFoundException("존재하지 않는 사용자입니다."));
    }
}
