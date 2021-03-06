package com.dictiographer.utils;

/*
 * Copyright 2002-2004 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import freemarker.cache.ClassTemplateLoader;
import freemarker.cache.MultiTemplateLoader;
import freemarker.cache.TemplateLoader;
import freemarker.template.Configuration;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ResourceLoaderAware;
import org.springframework.ui.freemarker.FreeMarkerConfigurationFactory;

import java.io.IOException;

/**
 * JavaBean to configure FreeMarker for web usage, via the "configLocation"
 * and/or "freemarkerSettings" and/or "templateLoaderPath" properties.
 * The simplest way to use this class is to specify just a "templateLoaderPath":
 * You do not need any further configuration then.
 * <p/>
 * <pre>
 * &lt;bean id="freemarkerConfig" class="org.springframework.web.servlet.view.freemarker.FreeMarkerConfigurer">
 *   &lt;property name="templateLoaderPath">&lt;value>/WEB-INF/freemarker/&lt;/value>&lt;/property>
 * &lt;/bean></pre>
 *
 * This bean must be included in the application context of any application
 * using Spring's FreeMarkerView for web MVC. It exists purely to configure FreeMarker.
 * It is not meant to be referenced by application components but just internally
 * by FreeMarkerView. Implements FreeMarkerConfig to be found by FreeMarkerView without
 * depending on the bean name the configurer. Each DispatcherServlet can define its
 * own FreeMarkerConfigurer if desired.
 *
 * <p>Note that you can also refer to a preconfigured FreeMarker Configuration
 * instance, for example one set up by FreeMarkerConfigurationFactoryBean, via
 * the "configuration" property. This allows to share a FreeMarker Configuration
 * for web and email usage, for example.
 *
 * <p>This configurer registers a template loader for this package, allowing to
 * reference the "spring.ftl" macro library (contained in this package and thus
 * in spring.jar) like this:
 *
 * <pre>
 * &lt;#import "spring.ftl" as spring/&gt;
 * &lt;@spring.bind "person.age"/&gt;
 * age is ${spring.status.value}</pre>
 *
 * Note: Spring's FreeMarker support requires FreeMarker 2.3 or higher.
 *
 * @author Darren Davison
 * @version $Id: FreeMarkerConfigurer.java,v 1.5 2004-07-28 09:40:21 jhoeller Exp $
 * @see #setConfigLocation
 * @see #setFreemarkerSettings
 * @see #setTemplateLoaderPath
 * @see #setConfiguration
 * @see org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean
 * @since 3/3/2004
 */
public class FreeMarkerConfigurer extends FreeMarkerConfigurationFactory
        implements FreeMarkerConfig, InitializingBean, ResourceLoaderAware {

    private Configuration configuration;

    /**
     * Set a preconfigured Configuration to use for the FreeMarker web config, e.g. a
     * shared one for web and email usage, set up via FreeMarkerConfigurationFactoryBean.
     * If this is not set, FreeMarkerConfigurationFactory's properties (inherited by
     * this class) have to be specified.
     *
     * @see org.springframework.ui.freemarker.FreeMarkerConfigurationFactoryBean
     */
    public void setConfiguration(Configuration configuration) {
        this.configuration = configuration;
    }

    /**
     * Initialize FreeMarkerConfigurationFactory's Configuration
     * if not overridden by a preconfigured FreeMarker Configuation.
     *
     * @see #createConfiguration
     * @see #setConfiguration
     */
    public void afterPropertiesSet() throws IOException, TemplateException {
        if (this.configuration == null) {
            this.configuration = createConfiguration();
        }
    }

    /**
     * Post-processes the config to ensure that the Spring macro library can be resolved
     * and imported by application templates: A ClassTemplateLoader for this package is
     * added to the FreeMarker configuration.
     *
     * @see freemarker.cache.ClassTemplateLoader
     */
    protected void postProcessConfiguration(Configuration config) {
        TemplateLoader loader = config.getTemplateLoader();
        ClassTemplateLoader formMacroLoader = new ClassTemplateLoader(getClass(),"templates");
        TemplateLoader[] loaders = new TemplateLoader[]{loader, formMacroLoader};
        MultiTemplateLoader multiLoader = new MultiTemplateLoader(loaders);
        config.setTemplateLoader(multiLoader);
        if (logger.isInfoEnabled()) {
            logger.info("ClassTemplateLoader for Spring macros added to FreeMarker configuration");
        }
    }

    public Configuration getConfiguration() {
        return this.configuration;
    }

}
