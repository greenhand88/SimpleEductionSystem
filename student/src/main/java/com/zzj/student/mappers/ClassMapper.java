package com.zzj.student.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
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

    @Insert("insert into sclass(uid,cname,suid,sname) values (#{uid},#{cname},#{suid},#{sname})")
    public void addClass(String uid,String cname,String suid,String name);

}
