package com.onlineservise.ui;

import com.onlineservise.ServicePlatformApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

public class SpringContext {

    private static final ConfigurableApplicationContext context =
            new SpringApplicationBuilder(
                    ServicePlatformApplication.class
            ).run();

    public static ConfigurableApplicationContext getContext() {

        return context;
    }
}
