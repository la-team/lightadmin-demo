package org.lightadmin.demo;

import org.lightadmin.api.config.LightAdmin;
import org.lightadmin.demo.configuration.ApplicationConfiguration;
import org.springframework.core.annotation.Order;
import org.springframework.web.context.AbstractContextLoaderInitializer;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;

import static java.util.EnumSet.of;
import static javax.servlet.SessionTrackingMode.COOKIE;
import static org.lightadmin.logging.configurer.LightConfigurerWebApplicationInitializer.LIGHT_CONFIGURER_BACK_TO_SITE_URL;
import static org.lightadmin.logging.configurer.LightConfigurerWebApplicationInitializer.LIGHT_CONFIGURER_BASE_URL;
import static org.springframework.core.Ordered.HIGHEST_PRECEDENCE;

@SuppressWarnings("unused")
@Order(HIGHEST_PRECEDENCE)
public class ApplicationInitializer extends AbstractContextLoaderInitializer {

    @Override
    public void onStartup(ServletContext servletContext) throws ServletException {
        configureWebLogger(servletContext, "/logger");

        LightAdmin.configure(servletContext)
                .baseUrl("/")
                .backToSiteUrl("http://lightadmin.org")
                .basePackage("org.lightadmin.demo.administration")
                .fileStoragePath("/file-storage/lightadmin-demo")
                .fileStreaming(true)
                .demoMode();

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

    private static void configureWebLogger(ServletContext servletContext, String baseUrl) {
        servletContext.setInitParameter(LIGHT_CONFIGURER_BASE_URL, baseUrl);
        servletContext.setInitParameter(LIGHT_CONFIGURER_BACK_TO_SITE_URL, "http://lightadmin.org");
    }
}