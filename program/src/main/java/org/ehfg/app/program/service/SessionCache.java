package org.ehfg.app.program.service;

import org.springframework.cache.annotation.Cacheable;

import java.lang.annotation.*;

/**
 * @author patrick
 * @since 11.2016
 */
@Documented
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Cacheable("sessions")
public @interface SessionCache {
	// empty
}
