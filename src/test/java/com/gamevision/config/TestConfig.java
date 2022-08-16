package com.gamevision.config;

import org.junit.platform.engine.Filter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.test.web.servlet.setup.MockMvcConfigurer;
import org.springframework.web.context.WebApplicationContext;

@Configuration
public class TestConfig {


    //  @Autowired
    //  private Filter springSecurityFilterChain;
//
    //  @Autowired
    //  private WebApplicationContext webApplicationContext;
//
    //  @Bean
    //  public MockMvc mockMvc() {
    //      return new MockMvc();
    //      //return MockMvcBuilders
    //      //        .webAppContextSetup(webApplicationContext)
    //      //        .apply(springSecurityFilterChain)
    //      //        .build();
    //  }
}