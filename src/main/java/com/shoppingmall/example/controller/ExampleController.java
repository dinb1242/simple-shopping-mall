package com.shoppingmall.example.controller;

import com.amazonaws.SdkClientException;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.AmazonS3Exception;
import com.amazonaws.services.s3.model.Bucket;
import com.shoppingmall.example.dto.request.ExampleSaveRequestDto;
import com.shoppingmall.example.dto.response.ExampleResponseDto;
import com.shoppingmall.example.service.ExampleService;
import com.shoppingmall.product.dto.request.ProductSaveRequestDto;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(tags = {"연동용 API"})
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/example")
public class ExampleController {

    private final ExampleService exampleService;
    private final AmazonS3 s3;

    @PostMapping("")
    @ApiOperation(value = "예제 등록 API", notes = "DTO 를 전달받아 DB 에 예제 데이터를 등록한다.")
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses({
            @ApiResponse(code = 201, message = "생성 성공", response = ExampleResponseDto.class)
    })
    public ResponseEntity<ExampleResponseDto> createExample(@RequestBody ExampleSaveRequestDto saveRequestDto) {
        return new ResponseEntity<>(exampleService.createExample(saveRequestDto), HttpStatus.CREATED);
    }

    @GetMapping("")
    @ApiOperation(value = "예제 전체 조회 API", notes = "전체 예제 데이터를 조회한다.")
    @ApiResponses({
            @ApiResponse(code = 200, message = "조회 성공", response = ExampleResponseDto.class)
    })
    public ResponseEntity<List<ExampleResponseDto>> findExamples() {
        return new ResponseEntity<>(exampleService.findExamples(), HttpStatus.OK);
    }

    @PostMapping("/s3")
    @ApiOperation(value = "테스트")
    public ResponseEntity<ExampleResponseDto> testFunc(
            ) {
        try {
            List<Bucket> buckets = s3.listBuckets();
            System.out.println("Bucket List: ");
            for (Bucket bucket : buckets) {
                System.out.println("    name=" + bucket.getName() + ", creation_date=" + bucket.getCreationDate() + ", owner=" + bucket.getOwner().getId());
            }
        } catch (AmazonS3Exception e) {
            e.printStackTrace();
        } catch(SdkClientException e) {
            e.printStackTrace();
        }
        return null;
    }

}
