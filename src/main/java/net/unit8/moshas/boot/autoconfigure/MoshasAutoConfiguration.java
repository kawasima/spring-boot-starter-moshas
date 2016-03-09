package net.unit8.moshas.boot.autoconfigure;

import net.unit8.moshas.MoshasEngine;
import net.unit8.moshas.boot.web.MoshasViewResolver;
import net.unit8.moshas.loader.ResourceTemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author kawasima
 */
@Configuration
@EnableConfigurationProperties(MoshasProperties.class)
public class MoshasAutoConfiguration {
    @Autowired
    private MoshasProperties properties;

    @Bean
    public MoshasEngine moshasEngine() {
        MoshasEngine moshasEngine = new MoshasEngine();
        ResourceTemplateLoader resourceTemplateLoader = new ResourceTemplateLoader();
        resourceTemplateLoader.setPrefix(properties.getPrefix());
        resourceTemplateLoader.setSuffix(properties.getSuffix());
        moshasEngine.getTemplateManager().setTemplateLoaders(resourceTemplateLoader);

        return moshasEngine;
    }

    @Bean
    public MoshasViewResolver moshasViewResolver() {
        MoshasViewResolver resolver = new MoshasViewResolver();
        resolver.setPrefix(properties.getPrefix());
        resolver.setSuffix(properties.getSuffix());
        resolver.setViewNames(properties.getViewNames());
        resolver.setContentType(properties.getContentType().toString());

        resolver.setViewComponentPrefix(properties.getViewComponentPrefix());
        resolver.setOrder(Ordered.LOWEST_PRECEDENCE - 10);
        return resolver;
    }
}
