package net.unit8.moshas.boot.autoconfigure;

import net.unit8.moshas.MoshasEngine;
import net.unit8.moshas.boot.web.MoshasViewResolver;
import net.unit8.moshas.loader.CompositTemplateLoader;
import net.unit8.moshas.loader.ResourceTemplateLoader;
import net.unit8.moshas.loader.WebAppTemplateLoader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnNotWebApplication;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.context.embedded.EmbeddedWebApplicationContext;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * @author kawasima
 */
@Configuration
@ConditionalOnClass({MoshasEngine.class})
@EnableConfigurationProperties(MoshasProperties.class)
public class MoshasAutoConfiguration {
    @Configuration
    public static class MoshasConfiguration {
        @Autowired
        protected MoshasProperties properties;

    }

    @Configuration
    @ConditionalOnNotWebApplication
    public static class MoshasNonWebConfiguration extends MoshasConfiguration {
        @ConditionalOnMissingBean(MoshasEngine.class)
        @Bean
        public MoshasEngine moshasEngine() {
            MoshasEngine moshasEngine = new MoshasEngine();
            ResourceTemplateLoader resourceTemplateLoader = new ResourceTemplateLoader();
            resourceTemplateLoader.setPrefix(properties.getPrefix());
            resourceTemplateLoader.setSuffix(properties.getSuffix());
            moshasEngine.getTemplateManager().setTemplateLoaders(resourceTemplateLoader);

            return moshasEngine;
        }
    }

    @Configuration
    @ConditionalOnClass(EmbeddedWebApplicationContext.class)
    @ConditionalOnWebApplication
    public static class MoshasWebConfiguration extends MoshasConfiguration {
        @Autowired
        private EmbeddedWebApplicationContext context;

        @ConditionalOnMissingBean(MoshasEngine.class)
        @Bean
        public MoshasEngine moshasEngine() {
            MoshasEngine moshasEngine = new MoshasEngine();

            WebAppTemplateLoader webAppTemplateLoader = new WebAppTemplateLoader(context.getServletContext());
            webAppTemplateLoader.setPrefix(properties.getPrefix());
            webAppTemplateLoader.setSuffix(properties.getSuffix());

            ResourceTemplateLoader resourceTemplateLoader = new ResourceTemplateLoader();
            resourceTemplateLoader.setPrefix(properties.getPrefix());
            resourceTemplateLoader.setSuffix(properties.getSuffix());

            moshasEngine.getTemplateManager()
                    .setTemplateLoaders(new CompositTemplateLoader(webAppTemplateLoader, resourceTemplateLoader));

            return moshasEngine;
        }

        @ConditionalOnMissingBean(MoshasViewResolver.class)
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

}
