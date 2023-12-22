package com.example.problemJson;

import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author kiyota
 */
@RestController
public class ErrorController {

//    @GetMapping("/error")
//    public void error() {
//        throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR);
//    }
//
//    @GetMapping("/error-detail")
//    public void errorDetail(){
//        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
//        problemDetail.setDetail("error detail");
//        throw new ErrorResponseException(HttpStatus.INTERNAL_SERVER_ERROR, problemDetail, null);
//    }

    @GetMapping("/problem-detail")
    public ProblemDetail problemDetail() {
        var problemDetail = ProblemDetail.forStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        problemDetail.setDetail("problem detail");
        problemDetail.setProperty("hoge", "fuga");
        return problemDetail;
    }

    @GetMapping("/runtime-exception")
    public void runtimeException() {
        int[] numbers = {1,2,3};
        System.out.println(numbers[3]);
    }
}
