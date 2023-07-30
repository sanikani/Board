package com.study;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.study.domain.post.PostMapper;
import com.study.domain.post.PostRequest;
import com.study.domain.post.PostResponse;
import com.study.domain.post.PostService;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.assertj.core.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class PostServiceTest {

    @Autowired
    PostService postService;
    @Autowired
    PostMapper postMapper;

    @Test
    void savePost() {
        PostRequest params = new PostRequest();
        params.setTitle("1번 게시글 제목");
        params.setContent("1번 게시글 내용");
        params.setWriter("테스터");
        params.setNoticeYn(false);
        Long id = postService.savePost(params);
        System.out.println("생성된 게시글 ID : " + id);
        assertThat(id).isEqualTo(params.getId());
    }

    @Test
    void updatePost() {
        PostRequest params = new PostRequest();
        params.setId(2L);
        params.setTitle("2번 게시글 제목 수정");
        params.setContent("2번 게시글 내용 수정");
        params.setWriter("김산이");
        params.setNoticeYn(true);
        Long id = postService.updatePost(params);
        System.out.println("수정된 게시글 ID : " + id);
        assertThat(id).isEqualTo(params.getId());
        PostResponse post = postMapper.findById(2L);
        try {
            String postJson = new ObjectMapper().registerModule(new JavaTimeModule()).writeValueAsString(post);
            System.out.println(postJson);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    @Test
    void deletePost() {
        Long id = postService.deletePost(1L);
        System.out.println("삭제된 게시글 ID : " + id);

    }

    @Test
    void findAllPost() {
        List<PostResponse> posts = postService.findAllPost();
        System.out.println("전체 게시글 갯수는 " + posts.size() + "개 입니다");
    }
}