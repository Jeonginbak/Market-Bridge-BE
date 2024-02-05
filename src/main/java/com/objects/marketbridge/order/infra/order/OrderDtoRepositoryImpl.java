package com.objects.marketbridge.order.infra.order;

import com.objects.marketbridge.order.controller.dto.GetOrderHttp;
import com.objects.marketbridge.order.domain.Order;
import com.objects.marketbridge.order.domain.OrderDetail;
import com.objects.marketbridge.order.infra.dtio.CancelReturnResponseDtio;
import com.objects.marketbridge.order.infra.dtio.DetailResponseDtio;
import com.objects.marketbridge.order.infra.dtio.QCancelReturnResponseDtio;
import com.objects.marketbridge.order.infra.dtio.QDetailResponseDtio;
import com.objects.marketbridge.order.service.dto.OrderDto;
import com.objects.marketbridge.order.service.port.OrderDtoRepository;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.objects.marketbridge.common.domain.QMember.member;
import static com.objects.marketbridge.order.controller.dto.GetOrderHttp.*;
import static com.objects.marketbridge.order.domain.QAddress.address;
import static com.objects.marketbridge.order.domain.QOrder.order;
import static com.objects.marketbridge.order.domain.QOrderDetail.orderDetail;
import static com.objects.marketbridge.order.domain.StatusCodeType.ORDER_CANCEL;
import static com.objects.marketbridge.order.domain.StatusCodeType.RETURN_COMPLETED;
import static com.objects.marketbridge.product.domain.QProduct.product;
import static org.springframework.util.StringUtils.hasText;


@Slf4j
@Repository
@Transactional(readOnly = true)
public class OrderDtoRepositoryImpl implements OrderDtoRepository {

    private final OrderJpaRepository orderJpaRepository;

    private final JPAQueryFactory queryFactory;

    public OrderDtoRepositoryImpl(OrderJpaRepository orderJpaRepository, EntityManager em) {
        this.orderJpaRepository = orderJpaRepository;
        this.queryFactory = new JPAQueryFactory(em);
    }

    @Override
    public Page<CancelReturnResponseDtio> findOrdersByMemberId(Long memberId, Pageable pageable) {
        List<CancelReturnResponseDtio> content = getOrderCancelReturnListResponses(memberId);
        Map<String, List<DetailResponseDtio>> orderDetailResponseMap = getOrderDetailResponseMap(findOrderNos(content));
        orderDetailResponseSetting(content, orderDetailResponseMap);

        JPAQuery<CancelReturnResponseDtio> countQuery = getCountQuery(memberId);

        return PageableExecutionUtils.getPage(content, pageable, countQuery::fetchCount);
    }

    private List<CancelReturnResponseDtio> getOrderCancelReturnListResponses(Long memberId) {
        List<CancelReturnResponseDtio> content = queryFactory
                .select(
                        new QCancelReturnResponseDtio(
                                order.updatedAt,
                                order.createdAt,
                                order.orderNo
                        )
                ).from(order)
                .where(order.member.id.eq(memberId))
                .fetch();
        return content;
    }

    private List<String> findOrderNos(List<CancelReturnResponseDtio> content) {
        List<String> toOrderNos = content.stream()
                .map(CancelReturnResponseDtio::getOrderNo)
                .toList();
        return toOrderNos;
    }

    private Map<String, List<DetailResponseDtio>> getOrderDetailResponseMap(List<String> toOrderIds) {
        List<DetailResponseDtio> detailResponseDtioList = queryFactory
                .select(
                        new QDetailResponseDtio(
                                orderDetail.orderNo,
                                orderDetail.product.id,
                                orderDetail.product.productNo,
                                orderDetail.product.name,
                                orderDetail.product.price,
                                orderDetail.quantity,
                                orderDetail.statusCode
                        ))
                .from(orderDetail)
                .join(orderDetail.product, product)
                .where(
                        orderDetail.order.orderNo.in(toOrderIds),
                        orderDetail.statusCode.eq(ORDER_CANCEL.getCode())
                                .or(orderDetail.statusCode.eq(RETURN_COMPLETED.getCode()))
                ).fetch();


        return detailResponseDtioList.stream()
                .collect(Collectors.groupingBy(DetailResponseDtio::getOrderNo));
    }

    private void orderDetailResponseSetting(List<CancelReturnResponseDtio> content, Map<String, List<DetailResponseDtio>> orderDetailResponseMap) {
        content.forEach(o -> o.changeDetailResponsDaos(orderDetailResponseMap.get(o.getOrderNo())));
    }

    private JPAQuery<CancelReturnResponseDtio> getCountQuery(Long memberId) {
        JPAQuery<CancelReturnResponseDtio> countQuery = queryFactory
                .select(new QCancelReturnResponseDtio(
                                order.updatedAt,
                                order.createdAt,
                                order.orderNo
                        )
                ).from(order)
                .where(order.member.id.eq(memberId));
        return countQuery;
    }

    @Override
    public Page<OrderDto> findByMemberIdWithMemberAddress(Condition condition, Pageable pageable) {
        List<Order> orders = queryFactory
                .selectFrom(order)
                .join(order.address, address).fetchJoin()
                .join(order.member, member).fetchJoin()
                .where(
                        eqMemberId(condition.getMemberId()),
                        eqYear(condition.getYear())
                )
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .fetch();

        // 엔티티 -> dto 로 변환
        List<OrderDto> orderDtos = orders.stream().map(OrderDto::of).toList();

        return new PageImpl<>(orderDtos, pageable, orderDtos.size());
    }

    private BooleanExpression eqYear(String year) {
        return hasText(year) ? order.createdAt.year().eq(Integer.parseInt(year)) : null;
    }

    private BooleanExpression eqMemberId(Long memberId) {
        return order.member.id.eq(memberId);
    }
}
