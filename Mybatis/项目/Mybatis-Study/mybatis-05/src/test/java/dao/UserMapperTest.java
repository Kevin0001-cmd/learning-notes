package dao;

import com.kuang.dao.UserMapper;
import com.kuang.pojo.User;
import com.kuang.utils.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.util.List;

public class UserMapperTest {

    @Test
    public void test() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        //底层主要应用反射
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        List<User> users = mapper.getUsers();
        for (User user : users) {
            System.out.println(user);
        }
        sqlSession.close();
    }

    //测试使用注解开发
    @Test
    public void testgetUser() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        //查询
        List<User> userList = mapper.getUsers();
        for (User user : userList) {
            System.out.println(user);
        }

        //根据id查询
        User userById = mapper.getUserById(3);
        System.out.println(userById);

        //增加
        int i = mapper.insertUser(new User(7, "aaa", "12344"));

        //修改
        int j = mapper.updateUser(new User(1, "rrr", "456"));

        //删除
        int k = mapper.deleteUser(1);

        sqlSession.close();
    }
}
