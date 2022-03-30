package dao;

import com.kuang.dao.TeacherMapper;
import com.kuang.pojo.Teacher;
import com.kuang.util.MybatisUtils;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;
import java.util.List;

/**
 * 一对多查询
 * 一个老师对应多个学生
 */
public class TeacherTest {

    @Test
    public void testgetTeacher() {
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        TeacherMapper mapper = sqlSession.getMapper(TeacherMapper.class);
        List<Teacher> teachers = mapper.getTeachers(1);
        for (Teacher teacher : teachers) {
            System.out.println(teacher);
        }
        /*Teacher(
        id=1,
        name=秦老师,
        students=[Student(id=1, name=a, tid=1),
                  Student(id=2, name=b, tid=1),
                  Student(id=3, name=c, tid=1),
                  Student(id=4, name=d, tid=1),
                  Student(id=5, name=e, tid=1)])
        */
        sqlSession.close();
    }
}
