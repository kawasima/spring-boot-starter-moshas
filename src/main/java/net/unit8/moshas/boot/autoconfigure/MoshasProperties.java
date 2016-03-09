package net.unit8.moshas.boot.autoconfigure;

import org.springframework.boot.autoconfigure.template.AbstractViewResolverProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * @author kawasima
 */
@ConfigurationProperties(prefix = "spring.moshas")
public class MoshasProperties extends AbstractViewResolverProperties {
    public static final String DEFAULT_PREFIX = "/META-INF/templates/";
    public static final String DEFAULT_SUFFIX = ".html";
    public static final String DEFAULT_VIEW_COMPONENT_PREFIX = "view.";

    private String prefix = DEFAULT_PREFIX;
    private String suffix = DEFAULT_SUFFIX;
    private String viewComponentPrefix = DEFAULT_VIEW_COMPONENT_PREFIX;

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getSuffix() {
        return suffix;
    }

    public void setSuffix(String suffix) {
        this.suffix = suffix;
    }

    public String getViewComponentPrefix() {
        return viewComponentPrefix;
    }

    public void setViewComponentPrefix(String viewComponentPrefix) {
        this.viewComponentPrefix = viewComponentPrefix;
    }
}
