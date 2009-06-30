package com.bigfatgun.surrealbot;

import java.lang.reflect.AccessibleObject;
import java.util.logging.Logger;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;

class MethodPerfInterceptor implements MethodInterceptor {

	private static final double NANOS_PER_MS = 1e6;

	private final Logger log;

	public MethodPerfInterceptor(final Logger logger) {
		this.log = logger;
	}

	public Object invoke(final MethodInvocation methodInvocation) throws Throwable {
		final long before = System.nanoTime();
		final AccessibleObject staticPart = methodInvocation.getStaticPart();
		try {
			return methodInvocation.proceed();
		} finally {
			final double diff = (System.nanoTime() - before) / NANOS_PER_MS;
			log.info(String.format("Method %s invoked. Took %g ms.", staticPart, diff));
		}
	}
}
