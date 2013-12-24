package org.lightadmin.demo;

import org.lightadmin.demo.configuration.ApplicationConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import static java.lang.String.valueOf;
import static java.util.EnumSet.of;
import static javax.servlet.SessionTrackingMode.COOKIE;
import static org.lightadmin.core.util.LightAdminConfigurationUtils.*;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@SuppressWarnings("unused")
@Order(HIGHEST_PRECEDENCE)
public class ApplicationInitializer extends AbstractContextLoaderInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        servletContext.setInitParameter(LIGHT_ADMINISTRATION_BASE_URL, "/");
        servletContext.setInitParameter(LIGHT_ADMINISTRATION_BACK_TO_SITE_URL, "http://lightadmin.org");
        servletContext.setInitParameter(LIGHT_ADMINISTRATION_BASE_PACKAGE, "org.lightadmin.demo.administration");
        servletContext.setInitParameter(LIGHT_ADMINISTRATION_FILE_STORAGE_PATH, "/Users/max/Desktop/hubbery");
        servletContext.setInitParameter(LIGHT_ADMINISTRATION_FILE_STREAMING, valueOf(true));
        servletContext.setSessionTrackingModes(of(COOKIE));

        super.onStartup(servletContext);
    }

    @Override
    protected WebApplicationContext createRootApplicationContext() {
        AnnotationConfigWebApplicationContext webApplicationContext = new AnnotationConfigWebApplicationContext();
        webApplicationContext.register(ApplicationConfiguration.class);
        webApplicationContext.refresh();
        return webApplicationContext;
    }
}