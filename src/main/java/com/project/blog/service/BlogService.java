package com.project.blog.service;

import com.project.blog.domain.Article;
import com.project.blog.dto.AddArticleRequest;
import com.project.blog.dto.UpdateArticleRequest;
import com.project.blog.repository.BlogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor // final, not null 이 붙은 필드의 생성자 추가
public class BlogService {

    private final BlogRepository blogRepository;

    public Article save(AddArticleRequest request, String userName) {
        return blogRepository.save(request.toEntity(userName));
    }

    public List<Article> findAll() {
        return blogRepository.findAll();
    }
    public Article findById(Long id) {
        return blogRepository.findById(id)
                .orElseThrow(()-> new IllegalArgumentException("not found: "+ id));
    }

    public void delete(Long id) {
        Article article = blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("not found: "+ id));

        authorizeArticleAuthor(article);
        blogRepository.delete(article);
    }

    @Transactional
    public Article update(Long id, UpdateArticleRequest request) {
        Article article = blogRepository.findById(id).orElseThrow(()-> new IllegalArgumentException("not found: "+ id));

        authorizeArticleAuthor(article);
        article.update(request.getTitle(), request.getContent());
        return article;
    }

    // 게시글을 작성한 유저인지 확인
    // 이 메서드를 통해 수정 삭제 메서드 수행 전 현재 인증 객체에 담겨 있는 사용자의 정보와 글을 작성한 사용자의 정보를 비교한다.
    // 서로 다르면 예외를 발생시켜 작업 수행 X
    private static void authorizeArticleAuthor(Article article) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();

        if (!article.getAuthor().equals(username)) {
            throw new IllegalArgumentException("not authorized");
        }
    }


}
