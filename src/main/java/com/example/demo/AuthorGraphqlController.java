package com.example.demo;

import org.springframework.graphql.data.method.annotation.Argument;
import org.springframework.graphql.data.method.annotation.QueryMapping;
import org.springframework.graphql.data.method.annotation.SchemaMapping;
import org.springframework.stereotype.Controller;

import java.util.List;

@Controller
public class AuthorGraphqlController {

    @QueryMapping
    public Author author(@Argument String id) {
        return DataStore.getAuthorById(id);
    }

    @SchemaMapping
    public List<Post> posts(Author author) {
        return DataStore.getPostsByAuthorId(author.getId());
    }

    @org.springframework.graphql.data.method.annotation.MutationMapping
    public Post createPost(@Argument String title, @Argument String content, @Argument String authorId) {
        return DataStore.addPost(title, content, authorId);
    }
}
