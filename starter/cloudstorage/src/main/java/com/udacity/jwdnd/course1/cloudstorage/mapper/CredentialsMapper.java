package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Credentials;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface CredentialsMapper {
    @Select("SELECT * FROM Credentials WHERE userid = #{userid}")
    List<Credentials> findCredential(int userid);

    @Insert("INSERT INTO Credentials (url,username,key,password,userid) VALUES(#{url},#{username},#{key},#{password},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "credentialid")
    int insert(Credentials credentials);

    @Delete("DELETE FROM Credentials WHERE credentialid = #{credentialid}")
    void delete(int credentialid);

    @Update("UPDATE Credentials SET url = #{url}, username = #{username}, key = #{key}, password= #{password}, userid = #{userid} WHERE credentialid = #{credentialid}")
    void update(Credentials credentials);

    @Select("SELECT * FROM Credentials WHERE credentialid = #{credentialid}")
    Credentials getCredentialById(int credentialid);
}
