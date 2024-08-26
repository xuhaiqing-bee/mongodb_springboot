package com.xhq.demomongo.repository;

import com.xhq.demomongo.entity.User;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/**
 * @Author：XuHaiqing
 * @Package：com.xhq.demomongo.repository
 * @Project：demomongo
 * @name：UserRepository
 * @Date：2024/8/25 17:24
 * @Filename：UserRepository
 */
@Repository
public interface UserRepository extends MongoRepository<User,String> {
}
