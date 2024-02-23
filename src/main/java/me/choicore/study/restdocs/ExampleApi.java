package me.choicore.study.restdocs;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
class ExampleApi {

    @PostMapping("/v1/examples")
    ResponseEntity<?> get(@RequestBody ExampleRequest request) {
        return ResponseEntity.ok(new ExampleResponse("success"));
    }
}
