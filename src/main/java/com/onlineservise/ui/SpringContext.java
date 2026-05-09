package com.onlineservise.ui;

import com.onlineservise.ServicePlatformApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContext {

    private static ConfigurableApplicationContext context;

    public static synchronized ConfigurableApplicationContext getContext() {
        if (context == null) {
            context = new SpringApplicationBuilder(ServicePlatformApplication.class)
                    .run();
        }
        return context;
    }
}
