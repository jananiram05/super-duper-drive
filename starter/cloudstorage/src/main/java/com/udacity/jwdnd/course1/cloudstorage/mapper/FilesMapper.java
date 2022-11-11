package com.udacity.jwdnd.course1.cloudstorage.mapper;

import com.udacity.jwdnd.course1.cloudstorage.model.Files;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface FilesMapper {
    @Select("SELECT * FROM Files WHERE userid = #{userid}")
    List<Files> findFile(int userid);

    @Select("SELECT * FROM Files WHERE fileid = #{fileid}")
    Files getFileById(Integer fileid);

    @Select("SELECT * FROM Files WHERE userid = #{userId} AND filename = #{filename}")
    List<Files> getFileByFilename(int userId, String filename);


    @Insert("INSERT INTO Files (filename, contenttype,filesize,userid,filedata) VALUES(#{filename}, #{contenttype},#{filesize},#{userid},#{filedata})")
    @Options(useGeneratedKeys = true, keyProperty = "fileid")
    int insert(Files files);

    @Delete("DELETE FROM Files WHERE fileid = #{fileid}")
    void delete(int fileid);

    @Update("UPDATE Files SET filename = #{filename}," +
            "contenttype = #{contenttype},filesize = #{filesize}," +
            "userid = #{userid},filedata = #{filedata} ")
    void update(int fileid);

}
