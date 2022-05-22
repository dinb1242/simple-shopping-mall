package com.shoppingmall.product.service.impl;

import com.shoppingmall.boot.exception.RestException;
import com.shoppingmall.product.domain.model.Product;
import com.shoppingmall.product.domain.repository.ProductRepository;
import com.shoppingmall.product.dto.request.ProductSaveRequestDto;
import com.shoppingmall.product.dto.request.ProductUpdateRequestDto;
import com.shoppingmall.product.dto.response.ProductResponseDto;
import com.shoppingmall.product.service.ProductService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ProductServiceImpl implements ProductService {

    private final ProductRepository productRepository;

    /**
     * 관리자 페이지에서 상품을 등록한다.
     * 이때, 상품 번호가 중복될 경우 예외를 발생시킨다.]
     * 썸네일 파일은 로컬에 저장한다.
     * @param requestDto
     * @throws Exception
     */
    @Override
    @Transactional
    public ProductResponseDto createProduct(MultipartFile thumbnailFile, ProductSaveRequestDto requestDto) throws Exception {
        if(productRepository.existsByProductCode(requestDto.getProductCode()))
            throw new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 상품코드입니다. code=" + requestDto.getProductCode());

        /**
         * 썸네일 파일을 로컬에 저장한다.
         */
        String path = "/data";
        File directory = new File(path);

        // path 경로에 대한 폴더가 없을 경우 생성한다.
        if(!directory.exists()) {
            try {
                directory.mkdir();
                System.out.println(String.format("%s 경로에 대한 폴더가 생성되었습니다.", directory.getAbsolutePath()));
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        File file = new File(directory.getAbsolutePath(), thumbnailFile.getOriginalFilename());
        thumbnailFile.transferTo(file);

        // 상품을 등록한다.
        Product productEntity = productRepository.save(requestDto.toEntity());

        // DTO 로 변환하여 반환한다.
        return ProductResponseDto.builder().entity(productEntity).build();
    }

    @Override
    @Transactional
    public ProductResponseDto updateProduct(Long productId, ProductUpdateRequestDto requestDto) throws Exception {
        Product productEntity = productRepository.findById(productId)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 상품을 찾을 수 없습니다. productId=" + productId));

        // 상품 코드가 중복되는지 확인한다.
        if(productRepository.existsByProductCode(requestDto.getProductCode()))
            throw new RestException(HttpStatus.BAD_REQUEST, "이미 존재하는 상품코드입니다. code=" + requestDto.getProductCode());

        // 상품을 수정한다.
        productEntity.update(requestDto);

        return ProductResponseDto.builder().entity(productEntity).build();
    }

    /**
     * 페이징을 위한 인자를 전달받아 전체 상품을 조회한다.
     * @param page
     * @param elementCnt
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public Page<ProductResponseDto> findAllProduct(Integer page, Integer elementCnt) throws Exception {

        // 페이징 처리를 위한 Pageable 객체를 생성한다.
        Pageable pageable = PageRequest.of(page, elementCnt == null ? 10 : elementCnt);

        // Repository 를 통해 Paging 처리 된 엔티티 리스트를 가져온다.
        Page<Product> productEntityPage = productRepository.findAllWithPaging(pageable);

        // 가져온 엔티티 리스트를 Response DTO 의 형태로 Mapping 한다.
        Page<ProductResponseDto> productResponseDtoPage = productEntityPage.map(ProductResponseDto::new);

        return productResponseDtoPage;
    }

    /**
     * 특정 상품 시퀀스를 전달받아 상품 단건 조회를 수행한다.
     * 이때, 상품 status 가 -1 인 상품은 조회할 수 없다.
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ProductResponseDto findProduct(Long productId) throws Exception {
        Product productEntity = productRepository.findByIdAndStatus(productId, 1)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND, "일치하는 상품을 찾을 수 없습니다. productId=" + productId));
        return ProductResponseDto.builder().entity(productEntity).build();
    }

    /**
     * 특정 상품을 제거한다.
     * 이때, DB 에서 데이터 영구 제거가 아닌, 삭제 여부를 나타내는 status 필드 값을 -1로 변경한다.
     * @param productId
     * @return
     * @throws Exception
     */
    @Override
    @Transactional
    public ProductResponseDto deleteProduct(Long productId) throws Exception {
        Product productEntity = productRepository.findByIdAndStatus(productId ,1)
                .orElseThrow(() -> new RestException(HttpStatus.NOT_FOUND ,"일치하는 상품을 찾을 수 없습니다. productId=" + productId));

        // 상품을 제거한다.
        productEntity.delete();

        return ProductResponseDto.builder().entity(productEntity).build();
    }
}
