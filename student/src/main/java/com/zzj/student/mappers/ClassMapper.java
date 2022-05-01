package com.zzj.student.mappers;

import com.zzj.student.VO.ClassInfor;
import org.apache.ibatis.annotations.Select;

import java.util.ArrayList;

public interface ClassMapper {
    /**
     *
     * @return
     */
    @Select("select distinct cname from sclass")
    public ArrayList<String>getAllClass();
    /**
     *
     * @param suid
     * @return
     */
    @Select("select distinct cname from sclass where suid=#{suid}")
    public ArrayList<String>getMyClass(String suid);

    @Select("select distinct sclass.uid,sclass.cname,tid,tname from sclass inner join tclass on sclass.uid=tclass.uid where suid=#{suid}")
    public ArrayList<ClassInfor>getMyTeacher(String suid);

    @Select("select content from homework where tid=#{tid}")
    public ArrayList<String> getHomework(String tid);
}
