package io.dcns.wantitauction.global.jwt;

import java.util.concurrent.TimeUnit;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Repository;

@Repository
@Slf4j(topic = "refreshTokenRepository")
public class RefreshTokenRepository {

    private static final String TOKEN_PREFIX = "token_";  // 식별하기 편하게 하기 위해서, redis에 저장할때 사용한다.

    private final RedisTemplate<String, String> redisTemplate;

    private final ValueOperations<String, String> valueOperations;

    public RefreshTokenRepository(
        RedisTemplate<String, String> redisTemplate,
        @Qualifier("redisTemplate") ValueOperations<String, String> valueOperations
    ){
        this.redisTemplate = redisTemplate;
        this.valueOperations = valueOperations;
    }

    public void delete(final String subject) {
        redisTemplate.delete(TOKEN_PREFIX + subject);
    }

    public void save(final Long userId, final String refreshToken, Long expiration) {
        valueOperations.set(TOKEN_PREFIX + userId, refreshToken);
        redisTemplate.expire(TOKEN_PREFIX + userId, expiration, TimeUnit.SECONDS);
    }

    public boolean existsByUserId(Long userId) {
        return Boolean.TRUE.equals(valueOperations.getOperations().hasKey(TOKEN_PREFIX + userId));
    }

    public String findByUserId(Long userId) {
        return valueOperations.get(TOKEN_PREFIX + userId);
    }
}
