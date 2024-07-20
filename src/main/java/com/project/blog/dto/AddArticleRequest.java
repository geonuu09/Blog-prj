package com.project.blog.dto;

import com.project.blog.domain.Article;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@AllArgsConstructor
@Getter
public class AddArticleRequest {

    private String title;
    private String content;


    public Article toEntity(String author) { // DTO -> Entity로 만들어주는 메서드
        return Article.builder()
                .title(title)
                .content(content)
                .author(author)
                .build();
    }
}
