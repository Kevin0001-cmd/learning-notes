package com.kuang.dao;

import com.kuang.pojo.Blog;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public interface BlogMapper {

    int getId();

    List<Blog> getBolgIF(Map map);

    List<Blog> getBolgChoose(Map map);

    int updateBlog(Map map);

    List<Blog> queryBlogForeach(Map map);

}
