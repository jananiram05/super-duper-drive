package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper {
    /*  @Select("SELECT * FROM User WHERE userid = #{userid}")
      User findUser(Integer userid);*/
    @Select("SELECT * FROM USERS WHERE userName = #{userName}")
    User findUser(String username);

    @Select("SELECT * FROM Users")
    List<User> findAllUsers();

    @Insert("INSERT INTO Users (userName, salt,password,firstName,lastName) VALUES(#{userName}, #{salt},#{password},#{firstName},#{lastName})")
    @Options(useGeneratedKeys = true, keyProperty = "userid")
    Integer insert(User user);

    @Delete("DELETE FROM users WHERE userid = #{userid}")
    void delete(Integer userid);

    @Update("UPDATE users SET username = #{username}," +
            "salt = #{salt},password = #{password}," +
            "firstname = #{firstname},lastname = #{lastname} ")
    void update(Integer userid);


}
