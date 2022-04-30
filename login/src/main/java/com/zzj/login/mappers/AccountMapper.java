package com.zzj.login.mappers;
import com.zzj.login.VO.Id;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
public interface AccountMapper {
        @Select("select * from account where account=#{account}")
        public Id getInfor(String account);
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
        @Select("select uid from account where account=#{account}")
        public String getUid(String account);

        /**
         *
         * @param account
         * @param password
         * @param isT
         * @param uid
         */
        @Insert("insert into account(account,password,isT,uid) values(#{account},#{password},#{isT},#{uid})")
        public void register(String account, String password, int isT,String uid);

        /**
         * changePassword
         *
         * @param account
         * @param password
         */
        @Update("update student set password ={#newPassword} where account=#{account}")
        public void changePassword(String account, String password);

}
