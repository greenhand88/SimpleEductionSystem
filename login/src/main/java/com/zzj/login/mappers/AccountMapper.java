package com.zzj.login.mappers;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface AccountMapper {
    /**
     * getPassword
     *
     * @param account
     * @return password
     */
    @Select("select password from student where account=#{account}")
    public String getPassword(String account);

    /**
     * getUserName
     *
     * @param account
     * @return userName
     */
    @Select("select uid from student where account=#{account}")
    public String getUid(String account);

    /**
     * register
     *
     * @param account
     * @param password
     */
    @Insert("insert into student(uid,name,account,password) values(#{uid},#{name},#{account},#{password})")
    public void register(String account, String password, String uid,String name);

    /**
     * changePassword
     *
     * @param account
     * @param password
     */
    @Update("update student set password ={#newPassword} where account=#{account}")
    public void changePassword(String account, String password);


}
