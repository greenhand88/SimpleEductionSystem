package com.zzj.teacher.VO;

import lombok.AllArgsConstructor;
import lombok.Data;

@AllArgsConstructor
@Data
public class Result {
    String token;
    String code;
    boolean pass;
    String message;
}
