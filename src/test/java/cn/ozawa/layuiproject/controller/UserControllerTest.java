package cn.ozawa.layuiproject.controller;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.util.DigestUtils;

import java.util.Arrays;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/28
 * @description : cn.ozawa.layuiproject.controller
 */
@Slf4j
public class UserControllerTest {

    @Test
    public void passwordEncryption() {
        // spring自带的md5加密，未加盐，需要加盐的话，需要数据库维护这个字段
        String md51 = Arrays.toString(DigestUtils.md5Digest("123456".getBytes()));
        String md52 = Arrays.toString(DigestUtils.md5Digest("123456".getBytes()));
        // 可以看到的时，无论运行几次，加密的结果都是一样
        log.info("\n" + md51 + "\n" + md52);

        // spring-security自带的md5加密，可自定义加盐，不需要数据库维护这个字段
        BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        String encode1 = passwordEncoder.encode("123456");
        String encode2 = passwordEncoder.encode("123456");
        // 可以看到的时，无论运行几次，加密的结果都是不一样
        log.info("\n" + encode1 + "\n" + encode2);
        // 匹配的话，可以使用自带方法
        boolean matches1 = passwordEncoder.matches("123456", encode1);
        boolean matches2 = passwordEncoder.matches("123456", encode2);
        // 可以看到，两次匹配都一样
        log.info("\n" + matches1 + "\n" + matches2);
    }
}
