package com.zzj.login.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Id {
    String account;
    String password;
    int isT;
    String uid;
}
