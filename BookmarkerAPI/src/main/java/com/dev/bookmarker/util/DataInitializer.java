package com.dev.bookmarker.util;

import com.dev.bookmarker.entity.Bookmark;
import com.dev.bookmarker.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;

import java.time.Instant;
import java.util.List;

//@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final BookmarkRepository bookmarkRepository;

    @Override
    public void run(String... args) throws Exception {
        bookmarkRepository.saveAll(List.of(
                new Bookmark(null, "SpringBlog", "https://spring.io/blog", Instant.now()),
                new Bookmark(null, "DZone", "https://dzone.com", Instant.now()),
                new Bookmark(null, "Baeldung", "https://baeldung.com", Instant.now()),
                new Bookmark(null, "Quarkus", "https://quarkus.com", Instant.now()))
        );
    }
}
