package design_pattern;

import lombok.extern.slf4j.Slf4j;

import java.util.Observable;
import java.util.Observer;

@Slf4j
public class Observer1 implements Observer {
    @Override
    public void update(Observable o, Object arg) {
        String source = o.getClass().getSimpleName();
        log.warn("--event update--, source={}, arg={}", source, arg.toString());
    }
}
