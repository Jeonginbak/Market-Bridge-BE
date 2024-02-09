package com.objects.marketbridge.order.controller.dto;

import com.objects.marketbridge.order.service.dto.GetCancelDetailDto;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

public class GetCancelDetailHttp {

    @NoArgsConstructor
    @Getter
    public static class Response {

        private LocalDateTime orderDate;
        private LocalDateTime cancelDate;
        private String orderNo;
        private String cancelReason;
        private ProductInfo productInfo;
        private RefundInfo refundInfo;

        @Builder
        private Response(LocalDateTime orderDate, String orderNo, ProductInfo productInfo, LocalDateTime cancelDate, String cancelReason, RefundInfo refundInfo) {
            this.orderDate = orderDate;
            this.orderNo = orderNo;
            this.productInfo = productInfo;
            this.cancelDate = cancelDate;
            this.cancelReason = cancelReason;
            this.refundInfo = refundInfo;
        }

        public static Response of(GetCancelDetailDto.Response dto) {
            return Response.builder()
                    .orderDate(dto.getOrderDate())
                    .cancelDate(dto.getCancelDate())
                    .orderNo(dto.getOrderNo())
                    .cancelReason(dto.getCancelReason())
                    .refundInfo(RefundInfo.of(dto.getRefundInfo()))
                    .productInfo(ProductInfo.of(dto.getProductInfo()))
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class ProductInfo {

        private Long productId;
        private String productNo;
        private String name;
        private Long price;
        private Long quantity;

        @Builder
        private ProductInfo(Long productId, String productNo, String name, Long price, Long quantity) {
            this.productId = productId;
            this.productNo = productNo;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
        }

        public static ProductInfo of(GetCancelDetailDto.ProductInfo dto) {
            return ProductInfo.builder()
                    .productId(dto.getProductId())
                    .name(dto.getName())
                    .price(dto.getPrice())
                    .quantity(dto.getQuantity())
                    .productNo(dto.getProductNo())
                    .build();
        }
    }

    @Getter
    @NoArgsConstructor
    public static class RefundInfo {

        private Long deliveryFee;
        private Long refundFee;
        private Long discountPrice;
        private Long totalPrice;

        @Builder
        private RefundInfo(Long deliveryFee, Long refundFee, Long discountPrice, Long totalPrice) {
            this.deliveryFee = deliveryFee;
            this.refundFee = refundFee;
            this.discountPrice = discountPrice;
            this.totalPrice = totalPrice;
        }

        public static RefundInfo of(GetCancelDetailDto.RefundInfo dto) {
            return RefundInfo.builder()
                    .deliveryFee(dto.getDeliveryFee())
                    .refundFee(dto.getRefundFee())
                    .discountPrice(dto.getDiscountPrice())
                    .totalPrice(dto.getTotalPrice())
                    .build();
        }
    }
}
