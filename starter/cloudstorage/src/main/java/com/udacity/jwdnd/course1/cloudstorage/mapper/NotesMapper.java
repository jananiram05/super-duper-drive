package com.udacity.jwdnd.course1.cloudstorage.mapper;


import com.udacity.jwdnd.course1.cloudstorage.model.Notes;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface NotesMapper {
    @Select("SELECT * FROM Notes WHERE userid = #{userid}")
    List<Notes> findNotes(int userid);

    @Insert("INSERT INTO Notes (notetitle, notedescription,userid) VALUES(#{notetitle}, #{notedescription},#{userid})")
    @Options(useGeneratedKeys = true, keyProperty = "noteid")
    int insert(Notes note);

    @Delete("DELETE FROM Notes WHERE noteid = #{noteid}")
    void delete(int noteid);

    @Select("SELECT * FROM Notes WHERE noteid = #{noteid}")
    Notes getNoteById(int noteid);

    @Update("UPDATE Notes SET notetitle = #{notetitle}, notedescription = #{notedescription} WHERE noteid = #{noteid}")
    public void update(Notes note);
}
