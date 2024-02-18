package com.objects.marketbridge.order.controller;

import com.objects.marketbridge.common.interceptor.ApiResponse;
import com.objects.marketbridge.member.domain.Member;
import com.objects.marketbridge.member.domain.MembershipType;
import com.objects.marketbridge.order.controller.dto.*;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.mock.BaseFakeOrderDetailRepository;
import com.objects.marketbridge.order.mock.BaseFakeOrderRepository;
import com.objects.marketbridge.order.mock.TestContainer;
import com.objects.marketbridge.order.mock.TestDateTimeHolder;
import com.objects.marketbridge.product.domain.Product;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.Page;
import org.springframework.test.util.ReflectionTestUtils;

import java.time.LocalDateTime;

import static com.objects.marketbridge.order.domain.StatusCodeType.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.springframework.http.HttpStatus.*;

public class OrderCancelReturnControllerTest {

    private LocalDateTime orderDate = LocalDateTime.of(2024, 2, 9, 3, 9);
    private TestContainer testContainer = TestContainer.builder()
            .dateTimeHolder(
                    TestDateTimeHolder.builder()
                            .createTime(orderDate)
                            .build()
            )
            .build();

    @AfterEach
    void afterEach() {
        BaseFakeOrderDetailRepository.getInstance().clear();
        BaseFakeOrderRepository.getInstance().clear();
        testContainer.memberRepository.deleteAllInBatch();
        testContainer.productRepository.deleteAllInBatch();
    }

    // TODO 취소시간 테스트 고려 + 반품/취소 구별 필요
//    @Test
//    @DisplayName("취소/반품 확정")
//    public void confirmCancelReturn() {
//        // given
//        ConfirmCancelReturnHttp.OrderDetailInfo orderDetailInfo1 = ConfirmCancelReturnHttp.OrderDetailInfo.builder()
//                .orderDetailId(1L)
//                .numberOfCancellation(1L)
//                .build();
//        ConfirmCancelReturnHttp.OrderDetailInfo orderDetailInfo2 = ConfirmCancelReturnHttp.OrderDetailInfo.builder()
//                .orderDetailId(2L)
//                .numberOfCancellation(2L)
//                .build();
//        List<ConfirmCancelReturnHttp.OrderDetailInfo> orderDetailInfos = List.of(orderDetailInfo1, orderDetailInfo2);
//
//        ConfirmCancelReturnHttp.Request request = ConfirmCancelReturnHttp.Request.builder()
//                .orderDetailInfos(orderDetailInfos)
//                .cancelReason("단순변심")
//                .build();
//
//        // when
//        ApiResponse<ConfirmCancelReturnHttp.Response> result = orderCancelReturnController.confirmCancelReturn(request);
//
//        // then
//        assertThat(result.getCode()).isEqualTo(OK.value());
//        assertThat(result.getStatus()).isEqualTo(OK);
//        assertThat(result.getMessage()).isEqualTo(OK.name());
//        assertThat(result.getData().getOrderId()).isEqualTo(1L);
//        assertThat(result.getData().getOrderNo()).isEqualTo("1");
//        assertThat(result.getData().getTotalPrice()).isEqualTo(30000L);
//
//        assertThat(result.getData().getCancelledItems().get(0).getProductId()).isEqualTo(1L);
//        assertThat(result.getData().getCancelledItems().get(0).getProductNo()).isEqualTo("1");
//        assertThat(result.getData().getCancelledItems().get(0).getName()).isEqualTo("빵빵이키링");
//        assertThat(result.getData().getCancelledItems().get(0).getPrice()).isEqualTo(1000L);
//        assertThat(result.getData().getCancelledItems().get(0).getQuantity()).isEqualTo(1L);
//
//        assertThat(result.getData().getCancelledItems().get(1).getProductId()).isEqualTo(2L);
//        assertThat(result.getData().getCancelledItems().get(1).getProductNo()).isEqualTo("2");
//        assertThat(result.getData().getCancelledItems().get(1).getName()).isEqualTo("옥지얌키링");
//        assertThat(result.getData().getCancelledItems().get(1).getPrice()).isEqualTo(2000L);
//        assertThat(result.getData().getCancelledItems().get(1).getQuantity()).isEqualTo(2L);
//
//        assertThat(result.getData().getRefundInfo().getRefundMethod()).isEqualTo("카드");
//        assertThat(result.getData().getRefundInfo().getTotalRefundAmount()).isEqualTo(5000L);
//    }







    @Test
    @DisplayName("취소/반품한 상품들을 조회할 수 있다.")
    public void getCancelReturnList() {
        // given
        Member member = Member.builder()
                .membership(MembershipType.BASIC.getText())
                .build();

        Product product1 = Product.builder()
                .name("빵빵이키링")
                .thumbImg("빵빵이썸네일")
                .productNo("1")
                .build();
        Product product2 = Product.builder()
                .name("옥지얌키링")
                .thumbImg("옥지얌썸네일")
                .productNo("2")
                .build();

        LocalDateTime orderDate1 = LocalDateTime.of(2024, 2, 8, 9, 30);
        LocalDateTime orderDate2 = LocalDateTime.of(2024, 2, 8, 9, 31);
        LocalDateTime cancelDate1 = LocalDateTime.of(2024, 2, 8, 9, 32);
        LocalDateTime cancelDate2 = LocalDateTime.of(2024, 2, 8, 9, 33);

        Order order = Order.builder()
                .member(member)
                .orderNo("1")
                .build();

        OrderDetail orderDetail1 = OrderDetail.builder()
                .order(order)
                .orderNo("1")
                .quantity(10L)
                .product(product1)
                .price(1000L)
                .statusCode(ORDER_CANCEL.getCode())
                .cancelledAt(cancelDate1)
                .build();
        ReflectionTestUtils.setField(orderDetail1, "createdAt", orderDate1, LocalDateTime.class);
        OrderDetail orderDetail2 = OrderDetail.builder()
                .order(order)
                .orderNo("1")
                .quantity(10L)
                .product(product2)
                .price(2000L)
                .statusCode(ORDER_CANCEL.getCode())
                .cancelledAt(cancelDate2)
                .build();
        ReflectionTestUtils.setField(orderDetail2, "createdAt", orderDate2, LocalDateTime.class);

        order.addOrderDetail(orderDetail1);
        order.addOrderDetail(orderDetail2);

        testContainer.orderCommendRepository.save(order);
        testContainer.productRepository.save(product1);
        testContainer.productRepository.save(product2);
        testContainer.orderDetailCommendRepository.save(orderDetail1);
        testContainer.orderDetailCommendRepository.save(orderDetail2);
        testContainer.memberRepository.save(member);

        Integer page = 0;
        Integer size = 5;
        Long memberId = 1L;

        // when
        ApiResponse<Page<GetCancelReturnListHttp.Response>> result = testContainer.orderCancelReturnController.getCancelReturnList(page, size, memberId);

        // then
        assertThat(result.getCode()).isEqualTo(OK.value());
        assertThat(result.getStatus()).isEqualTo(OK);
        assertThat(result.getMessage()).isEqualTo(OK.name());
        assertThat(result.getData().getContent().size()).isEqualTo(2);
        assertThat(result.getData().getContent().get(0).getCancelReceiptDate()).isEqualTo(cancelDate1);
        assertThat(result.getData().getContent().get(0).getOrderDate()).isEqualTo(orderDate1);
        assertThat(result.getData().getContent().get(1).getCancelReceiptDate()).isEqualTo(cancelDate2);
        assertThat(result.getData().getContent().get(1).getOrderDate()).isEqualTo(orderDate2);

        GetCancelReturnListHttp.OrderDetailInfo orderDetailInfo1 = result.getData().getContent().get(0).getOrderDetailInfo();
        assertThat(orderDetailInfo1.getOrderNo()).isEqualTo("1");
        assertThat(orderDetailInfo1.getProductId()).isEqualTo(1L);
        assertThat(orderDetailInfo1.getProductNo()).isEqualTo("1");
        assertThat(orderDetailInfo1.getName()).isEqualTo("빵빵이키링");
        assertThat(orderDetailInfo1.getPrice()).isEqualTo(1000L);
        assertThat(orderDetailInfo1.getQuantity()).isEqualTo(10L);
        assertThat(orderDetailInfo1.getOrderStatus()).isEqualTo(ORDER_CANCEL.getCode());

        GetCancelReturnListHttp.OrderDetailInfo orderDetailInfo2 = result.getData().getContent().get(1).getOrderDetailInfo();
        assertThat(orderDetailInfo2.getOrderNo()).isEqualTo("1");
        assertThat(orderDetailInfo2.getProductId()).isEqualTo(2L);
        assertThat(orderDetailInfo2.getProductNo()).isEqualTo("2");
        assertThat(orderDetailInfo2.getName()).isEqualTo("옥지얌키링");
        assertThat(orderDetailInfo2.getPrice()).isEqualTo(2000L);
        assertThat(orderDetailInfo2.getQuantity()).isEqualTo(10L);
        assertThat(orderDetailInfo2.getOrderStatus()).isEqualTo(ORDER_CANCEL.getCode());
    }



}
