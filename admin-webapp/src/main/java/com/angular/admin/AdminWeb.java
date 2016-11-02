package com.angular.admin;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.PropertySource;

import java.io.FileOutputStream;
import java.lang.management.ManagementFactory;

@SpringBootApplication
//@PropertySource(value = "file:./application.properties")
public class AdminWeb {

    public static void main(String[] args) throws Exception {
        ApplicationContext ctx = SpringApplication.run(AdminWeb.class, args);

        System.out.println("Spring boot completed!");

        /*生成sh脚本*/
        FileOutputStream out = new FileOutputStream(String.format("close%s.sh", AdminWeb.class.getSimpleName()));
        out.write(String.format("kill 9 %s", ManagementFactory.getRuntimeMXBean().getName()
                .split("@")[0]).getBytes());
        out.flush();
        out.close();
        /*
        System.out.println("Let's inspect the beans provided by Spring Boot:");
        String[] beanNames = ctx.getBeanDefinitionNames();
        Arrays.sort(beanNames);
        for (String beanName : beanNames) {
            System.out.println(beanName);
        }
        */
    }

}
