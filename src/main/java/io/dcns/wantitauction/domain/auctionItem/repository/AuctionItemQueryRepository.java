package io.dcns.wantitauction.domain.auctionItem.repository;

import static io.dcns.wantitauction.domain.auctionItem.entity.QAuctionItem.auctionItem;

import com.querydsl.core.types.Projections;
import com.querydsl.jpa.impl.JPAQueryFactory;
import io.dcns.wantitauction.domain.auctionItem.dto.AuctionItemResponseDto;
import io.dcns.wantitauction.domain.auctionItem.dto.FinishedItemResponseDto;
import io.dcns.wantitauction.domain.auctionItem.entity.AuctionItemEnum;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class AuctionItemQueryRepository {

    private final JPAQueryFactory jpaQueryFactory;

    public List<AuctionItemResponseDto> findAll() {
        return jpaQueryFactory
            .select(Projections.fields(AuctionItemResponseDto.class,
                auctionItem.auctionItemId,
                auctionItem.userId,
                auctionItem.itemName,
                auctionItem.productDescription,
                auctionItem.minPrice,
                auctionItem.maxPrice,
                auctionItem.startDate,
                auctionItem.endDate,
                auctionItem.status))
            .from(auctionItem)
            .fetch();
    }

    public List<FinishedItemResponseDto> findAllByFinished() {
        return jpaQueryFactory
            .select(Projections.fields(FinishedItemResponseDto.class,
                auctionItem.auctionItemId,
                auctionItem.userId,
                auctionItem.winnerId,
                auctionItem.itemName,
                auctionItem.productDescription,
                auctionItem.minPrice,
                auctionItem.maxPrice,
                auctionItem.startDate,
                auctionItem.endDate))
            .from(auctionItem)
            .where(auctionItem.status.eq(AuctionItemEnum.FINISHED))
            .fetch();
    }

    public Optional<FinishedItemResponseDto> findByIdAndFinished(Long auctionItemId) {
        return Optional.ofNullable(
            jpaQueryFactory
                .select(Projections.fields(FinishedItemResponseDto.class,
                    auctionItem.auctionItemId,
                    auctionItem.userId,
                    auctionItem.winnerId,
                    auctionItem.itemName,
                    auctionItem.productDescription,
                    auctionItem.minPrice,
                    auctionItem.maxPrice,
                    auctionItem.startDate,
                    auctionItem.endDate))
                .from(auctionItem)
                .where(auctionItem.auctionItemId.eq(auctionItemId)
                    .and(auctionItem.status.eq(AuctionItemEnum.FINISHED)))
                .fetchFirst()
        );
    }
}
