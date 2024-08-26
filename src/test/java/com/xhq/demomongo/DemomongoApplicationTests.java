package com.xhq.demomongo;

import com.mongodb.client.result.DeleteResult;
import com.mongodb.client.result.UpdateResult;
import com.xhq.demomongo.entity.User;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.HashMap;
import java.util.List;
import java.util.regex.Pattern;

@SpringBootTest
class DemomongoApplicationTests {
    @Autowired
    private MongoTemplate mongoTemplate;

    //添加
    @Test
    void createUser() {
        User user = new User();
        user.setName("周三");
        user.setAge(18);
        user.setEmail("zhousan@163.com");
        User user1 = mongoTemplate.insert(user);
        System.out.println(user1);
    }

    //查询所有
    @Test
    void findAllUsers() {
        List<User> users = mongoTemplate.findAll(User.class);
        for (User user : users) {
            System.out.println(user);
        }
    }

    //根据id查询
    @Test
    void getById() {
        User user = mongoTemplate.findById("66c9934f8b53740167511c4d", User.class);
        System.out.println(user);
    }

    //根据条件查询
    @Test
    void findByCondition() {
        // 构建条件
        Query query1 = new Query(Criteria.where("name").is("李四"));
        Query query2 = new Query(Criteria.where("age").gt(10));
        List<User> users = mongoTemplate.find(query1, User.class);
        List<User> users1 = mongoTemplate.find(query2, User.class);
        users.forEach(System.out::println);
        users1.forEach(System.out::println);

    }

    //模糊查询
    @Test
    public void findUsersLikeName() {
        String name = "三";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        List<User> users = mongoTemplate.find(query, User.class);
        users.forEach(System.out::println);

    }

    //分页查询
    @Test
    public void findUsersPage() {
        String name = "三";
        String regex = String.format("%s%s%s", "^.*", name, ".*$");
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Query query = new Query(Criteria.where("name").regex(pattern));
        int page = 1;
        int size = 2;
        // 分页查询
        Query pageQuery = query.skip((page - 1) * size).limit(size);
        List<User> users = mongoTemplate.find(pageQuery, User.class);
        long count = mongoTemplate.count(pageQuery, User.class);
        HashMap<String, Object> map = new HashMap<>();
        map.put("count", count);
        map.put("data", users);
        System.out.println(map);
    }

    //修改
    @Test
    public void updateUser() {
        User user = mongoTemplate.findById("66cac8a363edb37ee4308aae", User.class);
        user.setAge(38);
        Query query = new Query(Criteria.where("_id").is(user.getId()));
        Update update = new Update();
        update.set("age", user.getAge());
        UpdateResult result = mongoTemplate.upsert(query, update, User.class);
        long count = result.getModifiedCount();
        System.out.println(count);
    }

    //删除
    @Test
    public void deleteUser() {
        Query query = new Query(Criteria.where("_id").is("66cac8fe7117c35b0b626c55"));
        DeleteResult result = mongoTemplate.remove(query, User.class);
        long deletedCount = result.getDeletedCount();
        System.out.println("删除了" + deletedCount + "条数据");
    }

}
