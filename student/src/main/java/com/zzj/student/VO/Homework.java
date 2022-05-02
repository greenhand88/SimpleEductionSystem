package com.zzj.student.VO;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class Homework {
    ClassInfor classInfor;
    ArrayList<Infor> infors;
}
