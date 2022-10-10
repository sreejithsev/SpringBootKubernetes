package com.dev.bookmarker.service;

import com.dev.bookmarker.dto.BookmarksDTO;
import com.dev.bookmarker.repository.BookmarkRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static org.springframework.data.domain.Sort.Direction.DESC;

@Service
@Transactional
@RequiredArgsConstructor
public class BookmarkService {

    private final BookmarkRepository bookmarkRepository;

    @Transactional(readOnly = true)
    public BookmarksDTO getBookmarks(Integer page){
        int pageNo = page < 1 ? 0 : page-1;
        var pageable = PageRequest.of(pageNo, 3, DESC, "createdAt");
        return new BookmarksDTO(bookmarkRepository.findBookmarks(pageable));
    }
}
