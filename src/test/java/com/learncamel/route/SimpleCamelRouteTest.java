package com.learncamel.route;

import org.apache.camel.Exchange;
import org.apache.camel.ProducerTemplate;
import org.apache.camel.test.spring.CamelSpringBootRunner;
import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.env.Environment;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.io.File;
import java.io.IOException;

import static org.junit.Assert.assertTrue;

@ActiveProfiles("dev")
@RunWith(CamelSpringBootRunner.class)
@SpringBootTest
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
public class SimpleCamelRouteTest {
    @Autowired
    ProducerTemplate producerTemplate;

    @Autowired
    Environment env;

    @BeforeClass
    public  static  void startCleanUp() throws IOException {
        FileUtils.cleanDirectory(new File("data/input"));
        FileUtils.deleteDirectory(new File("data/output"));
    }

    @Test
    public void testMoveFile() throws InterruptedException {
        String message = "type,sku#,itemdescription,price\n" +
                "ADD,100,Samsung Tv,600\n" +
                "ADD,101,LG Tv,300";
        String fileName="fileTest.txt";

        producerTemplate.sendBodyAndHeader(
                env.getProperty("fromRoute"), message, Exchange.FILE_NAME,fileName
        );

        Thread.sleep(3000);

        File output= new File("data/output/"+fileName);
        assertTrue(output.exists());
    }
}
