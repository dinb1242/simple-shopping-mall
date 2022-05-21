package com.shoppingmall.product.domain.repository.custom;

import com.querydsl.core.QueryResults;
import com.querydsl.jpa.impl.JPAQueryFactory;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.domain.model.QProduct;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Slf4j
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    private QProduct product = QProduct.product;

    /**
     * Pageable 객체를 전달받아 QueryDSL 을 활용하여 product 테이블에 대한 데이터를 조회한다.
     * status 가 1인 것만 조회한다.
     * 최종적으로 반환하는 객체는 Page 리스트 객체이다.
     * @param pageable
     * @return
     */
    @Override
    public Page<Product> findAllWithPaging(Pageable pageable) {

        List<Product> productEntityList = queryFactory.selectFrom(product)
                .where(
                        product.status.eq(1)
                ).offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        return new PageImpl<>(productEntityList, pageable, productEntityList.size());
    }
}
