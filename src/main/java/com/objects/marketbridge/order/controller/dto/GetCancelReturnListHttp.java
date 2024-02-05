package com.objects.marketbridge.order.controller.dto;

import com.objects.marketbridge.order.infra.dtio.GetCancelReturnListDtio;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;

import java.time.LocalDateTime;
import java.util.List;

@Getter
@NoArgsConstructor
public class GetCancelReturnListHttp {

    @Getter
    @NoArgsConstructor
    public static class Response {

        private LocalDateTime cancelReceiptDate;
        private LocalDateTime orderDate;
        private String orderNo;
        private List<OrderDetailInfo> orderDetailInfos;

        @Builder
        private Response(LocalDateTime cancelReceiptDate, LocalDateTime orderDate, String orderNo, List<OrderDetailInfo> orderDetailInfos) {
            this.cancelReceiptDate = cancelReceiptDate;
            this.orderDate = orderDate;
            this.orderNo = orderNo;
            this.orderDetailInfos = orderDetailInfos;
        }

        public static Response of(GetCancelReturnListDtio.Response cancelReturnResponse) {
            return Response.builder()
                    .cancelReceiptDate(cancelReturnResponse.getCancelReceiptDate())
                    .orderDate(cancelReturnResponse.getOrderDate())
                    .orderNo(cancelReturnResponse.getOrderNo())
                    .orderDetailInfos(
                            cancelReturnResponse.getOrderDetailInfos().stream()
                                    .map(OrderDetailInfo::of)
                                    .toList()
                    )
                    .build();
        }

        public static Page<Response> of(Page<GetCancelReturnListDtio.Response> cancelReturnResponsePage) {
            List<Response> responseList = cancelReturnResponsePage.getContent().stream()
                    .map(Response::of)
                    .toList();

            return new PageImpl<>(responseList, cancelReturnResponsePage.getPageable(), cancelReturnResponsePage.getTotalElements());
        }
    }

    @Getter
    @NoArgsConstructor
    public static class OrderDetailInfo {
        private String orderNo;
        private Long productId;
        private String productNo;
        private String name;
        private Long price;
        private Long quantity;
        private String orderStatus;

        @Builder
        private OrderDetailInfo(String orderNo, Long productId, String productNo, String name, Long price, Long quantity, String orderStatus) {
            this.orderNo = orderNo;
            this.productId = productId;
            this.productNo = productNo;
            this.name = name;
            this.price = price;
            this.quantity = quantity;
            this.orderStatus = orderStatus;
        }

        public static OrderDetailInfo of(GetCancelReturnListDtio.OrderDetailInfo orderDetailInfo) {
            return OrderDetailInfo.builder()
                    .orderNo(orderDetailInfo.getOrderNo())
                    .productId(orderDetailInfo.getProductId())
                    .productNo(orderDetailInfo.getProductNo())
                    .name(orderDetailInfo.getName())
                    .price(orderDetailInfo.getPrice())
                    .quantity(orderDetailInfo.getQuantity())
                    .orderStatus(orderDetailInfo.getOrderStatus())
                    .build();
        }
    }
}
