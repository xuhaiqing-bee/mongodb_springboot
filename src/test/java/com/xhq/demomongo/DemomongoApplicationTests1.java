package com.xhq.demomongo;

import com.xhq.demomongo.entity.User;
import com.xhq.demomongo.repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.*;

import java.util.List;

@SpringBootTest
class DemomongoApplicationTests1 {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void createUser() {
        User user = new User();
        user.setName("lucy");
        user.setAge(18);
        user.setEmail("lucy@163.com");
        userRepository.save(user);
    }

    //查询所有
    @Test
    void findAllUsers() {
        List<User> users = userRepository.findAll();
        users.forEach(System.out::println);
    }

    //根据id查询
    @Test
    void getById() {
        User user = userRepository.findById("66cac8a363edb37ee4308aae").get();
        System.out.println(user);
    }

    //条件查询
    @Test
    public void findUserList() {
        User user = new User();
        user.setAge(18);
        // 创建查询条件
        Example<User> example = Example.of(user);
        List<User> users = userRepository.findAll(example);
        users.forEach(System.out::println);
    }

    //模糊查询
    @Test
    public void findUsersLikeName() {
        // 创建匹配器
        ExampleMatcher matcher = ExampleMatcher.matching()
                // 匹配姓名包含的
                .withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING)
                // 忽略大小写
                .withIgnoreCase(true);
        // 创建条件对象
        User user = new User();
        user.setName("三");
        // 传入条件对象和匹配规则
        Example<User> example = Example.of(user, matcher);
        List<User> users = userRepository.findAll(example);
        users.forEach(System.out::println);
    }

    //分页查询
    @Test
    public void findUsersPage() {
        // 设置分页参数
        int pageNo = 0;
        int size = 2;
        Pageable pageable = PageRequest.of(pageNo, size);
        // 创建查询条件
        ExampleMatcher matcher = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING).withIgnoreCase(true);
        User user = new User();
        user.setName("三");
        Example<User> example = Example.of(user, matcher);
        Page<User> page = userRepository.findAll(example, pageable);
        List<User> users = page.getContent();
        System.out.println(users.size());
        users.forEach(System.out::println);
    }

    //修改
    @Test
    public void updateUser() {
        User user = userRepository.findById("66cac93c27818561a936c827").get();
        user.setAge(20);
        userRepository.save(user);
    }

    //删除
    @Test
    public void deleteUser() {
        userRepository.deleteById("66caf915bcbd322883d8e4e0");
    }

}
