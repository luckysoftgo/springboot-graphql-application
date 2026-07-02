package com.application.graphql.netflix;

import com.netflix.graphql.dgs.test.EnableDgsTest;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@SpringBootTest
@EnableDgsTest // 开启DGS测试工具
@ExtendWith(SpringExtension.class)
public class NetflixApplicationTests {

}
