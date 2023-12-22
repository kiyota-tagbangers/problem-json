package com.example.problemJson;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

/**
 * @author kiyota
 */
public record Item(
        Integer id,
        @NotBlank @Size(min = 2, max = 10) String name,
        @Size(max = 100, message = "memo contents limit 100 character!!!") String memo
) {
}
