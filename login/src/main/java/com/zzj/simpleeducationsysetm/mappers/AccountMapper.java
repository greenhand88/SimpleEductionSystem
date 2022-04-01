package com.zzj.simpleeducationsysetm.mappers;

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
    @Select("select password from account where account=#{account}")
    public String getPassword(String account);

    /**
     * getUserName
     *
     * @param account
     * @return userName
     */
    @Select("select userName from account where account=#{account}")
    public String getUserName(String account);

    /**
     * register
     *
     * @param account
     * @param password
     */
    @Insert("insert into account(account,password,userName) values(#{account},#{password},#{userName})")
    public void register(String account, String password, String userName);

    /**
     * changePassword
     *
     * @param account
     * @param password
     */
    @Update("update account set password ={#newPassword} where account=#{account}")
    public void changePassword(String account, String password);


}
