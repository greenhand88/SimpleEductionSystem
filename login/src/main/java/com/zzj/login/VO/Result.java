package com.zzj.login.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result {
    String token;
    String uid;
    Boolean isTeacher;
    Boolean pass;
}
