package com.objects.marketbridge.common.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;

public class KakaoPayCancelRequest {

    private String cid;
    private String tid;

    @JsonProperty("cancel_amount")
    private Integer cancelAmount;

    @JsonProperty("cancel_tax_free_amount")
    private Integer cancelTaxFreeAmount;

    @Builder
    public KakaoPayCancelRequest(String cid, String tid, Integer cancelAmount, Integer cancelTaxFreeAmount) {
        this.cid = cid;
        this.tid = tid;
        this.cancelAmount = cancelAmount;
        this.cancelTaxFreeAmount = cancelTaxFreeAmount;
    }
}