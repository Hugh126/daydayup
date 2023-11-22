package design_pattern.strategy.in;

import com.example.myspring.common.Result;
import design_pattern.strategy.StudyEnum;

import javax.servlet.http.HttpServletRequest;

public interface IStudyHandle {
    public StudyEnum getType();
    public Result handleSumbit(HttpServletRequest request);
}
