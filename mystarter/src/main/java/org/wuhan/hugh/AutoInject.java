package org.wuhan.hugh;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
@EnableConfigurationProperties(ExampleAutoConfig.class)
public class AutoInject {

    private ExampleAutoConfig exampleAutoConfig;

    public AutoInject(ExampleAutoConfig exampleAutoConfig) {
        this.exampleAutoConfig = exampleAutoConfig;
    }

    @Bean
    @ConditionalOnMissingBean(LearnCourse.class)
    public LearnCourse getLearnCourse() {
        return new LearnCourseHardWay(exampleAutoConfig.getLoop());
    }

}
