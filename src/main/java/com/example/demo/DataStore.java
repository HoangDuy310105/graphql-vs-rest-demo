package com.example.demo;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class DataStore {

    // Khởi tạo List có thể thay đổi (Mutable List)
    private static List<Author> authors = new ArrayList<>(Arrays.asList(
            new Author("1", "Nguyen Van A", 20, "0987654321", "nva@gmail.com"),
            new Author("2", "Tran Thi B", 22, "0123456789", "ttb@gmail.com")
    ));

    private static List<Post> posts = new ArrayList<>(Arrays.asList(
            new Post("101", "Căn bản về GraphQL", "Hôm nay chúng ta sẽ học lý thuyết", "1"),
            new Post("102", "Cách dùng REST API", "Ví dụ chi tiết", "1"),
            new Post("103", "Series Spring Boot", "Hướng dẫn tạo Project", "2")
    ));

    public static List<Author> getAuthors() {
        return authors;
    }

    public static Author getAuthorById(String id) {
        return authors.stream().filter(a -> a.getId().equals(id)).findFirst().orElse(null);
    }

    public static List<Post> getPosts() {
        return posts;
    }

    public static List<Post> getPostsByAuthorId(String authorId) {
        return posts.stream().filter(p -> p.getAuthorId().equals(authorId)).collect(Collectors.toList());
    }

    public static Post addPost(String title, String content, String authorId) {
        // Tạo ID giả (tăng dần) dựa trên số lượng hiện tại
        String id = String.valueOf(posts.size() + 101);
        Post newPost = new Post(id, title, content, authorId);
        posts.add(newPost);
        return newPost;
    }
}
