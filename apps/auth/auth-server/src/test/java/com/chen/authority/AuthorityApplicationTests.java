package com.chen.authority;


import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class AuthorityApplicationTests {

    @Test
    public void Test1(){
        String root = DigestUtils.md5Hex("root");
        System.out.println(root);
    }


}
