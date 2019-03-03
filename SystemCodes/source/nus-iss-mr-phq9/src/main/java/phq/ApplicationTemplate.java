package phq;

import org.thymeleaf.TemplateEngine;
import org.thymeleaf.spring5.templateresolver.SpringResourceTemplateResolver;
import org.thymeleaf.templateresolver.ServletContextTemplateResolver;

public class ApplicationTemplate {
    /*private static TemplateEngine templateEngine;

    static {
        initializeTemplateEngine();
    }*/


    public static TemplateEngine initializeTemplateEngine() {

        //ServletContextTemplateResolver templateResolver = new ServletContextTemplateResolver(null);
        SpringResourceTemplateResolver templateResolver = new SpringResourceTemplateResolver();
        // XHTML is the default mode, but we set it anyway for better understanding of code
        templateResolver.setTemplateMode("HTML");
        // This will convert "home" to "/WEB-INF/templates/home.html"
        templateResolver.setPrefix("/templates/");
        templateResolver.setSuffix(".html");
        // Template cache TTL=1h. If not set, entries would be cached until expelled by LRU
        templateResolver.setCacheTTLMs(10L);

        TemplateEngine templateEngine = new TemplateEngine();
        templateEngine.setTemplateResolver(templateResolver);

        return templateEngine;
    }
}
