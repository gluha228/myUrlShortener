package com.db.repository;

import com.db.enity.ShortenedUrl;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ShortenedUrlRepository extends JpaRepository<ShortenedUrl, String> {
    Optional<ShortenedUrl> findFirstByOriginalUrl(String originalUrl);
    Optional<ShortenedUrl> findFirstByShortUrl(Long shortUrl);
}