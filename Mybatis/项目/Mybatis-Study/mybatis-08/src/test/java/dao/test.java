package dao;

import com.kuang.dao.BlogMapper;
import com.kuang.pojo.Blog;
import com.kuang.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class test {

    @Test
    public void testIF(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
         HashMap hashMap = new HashMap();
         hashMap.put("title","JAVA如此简单");
        hashMap.put("author","zkf");
        List<Blog> blogs = mapper.getBolgIF(hashMap);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }


    @Test
    public void testChoose(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap hashMap = new HashMap();
        hashMap.put("title","JAVA如此简单");
//        hashMap.put("author","zkf");
        /**
         * choose在传参的时候不能为空，必须传一个参数
         * 如果传入多个参数，则只会选择第一个条件，而且只选择一个条件
         */
        List<Blog> blogs = mapper.getBolgChoose(hashMap);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }

    @Test
    public void testUpdate(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap hashMap = new HashMap();
        hashMap.put("id","89f5aba5f2904753a4cf35f657f0a5c4");
        hashMap.put("title","JAVA如此简单hhh");
        hashMap.put("author","zkf");
        int i = mapper.updateBlog(hashMap);
        sqlSession.commit();
        sqlSession.close();
    }


    @Test
    public void testForeach(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        BlogMapper mapper = sqlSession.getMapper(BlogMapper.class);
        HashMap hashMap = new HashMap();
        ArrayList<Object> arrayList = new ArrayList<>();
        arrayList.add(1);
        arrayList.add(2);
        hashMap.put("ids",arrayList);
        List<Blog> blogs = mapper.queryBlogForeach(hashMap);
        for (Blog blog : blogs) {
            System.out.println(blog);
        }
        sqlSession.close();
    }
}
