package com.zzj.student.mappers;

import com.zzj.student.VO.ClassInfor;
import com.zzj.student.VO.Infor;
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

    @Select("select distinct sclass.cid,sclass.cname,tid,tname from sclass inner join tclass on sclass.cid=tclass.cid where suid=#{suid}")
    public ArrayList<ClassInfor>getMyTeacher(String suid);

    @Select("select distinct hid,content from homework where tid=#{tid}")
    public ArrayList<Infor> getHomework(String tid);

}
