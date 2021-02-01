// Copyright (c) Microsoft Corporation. All rights reserved.
// Licensed under the MIT License.
// Code generated by Microsoft (R) AutoRest Code Generator.
// Changes may cause incorrect behavior and will be lost if the code is
// regenerated.

package com.azure.search.documents.indexes.implementation.models;

import com.azure.core.rest.annotation.Fluent;
import com.azure.core.serde.JsonFlatten;
import com.azure.search.documents.indexes.models.ImageAnalysisSkillLanguage;
import com.azure.search.documents.indexes.models.ImageDetail;
import com.azure.search.documents.indexes.models.OutputFieldMappingEntry;
import com.azure.search.documents.indexes.models.VisualFeature;
import com.azure.core.serde.SerdeToPojo;
import com.azure.core.serde.SerdeProperty;
import com.azure.core.serde.SerdeTypeInfo;
import com.azure.core.serde.SerdeTypeName;
import java.util.List;

/** A skill that analyzes image files. It extracts a rich set of visual features based on the image content. */
@SerdeTypeInfo(use = SerdeTypeInfo.Id.NAME, include = SerdeTypeInfo.As.PROPERTY, property = "@odata\\.type")
@SerdeTypeName("#Microsoft.Skills.Vision.ImageAnalysisSkill")
@JsonFlatten
@Fluent
public class ImageAnalysisSkill extends SearchIndexerSkill {
    /*
     * A value indicating which language code to use. Default is en.
     */
    @SerdeProperty(value = "defaultLanguageCode")
    private ImageAnalysisSkillLanguage defaultLanguageCode;

    /*
     * A list of visual features.
     */
    @SerdeProperty(value = "visualFeatures")
    private List<VisualFeature> visualFeatures;

    /*
     * A string indicating which domain-specific details to return.
     */
    @SerdeProperty(value = "details")
    private List<ImageDetail> details;

    /**
     * Creates an instance of ImageAnalysisSkill class.
     *
     * @param inputs the inputs value to set.
     * @param outputs the outputs value to set.
     */
    @SerdeToPojo
    public ImageAnalysisSkill(
            @SerdeProperty(value = "inputs") List<InputFieldMappingEntry> inputs,
            @SerdeProperty(value = "outputs") List<OutputFieldMappingEntry> outputs) {
        super(inputs, outputs);
    }

    /**
     * Get the defaultLanguageCode property: A value indicating which language code to use. Default is en.
     *
     * @return the defaultLanguageCode value.
     */
    public ImageAnalysisSkillLanguage getDefaultLanguageCode() {
        return this.defaultLanguageCode;
    }

    /**
     * Set the defaultLanguageCode property: A value indicating which language code to use. Default is en.
     *
     * @param defaultLanguageCode the defaultLanguageCode value to set.
     * @return the ImageAnalysisSkill object itself.
     */
    public ImageAnalysisSkill setDefaultLanguageCode(ImageAnalysisSkillLanguage defaultLanguageCode) {
        this.defaultLanguageCode = defaultLanguageCode;
        return this;
    }

    /**
     * Get the visualFeatures property: A list of visual features.
     *
     * @return the visualFeatures value.
     */
    public List<VisualFeature> getVisualFeatures() {
        return this.visualFeatures;
    }

    /**
     * Set the visualFeatures property: A list of visual features.
     *
     * @param visualFeatures the visualFeatures value to set.
     * @return the ImageAnalysisSkill object itself.
     */
    public ImageAnalysisSkill setVisualFeatures(List<VisualFeature> visualFeatures) {
        this.visualFeatures = visualFeatures;
        return this;
    }

    /**
     * Get the details property: A string indicating which domain-specific details to return.
     *
     * @return the details value.
     */
    public List<ImageDetail> getDetails() {
        return this.details;
    }

    /**
     * Set the details property: A string indicating which domain-specific details to return.
     *
     * @param details the details value to set.
     * @return the ImageAnalysisSkill object itself.
     */
    public ImageAnalysisSkill setDetails(List<ImageDetail> details) {
        this.details = details;
        return this;
    }
}