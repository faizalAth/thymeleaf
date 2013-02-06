/*
 * =============================================================================
 * 
 *   Copyright (c) 2011-2012, The THYMELEAF team (http://www.thymeleaf.org)
 * 
 *   Licensed under the Apache License, Version 2.0 (the "License");
 *   you may not use this file except in compliance with the License.
 *   You may obtain a copy of the License at
 * 
 *       http://www.apache.org/licenses/LICENSE-2.0
 * 
 *   Unless required by applicable law or agreed to in writing, software
 *   distributed under the License is distributed on an "AS IS" BASIS,
 *   WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *   See the License for the specific language governing permissions and
 *   limitations under the License.
 * 
 * =============================================================================
 */
package org.thymeleaf.testing.templateengine.standard.config.test;

import java.util.List;

import org.thymeleaf.testing.templateengine.exception.TestEngineConfigurationException;
import org.thymeleaf.testing.templateengine.test.ITestSuite;
import org.thymeleaf.testing.templateengine.test.ITestable;




public class DefaultExceptionDirectiveResolver 
        extends AbstractStandardDirectiveResolver<Class<? extends Throwable>> {

    
    public static final DefaultExceptionDirectiveResolver INSTANCE = new DefaultExceptionDirectiveResolver();
    public static final Class<? extends Throwable> DEFAULT_VALUE = null; 
    
    
    @SuppressWarnings("unchecked")
    private DefaultExceptionDirectiveResolver() {
        super((Class<Class<? extends Throwable>>)(Class<?>)Class.class);
    }


    @Override
    @SuppressWarnings("unchecked")
    public Class<? extends Throwable> getValue(final ITestSuite suite, final List<ITestable> path, final String fileName, 
            final String directiveName, final String directiveValue) {
        
        if (directiveValue == null || directiveValue.trim().equals("")) {
            return DEFAULT_VALUE;
        }

        Class<?> specifiedClass = null;
        try {
            specifiedClass = Class.forName(directiveValue.trim());
        } catch (final Throwable t) {
            throw new TestEngineConfigurationException(
                    suite.getName(),
                    "Exception initializing directive \"" + directiveName + "\" in test file " +
            		"\"" + fileName + "\"", t);
        }
        
        if (!Throwable.class.isAssignableFrom(specifiedClass)) {
            throw new TestEngineConfigurationException(
                    suite.getName(),
                    "Exception initializing directive \"" + directiveName + "\" in test file " +
                    "\"" + fileName + "\": Class \"" + specifiedClass.getClass().getName() + "\" does not " +
                    "extend from " + Throwable.class.getName());
        }
        
        return (Class<? extends Throwable>) specifiedClass;
        
    }

    
}
