package com.example.demo;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/authors")
public class AuthorRestController {

    // Đường dẫn 1: Lấy thông tin Tác giả (Gây ra Over-fetching)
    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable String id) {
        return DataStore.getAuthorById(id);
    }

    // Đường dẫn 2: Lấy danh sách Bài viết của tác giả (Khách hàng phải gọi 2 lần gây Under-fetching)
    @GetMapping("/{id}/posts")
    public List<Post> getAuthorPosts(@PathVariable String id) {
        return DataStore.getPostsByAuthorId(id);
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
