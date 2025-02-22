package by.kochagin.teacher_restapi.api;

import feign.Response;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Map;

@FeignClient(name = "docGeneratorClient", url = "http://127.0.0.1:5000")
public interface DocGeneratorClient {

    @PostMapping("/generate-doc")
    Response generateDoc(@RequestBody Map<String, String> requestData);
}