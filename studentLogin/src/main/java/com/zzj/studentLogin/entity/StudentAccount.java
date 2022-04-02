package com.zzj.studentLogin.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class StudentAccount{
    String account;
    String password;
    String uid;
    String name;
}
