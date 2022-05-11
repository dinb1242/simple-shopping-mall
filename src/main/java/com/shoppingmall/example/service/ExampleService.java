package com.shoppingmall.example.service;

import com.shoppingmall.example.domain.model.Example;
import com.shoppingmall.example.domain.repository.ExampleRepository;
import com.shoppingmall.example.dto.request.ExampleSaveRequestDto;
import com.shoppingmall.example.dto.response.ExampleResponseDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ExampleService {

    private final ExampleRepository exampleRepository;

    @Transactional
    public ExampleResponseDto createExample(ExampleSaveRequestDto saveRequestDto) {
        Example exampleEntity = exampleRepository.save(saveRequestDto.toEntity());
        return ExampleResponseDto.builder().entity(exampleEntity).build();
    }

    @Transactional
    public List<ExampleResponseDto> findExamples() {
        return exampleRepository.findAll().stream()
                .map(ExampleResponseDto::new)
                .collect(Collectors.toList());
    }

}
