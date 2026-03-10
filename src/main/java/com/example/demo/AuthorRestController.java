package com.example.demo;

import java.util.List;

import org.springframework.http.CacheControl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    // Đường dẫn 1: Lấy thông tin Tác giả — trả 404 nếu không tìm thấy (REST dùng đúng HTTP Status)
    // Đồng thời thêm Cache-Control header để browser/CDN có thể cache (lợi thế REST)
    @GetMapping("/{id}")
    public ResponseEntity<Author> getAuthor(@PathVariable String id) {
        Author author = DataStore.getAuthorById(id);
        if (author == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS).cachePublic())
                .body(author);
    }

    // Đường dẫn 2: Lấy danh sách Bài viết của tác giả — có cache
    @GetMapping("/{id}/posts")
    public ResponseEntity<List<Post>> getAuthorPosts(@PathVariable String id) {
        List<Post> posts = DataStore.getPostsByAuthorId(id);
        return ResponseEntity.ok()
                .cacheControl(CacheControl.maxAge(30, TimeUnit.SECONDS).cachePublic())
                .body(posts);
    }

    // Đường dẫn 3: Tạo bài viết mới (REST POST)
    @org.springframework.web.bind.annotation.PostMapping("/{id}/posts")
    public ResponseEntity<Post> createPost(@PathVariable String id,
            @org.springframework.web.bind.annotation.RequestBody PostRequestBody request) {
        Post post = DataStore.addPost(request.getTitle(), request.getContent(), id);
        return ResponseEntity.status(HttpStatus.CREATED).body(post);
    }

    // Đường dẫn 3: Tạo bài viết mới (REST POST)
    @org.springframework.web.bind.annotation.PostMapping("/{id}/posts")
    public Post createPost(@PathVariable String id, @org.springframework.web.bind.annotation.RequestBody PostRequest request) {
        return DataStore.addPost(request.getTitle(), request.getContent(), id);
    }
}

class PostRequest {
    private String title;
    private String content;

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getContent() { return content; }
    public void setContent(String content) { this.content = content; }
}
