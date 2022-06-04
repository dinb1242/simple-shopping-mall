package com.shoppingmall.order.service.impl;

import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.order.domain.model.Order;
import com.shoppingmall.order.domain.model.OrderProduct;
import com.shoppingmall.order.domain.repository.OrderRepository;
import com.shoppingmall.order.dto.request.OrderProductRequestDto;
import com.shoppingmall.order.dto.request.OrderSaveRequestDto;
import com.shoppingmall.order.dto.response.OrderResponseDto;
import com.shoppingmall.order.service.OrderService;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.domain.repository.ProductRepository;
import com.shoppingmall.shoppingcart.domain.model.ShoppingCart;
import com.shoppingmall.shoppingcart.domain.repository.ShoppingCartRepository;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    /**
     * 주문을 생성한다.
     *
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public OrderResponseDto createOrder(OrderSaveRequestDto requestDto) throws Exception {

        // 유저 시퀀스 가져오기
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        // 주문 번호 생성
        String orderNumber = UUID.randomUUID().toString();

        // 장바구니의 시퀀스를 전달받아 해당하는 상품을 찾고, 유효성을 검증한다.
        // 또한, 전체 합계 금액을 계산하고, 주문 엔티티에 대한 하위 구매 상품에 대한 참조 테이블에 들어갈 데이터를 구성한다.
        List<Product> productList = new ArrayList<>();
        Long totalPrice = 0L;
        List<OrderProduct> orderProductList = new ArrayList<>();
        for (Long eachShoppingCartId : requestDto.getShoppingCartIdList()) {
            ShoppingCart shoppingCartEntity = shoppingCartRepository.findById(eachShoppingCartId)
                    .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 장바구니의 아이템을 찾을 수 없습니다. shoppingCartId=" + eachShoppingCartId));

            Product productEntity = productRepository.findByProductCodeAndStatus(shoppingCartEntity.getProductCode(), 1)
                    .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 상품을 찾을 수 없습니다. productCode=" + shoppingCartEntity.getProductCode()));
            productList.add(productEntity);

            totalPrice = productEntity.getProductPrice() * shoppingCartEntity.getProductCnt();

            log.info(String.format("상품 코드: %s, 상품 금액: %d, 구매 개수: %d, 합계 금액: %d, 총 구매금액: %d",
                    productEntity.getProductCode(),
                    productEntity.getProductPrice(),
                    shoppingCartEntity.getProductCnt(),
                    shoppingCartEntity.getProductCnt() * productEntity.getProductPrice(),
                    totalPrice)
            );

            orderProductList.add(
                    OrderProduct.builder()
                            .orderNumber(orderNumber)
                            .productName(productEntity.getProductName())
                            .productCode(productEntity.getProductCode())
                            .productCnt(shoppingCartEntity.getProductCnt())
                            .build()
            );
        }

        log.info("전체 구매 금액: %d", totalPrice);

        // 주문 엔티티를 생성한다.
        Order orderEntity = Order.builder()
                .userId(userId)
                .orderNumber(orderNumber)
                .price(totalPrice)
                .orderType(requestDto.getOrderType())
                .receiverName(requestDto.getReceiverName())
                .receiverAddress(requestDto.getReceiverAddress())
                .receiverPhone(requestDto.getReceiverPhone())
                .build();

        orderEntity.setOrderProductList(orderProductList);

        orderRepository.save(orderEntity);

        // 주문된 상품에 대하여 유저의 장바구니에서 해당 장바구니 아이템을 제거한다.
        shoppingCartRepository.deleteByIdIn(requestDto.getShoppingCartIdList());
        log.info(String.format("총 %d 건의 아이템이 장바구니에서 제거되었습니다.", requestDto.getShoppingCartIdList().size()));

        return OrderResponseDto.builder()
                .entity(orderEntity)
                .build();
    }

    /**
     * 전체 주문을 조회한다.
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public List<OrderResponseDto> findAllOrders() throws Exception {
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        List<Order> orderEntity = orderRepository.findAllByUserIdAndStatus(userId, 1);
        List<OrderResponseDto> orderResponseDtoList = orderEntity.stream()
                .map(eachEntity -> OrderResponseDto.builder().entity(eachEntity).build())
                .collect(Collectors.toList());

        return orderResponseDtoList;
    }

    /**
     * 주문을 조회한다.
     * @param orderId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public OrderResponseDto findOrder(Long orderId) throws Exception {
        Order orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 주문을 찾을 수 없습니다. orderId=" + orderId));
        return OrderResponseDto.builder().entity(orderEntity).build();
    }

    @Override
    @Transactional
    public OrderResponseDto deleteOrder(Long orderId) throws Exception {
        Order orderEntity = orderRepository.findById(orderId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 주문을 찾을 수 없습니다. orderId=" + orderId));

        orderEntity.setStatus(-1);

        return OrderResponseDto.builder().entity(orderEntity).build();
    }

}
