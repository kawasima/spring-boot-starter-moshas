package net.unit8.moshas.boot;

import org.springframework.boot.autoconfigure.template.TemplateAvailabilityProvider;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ResourceLoader;
import org.springframework.util.ClassUtils;

/**
 * @author kawasima
 */
public class MoshasTemplateAvailabilityProvider implements TemplateAvailabilityProvider {
    @Override
    public boolean isTemplateAvailable(String view, Environment environment, ClassLoader classLoader, ResourceLoader resourceLoader) {

        if (ClassUtils.isPresent("net.unit8.moshas.MoshasEngine", classLoader)) {

            String prefix = environment.getProperty("spring.moshas.prefix", "/META-INF/templates");
            String suffix = environment.getProperty("spring.moshas.suffix", ".html");

            return resourceLoader.getResource(prefix + view + suffix).exists();
        }

        return false;
    }
}
