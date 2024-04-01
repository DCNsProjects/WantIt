package io.dcns.wantitauction.domain.like.entity;

import io.dcns.wantitauction.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor
@Table(name = "likes")
public class Like {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long likeId;

    @Column(nullable = false)
    private Long userId;

    @Column(nullable = false)
    private Long auctionId;

    private Integer count;

    public Like(User user, Long auctionitemId) {
        this.userId = user.getUserId();
        this.auctionId = auctionitemId;
    }

    public void addCount(Integer count) {
        this.count = this.count + count;
    }
}


