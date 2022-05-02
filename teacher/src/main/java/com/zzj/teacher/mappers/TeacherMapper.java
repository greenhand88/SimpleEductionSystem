package com.zzj.teacher.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface TeacherMapper {
   @Select("Insert into homework(tid,cid,content,hid)values (#{tid},#{cid},#{content},#{hid})")
    public void putHomework(String tid,String cid,String hid,String content);


}
