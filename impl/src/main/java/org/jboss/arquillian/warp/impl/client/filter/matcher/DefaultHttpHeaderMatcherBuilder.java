/*
 * JBoss, Home of Professional Open Source
 * Copyright 2012, Red Hat Middleware LLC, and individual contributors
 * by the @authors tag. See the copyright.txt in the distribution for a
 * full listing of individual contributors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 * http://www.apache.org/licenses/LICENSE-2.0
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package org.jboss.arquillian.warp.impl.client.filter.matcher;

import java.util.List;

import org.jboss.arquillian.warp.client.filter.RequestFilter;
import org.jboss.arquillian.warp.client.filter.http.HttpFilterBuilder;
import org.jboss.arquillian.warp.client.filter.http.HttpRequest;
import org.jboss.arquillian.warp.client.filter.matcher.HttpHeaderMatcherBuilder;
import org.jboss.arquillian.warp.impl.client.filter.http.HttpFilterChainBuilder;
import org.jboss.arquillian.warp.impl.client.filter.http.NotHttpFilterChainBuilder;

/**
 * A default implementation of {@link DefaultHttpHeaderMatcherBuilder}.
 */
public class DefaultHttpHeaderMatcherBuilder extends AbstractMatcherFilterBuilder
    implements HttpHeaderMatcherBuilder<HttpFilterBuilder> {

    /**
     * Creates new instance of {@link DefaultHttpHeaderMatcherBuilder} with given filter builder.
     *
     * @param filterChainBuilder the instance of {@link HttpFilterChainBuilder}
     */
    public DefaultHttpHeaderMatcherBuilder(HttpFilterChainBuilder<HttpFilterBuilder> filterChainBuilder) {
        super(filterChainBuilder);
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpFilterBuilder equal(final String name, final String value) {

        return addFilter(new RequestFilter<HttpRequest>() {

            @Override
            public boolean matches(HttpRequest request) {

                String headerValue = request.getHeader(name);

                return headerValue != null ? headerValue.equals(value) : value == null;
            }

            @Override
            public String toString() {
                return String.format("header.equal('%s', '%s')", name, value);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpFilterBuilder containsHeader(final String name) {

        return addFilter(new RequestFilter<HttpRequest>() {

            @Override
            public boolean matches(HttpRequest request) {

                return request.containsHeader(name);
            }

            @Override
            public String toString() {
                return String.format("containsHeader('%s')", name);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpFilterBuilder containsValue(final String name, final String value) {

        return addFilter(new RequestFilter<HttpRequest>() {

            @Override
            public boolean matches(HttpRequest request) {

                List<String> values = request.getHeaders(name);

                for (String val : values) {

                    if (val.equals(values)) {

                        return true;
                    }
                }

                return false;
            }

            @Override
            public String toString() {
                return String.format("containsValue('%s', '%s')", name, value);
            }
        });
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public HttpHeaderMatcherBuilder<HttpFilterBuilder> not() {

        return new DefaultHttpHeaderMatcherBuilder(new NotHttpFilterChainBuilder(getFilterChainBuilder()));
    }
}
