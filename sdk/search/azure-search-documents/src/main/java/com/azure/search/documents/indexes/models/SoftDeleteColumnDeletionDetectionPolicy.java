// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.

package com.azure.search.documents.indexes.models;

import com.azure.core.rest.annotation.Fluent;
import com.azure.core.serde.SerdeProperty;
import com.azure.core.serde.SerdeTypeInfo;
import com.azure.core.serde.SerdeTypeName;

/**
 * Defines a data deletion detection policy that implements a soft-deletion
 * strategy. It determines whether an item should be deleted based on the value
 * of a designated 'soft delete' column.
 */
@SerdeTypeInfo(use = SerdeTypeInfo.Id.NAME, include = SerdeTypeInfo.As.PROPERTY, property = "@odata.type")
@SerdeTypeName("#Microsoft.Azure.Search.SoftDeleteColumnDeletionDetectionPolicy")
@Fluent
public final class SoftDeleteColumnDeletionDetectionPolicy extends DataDeletionDetectionPolicy {
    /*
     * The name of the column to use for soft-deletion detection.
     */
    @SerdeProperty(value = "softDeleteColumnName")
    private String softDeleteColumnName;

    /*
     * The marker value that identifies an item as deleted.
     */
    @SerdeProperty(value = "softDeleteMarkerValue")
    private String softDeleteMarkerValue;

    /**
     * Get the softDeleteColumnName property: The name of the column to use for
     * soft-deletion detection.
     *
     * @return the softDeleteColumnName value.
     */
    public String getSoftDeleteColumnName() {
        return this.softDeleteColumnName;
    }

    /**
     * Set the softDeleteColumnName property: The name of the column to use for
     * soft-deletion detection.
     *
     * @param softDeleteColumnName the softDeleteColumnName value to set.
     * @return the SoftDeleteColumnDeletionDetectionPolicy object itself.
     */
    public SoftDeleteColumnDeletionDetectionPolicy setSoftDeleteColumnName(String softDeleteColumnName) {
        this.softDeleteColumnName = softDeleteColumnName;
        return this;
    }

    /**
     * Get the softDeleteMarkerValue property: The marker value that identifies
     * an item as deleted.
     *
     * @return the softDeleteMarkerValue value.
     */
    public String getSoftDeleteMarkerValue() {
        return this.softDeleteMarkerValue;
    }

    /**
     * Set the softDeleteMarkerValue property: The marker value that identifies
     * an item as deleted.
     *
     * @param softDeleteMarkerValue the softDeleteMarkerValue value to set.
     * @return the SoftDeleteColumnDeletionDetectionPolicy object itself.
     */
    public SoftDeleteColumnDeletionDetectionPolicy setSoftDeleteMarkerValue(String softDeleteMarkerValue) {
        this.softDeleteMarkerValue = softDeleteMarkerValue;
        return this;
    }
}