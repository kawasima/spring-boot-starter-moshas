package net.unit8.moshas.boot.web;

import net.unit8.moshas.Template;
import net.unit8.moshas.context.Context;
import org.springframework.web.servlet.view.AbstractTemplateView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.util.Map;

/**
 * A representation of view for Moshas.
 *
 * @author kawasima
 */
public class MoshasView extends AbstractTemplateView {
    private final String viewName;
    private String viewComponentPrefix;

    public MoshasView(String viewName) {
        this.viewName = viewName;
    }

    private String viewComponentName() {
        return viewComponentPrefix != null ? viewComponentPrefix + viewName : viewName;
    }

    @Override
    protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request, HttpServletResponse response) throws Exception {
        response.setContentType(getContentType());
        response.setCharacterEncoding("UTF-8");

        final Writer writer = response.getWriter();
        try {
            Template template = getApplicationContext().getBean(viewComponentName(), Template.class);
            Context context = new Context(model);
            template.render(context, writer);
        } finally {
            try {
                writer.flush();
            } catch (IOException ignore) {}
        }
    }

    public void setViewComponentPrefix(String viewComponentPrefix) {
        this.viewComponentPrefix = viewComponentPrefix;
    }
}
