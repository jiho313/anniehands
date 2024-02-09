package com.jiho.anniehands.domain.options;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class OptionsService {

    private final OptionsRepository optionsRepository;

    public OptionsDto findAll() {
        List<Options> options = optionsRepository.findAll();
        log.info("옵션 데이터 =====> {}", options);
        return OptionsDto.createDto(options);
    }
}
