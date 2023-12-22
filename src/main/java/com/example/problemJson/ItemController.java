package com.example.problemJson;

import jakarta.validation.Valid;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

/**
 * @author kiyota
 */
@RestController
public class ItemController {

    @PostMapping("/items")
    public ResponseEntity<?> add(@RequestBody @Valid Item item) {
        var uri = ServletUriComponentsBuilder
                .fromCurrentRequest()
                .path("/0")
                .build()
                .toUri();
        return ResponseEntity.created(uri).build();
    }
}
