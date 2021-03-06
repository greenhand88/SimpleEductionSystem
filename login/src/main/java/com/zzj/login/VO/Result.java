package com.zzj.login.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Result implements Serializable {
    String token;
    String uid;
    Boolean isTeacher;
    Boolean pass;
}
