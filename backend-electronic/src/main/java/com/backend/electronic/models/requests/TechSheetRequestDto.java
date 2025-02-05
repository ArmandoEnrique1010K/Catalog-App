package com.backend.electronic.models.requests;

import java.util.List;

import com.backend.electronic.models.dto.TechSheetDto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class TechSheetRequestDto {
    private Long productId;
    private List<TechSheetDto> techSheet;
}
