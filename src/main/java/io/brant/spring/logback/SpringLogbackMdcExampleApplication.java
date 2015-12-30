package io.brant.spring.logback;

import io.brant.spring.logback.filters.LogbackMdcFilter;
import io.brant.spring.logback.filters.MultiReadableHttpServletRequestFilter;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.embedded.ConfigurableEmbeddedServletContainer;
import org.springframework.boot.context.embedded.EmbeddedServletContainerCustomizer;
import org.springframework.boot.context.embedded.ErrorPage;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;

@SpringBootApplication
public class SpringLogbackMdcExampleApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringLogbackMdcExampleApplication.class, args);
	}

	@Bean
	public FilterRegistrationBean multiReadableHttpServletRequestFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		MultiReadableHttpServletRequestFilter multiReadableHttpServletRequestFilter = new MultiReadableHttpServletRequestFilter();
		registrationBean.setFilter(multiReadableHttpServletRequestFilter);
		registrationBean.setOrder(1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean logbackMdcFilterRegistrationBean() {
		FilterRegistrationBean registrationBean = new FilterRegistrationBean();
		LogbackMdcFilter logbackMdcFilter = new LogbackMdcFilter();
		registrationBean.setFilter(logbackMdcFilter);
		registrationBean.setOrder(2);
		return registrationBean;
	}
	
	@Bean
	public EmbeddedServletContainerCustomizer containerCustomizer() {
	    return new MyCustomizer();
	}
	
	private static class MyCustomizer implements EmbeddedServletContainerCustomizer {

	    @Override
	    public void customize(ConfigurableEmbeddedServletContainer container) {
	        container.addErrorPages(new ErrorPage(HttpStatus.UNAUTHORIZED, "/errors/401"));
	        container.addErrorPages(new ErrorPage(HttpStatus.NOT_FOUND, "/errors/404"));
	        container.addErrorPages(new ErrorPage(HttpStatus.INTERNAL_SERVER_ERROR, "/errors/500"));
	    }

	}
}
