package com.objects.marketbridge.domains.order.infra.orderdetail;

import com.objects.marketbridge.domains.order.domain.OrderDetail;
import com.objects.marketbridge.domains.product.domain.Product;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OrderDetailJpaRepository extends JpaRepository<OrderDetail, Long> {

    @Modifying(clearAutomatically = true)
    @Query("update OrderDetail pod set pod.statusCode = :type where pod.order.id = :orderId")
    int changeAllType(@Param("orderId") Long orderId, @Param("type") String type);

    List<OrderDetail> findByProductId(Long memberId);

    List<OrderDetail> findByOrderNo(String orderNo);

    List<OrderDetail> findByOrder_IdAndProductIn(Long orderId, List<Product> products);

    List<OrderDetail> findByOrderNoAndProduct_IdIn(String orderNo, List<Long> productIds);

    List<OrderDetail> findByOrderNoAndIdIn(String orderNo, List<Long> orderDetailIds);

}
