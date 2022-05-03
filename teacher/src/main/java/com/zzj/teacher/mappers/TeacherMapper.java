package com.zzj.teacher.mappers;

import com.zzj.teacher.VO.ClassInfor;
import com.zzj.teacher.VO.Infor;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.ArrayList;

public interface TeacherMapper {
    @Select("Insert into homework(tid,cid,content,hid)values (#{tid},#{cid},#{content},#{hid})")
    public void putHomework(String tid,String cid,String hid,String content);
    @Select("select distinct hid,content from homework where tid=#{tid}")
    public ArrayList<Infor>getHomework(String tid);
    @Select("select name from teacher where uid=#{tid}")
    public String getName(String tid);
    @Select("select cid,cname from tclass where tid=#{tid}")
    public ArrayList<ClassInfor>getClassInfor(String tid );
}
