package com.shoppingmall.shoppingcart.service.impl;

import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.domain.repository.ProductRepository;
import com.shoppingmall.shoppingcart.domain.model.ShoppingCart;
import com.shoppingmall.shoppingcart.domain.repository.ShoppingCartRepository;
import com.shoppingmall.shoppingcart.dto.request.ShoppingCartSaveRequestDto;
import com.shoppingmall.shoppingcart.dto.request.ShoppingCartUpdateRequestDto;
import com.shoppingmall.shoppingcart.dto.response.ShoppingCartResponseDto;
import com.shoppingmall.shoppingcart.service.ShoppingCartService;
import com.shoppingmall.user.dto.response.UserDetailsImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ShoppingCartServiceImpl implements ShoppingCartService {

    private final ShoppingCartRepository shoppingCartRepository;
    private final ProductRepository productRepository;

    /**
     * 쇼핑 카트에 아이템을 추가한다.
     * 상품 코드를 통해 해당 상품의 가격을 조회한 후, DTO 로 전달받은 구매 수량으로 계산하여 총 금액을 산출하여 DTO 에 삽입한다.
     * 만일, 동일한 상품코드가 이미 기존 장바구니에 있을 경우, 덮어씌우기한다.
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ShoppingCartResponseDto createShoppingCart(ShoppingCartSaveRequestDto requestDto) throws Exception {

        // 상품이 존재하는지 확인한다.
        Product productEntity = productRepository.findByProductCodeAndStatus(requestDto.getProductCode(), 1)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "상품 코드와 일치하는 상품을 찾을 수 없습니다. productCode=" + requestDto.getProductCode()));

        // DTO 에 유저 시퀀스를 삽입한다.
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        requestDto.setUserId(userDetails.getId());

        // 상품에 대한 전체 총 금액을 계산 후, 총액을 DTO 에 삽입한다.
        Long price = productEntity.getProductPrice() * requestDto.getProductCnt();
        log.info(String.format("총 금액: %d원", price));
        requestDto.setPrice(price);

        // 상품명을 DTO 에 삽입한다.
        requestDto.setProductName(productEntity.getProductName());

        // 만일, 동일한 상품코드가 이미 장바구니 내에 있을 경우 기존 데이터를 삭제한다.
        if(shoppingCartRepository.existsByProductCodeAndUserId(requestDto.getProductCode(), requestDto.getUserId())) {
            shoppingCartRepository.deleteByProductCodeAndUserId(requestDto.getProductCode(), requestDto.getUserId());
            log.info(String.format("장바구니 내 상품이 제거되었습니다. userId: %d, productCode: %s", requestDto.getUserId(), requestDto.getProductCode()));
        }

        // 엔티티를 저장한다.
        ShoppingCart shoppingCartEntity = shoppingCartRepository.save(requestDto.toEntity());

        return ShoppingCartResponseDto.builder().entity(shoppingCartEntity).build();
    }

    /**
     * 특정 유저에 대한 전체 장바구니를 조회한다.
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public List<ShoppingCartResponseDto> findAllShoppingCarts() throws Exception {

        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        // 유저의 전체 장바구니 목록을 조회한다.
        List<ShoppingCart> shoppingCartEntityList = shoppingCartRepository.findAllByUserId(userId);

        return shoppingCartEntityList.stream()
                .map(ShoppingCartResponseDto::new)
                .collect(Collectors.toList());
    }

    /**
     * 특정 시퀀스에 해당하는 장바구니를 수정한다.
     * @param shoppingCartId
     * @param requestDto
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ShoppingCartResponseDto updateShoppingCart(Long shoppingCartId, ShoppingCartUpdateRequestDto requestDto) throws Exception {
        ShoppingCart shoppingCartEntity = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 장바구니 아이템을 찾을 수 없습니다. shoppingCartId=" + shoppingCartId));

        // 장바구니를 수정한다.
        shoppingCartEntity.update(requestDto);

        return ShoppingCartResponseDto.builder().entity(shoppingCartEntity).build();
    }

    /**
     * 특정 장바구니를 제거한다.
     * @param shoppingCartId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ShoppingCartResponseDto deleteShoppingCart(Long shoppingCartId) throws Exception {
        
        UserDetailsImpl userDetails = (UserDetailsImpl) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        Long userId = userDetails.getId();

        ShoppingCart shoppingCartEntity = shoppingCartRepository.findById(shoppingCartId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 장바구니 아이템을 찾을 수 없습니다. shoppingCartId=" + shoppingCartId));
        
        shoppingCartRepository.deleteByIdAndUserId(shoppingCartId, userId);
        log.info(String.format("%d 번 시퀀스 장바구니 아이템이 제거되었습니다.", shoppingCartId));

        return ShoppingCartResponseDto.builder().entity(shoppingCartEntity).build();
    }
}
