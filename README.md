# spring-boot-starter-moshas

A spring boot starter for using moshas.

## 

```java
@Controller
@Configuration
public class SomeController {
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
}
```
