/**
 * zoneland.net Inc.
 * Copyright (c) 2002-2012 All Rights Reserved.
 */
package com.foodoon.mvc.runtime.core.autoconfig;

import java.util.Properties;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanDefinitionStoreException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanDefinitionVisitor;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.core.Ordered;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.PropertyPlaceholderHelper;
import org.springframework.util.PropertyPlaceholderHelper.PlaceholderResolver;
import org.springframework.util.StringValueResolver;

import com.foodoon.mvc.runtime.core.xml.PropertiesReplace;

/**
 * 
 * @author gag
 * @version $Id: XmlPropertyResourceConfigurer.java, v 0.1 2012-4-26 下午1:24:52 gag Exp $
 */
public class XmlPropertyResourceConfigurer implements BeanFactoryPostProcessor, PriorityOrdered {

    private BeanFactory beanFactory;

    private String      beanName;

    /** 
     * @see org.springframework.core.Ordered#getOrder()
     */
    public int getOrder() {
        return Ordered.LOWEST_PRECEDENCE;
    }

    /** 
     * @see org.springframework.beans.factory.config.BeanFactoryPostProcessor#postProcessBeanFactory(org.springframework.beans.factory.config.ConfigurableListableBeanFactory)
     */
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory)
                                                                                   throws BeansException {
        //����auto-config�е�����

    }

    protected void processProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                     Properties props) throws BeansException {

        StringValueResolver valueResolver = new PlaceholderResolvingStringValueResolver(props);

        this.doProcessProperties(beanFactoryToProcess, valueResolver);
    }

    protected void doProcessProperties(ConfigurableListableBeanFactory beanFactoryToProcess,
                                       StringValueResolver valueResolver) {

        BeanDefinitionVisitor visitor = new BeanDefinitionVisitor(valueResolver);

        String[] beanNames = beanFactoryToProcess.getBeanDefinitionNames();
        for (String curName : beanNames) {
            if (!(curName.equals(this.beanName) && beanFactoryToProcess.equals(this.beanFactory))) {
                BeanDefinition bd = beanFactoryToProcess.getBeanDefinition(curName);
                try {
                    visitor.visitBeanDefinition(bd);
                } catch (Exception ex) {
                    throw new BeanDefinitionStoreException(bd.getResourceDescription(), curName,
                        ex.getMessage());
                }
            }
        }
        beanFactoryToProcess.resolveAliases(valueResolver);
        beanFactoryToProcess.addEmbeddedValueResolver(valueResolver);
    }

    private class PlaceholderResolvingStringValueResolver implements StringValueResolver {

        private final PropertyPlaceholderHelper helper;

        private final PlaceholderResolver       resolver;

        public PlaceholderResolvingStringValueResolver(Properties props) {
            this.helper = new PropertyPlaceholderHelper(
                PropertiesReplace.DEFAULT_PLACEHOLDER_PREFIX,
                PropertiesReplace.DEFAULT_PLACEHOLDER_SUFFIX,
                PropertiesReplace.DEFAULT_VALUE_SEPARATOR, false);
            this.resolver = new PropertyPlaceholderConfigurerResolver(props);
        }

        public String resolveStringValue(String strVal) throws BeansException {
            return this.helper.replacePlaceholders(strVal, this.resolver);

        }
    }

    private class PropertyPlaceholderConfigurerResolver implements PlaceholderResolver {

        private final Properties props;

        private PropertyPlaceholderConfigurerResolver(Properties props) {
            this.props = props;
        }

        public String resolvePlaceholder(String placeholderName) {
            return XmlPropertyResourceConfigurer.this.resolvePlaceholder(placeholderName, props);
        }
    }

    protected String resolvePlaceholder(String placeholder, Properties props) {

        return resolvePlaceholder(placeholder, props);

    }

    /**
     * Setter method for property <tt>beanName</tt>.
     * 
     * @param beanName value to be assigned to property beanName
     */
    public void setBeanName(String beanName) {
        this.beanName = beanName;
    }

    public void setBeanFactory(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }

}
