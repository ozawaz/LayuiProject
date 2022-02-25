package cn.ozawa.layuiproject.generate;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.generator.FastAutoGenerator;
import com.baomidou.mybatisplus.generator.config.OutputFile;
import com.baomidou.mybatisplus.generator.config.rules.NamingStrategy;
import com.baomidou.mybatisplus.generator.engine.FreemarkerTemplateEngine;
import org.junit.jupiter.api.Test;

import java.util.Collections;

/**
 * @author : zheng.xiong
 * @version : 1.0
 * @date : Created at 2022/02/25
 * @description : cn.ozawa.layuiproject.generate
 */
public class CodeGenerateTest {

    @Test
    public void generate() {
        // 数据源配置
        String url = "jdbc:mysql://localhost:3306/database?characterEncoding=utf8&" +
                "zeroDateTimeBehavior=convertToNull&useSSL=false&allowMultiQueries=true&" +
                "useJDBCCompliantTimezoneShift=true&useLegacyDatetimeCode=false&" +
                "serverTimezone=Asia/Shanghai&autoReconnct=true&autoReconnectForPools=true";
        String userName = "";
        String password = "";

        // globalConfig配置
        // 作者
        String author = "ozawa";
        // 获取本项目的项目路径
        String projectPath = System.getProperty("user.dir");
        // 设置输出目录
        String outputDir= projectPath + "/src/main/java";

        // 配置数据源
        FastAutoGenerator.create(url, userName, password)

                // 全局配置
                .globalConfig(builder -> {
                    builder.author(author) // 设置作者
                            .enableSwagger() // 开启 swagger 模式
                            .fileOverride() // 覆盖已生成文件
                            .outputDir(outputDir) // 指定输出目录
                            .disableOpenDir();   // 禁止打开输出目录，默认打开
                })

                // 包配置
                .packageConfig(builder -> {
                    builder.parent("cn.ozawa.layuiproject") // 设置父包名
                            .entity("entity")   // pojo 实体类包名
                            .service("service") // Service 包名
                            .serviceImpl("serviceImpl") // ***ServiceImpl 包名
                            .mapper("mapper")   // Mapper 包名
                            .xml("mapper")  // Mapper XML 包名
                            .controller("controller") // Controller 包名
                            .other("utils") // 自定义文件包名
                            .pathInfo(Collections.singletonMap(OutputFile.mapperXml,
                                    System.getProperty("user.dir")+"/src/main/resources/mapper")); // 设置mapperXml生成路径
                })

                // 策略配置
                .strategyConfig(builder -> {
                    builder.addInclude("user", "emp", "dept") // 设置需要生成的表名

                            // Entity 策略配置
                            .entityBuilder()
                            .enableLombok() // 开启lombok
                            .enableTableFieldAnnotation() // 开启字段注解
                            .naming(NamingStrategy.underline_to_camel) // 数据库表映射到实体的命名策略
                            .columnNaming(NamingStrategy.underline_to_camel) // 数据库表字段映射到实体的命名策略

                            // Service 策略配置
                            .serviceBuilder()
                            .formatServiceFileName("%sService") // 去掉生成service的前缀I
                            .formatServiceImplFileName("%sServiceImpl") // 去掉生成serviceImp的前缀I

                            // Mapper 策略配置
                            .mapperBuilder()
                            .superClass(BaseMapper.class) // 设置父类
                            .enableMapperAnnotation() // 开启mapper注解
                            .formatMapperFileName("%sMapper") // 格式化 mapper 文件名称
                            .formatXmlFileName("%sXml") // 格式化 Xml 文件名称

                            // Controller 策略配置
                            .controllerBuilder()
                            .formatFileName("%sController") // 格式化 Controller 类文件名称，%s进行匹配表名，如 UserController
                            .enableRestStyle();  // 开启生成 @RestController 控制器

                })

                // 模板
                .templateEngine(new FreemarkerTemplateEngine()) // 使用Freemarker引擎模板，默认的是Velocity引擎模板
                // 执行
                .execute();
    }
}
