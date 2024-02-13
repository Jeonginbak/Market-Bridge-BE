package com.objects.marketbridge.order.service.dto;

import com.objects.marketbridge.common.service.port.DateTimeHolder;
import com.objects.marketbridge.member.domain.MembershipType;
import com.objects.marketbridge.order.domain.OrderCancelReturn;
import com.objects.marketbridge.order.domain.OrderDetail;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;

import static com.objects.marketbridge.order.domain.MemberShipPrice.BASIC;
import static com.objects.marketbridge.order.domain.MemberShipPrice.WOW;

public class GetReturnDetailDto {

    @Getter
    @NoArgsConstructor
    public static class Response {
        private LocalDateTime orderDate;
        private LocalDateTime cancelDate;
        private String orderNo;
        private String reason;
        private ProductInfo productInfo;
        private RefundInfo refundInfo;

        @Builder
        private Response(LocalDateTime orderDate, LocalDateTime cancelDate, String orderNo, String reason, ProductInfo productInfo, RefundInfo refundInfo) {
            this.orderDate = orderDate;
            this.cancelDate = cancelDate;
            this.orderNo = orderNo;
            this.reason = reason;
            this.productInfo = productInfo;
            this.refundInfo = refundInfo;
        }

        public static GetReturnDetailDto.Response of(OrderCancelReturn orderReturn, String memberShip, DateTimeHolder dateTimeHolder) {
            return Response.builder()
                    .orderDate(dateTimeHolder.getCreateTime(orderReturn))
                    .cancelDate(orderReturn.getOrderDetail().getCancelledAt())
                    .reason(orderReturn.getReason())
                    .orderNo(orderReturn.getOrderDetail().getOrderNo())
                    .productInfo(ProductInfo.of(orderReturn.getOrderDetail()))
                    .refundInfo(RefundInfo.of(orderReturn.getOrderDetail(), memberShip))
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

        public static GetReturnDetailDto.ProductInfo of(OrderDetail orderDetail) {
            return GetReturnDetailDto.ProductInfo.builder()
                    .productId(orderDetail.getProduct().getId())
                    .productNo(orderDetail.getProduct().getProductNo())
                    .name(orderDetail.getProduct().getName())
                    .price(orderDetail.getPrice())
                    .quantity(orderDetail.getQuantity())
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

        public static GetReturnDetailDto.RefundInfo of(OrderDetail orderDetail, String memberShip) {
            if (isBasicMember(memberShip)) {
                return createDto(orderDetail, BASIC.getDeliveryFee(), BASIC.getRefundFee());
            }
            return createDto(orderDetail, WOW.getDeliveryFee(), WOW.getRefundFee());
        }

        private static boolean isBasicMember(String memberShip) {
            return Objects.equals(memberShip, MembershipType.BASIC.getText());
        }

        private static GetReturnDetailDto.RefundInfo createDto(OrderDetail orderDetail, Long deliveryFee, Long refundFee) {
            Long discountPrice = 0L;
            if (hasMemberCoupon(orderDetail))
                discountPrice = orderDetail.getMemberCoupon().getCoupon().getPrice();

            return GetReturnDetailDto.RefundInfo.builder()
                    .discountPrice(discountPrice)
                    .totalPrice(Long.valueOf(Objects.requireNonNull(orderDetail).cancelAmount()))
                    .refundFee(refundFee)
                    .deliveryFee(deliveryFee)
                    .build();
        }

        private static boolean hasMemberCoupon(OrderDetail orderDetail) {
            return orderDetail != null && orderDetail.getMemberCoupon() != null;
        }
    }
}
