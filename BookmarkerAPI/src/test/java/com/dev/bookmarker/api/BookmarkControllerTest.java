package com.dev.bookmarker.api;

import com.dev.bookmarker.entity.Bookmark;
import com.dev.bookmarker.repository.BookmarkRepository;
import org.hamcrest.CoreMatchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.web.servlet.MockMvc;

import java.time.Instant;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
@TestPropertySource(properties = {
        "spring.datasource.url=jdbc:tc:postgresql:14-alpine:///demo"
})
class BookmarkControllerTest {

    @Autowired
    private MockMvc mvc;

    @Autowired
    private BookmarkRepository repository;

    @BeforeEach
    void setUp(){
        repository.deleteAllInBatch();

        var bookmarks = List.of(new Bookmark(null, "SpringBlog", "https://spring.io/blog", Instant.now()),
                new Bookmark(null, "DZone", "https://dzone.com", Instant.now()),
                new Bookmark(null, "Baeldung", "https://baeldung.com", Instant.now()),
                new Bookmark(null, "Quarkus", "https://quarkus.com", Instant.now()));

        repository.saveAll(bookmarks);
    }

    @ParameterizedTest
    @CsvSource({
            "1,4,2,1,true,false,true,false",
            "2,4,2,2,false,true,false,true"
    })
    void shouldGetBookmarks(int page, int totalElements, int totalPages, int currentPage,
                            boolean hasNext, boolean hasPrevious, boolean isFirst, boolean isLast) throws Exception {
        mvc.perform(get("/api/bookmarks?page="+page))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.totalElements", CoreMatchers.equalTo(totalElements)))
                .andExpect(jsonPath("$.totalPages", CoreMatchers.equalTo(totalPages)))
                .andExpect(jsonPath("$.currentPage", CoreMatchers.equalTo(currentPage)))
                .andExpect(jsonPath("$.hasNext", CoreMatchers.equalTo(hasNext)))
                .andExpect(jsonPath("$.hasPrevious", CoreMatchers.equalTo(hasPrevious)))
                .andExpect(jsonPath("$.isFirst", CoreMatchers.equalTo(isFirst)))
                .andExpect(jsonPath("$.isLast", CoreMatchers.equalTo(isLast)));
    }
}