package cn.ozawa.layuiproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author Ozawa
 */
@SpringBootApplication(scanBasePackages = {"cn.ozawa.layuiproject"})
@MapperScan({"cn.ozawa.layuiproject.mapper"})
public class LayuiProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(LayuiProjectApplication.class, args);
    }

}
