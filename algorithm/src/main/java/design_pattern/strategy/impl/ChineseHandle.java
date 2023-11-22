package design_pattern.strategy.impl;

import design_pattern.strategy.StudyEnum;
import org.springframework.stereotype.Component;

@Component
public class ChineseHandle extends AbstractHandle {
    @Override
    public StudyEnum getType() {
        return StudyEnum.chinese;
    }
}
