package cn.ozawa.layuiproject;

import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/03/20
 * @description : cn.ozawa.layuiproject
 */
public class TimeTest {

    @Test
    public void test1() {
        // 格式转换器
        DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        // 获取当前时间
        LocalDateTime nowTime = LocalDateTime.now();
        System.out.println(format.format(nowTime));
    }
}
