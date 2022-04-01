package com.zzj.simpleeducationsysetm.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ChangePassword {
    String account;
    String oldPassword;
    String newPassword;
}
