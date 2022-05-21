package com.shoppingmall.product.controller;

import com.shoppingmall.product.domain.repository.ProductRepository;
import com.shoppingmall.product.dto.request.ProductSaveRequestDto;
import com.shoppingmall.product.dto.request.ProductUpdateRequestDto;
import com.shoppingmall.product.dto.response.ProductResponseDto;
import com.shoppingmall.product.service.ProductService;
import io.swagger.annotations.*;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"[App & 관리자] 상품 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/product")
public class ProductController {

    private final ProductService productService;

    @PostMapping("/adm")
    @ApiOperation(value = "[관리자] 상품 등록 API", notes = "관리자 페이지 내에서 썸네일 파일과 data 키로 표현된 Request DTO 를 MultipartFile 로 전달받아 상품 등록을 수행한다. 이때, data 키로 표현된 request DTO 는 application/json 타입으로 전송해야한다.\n" +
            "{\n" +
            "  \"productCode\": \"string\" -> 상품 코드,\n" +
            "  \"productInfo\": \"string\" -> 상품 설명,\n" +
            "  \"productName\": \"string\" -> 상품명,\n" +
            "  \"productPrice\": 0 -> 상품 단가\n" +
            "}")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공", response = ProductResponseDto.class),
            @ApiResponse(code = 400, message = "생성 실패 - 중복 코드")
    })
    public ResponseEntity<ProductResponseDto> admCreateProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @RequestPart(required = false) MultipartFile thumbnailFile,
            @ModelAttribute ProductSaveRequestDto requestDto
            ) throws Exception {

        return new ResponseEntity<>(productService.createProduct(thumbnailFile, requestDto), HttpStatus.CREATED);
    }

    @PutMapping("/adm/{productId}")
    @ApiOperation(value = "[관리자] 상품 수정 API", notes = "관리자 페이지에서 상품 시퀀스를 Path Variable 로, DTO 를 Request Body 로 전달받아 상품 수정을 수행한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "수정 성공", response = ProductResponseDto.class),
            @ApiResponse(code = 400, message = "수정 실패 - 상품코드 중복"),
            @ApiResponse(code = 404, message = "수정 실패 - 상품 시퀀스 미조회")
    })
    public ResponseEntity<ProductResponseDto> admUpdateProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable("productId") Long productId,
            @RequestBody ProductUpdateRequestDto requestDto
            ) throws Exception {
        return new ResponseEntity<>(productService.updateProduct(productId, requestDto), HttpStatus.OK);
    }

    @GetMapping("")
    @ApiOperation(value = "[App & 관리자] 전체 상품 조회 - Pagination", notes = "앱 또는 관리자 페이지에서 전체 상품을 조회한다. 이때, 삭제 여부를 나타내는 status 가 -1 인 상품은 조회되지 않는다.\n페이징 인자로 page 와 elementCnt 를 받으며, page 는 0부터 시작한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = ProductResponseDto.class, responseContainer = "List"),
    })
    public ResponseEntity<Page<ProductResponseDto>> findAllProduct(
            @RequestParam(value = "page", defaultValue = "0", required = true) Integer page,
            @RequestParam(value = "elementCnt", required = false, defaultValue = "10") Integer elementCnt
    ) throws Exception {
        return new ResponseEntity<>(productService.findAllProduct(page, elementCnt), HttpStatus.OK);
    }

    @GetMapping("/{productId}")
    @ApiOperation(value = "[App & 관리자] 상품 상세 조회 API", notes = "앱 또는 관리자 페이지에서 특정 상품 단건을 조회한다. 이때, 삭제 여부를 나타내는 status 가 -1 인 상품은 조회되지 않는다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = ProductResponseDto.class),
            @ApiResponse(code = 404, message = "조회 실패 - 상품 시퀀스 미조회")
    })
    public ResponseEntity<ProductResponseDto> findProduct(@PathVariable Long productId) throws Exception {
        return new ResponseEntity<>(productService.findProduct(productId), HttpStatus.OK);
    }

    @DeleteMapping("/adm/{productId}")
    @ApiOperation(value = "[관리자] 상품 삭제 API", notes = "관리자 페이지에서 특정 상품을 제거한다.\n이때, 실제 DB 에서 데이터가 사라지지는 않으며, 삭제 여부를 나타내는 status 를 -1 로 변경한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "삭제 성공", response = ProductResponseDto.class),
            @ApiResponse(code = 404, message = "삭제 실패 - 상품 시퀀스 미조회")
    })
    public ResponseEntity<ProductResponseDto> deleteProduct(
            @RequestHeader(HttpHeaders.AUTHORIZATION) String authorization,
            @PathVariable Long productId
    ) throws Exception {
        return new ResponseEntity<>(productService.deleteProduct(productId), HttpStatus.OK);
    }
}
