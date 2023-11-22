package com.example.myspring.filter;

import ch.qos.logback.classic.spi.ILoggingEvent;
import ch.qos.logback.core.filter.Filter;
import ch.qos.logback.core.spi.FilterReply;

/**
 * @author hugh
 * @version 1.0
 * @description: 自定义logback filter <br/>
 * 屏蔽shrio 中token过期异常打印 <br/>
 * @date 2021/1/28 0028
 */
public class LogBackFilter extends Filter<ILoggingEvent> {

	private final static String TOKEN_EXCEPTION = "Token失效";

	@Override
	public FilterReply decide(ILoggingEvent event) {
		if (event.getMessage().contains(TOKEN_EXCEPTION)) {
			return FilterReply.DENY;
		} else {
			return FilterReply.NEUTRAL;
		}
	}
}
