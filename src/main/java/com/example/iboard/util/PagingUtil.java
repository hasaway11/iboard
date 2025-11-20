package com.example.iboard.util;

import com.example.iboard.dto.*;
import com.example.iboard.entity.*;

import java.util.*;

public class PagingUtil {
  public static PostDto.Pages getPages(long pageno, long pagesize, long blocksize, long totalcount, List<Post> posts) {
    long numberOfPages = (int) (Math.ceil((double)totalcount/pagesize));
    long prev = (pageno-1)/blocksize * blocksize;
    long start = prev + 1;
    long end = prev + blocksize;
    long next = end + 1;
    if(end>=numberOfPages) {
      end = numberOfPages;
      next = 0;
    }
    return new PostDto.Pages(prev, start, end, next, pageno, posts);
  }
}
