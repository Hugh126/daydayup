package com.example.myspring.designPattern.strategy;

import com.example.myspring.designPattern.strategy.in.IStudyHandle;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.EnumMap;

@Component
public class StudyHandlerFactory implements InitializingBean, ApplicationContextAware {

    private ApplicationContext appContext;

    private EnumMap<StudyEnum, IStudyHandle> studyHandleMap = new EnumMap(StudyEnum.class);

    public IStudyHandle createHandle(StudyEnum type) {
        return studyHandleMap.get(type);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        // 将handle注入到Application
        appContext.getBeansOfType(IStudyHandle.class) .values().forEach(handle -> studyHandleMap.put(handle.getType(), handle));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.appContext = applicationContext;
    }
}
