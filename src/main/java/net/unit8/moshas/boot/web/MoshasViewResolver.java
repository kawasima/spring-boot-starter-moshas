package net.unit8.moshas.boot.web;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.View;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;

import java.util.Locale;

/**
 * View resolver for Moshas
 *
 * @author kawasima
 */
public class MoshasViewResolver extends AbstractTemplateViewResolver implements ViewResolver {

    private static final Logger log = LoggerFactory.getLogger(MoshasViewResolver.class);
    private String viewComponentPrefix;

    public MoshasViewResolver() {
        log.debug("Use MoshasViewResolver in为 ViewResolver.");
        setViewClass(requiredViewClass());
    }

    @Override
    protected Class<?> requiredViewClass() {
        return MoshasView.class;
    }

    @Override
    protected View loadView(String viewName, Locale locale) {
        if(viewName.endsWith(".html")) {
            viewName = viewName.substring(0, viewName.length()-5);
        }
        MoshasView view = new MoshasView(viewName);
        view.setViewComponentPrefix(viewComponentPrefix);
        view.setApplicationContext(getApplicationContext());
        view.setServletContext(getServletContext());


        return view;

    }

    public void setViewComponentPrefix(String viewComponentPrefix) {
        this.viewComponentPrefix = viewComponentPrefix;
    }
}
