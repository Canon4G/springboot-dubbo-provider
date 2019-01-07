package com.canon4g.provider.redis;

import com.alibaba.dubbo.common.utils.StringUtils;
import com.canon4g.provider.model.User;
import com.google.common.reflect.TypeToken;
import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * create by Canon4G 2019-01-07
 **/
@Repository
public class UserRedis {

    @Autowired
    private RedisTemplate<String, String> redisTemplate;

    public void addUser(String key,Long time,User user){
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(user),time, TimeUnit.MINUTES);
    }

    public void addUserList(String key,Long time,List<User> userList){
        Gson gson = new Gson();
        redisTemplate.opsForValue().set(key, gson.toJson(userList),time,TimeUnit.MINUTES);
    }

    public User getUserByKey(String key){
        Gson gson = new Gson();
        User user = null;
        String userJson = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotEmpty(userJson)){
            user =  gson.fromJson(userJson, User.class);
        }
        return user;
    }

    public List<User> getUserListByKey(String key){
        Gson gson = new Gson();
        List<User> userList = null;
        String userJson = redisTemplate.opsForValue().get(key);
        if(StringUtils.isNotEmpty(userJson)){
            userList =  gson.fromJson(userJson, new TypeToken<List<User>>(){}.getType()    );
        }
        return userList;
    }

    public void deleteByKey(String key){
        redisTemplate.opsForValue().getOperations().delete(key);
    }
}
