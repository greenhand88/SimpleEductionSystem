package com.zzj.teacher.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class TClassInfor implements Serializable {
    String tid;
    String cid;
    String hid;
    String content;
}
