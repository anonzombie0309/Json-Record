
package com.example.jsonquery.controller;

import com.example.jsonquery.dto.InsertResponse;
import com.example.jsonquery.service.DatasetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
public class DatasetController {

    @Autowired
    private DatasetService datasetService;

    @PostMapping("/api/dataset/{datasetName}/record")
    public ResponseEntity<InsertResponse> insertRecord(
            @PathVariable String datasetName,
            @RequestBody Map<String, Object> payload) {
        Long id = datasetService.insertRecord(datasetName, payload);
        return ResponseEntity.ok(new InsertResponse("Record added successfully", datasetName, id));
    }

    @GetMapping("/api/dataset/{datasetName}/query")
    public ResponseEntity<Object> queryDataset(
            @PathVariable String datasetName,
            @RequestParam(required = false) String groupBy,
            @RequestParam(required = false) String sortBy,
            @RequestParam(defaultValue = "asc") String order) {

        if (groupBy != null) {
            return ResponseEntity.ok(datasetService.groupBy(datasetName, groupBy));
        } else if (sortBy != null) {
            return ResponseEntity.ok(datasetService.sortBy(datasetName, sortBy, order));
        }
        return ResponseEntity.badRequest().body("Missing 'groupBy' or 'sortBy' parameter");
    }
}
