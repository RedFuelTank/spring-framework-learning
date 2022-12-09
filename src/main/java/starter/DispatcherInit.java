package starter;

import config.Config;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;
import security.SecurityConfig;

public class DispatcherInit extends AbstractAnnotationConfigDispatcherServletInitializer {
    @Override
    protected Class<?>[] getRootConfigClasses() {
        return new Class[]{
                Config.class,
                SecurityConfig.class
        };
    }

    @Override
    protected Class<?>[] getServletConfigClasses() {
        return new Class[0];
    }

    @Override
    protected String[] getServletMappings() {
        return new String[]{"/*"};
    }
}
