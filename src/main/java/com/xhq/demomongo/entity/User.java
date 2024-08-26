package com.xhq.demomongo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * @Author：XuHaiqing
 * @Package：com.xhq.demomongo.entity
 * @Project：demomongo
 * @name：User
 * @Date：2024/8/24 15:55
 * @Filename：User
 */
@Data
@Document("User")
public class User {
    @Id
    private String id;
    private String name;
    private Integer age;
    private String email;
    private String createDate;
}
