package com.picpay.timelineservice.models;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;
import org.springframework.data.elasticsearch.annotations.FieldType;
import org.springframework.data.elasticsearch.annotations.InnerField;
import org.springframework.data.elasticsearch.annotations.MultiField;
import org.springframework.data.elasticsearch.annotations.Setting;

@Document(indexName = "picpay-timeline")
@Setting(settingPath = "/config/elasticsearch/elasticsearch-config.json")
@Getter
@Setter
public class TimelineDocument {

    protected static final String DATE_PATTERN = "yyyy-MM-dd'T'HH:mm:ss'Z'";

    @Id
    private String id;

    private String userId;

    private String sourceAccountId;

    @MultiField(mainField = @Field(type = FieldType.Text, store = true, fielddata = true),
            otherFields = {@InnerField(suffix = "sort", type = FieldType.Date, store = true, pattern = DATE_PATTERN)})
    private String movementDate;

    private String description;

    private Double transactionAmount;

    private String movementType;

    private Boolean effectiveness;

    private Double previousAmount;

    private Double currentAmount;

    private String financialOperationType;

    private String targetAccountId;

    private String barcodeNumber;
}
