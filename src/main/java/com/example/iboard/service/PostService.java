package com.example.iboard.service;

import com.example.iboard.dao.*;
import com.example.iboard.dto.*;
import com.example.iboard.entity.*;
import com.example.iboard.exception.*;
import com.example.iboard.util.*;
import org.springframework.beans.factory.annotation.*;
import org.springframework.stereotype.*;

import java.util.*;

@Service
public class PostService {
  @Autowired
  private PostDao postDao;
  @Autowired
  private CommentDao commentDao;
  private static final long BLOCK_SIZE = 5;

  public PostDto.Pages findAll(long pageno, long pagesize) {
    long totalcount = postDao.count();
    List<Post> posts = postDao.findAll(pageno, pagesize);
    return PagingUtil.getPages(pageno, pagesize, BLOCK_SIZE, totalcount, posts);
  }

  public PostDto.Read findByPno(long pno, boolean includeComments, String loginId) {
    Post post = postDao.findByPno(pno).orElseThrow(()->new JobFailException("글을 찾을 수 없습니다"));
    if(loginId!=null && !post.getWriter().equals(loginId)) {
      postDao.increaseReadCnt(pno);
      post.increaseReadCnt();
    }

    List<Comment> comments = null;
    if(includeComments)
      comments = commentDao.findByPno(pno);
    return new PostDto.Read(post, comments);
  }

  public Post write(PostDto.Write dto, String loginId) {
    Post post = dto.toEntity(loginId);
    postDao.insert(post);
    return post;
  }

  public void update(PostDto.Update dto, String loginId) {
    Post post = postDao.findByPno(dto.getPno()).orElseThrow(()->new JobFailException("글을 찾을 수 없습니다"));
    if(!post.getWriter().equals(loginId))
      throw new JobFailException("잘못된 작업있니다");
    postDao.update(dto.getTitle(), dto.getContent(), dto.getPno());
  }

  public void delete(long pno, String loginId) {
    Post post = postDao.findByPno(pno).orElseThrow(()->new JobFailException("글을 찾을 수 없습니다"));
    if(!post.getWriter().equals(loginId))
      throw new JobFailException("잘못된 작업있니다");
    postDao.delete(pno);
  }
}












