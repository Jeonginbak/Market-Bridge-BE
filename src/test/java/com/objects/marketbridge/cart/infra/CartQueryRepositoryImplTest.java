package com.objects.marketbridge.cart.infra;

import com.objects.marketbridge.cart.domain.Cart;
import com.objects.marketbridge.cart.service.port.CartCommendRepository;
import com.objects.marketbridge.cart.service.port.CartDtoRepository;
import com.objects.marketbridge.cart.service.port.CartQueryRepository;
import com.objects.marketbridge.member.domain.Member;
import com.objects.marketbridge.member.service.port.MemberRepository;
import com.objects.marketbridge.product.domain.Product;
import com.objects.marketbridge.product.service.port.ProductRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Slice;
import org.springframework.data.domain.Sort;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")
class CartQueryRepositoryImplTest {

    @Autowired CartQueryRepository cartQueryRepository;
    @Autowired CartDtoRepository cartDtoRepository;
    @Autowired CartCommendRepository cartCommendRepository;
    @Autowired ProductRepository productRepository;
    @Autowired MemberRepository memberRepository;

    @AfterEach
    void clear() {
        cartCommendRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        memberRepository.deleteAllInBatch();
    }

    @DisplayName("상품 번호를 통해 카트를 조회할 수 있다")
    @Test
    void findByProductNo(){
        //given
        Member member = Member.builder().email("test@email.com").build();
        memberRepository.save(member);

        Product product1 = Product.builder().stock(5L).productNo("productNo1").build();
        Product product2 = Product.builder().stock(5L).productNo("productNo2").build();
        productRepository.saveAll(List.of(product1, product2));

        Cart cart1 = Cart.create(member, product1, false, 1L);
        Cart cart2 = Cart.create(member, product2, false, 1L);
        cartCommendRepository.saveAll(List.of(cart1, cart2));

        //when
        Optional<Cart> findProduct1 = cartQueryRepository.findByProductId(product1.getId());
        Optional<Cart> findProduct2 = cartQueryRepository.findByProductId(product1.getId());

        //then
        assertThat(findProduct1.isPresent()).isTrue();
        assertThat(findProduct2.isPresent()).isTrue();
    }

    @DisplayName("[페이지:0번째, 사이즈:2]장바구니 조회시 페이징(슬라이스)이 되어야한다")
    @Test
    void findSlicedCart_0_2(){
        //given
        Member member = Member.builder().email("test@email.com").build();
        memberRepository.save(member);

        Product product1 = Product.builder().stock(5L).productNo("productNo1").build();
        Product product2 = Product.builder().stock(5L).productNo("productNo2").build();
        Product product3 = Product.builder().stock(5L).productNo("productNo3").build();
        Product product4 = Product.builder().stock(5L).productNo("productNo4").build();
        Product product5 = Product.builder().stock(5L).productNo("productNo5").build();
        Product product6 = Product.builder().stock(5L).productNo("productNo6").build();
        productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));

        Cart cart1 = Cart.create(member, product1, false, 1L);
        Cart cart2 = Cart.create(member, product2, false, 1L);
        Cart cart3 = Cart.create(member, product3, false, 1L);
        Cart cart4 = Cart.create(member, product4, false, 1L);
        Cart cart5 = Cart.create(member, product5, false, 1L);
        Cart cart6 = Cart.create(member, product6, false, 1L);
//        cartCommendRepository.saveAll(List.of(cart1, cart2, cart3, cart4, cart5, cart6));
        cartCommendRepository.save(cart1);
        cartCommendRepository.save(cart2);
        cartCommendRepository.save(cart3);
        cartCommendRepository.save(cart4);
        cartCommendRepository.save(cart5);
        cartCommendRepository.save(cart6);

        int pageNumber = 0;
        int pageSize = 2;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        //when
        Slice<Cart> slicedCart = cartQueryRepository.findSlicedCart(pageRequest, member.getId());

        //then
        assertThat(slicedCart.isFirst()).isTrue();
        assertThat(slicedCart.hasNext()).isTrue();
        assertThat(slicedCart.getNumberOfElements()).isEqualTo(2);
        assertThat(slicedCart.getContent().size()).isEqualTo(2);
        assertThat(slicedCart.getContent().get(0).getProduct().getProductNo()).isEqualTo("productNo6");// 최신 등록 순
        assertThat(slicedCart.getContent().get(1).getProduct().getProductNo()).isEqualTo("productNo5");// 최신 등록 순
        assertThat(slicedCart.getContent().get(0).getMember().getEmail()).isEqualTo("test@email.com");
        assertThat(slicedCart.getContent().get(1).getMember().getEmail()).isEqualTo("test@email.com");
    }

    @DisplayName("[페이지:마지막, 사이즈:4]장바구니 조회시 페이징(슬라이스)이 되어야한다")
    @Test
    void findSlicedCart_last(){
        //given
        Member member = Member.builder().email("test@email.com").build();
        memberRepository.save(member);

        Product product1 = Product.builder().stock(5L).productNo("productNo1").build();
        Product product2 = Product.builder().stock(5L).productNo("productNo2").build();
        Product product3 = Product.builder().stock(5L).productNo("productNo3").build();
        Product product4 = Product.builder().stock(5L).productNo("productNo4").build();
        Product product5 = Product.builder().stock(5L).productNo("productNo5").build();
        Product product6 = Product.builder().stock(5L).productNo("productNo6").build();
        productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));

        Cart cart1 = Cart.create(member, product1, false, 1L);
        Cart cart2 = Cart.create(member, product2, false, 1L);
        Cart cart3 = Cart.create(member, product3, false, 1L);
        Cart cart4 = Cart.create(member, product4, false, 1L);
        Cart cart5 = Cart.create(member, product5, false, 1L);
        Cart cart6 = Cart.create(member, product6, false, 1L);

        cartCommendRepository.save(cart1);
        cartCommendRepository.save(cart2);
        cartCommendRepository.save(cart3);
        cartCommendRepository.save(cart4);
        cartCommendRepository.save(cart5);
        cartCommendRepository.save(cart6);

        int pageNumber = 1;
        int pageSize = 4;
        Sort sort = Sort.by(Sort.Direction.DESC, "createdAt");
        Pageable pageRequest = PageRequest.of(pageNumber, pageSize, sort);

        //when
        Slice<Cart> slicedCart = cartQueryRepository.findSlicedCart(pageRequest, member.getId());

        //then
        assertThat(slicedCart.isLast()).isTrue();
        assertThat(slicedCart.hasNext()).isFalse();
        assertThat(slicedCart.getNumberOfElements()).isEqualTo(2);
        assertThat(slicedCart.getContent().size()).isEqualTo(2);
        assertThat(slicedCart.getContent().get(0).getProduct().getProductNo()).isEqualTo("productNo2");// 최신 등록 순
        assertThat(slicedCart.getContent().get(1).getProduct().getProductNo()).isEqualTo("productNo1");// 최신 등록 순
        assertThat(slicedCart.getContent().get(0).getMember().getEmail()).isEqualTo("test@email.com");
        assertThat(slicedCart.getContent().get(1).getMember().getEmail()).isEqualTo("test@email.com");
    }

    @DisplayName("회원이 장바구니 전체를 조회할 수 있다")
    @Test
    void countByMemberId(){
        //given
        Member member = Member.builder().email("test@email.com").build();
        memberRepository.save(member);

        Product product1 = Product.builder().stock(5L).productNo("productNo1").build();
        Product product2 = Product.builder().stock(5L).productNo("productNo2").build();
        Product product3 = Product.builder().stock(5L).productNo("productNo3").build();
        Product product4 = Product.builder().stock(5L).productNo("productNo4").build();
        Product product5 = Product.builder().stock(5L).productNo("productNo5").build();
        Product product6 = Product.builder().stock(5L).productNo("productNo6").build();
        productRepository.saveAll(List.of(product1, product2, product3, product4, product5, product6));

        Cart cart1 = Cart.create(member, product1, false, 1L);
        Cart cart2 = Cart.create(member, product2, false, 1L);
        Cart cart3 = Cart.create(member, product3, false, 1L);
        Cart cart4 = Cart.create(member, product4, false, 1L);
        Cart cart5 = Cart.create(member, product5, false, 1L);
        Cart cart6 = Cart.create(member, product6, false, 1L);

        cartCommendRepository.save(cart1);
        cartCommendRepository.save(cart2);
        cartCommendRepository.save(cart3);
        cartCommendRepository.save(cart4);
        cartCommendRepository.save(cart5);
        cartCommendRepository.save(cart6);

        //when
        Long countResult = cartQueryRepository.countByMemberId(member.getId());

        //then
        assertThat(countResult).isEqualTo(6);
    }
}