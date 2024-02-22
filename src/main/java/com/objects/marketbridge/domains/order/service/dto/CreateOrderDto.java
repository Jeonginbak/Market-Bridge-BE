package com.objects.marketbridge.domains.order.service.dto;

import com.objects.marketbridge.domains.order.domain.ProductValue;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor
public class CreateOrderDto {

    private String tid;
    private Long memberId;
    private Long addressId;
    private String orderName;
    private String orderNo;
    private Long totalOrderPrice;
    private Long realOrderPrice;
    private Long totalDiscountPrice;
    private List<ProductValue> productValues;

    @Builder
    public CreateOrderDto(String tid, Long memberId, Long addressId, String orderName, String orderNo, Long totalOrderPrice, Long realOrderPrice, Long totalDiscountPrice, List<ProductValue> productValues) {
        this.tid = tid;
        this.memberId = memberId;
        this.addressId = addressId;
        this.orderName = orderName;
        this.orderNo = orderNo;
        this.totalOrderPrice = totalOrderPrice;
        this.realOrderPrice = realOrderPrice;
        this.totalDiscountPrice = totalDiscountPrice;
        this.productValues = productValues;
    }
}