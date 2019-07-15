package com.example.demo;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.powermock.api.mockito.PowerMockito;
import org.powermock.core.classloader.annotations.PowerMockIgnore;
import org.powermock.core.classloader.annotations.PrepareForTest;
import org.powermock.modules.junit4.PowerMockRunner;
import org.powermock.modules.junit4.PowerMockRunnerDelegate;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.io.IOException;

@RunWith(PowerMockRunner.class) //使用PowerMockRunner运行时
@PowerMockRunnerDelegate(SpringJUnit4ClassRunner.class) //委派给SpringJUnit4ClassRunner
@SpringBootTest(classes = DemoApplication.class)
@PrepareForTest(HttpByJavaNET.class)
@WebAppConfiguration    //使用这个Annotate会在跑单元测试的时候真实的启一个web服务，然后开始调用Controller的Rest API，待单元测试跑完之后再将web服务停掉
@PowerMockIgnore({"com.sun.org.apache.xerces.*",    //忽略一些mock异常
        "com.sun.org.apache.xalan.*",
        "javax.xml.*",
        "org.xml.*",
        "javax.management.*",
        "javax.net.ssl.*",
        "org.apache.logging.log4j.*",
        "javax.crypto.*",
        "javax.security.*",
        "sun.security.*"})
public class HttpByJavaNETTest {

    @Test
    public void testGet() throws IOException {
        PowerMockito.mockStatic(HttpByJavaNET.class);
        PowerMockito.when(HttpByJavaNET.get(Mockito.anyString())).thenReturn("mockTest");
        String body = HttpByJavaNET.get("https://www.itread01.com/content/1545844517.html");
        System.out.println(body);
    }
}
