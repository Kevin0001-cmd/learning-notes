# Mybatis

## 1、简介

### 1.1 、什么是Mybatis

* MyBatis 是一款优秀的**持久层框架**，
* 它支持自定义 SQL、存储过程以及高级映射。
* MyBatis 免除了几乎所有的 JDBC 代码以及设置参数和获取结果集的工作。
* MyBatis 可以通过简单的 XML 或注解来配置和映射原始类型、接口和 Java POJO（Plain Old Java Objects，普通老式 Java 对象）为数据库中的记录。



如何获得Mybatis?

* maven仓库

  ```
  <!-- https://mvnrepository.com/artifact/org.mybatis/mybatis -->
  <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>3.5.2</version>
  </dependency>
  
  ```

  

* Github：https://github.com/mybatis/mybatis-3

* 中文文档：https://github.com/tuguangquan/mybatis



### 1.2、持久化

数据持久化

* 持久化就是将程序的数据在持久状态和瞬时状态转化的过程

* 内存：断电即失

* 数据库（Jdbc），io文件持久化。

* 生活：冷藏.罐头。

  为什么需要需要持久化？

* 有一些对象，不能让他丢掉。

* 内存太贵了



### 1.3、持久层

Dao层，Service层，Controller层.…

* 完成持久化工作的代码块

* 层界限十分明显。

  

### 1.4 为什么需要Mybatis？

* 帮助程序猿将数据存入到数据库中。
* 方便
* 传统JDBC代码太复杂了。简化。框架。自动化。
* 不用Mybatis也可以，只是Mybatis更容易上手，**技术没有高低之分**。

* 优点：
  * 简单易学
  * 灵活
  * sql和代码的分离，提高了可维护性。
  * 提供映射标签，支持对象与数据库的orm字段关系映射
  * 提供对象关系映射标签，支持对象关系组建维护
  * 提供xml标签，支持编写动态sql。



**最重要的一点：使用的人多！**
Spring SpringMVC SpringBoot



## 2、第一个Mybatis程序

思路：搭建环境-->导入Mybatis-->编写代码-->测试！（多学一些学习的方式）

### 2.1 搭建环境

搭建数据库

```
CREATE DATABASE mybatis;
USE mybatis;

CREATE TABLE `user`(
`id` INT(20) NOT NULL PRIMARY KEY,
`name` VARCHAR(30) DEFAULT NULL,
`pwd` VARCHAR(30) DEFAULT NULL
)ENGINE=INNODB DEFAULT CHARSET=utf8;

INSERT INTO `user` (id,`name`,pwd)
 VALUES(1,'狂神','123'),(2,'张三','123657'),(3,'李四','123890');
```

1. 新建项目

2. 删除src目录

3. 导入maven依赖

   ```
   <dependencies>
           <dependency>
               <groupId>mysql</groupId>
               <artifactId>mysql-connector-java</artifactId>
               <version>5.1.47</version>
           </dependency>
           <dependency>
               <groupId>org.mybatis</groupId>
               <artifactId>mybatis</artifactId>
               <version>3.5.2</version>
           </dependency>
           <dependency>
               <groupId>junit</groupId>
               <artifactId>junit</artifactId>
               <version>4.12</version>
           </dependency>
   
       </dependencies>
   ```

### 2.2 创建一个模块

* 编写mybatis的核心配置文件

  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="12344"/>
              </dataSource>
          </environment>
      </environments>
  
  </configuration>
  ```

* 编写mybatis的工具类

  ```
  public class MybatisUtils {
      public static SqlSessionFactory sqlSessionFactory;
  
      static {
          try {
              //使用Mybatis的第一步：获取SqlSessionFactory对象
              String resource = "mybatis-config.xml";
              InputStream inputStream = Resources.getResourceAsStream(resource);
              sqlSessionFactory = new SqlSessionFactoryBuilder().build(inputStream);
          } catch (IOException e) {
              e.printStackTrace();
          }
      }
  
      public static SqlSession getSqlSession() {
          return sqlSessionFactory.openSession();
      }
  }
  
  ```

### 2.3 编写代码

* 实体类

  ```
  package com.kuang.pojo;
  
  public class User {
      private int id;
      private String name;
      private String pwd;
  
      public int getId() {
          return id;
      }
  
      public void setId(int id) {
          this.id = id;
      }
  
      public String getName() {
          return name;
      }
  
      public void setName(String name) {
          this.name = name;
      }
  
      public String getPwd() {
          return pwd;
      }
  
      public void setPwd(String pwd) {
          this.pwd = pwd;
      }
  
      @Override
      public String toString() {
          return "User{" +
                  "id=" + id +
                  ", name='" + name + '\'' +
                  ", pwd='" + pwd + '\'' +
                  '}';
      }
  }
  
  ```

  

* Dao接口

  ```
  package com.kuang.dao;
  
  import com.kuang.pojo.User;
  
  import java.util.List;
  
  public interface UserDao {
      List<User> getUserList();
  }
  
  ```

  

* 实现类

  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE mapper
          PUBLIC "-//mybatis.org//DTD Mapper 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
  <mapper namespace="com.kuang.dao.UserDao">
      <select id="getUserList" resultType="com.kuang.pojo.User">
      select * from mybatis.user
    </select>
  </mapper>
  ```

* 在mybatis-config.xml中注册UserMapper.xml

  ```
  <?xml version="1.0" encoding="UTF-8" ?>
  <!DOCTYPE configuration
          PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
          "http://mybatis.org/dtd/mybatis-3-config.dtd">
  <configuration>
      <environments default="development">
          <environment id="development">
              <transactionManager type="JDBC"/>
              <dataSource type="POOLED">
                  <property name="driver" value="com.mysql.jdbc.Driver"/>
                  <property name="url" value="jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8"/>
                  <property name="username" value="root"/>
                  <property name="password" value="12344"/>
              </dataSource>
          </environment>
      </environments>
      <mappers>
          <mapper resource="com/kuang/dao/UserMapper.xml"/>
      </mappers>
  
  </configuration>
  ```

* 写测试类

  ```
  package com.kuang.dao;
  
  import com.kuang.pojo.User;
  import com.kuang.utils.MybatisUtils;
  import org.apache.ibatis.session.SqlSession;
  import org.junit.Test;
  
  import java.util.List;
  
  
  public class UserDaoTest {
      @Test
      public void Test(){
          SqlSession sqlSession = MybatisUtils.getSqlSession();
          try {
              //方式一：getMapper
              UserDao mapper = sqlSession.getMapper(UserDao.class);
              List<User> userList = mapper.getUserList();
  
              //方式二：
  //            List<UserDao> userList = sqlSession.selectList("com.kuang.dao.UserDao.getUserList");
  
              for (User user : userList) {
                  System.out.println(user);
              }
  
          }catch (Exception e){
              e.printStackTrace();
          }finally {
              sqlSession.close();
          }
      }
  }
  
  ```

注意：

1. UserMapper.xml文件无法放到target里面，解决：在pom.xml加入如下内容：

   ```
   <build>
           <resources>
               <resource>
                   <directory>src/main/resources</directory>
                   <includes>
                       <include>**/*.properties</include>
                       <include>**/*.xml</include>
                   </includes>
                   <filtering>false</filtering>
               </resource>
               <resource>
                   <directory>src/main/java</directory>
                   <includes>
                       <include>**/*.xml</include>
                   </includes>
                   <filtering>false</filtering>
               </resource>
           </resources>
       </build>
   ```

可能会遇到的问题：

1. 配置文件没有注册
2. 绑定接口错误
3. 方法名不对
4. 返回类型不对
5. Maven导出资源问题

# 3.CRUD

### 3.1 namespace

namespace中的包名要和Dao/mapper接口的包名一致！

### 3.2 select

选择，查询语句

* id：就是对应的namespace中的方法名
* resultType：Sql语句执行的返回值
* parameterType：参数类型



1. 编写接口

   ```
       User selectUser(int id);
   
   ```

   

2. 编写对应的mapper中的sql语句

   ```
    <select id="selectUser" resultType="com.kuang.pojo.User">
           select * from mybatis.user where id = #{id}
       </select>
   ```

   

3. 测试

   ```
   @Test
       public void selectUser(){
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           User user = mapper.selectUser(1);
           System.out.println(user);
       }
   ```

   

### 3.3 Insert

1. 编写接口

   ```
      int insertUser(User user);
   
   ```

   

2. 编写对应的mapper中的sql语句

   ```
    <insert id="insertUser" parameterType="com.kuang.pojo.User">
           insert into mybatis.user (id,name,pwd) values (#{id},#{name},#{pwd})
       </insert>
   ```

   

3. 测试

   ```
    @Test
       public void insertUser(){
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           int i = mapper.insertUser(new User(5,"zzz","123123"));
           sqlSession.commit();
           sqlSession.close();
           System.out.println("成功插入一个User");
   
       }
   ```

   

### 3.4 update

1. 编写接口

   ```
      int updateUser(User user);
   
   ```

   

2. 编写对应的mapper中的sql语句

   ```
    <update id="updateUser" parameterType="com.kuang.pojo.User">
           update mybatis.user set name=#{name},pwd=#{pwd} where id = #{id}
       </update>
   ```

   

3. 测试

   ```
   @Test
       public void updateUser(){
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           int i = mapper.updateUser(new User(1,"222","222"));
           sqlSession.commit();
           sqlSession.close();
           System.out.println("更新插入一个User");
   
       }
   ```

   

### 3.5 Delete

1. 编写接口

   ```
       int deleteUser(int id);
   
   ```

   

2. 编写对应的mapper中的sql语句

   ```
   <delete id="deleteUser" parameterType="int">
           delete from  mybatis.user where id = #{id}
       </delete>
   ```

   

3. 测试

   ```
   @Test
       public void deleteUser(){
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           int i = mapper.deleteUser(3);
           sqlSession.commit();
           sqlSession.close();
           System.out.println("成功删除一个User");
       }
   ```

   

注意点：增删改需要提交事务！

### 3.6 分析错误

* 标签不要写错
* resource绑定mapper，需要使用路径
* 程序配置文件必须符合规范！
* NullPointerException，没有注册到资源！
* 输出的xml文件中存在中文乱码问题！
* maven资源没有导出问题！

### 3.7 万能Map

假设，我们的实体类，或者数据库中的表，字段或者参数过多，我们应当考虑使用Map！

```
  int addUser2(Map<String,Object> map);
```

```
 <insert id="addUser2" parameterType="map">
        insert into mybatis.user (id,name,pwd) values (#{userid},#{username},#{userpwd})
    </insert>
```

```
@Test
    public void addUser2(){
        SqlSession sqlSession = MybatisUtils.getSqlSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        Map<String, Object> map = new HashMap<String, Object>();
        map.put("userid",6);
        map.put("userpwd","zlf");
        mapper.addUser2(map);
        sqlSession.commit();
        sqlSession.close();
        System.out.println("成功插入一个User");
    }
```

Map传递参数，直接在sql中取出key即可！【parameterType="map"】
对象传递参数，直接在sql中取对象的属性即可！【parameterType="Object"】
只有一个基本类型参数的情况下，可以直接在sql中取到！

多个参数用Map，或者**注解**！

### 3.8 思考题

模糊查询怎么写？

1. Java代码执行的时候，传递通配符% %

   ```java
   List<User> userList = mapper.getUserLike("%李%");
   ```

   

2. 在sql拼接中使用通配符！

   ```xml
   select * from mybatis.user where name like "%"#{vallue}"%"
   ```



# 4.配置解析

### 1.核心配置文件

* mybatis-config.xml

* MyBatis 的配置文件包含了会深深影响 MyBatis 行为的设置和属性信息。

  ```
  configuration（配置）
  properties（属性）
  settings（设置）
  typeAliases（类型别名）
  typeHandlers（类型处理器）
  objectFactory（对象工厂）
  plugins（插件）
  environments（环境配置）
  environment（环境变量）
  transactionManager（事务管理器）
  dataSource（数据源）
  databaseIdProvider（数据库厂商标识）
  mappers（映射器）
  ```

### 2.环境配置（environments）

MyBatis 可以配置成适应多种环境

**不过要记住：尽管可以配置多个环境，但每个 SqlSessionFactory 实例只能选择一种环境。**

学会使用配置多套运行环境！

Mybatis默认的事务管理器就是JDBC，连接池：POOLED

### 3.属性（properties）

我们可以通过properties属性来实现引用配置文件

这些属性都是可外部配置且可动态替换的，既可以在典型的Java 属性文件中配置，亦可通过 properties元素的子元素来传递。[db.properties]

编写一个配置文件

db.properties

```properties
driver=com.mysql.jdbc.Driver
url=jdbc:mysql://localhost:3306/mybatis?useSSL=true&amp;useUnicode=true&amp;characterEncoding=UTF-8
username=root
password=12344
```

在核心配置文件中引入

```properties
<!--引入外部配置文件-->
    <properties resource="db.properties">
        <property name="username" value="root"/>  <!--添加一些配置-->
        <property name="password" value="12344"/>
    </properties>
```

* 可以直接引入外部文件
* 可以在其中增加一些属性配置
* 如果两个文件有同一个字段，优先使用外部配置文件的！

### 4.类型别名（typeAliases）

* 类型别名可为 Java 类型设置一个缩写名字。 

* 它仅用于 XML 配置，意在降低冗余的全限定类名书写。

```xml
<!--可以给实体类起别名-->
    <typeAliases>
        <!--给一个类取别名-->
        <typeAlias type="com.kuang.pojo.User" alias="User"/>
    </typeAliases>
```

也可以指定一个包名，MyBatis 会在包名下面搜索需要的Java Bean，比如：

扫描实体类的包，它的默认别名就为这个类的类名，首字母小写！

```xml
<!--可以给实体类起别名-->
<typeAliases>
        <package name="com.kuang.pojo"/>
</typeAliases>
```

在实体类比较少的时候，使用第一种方式。

如果实体类十分多，建议使用第二种。第一种可以DIY别名，第二种则不行，如果非要改，需要在实体类上增加注解

> 使用注解时，别名设置需要在扫描包的情况下

```java
@Alias("user")
public class User {
```

### 5.设置

这是 MyBatis 中极为重要的调整设置，它们会改变 MyBatis 的运行时行为。

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108281535028.png)

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108281533339.png)

### 6.其他配置

- [typeHandlers（类型处理器）](https://mybatis.org/mybatis-3/zh/configuration.html#typeHandlers)
- [objectFactory（对象工厂）](https://mybatis.org/mybatis-3/zh/configuration.html#objectFactory)
- [plugins（插件）](https://mybatis.org/mybatis-3/zh/configuration.html#plugins)
  * mybatis-generator-core
  * [mybatis-plus](https://mp.baomidou.com/guide/)
  * 通用mapper

### 7.映射器

MapperRegister：注册绑定我们的Mapper文件

方式一：【推荐使用】

```xml
<mappers>
        <mapper resource="com/kuang/dao/UserMapper.xml"/>
</mappers>
```

方式二：使用class文件绑定

```xml
<mappers>
        <mapper class="com.kuang.dao.UserMapper"/>
</mappers>
```

注意点：

* 接口和他的Mapper配置文件必须同名！

* 接口和他的Mapper配置文件必须在同一个包下！

  

方式三：使用扫描包进行注入绑定

```xml
<mappers>
        <package name="com.kuang.dao"/>
</mappers>
```

注意点：

* 接口和他的Mapper配置文件必须同名！
* 接口和他的Mapper配置文件必须在同一个包下！

> :bulb:org.apache.ibatis.binding.BindingException:Type interface com.kuang.dao.UserMapper is not known to the MapperRegistry.
>
> 这个报错的原因是xml文件没有注册，使用以上三种方式中的一种即可



练习时间

* 将数据库配置文件外部引入
* 实体类别名
* 保证UserMapper 接口和UserMapper.xml改为一致！并且放在同一个包下！



### 8.生命周期和作用域

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108281735737.png)

不同作用域和生命周期类别是至关重要的，因为错误的使用会导致非常严重的**并发问题**。

**SqlSessionFactoryBuilder**

* 一旦创建了 SqlSessionFactory，就不再需要它了。
* 局部变量

**SqlSessionFactory**

* 可以想象为：数据库连接池
* SqlSessionFactory 一旦被创建就应该在应用的运行期间一直存在，**没有任何理由丢弃它或重新创建另一个实例**。 
* SqlSessionFactory 的最佳作用域是应用作用域
* 最简单的就是使用单例模式或者静态单例模式

**SqlSession**

* 连接到连接池的请求
* SqlSession 的实例不是线程安全的，因此是不能被共享的，所以它的最佳的作用域是请求或方法作用域。
* 用完之后需要赶紧关闭，否则资源被占用！

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108281737795.png)



这里面的每一个Mapper，就代表一个具体的业务！



# 5.解决属性名和字段名不一致的问题

### 1.问题

数据库中的字段

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108281828009.png)

新建一个项目，拷贝之前的，测试实体类字段不一样的情况

```java
public class User {
    private int id;
    private String name;
    private String password;
}
```

出现问题

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108281847278.png)

```
select * from mybatis.user where id = #{id}
//类型处理器
select id,name,pwd from mybatis.user where id = #{id}
```



解决办法一

* 起别名

```xml

<select id="selectUser" resultType="com.kuang.pojo.User">
        select id,name,pwd as password from mybatis.user where id = #{id}
    </select>
```

### 2.起别名

结果集映射

```
id name pwd
id name password
```

```xml
<mapper namespace="com.kuang.dao.UserMapper">
    <resultMap id="UserMap" type="User">
        <result column="id" property="id"/>
        <result column="name" property="name"/> //id和name如果dao的字段和数据库一致的话，不写也可以
        <result column="pwd" property="password"/>
    </resultMap>

    <select id="selectUser" resultMap="UserMap">
        select * from mybatis.user where id = #{id}
    </select>
</mapper>
```

* resultMap 元素是MyBatis中最重要最强大的元素

* ResultMap的设计思想是，对于简单的语句根本不需要配置显式的结果映射，而对于复杂一点的语句只需要描述它们的关系就行了。

* ResultMap 最优秀的地方在于，虽然你已经对它相当了解了，但是根本就不需要显式地用到他们。



# 6.日志

## 6.1 日志工厂

如果一个数据库操作，出现了异常，我们需要排错，日志就是最好的助手！

曾经：sout,debug

现在：日志工厂

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202108301831373.png)

* SLF4J
* LOG4J
* LOG4J2
* JDK_LOGGING
* COMMONS_LOGGING 
* STDOUT_LOGGING 
* NO_LOGGING

配置日志

```xml
<settings>
        <setting name="logImpl" value="STDOUT_LOGGING"/>
</settings>
```

## 6.2 Log4j

1. 先导入log4j的包

   ```xml
    <dependency>
        <groupId>log4j</groupId>
        <artifactId>log4j</artifactId>
        <version>1.2.17</version>
   </dependency>
   ```

   

2. log4j.properties

   ```properties
   ### 设置###
   log4j.rootLogger = debug,stdout,D,E
   
   ### 输出信息到控制抬 ###
   log4j.appender.stdout = org.apache.log4j.ConsoleAppender
   log4j.appender.stdout.Target = System.out
   log4j.appender.stdout.layout = org.apache.log4j.PatternLayout
   log4j.appender.stdout.layout.ConversionPattern = [%-5p] %d{yyyy-MM-dd HH:mm:ss,SSS} method:%l%n%m%n
   
   ### 输出DEBUG 级别以上的日志到=E://logs/error.log ###
   log4j.appender.D = org.apache.log4j.DailyRollingFileAppender
   log4j.appender.D.File = E://logs/log.log
   log4j.appender.D.Append = true
   log4j.appender.D.Threshold = DEBUG 
   log4j.appender.D.layout = org.apache.log4j.PatternLayout
   log4j.appender.D.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
   
   ### 输出ERROR 级别以上的日志到=E://logs/error.log ###
   log4j.appender.E = org.apache.log4j.DailyRollingFileAppender
   log4j.appender.E.File =E://logs/error.log 
   log4j.appender.E.Append = true
   log4j.appender.E.Threshold = ERROR 
   log4j.appender.E.layout = org.apache.log4j.PatternLayout
   log4j.appender.E.layout.ConversionPattern = %-d{yyyy-MM-dd HH:mm:ss}  [ %t:%r ] - [ %p ]  %m%n
   ```

   

3. 配置log4j为日志的实现

   ```xml
   <settings>
       <setting name="logImpl" value="LOG4J"/>
   </settings>
   ```

4. Log4j的使用，直接测试刚才的查询

   ![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109031213080.png)



**简单使用**

1. 在要使用Log4j的类中，导入包import org.apache.log4j.Logger；

2. 日志对象，参数为当前类的class

   ```java
       static Logger logger = Logger.getLogger(UserMapperTest.class);
   ```

3. 日志级别

   ```java
   logger.info("info:进入了testLog4j");
   logger.debug("debug:进入了testLog4j");
   logger.debug("test:进入了testLog4j");
   ```



# 7.分页

思考：为什么要分页？

* 减少数据的处理量

  

## 7.1 **使用Limit分页**

```sql
语法：select * from user limit startIndex,pagesize;
select * from user limit 3;  #[0,n]
```



使用mybatis实现分页，核心SQL

1. 接口

   ```java
       List<User> getUserListByLimit(Map<String,Integer> map);
   ```

2. Mapper.xml

   ```xml
   <!--分页-->
       <select id="getUserListByLimit" parameterType="map" resultMap="UserMap">
           select * from mybatis.user limit #{startIndex},#{pageSize}
       </select>
   ```

3. 测试

   ```java
   @Test
       public void testgetUserListByLimit(){
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
   
           HashMap<String,Integer> map = new HashMap<String, Integer>();
           map.put("startIndex",0);
           map.put("pageSize",2);
   
           List<User> userList = mapper.getUserListByLimit(map);
           for (User user : userList) {
               System.out.println(user);
           }
           sqlSession.close();
       }
   ```

## 7.2 RowBounds分页

1. 接口

   ```java
   /*分页*/
       List<User> getUserListByRowBounds(String s, Object o, RowBounds rowBounds);
   ```

2. Mapper.xml

   ```xml
    <select id="getUserListByRowBounds" resultMap="UserMap">
        select * from mybatis.user
   </select>
   ```

3. 测试

   ```java
   @Test
       public void testgetUserListByRowBounds(){
           SqlSession sqlSession = MybatisUtils.getSqlSession();
           UserMapper mapper = sqlSession.getMapper(UserMapper.class);
           RowBounds rowBounds = new RowBounds(1,2);
           List<User> userList = mapper.getUserListByRowBounds("com.kuang.dao.UserMapper.getUserListByRowBounds", null, rowBounds);
           for (User user : userList) {
               System.out.println(user);
           }
           sqlSession.close();
       }
   ```

## 7.3 分页插件

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109051658690.png)

了解即可，万一以后公司的架构师，说要使用，你需要知道它是什么东西！	



# 8 使用注解开发

## 8.1 面向接口开发

1. 注解在接口上实现

   ```java
   @Select("select * from user")
   List<User> getUsers();
   ```

2. 需要在核心配置文件中绑定接口

   ```xml
   <mappers>
   	<mapper class="com.kuang.dao.UserMapper" />
   </mappers>
   ```

3. 测试





## 8.2 使用注解开发

1. 注解在接口上实现

   ```java
    @Select("select * from user")
   List<User> getUser();
   ```

   

2. 需要在核心配置文件中绑定接口

   ```xml
   <mappers>
      <mapper class="com.kuang.dao.UserMapper"/>
   </mappers>
   ```

3. 测试



本质：反射机制实现

底层：动态代理

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109051718077.png)





**Mybatis详细的执行流程**

![](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109101235545.png)





## 8.3 CRUD

我们可以在工具类创建的时候实现自动提交事务！

```java
public static SqlSession getSqlSession() {
        return sqlSessionFactory.openSession(true);  //添加true后事务会自动提交
    }
```



编写接口，增加注解

```java
	@Select("select * from user")
    List<User> getUser();

    @Select("select * from user where id = #{id}")
    User getUserById(@Param("id")int id);

    @Insert("insert into user(id,name,pwd) values(#{id},#{name},#{pwd})")
    int insertUser(User user);

    @Update("update user set name=#{name},pwd=#{pwd} where id = #{id}")
    int updateUser(User user);

    @Delete("delete from user where id = #{uid}")
    int deleteUser(@Param("uid") int id);

```



测试类

【注意：我们必须要将接口注册绑定到我们的核心配置文件中！】

**关于@Param()注解**

* 基本类型的参数或者String类型，需要加上

* 引用类型不需要加

* 如果只有一个基本类型的话，可以忽略，但是建议大家都加上！

* 我们在SQL中引用的就是我们这里的@Param（"uid"）中设定的属性名！

  

**${}  #{} 区别**



# 9 Lombok

* java library
* plugs
* build tools
* with one annotation your class



使用步骤：

1. 在IDEA中安装Lombok插件

2. 在项目中导入lombok的Jar包

   ```xml
   <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>1.18.20</version>
               <scope>provided</scope>
           </dependency>
   ```

3. 在实体类上加注解即可！

```
@Getter and @Setter
@FieldNameConstants
@ToString
@EqualsAndHashCode
@AllArgsConstructor, @RequiredArgsConstructor and @NoArgsConstructor
@Log, @Log4j, @Log4j2, @Slf4j, @XSlf4j, @CommonsLog, @JBossLog, @Flogger, @CustomLog
@Data
@Builder
@SuperBuilder
@Singular
@Delegate
@Value
@Accessors
@Wither
@With
@SneakyThrows
@val
@var
experimental @var
```

说明：

```
@Data：无参构造，get、set、tostring、hashcode，equals
@A11ArgsConstructor 
@NoArgsConstructor
@EqualsAndHashcode
@Tostring
@Getter
```



Lombok的缺点：改变了写源码的方式，因此不建议使用。



# 10 多对一处理

多对一

* 多个学生，对应一个老师
* 对于学生而言，**关联** .. 多个学生，关联一个老师
* 对于老师而言，**集合**，一个老师，有很多学生【一对多】



SQL:

新建一个老师表，在老师表里面插入一个老师；新建一个学生表，表中插入五个学生，每个学生都有一个外键关联那一个老师。

那么，学生和老师之间是多对一的关系。



## 测试环境搭建

1. 导入lombok
2. 新建实体类Teacher，Student
3. 建立Mapper接口
4. 建立Mapper.XML文件
5. 在核心配置文件中绑定注册我们的Mapper接口或者文件
6. 测试查询是否能够成功！



## 按照查询嵌套处理

```xml
<!--
    思路：
        1.查询所有的学生信息
        2.根据查出来的学生的tid，寻找对应的老师！  子查询
    -->
    <select id="getStudent" resultMap="StudentTeacher">
        select * from student
    </select>
    <resultMap id="StudentTeacher" type="Student">
        <result property="id" column="id"/>
        <result property="name" column="name"/>
        <!--
        复杂的属性，我们需要单独处理
        对象：association
        集合：collection
        -->
        <association property="teacher" column="tid" javaType="Teacher" select="getTeacher"/>
    </resultMap>

    <select id="getTeacher" resultType="Teacher">
        select * from teacher where id = #{id}
    </select>
```



## 按照结果嵌套处理

```xml
<select id="getStudents2" resultMap="StudentTeacher2">
         select s.id sid,s.name sname,t.name tname
         from student s,teacher t
         where s.tid=t.id;
    </select>

    <resultMap id="StudentTeacher2" type="Student">
        <result property="id" column="sid"/>
        <result property="name" column="sname"/>
        <association property="teacher" javaType="Teacher">
            <result property="name" column="tname"/>
        </association>
    </resultMap>
```



回顾Mysql多对一查询方式：

* 子查询
* 联表查询



# 11 一对多

* 一个老师拥有多个学生！

对于老师而言，就是一对多的关系！



### 按照结果嵌套处理

```xml
<select id="getTeachers" resultMap="TeacherStudent">
        select  s.id sid,s.name sname,t.id tid,t.name tname
        from student s,teacher t
        where s.tid=t.id and t.id = #{tid}
    </select>

    <resultMap id="TeacherStudent" type="Teacher">
        <result property="id" column="tid"/>
        <result property="name" column="tname"/>
        <!--复杂的属性，我们需要单独处理  对象：association  集合：collection
        javaType="" 指定属性的类型！
        集合中的泛型信息，我们使用ofType获取
        -->
        <collection property="students" ofType="Student">
            <result property="id" column="sid"/>
            <result property="name" column="sname"/>
            <result property="tid" column="tid"/>
        </collection>
    </resultMap>
```



### 按照查询嵌套处理

```xml
<select id="getTeacher2" resultMap="TeacherStudent2">
        select * from mybatis.teacher where id = #{tid}
    </select>

    <resultMap id="TeacherStudent2" type="Teacher">
        <collection property="students" javaType="ArrayList" ofType="Student" select="getStudentByTeacherId" column="id" />
    </resultMap>

    <select id="getStudentByTeacherId" resultType="Student">
        select * from mybatis.student where tid = #{tid}
    </select>
```





### 小结

1. 关联-association【多对一】
2. 集合-collection【一对多】
3. javaType&ofType
   1. JavaType用来指定实体类中属性的类型
   2. ofType用来指定映射到List或者集合中的pojo类型，泛型中的约束类型！



注意点：

* 保证SQL的可读性，尽量保证通俗易懂
* 注意一对多和多对一中，属性名和字段的问题！
* 如果问题不好排查错误，可以使用日志，建议使用Log4j



### 面试高频⭐️

* Mysql引擎
* InnoDB底层原理
* 索引
* 索引优化





# 12 动态SQL

==什么是动态SQL：动态SQL就是指根据不同的条件生成不同的SQL预计==

利用动态 SQL，可以彻底摆脱这种痛苦。

```
如果你之前用过 JSTL 或任何基于类 XML 语言的文本处理器，你对动态 SQL 元素可能会感觉似曾相识。在 MyBatis 之前的版本中，需要花时间了解大量的元素。借助功能强大的基于 OGNL 的表达式，MyBatis 3 替换了之前的大部分元素，大大精简了元素种类，现在要学习的元素种类比原来的一半还要少。

if
choose (when, otherwise)
trim (where, set)
foreach
```



### 搭建环境



1. 导包

   ```xml
   <dependencies>
           <!-- https://mvnrepository.com/artifact/org.projectlombok/lombok -->
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>1.18.20</version>
               <scope>provided</scope>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>RELEASE</version>
               <scope>compile</scope>
           </dependency>
           <dependency>
               <groupId>org.projectlombok</groupId>
               <artifactId>lombok</artifactId>
               <version>RELEASE</version>
               <scope>compile</scope>
           </dependency>
   
       </dependencies>
   ```

   

2. 写配置文件

   ```xml
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE configuration
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-config.dtd">
   <!--configuration中的配置是有顺序的-->
   <!-- "(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)"-->
   <configuration>
   
   
       <!--引入外部配置文件-->
       <properties resource="db.properties">
           <property name="username" value="root"/>  <!--添加一些配置-->
           <property name="password" value="12344"/>
       </properties>
   
       <settings>
           <setting name="logImpl" value="STDOUT_LOGGING"/>
           <setting name="mapUnderscoreToCamelCase" value="true"/>
       </settings>
   
       <!--可以给实体类起别名-->
       <typeAliases>
           <!--给一个类取别名-->
   <!--        <typeAlias type="com.kuang.pojo.User" alias="User"/>-->
           <package name="com.kuang.pojo"/>
       </typeAliases>
   
   
       <environments default="development">  <!--default：默认数据源-->
           <environment id="development">
               <transactionManager type="JDBC"/>
               <dataSource type="POOLED">
                   <property name="driver" value="${driver}"/>
                   <property name="url" value="${url}"/>
                   <property name="username" value="${username}"/>
                   <property name="password" value="${password}"/>
               </dataSource>
           </environment>
       </environments>
   
   <!--绑定接口-->
       <mappers>
           <mapper class="com.kuang.dao.BlogMapper"/>
       </mappers>
   
   
   </configuration>
   ```

   

3. 写实体类

   ```java
   package com.kuang.pojo;
   
   import lombok.Data;
   
   import java.util.Date;
   
   @Data
   public class Blog {
   
       private String id;
       private String title;
       private String author;
       private Date createTime;
       private int views;
   }
   
   ```

   

4. 写实体对应的Mapper和Mapper.xml文件

   ```java
   package com.kuang.dao;
   
   public interface BlogMapper {
   
       int getId();
   }
   ```

   ```xml-dtd
   <?xml version="1.0" encoding="UTF-8" ?>
   <!DOCTYPE mapper
           PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
           "http://mybatis.org/dtd/mybatis-3-mapper.dtd">
   <!--configuration中的配置是有顺序的-->
   <!-- "(properties?,settings?,typeAliases?,typeHandlers?,objectFactory?,objectWrapperFactory?,reflectorFactory?,plugins?,environments?,databaseIdProvider?,mappers?)"-->
   <mapper namespace="com.kuang.dao.BlogMapper">
   
   
   </mapper>
   
   ```

   

### IF

```xml
<select id="getBolgIF" resultType="blog" parameterType="map">
        select * from mybatis.blog where 1=1
        <if test="title !=null">
            and title = #{title}
        </if>
        <if test="author!=null">
            and author = #{author}
        </if>
    </select>
```



### choose(when,otherwise)

```xml
<select id="getBolgChoose" resultType="blog" parameterType="map">
        select * from mybatis.blog
        <where>
            <choose>
                <when test="title !=null">
                     title = #{title}
                </when>
                <when test="author!=null">
                    and author = #{author}
                </when>
                <otherwise>
                    and views = #{views}
                </otherwise>
            </choose>
        </where>
    </select>
```



### trim(where,set)

```xml
<update id="updateBlog" parameterType="map">
        update blog
        <set>
            <if test="title!=null">
                title = #{title},
            </if>
            <if test="author!=null">
                author = #{author}
            </if>
        </set>
        where id = #{id}
    </update>
```



==所谓的动态SQL，本质还是SQL语句，只是我们可以在SQL层面，去执行一个逻辑代码==

if 

where，set，choose，when



### SQL片段

我们可能将一些公共的sql抽取出来，方便重复使用

1. 使用sql标签抽取公共的部分

   ```xml
   <sql id="if-title-author">
           <if test="title !=null">
               title = #{title}
           </if>
           <if test="author!=null">
               and author = #{author}
           </if>
   </sql>
   ```

   

2. 需要使用的地方使用Include标签引用

   ```xml
   <select id="getBolgIF" resultType="blog" parameterType="map">
           select * from mybatis.blog
           <where>
               <include refid="if-title-author"></include>
           </where>
   </select>
   ```

   注意：

   * 最好基于表单来定义SQL片段
   * 不要存在where标签！



### Foreach

```xml
select * from user where 1=1 and (id=1 or id=2 or id=3)
//相当于
<select id="selectPostIn" resultType="domain.blog.Post">
  SELECT *
  FROM POST P
  WHERE ID in
  <foreach item="item" index="index" collection="list"
      open="(" separator="," close=")">
        #{item}
  </foreach>
</select>
```

![image-20210919171634172](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109191716509.png)

```xml
<select id="queryBlogForeach" parameterType="map" resultType="blog">
        select * from blog
        <where>
            <foreach collection="ids" item="id" open="(" close=")" separator="or">
                id = #{id}
            </foreach>
        </where>
</select>
```

==动态SQL就是在拼接SQL语句，我们只要保证SQL的正确性，按照SQL的格式，去排列组合就可以了==

建议：

* 现在Mysql中写出完成的SQL，再对应的去修改成为我们的动态SQL实现通用即可！



# 13 缓存

### 13.1 简介

```
查询：连接数据库，耗资源！
一次查询的结果，给他暂存在一个可以直接取到的地方！-->内存：缓存我们再次查询相同数据的时候，直接走缓存，就不用走数据库了
```

1. 什么是缓存[Cache]？
   * 存在内存中的临时数据。
   * 将用户经常查询的数据放在缓存（内存）中，用户去查询数据就不用从磁盘上（关系型数据库数据文件）查询，从缓存中查询，从而提高查询效率，解决了高并发系统的性能问题。

2. 为什么使用缓存？
   * 减少和数据库的交互次数，减少系统开销，提高系统效率。

3. 什么样的数据能使用缓存？
   * 经常查询并且不经常改变的数据。



### 13.2 Mybatis缓存

* Mybatis包含一个非常强大的查询缓存特性，它可以非常方便的定制和配置缓存。缓存可以极大的提升查询效率。
* Mybatis系统中默认定义了两级缓存：**一级缓存**和**二级缓存**
  * 默认情况下，只有一级缓存开启。（SqlSession级别的缓存，也称为本地缓存）
  * 二级缓存需要手动开启和配置，他是基于namespace级别的缓存。
  * 为了提高扩展性，MyBatis定义了缓存接口Cache。我们可以通过实现Cache接口来自定义二级缓存。



### 13.3 一级缓存

* 一级缓存也叫本地缓存：SqlSession
  * 与数据库同一次会话期间查询到的数据会放在本地缓存中。
  * 以后如果需要获取相同的数据，直接从缓存中拿，没必须再去查询数据库；
  * 

测试步骤：

1. 开启日志！
2. 测试在一个Session中查询两次相同记录
3. 查看日志



缓存失效的情况：

1. 查询不同的东西

2. 增删改操作可能会改变原来的数据，所以必定会刷新缓存！

3. 查询不同的Mapper.xml

4. 手动清理缓存！

   ```java
   sqlSession.clearCache();
   ```

   

小结：一级缓存默认是开启的，只在一次SqlSession中有效，也就是拿到连接到关闭连接这个区间段！

一级缓存就是一个Map。



### 13.4 二级缓存

* 二级缓存也叫全局缓存，一级缓存作用域太低了，所以诞生了二级缓存
* 基于namespace级别的缓存，一个名称空间，对应一个二级缓存；
* 工作机制
  * 一个会话查询一条数据，这个数据就会被放在当前会话的一级缓存中；
  * 如果当前会话关闭了，这个会话对应的一级缓存就没了；但是我们想要的是，会话关闭了，一级缓存中的数据被保存到二级缓存中；
  * 新的会话查询信息，就可以从二级缓存中获取内容；
  * 不同的mapper查出的数据会放在自己对应的缓存（map）中；



步骤：

1. 开启全局缓存

   ```xml
   <!--显示的开启全局缓存-->
   <setting name="cacheEnabled" value="true"/>
   ```

2. 在当前mapper.xml中使用二级缓存

   ```xml
   <!--在当前mapper.xml章使用二级缓存-->
   <cache/>
   ```

   可以自定义参数

   ```xml
   <cache
     eviction="FIFO"
     flushInterval="60000"
     size="512"
     readOnly="true"/>
   ```

3. 测试

   1. 问题：我们需要将实体类序列化！否则就会报错！

      ```
      Caused by:java.io.NotserializableException:com.kuang.pojo.User
      ```



小结：

* 只要开启了二级缓存，在同一个Mapper下就有效
* 所有的数据都会先放在一级缓存中
* 只有当会话提交，或者关闭的时候，才会提交到二级缓存中



### 13.5 缓存原理

![image-20210922124851736](https://raw.githubusercontent.com/Kevin0001-cmd/picgo/main/202109221248192.png)



### 13.6 自定义缓存-ehcache

```
Ehcache是一种广泛使用的开源Java分布式缓存。主要面向通用缓存
```

要在程序中使用ehcache.xml，先要导包

```xml
<dependency>
    <groupId>org.mybatis.caches</groupId>
    <artifactId>mybatis-ehcache</artifactId>
    <version>1.1.e</version>
</dependency>
```

在mapper中指定使用我们的ehcache缓存实现！

```xml
<！--在当前Mapper.xmL中使用二级级存->
<cache type="org.mybatis.caches.ehcache.EhcacheCache"/>
```

创建ehcache.xml

```xml
<?xml version="1.0" encoding="UTF-8"?>
<ehcache xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xsi:noNamespaceSchemaLocation="http://ehcache.org/ehcache.xsd"
         updateCheck="false">
    <!--
       diskStore：为缓存路径，ehcache分为内存和磁盘两级，此属性定义磁盘的缓存位置。参数解释如下：
       user.home – 用户主目录
       user.dir  – 用户当前工作目录
       java.io.tmpdir – 默认临时文件路径
     -->
    <diskStore path="java.io.tmpdir/Tmp_EhCache"/>
    <!--
       defaultCache：默认缓存策略，当ehcache找不到定义的缓存时，则使用这个缓存策略。只能定义一个。
     -->
    <!--
      name:缓存名称。
      maxElementsInMemory:缓存最大数目
      maxElementsOnDisk：硬盘最大缓存个数。
      eternal:对象是否永久有效，一但设置了，timeout将不起作用。
      overflowToDisk:是否保存到磁盘，当系统当机时
      timeToIdleSeconds:设置对象在失效前的允许闲置时间（单位：秒）。仅当eternal=false对象不是永久有效时使用，可选属性，默认值是0，也就是可闲置时间无穷大。
      timeToLiveSeconds:设置对象在失效前允许存活时间（单位：秒）。最大时间介于创建时间和失效时间之间。仅当eternal=false对象不是永久有效时使用，默认是0.，也就是对象存活时间无穷大。
      diskPersistent：是否缓存虚拟机重启期数据 Whether the disk store persists between restarts of the Virtual Machine. The default value is false.
      diskSpoolBufferSizeMB：这个参数设置DiskStore（磁盘缓存）的缓存区大小。默认是30MB。每个Cache都应该有自己的一个缓冲区。
      diskExpiryThreadIntervalSeconds：磁盘失效线程运行时间间隔，默认是120秒。
      memoryStoreEvictionPolicy：当达到maxElementsInMemory限制时，Ehcache将会根据指定的策略去清理内存。默认策略是LRU（最近最少使用）。你可以设置为FIFO（先进先出）或是LFU（较少使用）。
      clearOnFlush：内存数量最大时是否清除。
      memoryStoreEvictionPolicy:可选策略有：LRU（最近最少使用，默认策略）、FIFO（先进先出）、LFU（最少访问次数）。
      FIFO，first in first out，这个是大家最熟的，先进先出。
      LFU， Less Frequently Used，就是上面例子中使用的策略，直白一点就是讲一直以来最少被使用的。如上面所讲，缓存的元素有一个hit属性，hit值最小的将会被清出缓存。
      LRU，Least Recently Used，最近最少使用的，缓存的元素有一个时间戳，当缓存容量满了，而又需要腾出地方来缓存新的元素的时候，那么现有缓存元素中时间戳离当前时间最远的元素将被清出缓存。
   -->
    <defaultCache
            eternal="false"
            maxElementsInMemory="10000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="259200"
            memoryStoreEvictionPolicy="LRU"/>
 
    <cache
            name="cloud_user"
            eternal="false"
            maxElementsInMemory="5000"
            overflowToDisk="false"
            diskPersistent="false"
            timeToIdleSeconds="1800"
            timeToLiveSeconds="1800"
            memoryStoreEvictionPolicy="LRU"/>
 
</ehcache>
```



Redis用来做缓存，K-V









