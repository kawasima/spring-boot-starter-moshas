package net.unit8.moshas.boot;

import net.unit8.moshas.MoshasEngine;
import net.unit8.moshas.Template;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.boot.test.TestRestTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.Date;
import java.util.Map;

import static net.unit8.moshas.RenderUtils.text;

/**
 * @author kawasima
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = ApplicationTests.Application.class)
@IntegrationTest("server.port:0")
@WebAppConfiguration
public class ApplicationTests {

    @Autowired
    private EmbeddedWebApplicationContext context;
    private int port;

    @Before
    public void init() {
        port = context.getEmbeddedServletContainer().getPort();
    }

    @Test
    public void testHomePage() throws Exception {
        String body = new TestRestTemplate().getForObject("http://localhost:" + port,
                String.class);
        System.out.println(body);
        Assert.assertTrue(body.contains("Hello World"));

    }

    @Configuration
    @EnableAutoConfiguration
    @Controller
    public static class Application {
        @Bean(name = "view.home")
        public Template homeViewLogic(MoshasEngine engine) {
            return engine.defineTemplate("home", t -> {
                t.select("head > title", text("title"));
                t.select("#message", text("message"));
                t.select("#time", text("time"));
            });
        }

        @RequestMapping("/")
        public String home(Map<String, Object> model) {
            model.put("time", new Date());
            model.put("message", "Hello World");
            model.put("title", "Hello App");

            return "home";
        }

        public static void main(String... args) {
            SpringApplication.run(Application.class, args);
        }
    }
}
