/*
 * Copyright 2015 the original author or authors.
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
package org.grails.datastore.gorm

import org.grails.datastore.mapping.validation.ValidationErrors
import org.springframework.validation.Errors


/**
 * A trait that adds GORM validation behavior to any class
 *
 * @author Jeff Brown
 * @author Graeme Rocher
 * @since 4.0
 */
trait GormValidateable {
    private static GormValidationApi internalValidationApi

    /**
     * The validation errors object
     */
    Errors errors

    /**
     * Used to initialise the state of GORM. This method is used internally by the framework and SHOULD NOT be called by the developer
     */
    static void initInternalValidationApi(GormValidationApi gvi) {
        internalValidationApi = gvi
    }

    /**
     * Used to obtain the {@link GormValidationApi} instance. This method is used internally by the framework and SHOULD NOT be called by the developer
     */
    static GormValidationApi currentGormValidationApi() {
        if(internalValidationApi == null) {
            throw new IllegalStateException("Method on class [${this.getName()}] was used outside of a Grails application. If running in the context of a test using the mocking API or bootstrap Grails correctly.")
        }
        internalValidationApi
    }

    /**
     * Validates an instance for the given arguments
     *
     * @param arguments The arguments to use
     * @return True if the instance is valid
     */
    boolean validate(Map arguments) {
        currentGormValidationApi().validate this, arguments
    }

    /**
     * Validates an instance
     *
     * @param fields The list of fields to validate
     * @return True if the instance is valid
     */
    boolean validate(List fields) {
        currentGormValidationApi().validate this, fields
    }

    /**
     * Validates an instance
     *
     * @return True if the instance is valid
     */
    boolean validate() {
        currentGormValidationApi().validate this
    }

    /**
     * Obtains the errors for an instance
     * @return The {@link Errors} instance
     */
    Errors getErrors() {
        if(errors == null) {
            errors = new ValidationErrors(this)
        }
        errors
    }

    /**
     * Clears any errors that exist on an instance
     */
    void clearErrors() {
        errors = new ValidationErrors(this)
    }

    /**
     * Tests whether an instance has any errors
     * @return True if errors exist
     */
    Boolean hasErrors() {
        getErrors().hasErrors()
    }
}