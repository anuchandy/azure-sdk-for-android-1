// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package com.azure.search.documents.implementation.models;

import com.azure.core.http.HttpResponse;
import com.azure.core.http.exception.HttpResponseException;

/** Exception thrown for an invalid response with SearchError information. */
public final class SearchErrorException extends HttpResponseException {
    /**
     * Initializes a new instance of the SearchErrorException class.
     *
     * @param message the exception message or the response content if a message is not available.
     * @param response the HTTP response.
     */
    public SearchErrorException(String message, HttpResponse response) {
        super(message, response);
    }

    /**
     * Initializes a new instance of the SearchErrorException class.
     *
     * @param message the exception message or the response content if a message is not available.
     * @param response the HTTP response.
     * @param value the deserialized response value.
     */
    public SearchErrorException(String message, HttpResponse response, SearchError value) {
        super(message, response, value);
    }

    @Override
    public SearchError getValue() {
        return (SearchError) super.getValue();
    }
}