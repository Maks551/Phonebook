package com.example.phonebook.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.web.method.annotation.AuthenticationPrincipalArgumentResolver;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

import java.nio.charset.Charset;
import java.util.*;

import static com.example.phonebook.config.JacksonAutoConfiguration.getMapper;

@Configuration
public class MvcConfig implements WebMvcConfigurer {

    @Bean(name = "localeResolver")
    public LocaleResolver getLocaleResolver() {
        CookieLocaleResolver resolver = new CookieLocaleResolver();
        resolver.setDefaultLocale(new Locale("uk"));
        return resolver;
    }

    @Bean(name = "messageSource")
    public MessageSource getMessageResource() {
        ReloadableResourceBundleMessageSource messageResource = new ReloadableResourceBundleMessageSource();

        // Read i18n/messages_xxx.properties file.
        // For example: i18n/messages_en.properties
        messageResource.setCacheSeconds(5);
        messageResource.setBasenames("classpath:static/i18n/messages");
        messageResource.setDefaultEncoding("UTF-8");
        messageResource.setFallbackToSystemLocale(false);
        return messageResource;
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor()).addPathPatterns("/*");
    }

    @Bean
    public MappingJackson2HttpMessageConverter objectMapper() {
        MappingJackson2HttpMessageConverter jackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter();
        jackson2HttpMessageConverter.setObjectMapper(getMapper());
        return jackson2HttpMessageConverter;
    }

    @Bean
    public StringHttpMessageConverter supportedMediaTypes() {
        StringHttpMessageConverter messageConverter = new StringHttpMessageConverter();
        messageConverter.setSupportedMediaTypes(Arrays.asList(
                new MediaType("text", "plain", Charset.forName("UTF-8")),
                new MediaType("text", "html", Charset.forName("UTF-8"))));
        return messageConverter;
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        final Map<String, String> parameterMap = new HashMap<String, String>();
        parameterMap.put("charset", "utf-8");

        configurer.defaultContentType(new MediaType(
                MediaType.APPLICATION_JSON, parameterMap));
    }

    @Bean
    public AuthenticationPrincipalArgumentResolver getAuthenticationPrincipalArgumentResolver() {
        return new AuthenticationPrincipalArgumentResolver();
    }

    @SuppressWarnings("deprecation")
    @Bean
    public static NoOpPasswordEncoder encoder() {
        return (NoOpPasswordEncoder) NoOpPasswordEncoder.getInstance();
    }
}
