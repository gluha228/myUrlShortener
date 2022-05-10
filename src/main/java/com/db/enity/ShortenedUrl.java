package com.db.enity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "SHORTENED_URL")
public class ShortenedUrl {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long shortUrl;
    private String originalUrl;

    public ShortenedUrl(String originalUrl) {
        this.originalUrl = originalUrl;
    }
}
