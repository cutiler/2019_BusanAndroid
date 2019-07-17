package net.koreate.test_20190710_intent.vo;

import java.io.Serializable;

import lombok.Data;

@Data
public class MemberVO implements Serializable {

    private int uno;
    private String userId;
    private String userPw;
}
