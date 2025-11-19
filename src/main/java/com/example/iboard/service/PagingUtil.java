package com.example.iboard.service;

import com.example.demo.dto.*;
import com.example.demo.entity.*;

import java.util.*;

public class PagingUtil {
  public static PostDto.Pages getPages(int pageno, int pagesize, int blocksize, int totalcount, List<Post> posts) {
    int numberOfPages = (int) (Math.ceil((double)totalcount/pagesize));
    int prev = (pageno-1)/blocksize * blocksize;
    int start = prev + 1;
    int end = prev + blocksize;
    int next = end + 1;
    if(end>=numberOfPages) {
      end = numberOfPages;
      next = 0;
    }
    return new PostDto.Pages(prev, start, end, next, pageno, posts);
  }
}
