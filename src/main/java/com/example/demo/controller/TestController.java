package com.example.demo.controller;

import com.example.demo.tool.RomanToInteger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
public class TestController {

    @Autowired
    RomanToInteger romanToInteger;

    @GetMapping(value = "/api/switch")
    public Map<String,Integer> switchNumber(@RequestParam String symbol){
        HashMap<String, Integer> result = new HashMap<>();
        result.put("result",romanToInteger.romanToInt(symbol));
        return result;
    }

    @PostMapping(value = "/api/switch")
    public Map<String,Integer> switchNumber(@RequestBody Map<String,String> symbol){
        HashMap<String, Integer> result = new HashMap<>();
        result.put("result",romanToInteger.romanToInt(symbol.get("symbol")));
        return result;
    }

    @GetMapping("/api/injection")
    public ResponseEntity injectionApi(@RequestParam String symbol) {
        HashMap<String, Integer> result = new HashMap<>();
        try {
            result.put("result", romanToInteger.romanToInt(symbol));
            return ResponseEntity.status(500).body(result);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).build();
        }
    }

}
