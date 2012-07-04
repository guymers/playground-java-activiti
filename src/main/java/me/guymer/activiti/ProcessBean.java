package me.guymer.activiti;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

@Component
public class ProcessBean {
	
	private static final Logger LOGGER = LoggerFactory.getLogger(ProcessBean.class);
	
	public void doSomething() {
		LOGGER.info("doSomething");
	}
}
