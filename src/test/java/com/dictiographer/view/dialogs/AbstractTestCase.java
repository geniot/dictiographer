package com.dictiographer.view.dialogs;

import com.dictiographer.ApplicationContextProvider;
import com.dictiographer.view.MyLocalizer;
import com.dictiographer.view.MyThreadLocal;
import com.dictiographer.view.ThreadContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.uispec4j.UISpecTestCase;

import java.util.Locale;

/**
 * Author: Vitaly Sazanovich
 * Email: vitaly.sazanovich@gmail.com
 * Date: 21/07/14
 */
public abstract class AbstractTestCase extends UISpecTestCase {
    protected ReloadableResourceBundleMessageSource messageSource;
    protected Locale locale;

    public abstract String getLang();

    @Override
    protected void setUp() throws Exception {
        String[] contextPaths = new String[]{"context/app-test-context.xml"};
        new ClassPathXmlApplicationContext(contextPaths);
        messageSource = ApplicationContextProvider.getApplicationContext().getBean("messageSource", ReloadableResourceBundleMessageSource.class);
        locale = new Locale(getLang());
    }

    protected void updateThread() {
        ThreadContext context = new ThreadContext();
        context.setLocale(locale);
        context.setLocalizer(new MyLocalizer(locale));
        context.setMessageSource(messageSource);
        MyThreadLocal.set(context);
    }

    @Override
    protected void tearDown() throws Exception {
    }
}
