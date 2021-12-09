package com.cdp.tdp.dto;

import com.cdp.tdp.domain.Til;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.domain.Page;

@NoArgsConstructor
@Builder
@Data
@AllArgsConstructor
public class TilListDto {

    private Page<Til> tils;

    private int page;
    private int size;
    private int totalPages;

}