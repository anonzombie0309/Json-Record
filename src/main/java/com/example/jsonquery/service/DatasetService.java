
package com.example.jsonquery.service;

import com.example.jsonquery.entity.JsonRecord;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class DatasetService {

    @PersistenceContext
    private EntityManager entityManager;

    private final ObjectMapper mapper = new ObjectMapper();

    @Transactional
    public Long insertRecord(String datasetName, Map<String, Object> payload) {
        JsonRecord record = new JsonRecord();
        record.setDatasetName(datasetName);
        try {
            record.setData(mapper.writeValueAsString(payload));
        } catch (Exception e) {
            throw new RuntimeException("Error serializing JSON", e);
        }
        entityManager.persist(record);
        return record.getId();
    }

    public Map<String, List<Map<String, Object>>> groupBy(String datasetName, String groupByKey) {
        String sql = "SELECT data FROM json_records WHERE dataset_name = :datasetName";
        List<String> records = entityManager.createNativeQuery(sql)
                .setParameter("datasetName", datasetName)
                .getResultList();

        Map<String, List<Map<String, Object>>> grouped = new HashMap<>();

        for (String record : records) {
            try {
                Map<String, Object> map = mapper.readValue(record, new TypeReference<>() {});
                String key = String.valueOf(map.get(groupByKey));
                grouped.computeIfAbsent(key, k -> new ArrayList<>()).add(map);
            } catch (Exception e) {
                throw new RuntimeException("Error reading JSON record", e);
            }
        }
        return grouped;
    }

    public List<Map<String, Object>> sortBy(String datasetName, String sortKey, String order) {
        String sql = "SELECT data FROM json_records WHERE dataset_name = :datasetName";
        List<String> records = entityManager.createNativeQuery(sql)
                .setParameter("datasetName", datasetName)
                .getResultList();

        List<Map<String, Object>> parsed = new ArrayList<>();

        for (String record : records) {
            try {
                parsed.add(mapper.readValue(record, new TypeReference<>() {}));
            } catch (Exception e) {
                throw new RuntimeException("Error parsing JSON", e);
            }
        }

        Comparator<Map<String, Object>> comparator = Comparator.comparing(e -> ((Comparable) e.get(sortKey)));
        if ("desc".equalsIgnoreCase(order)) {
            comparator = comparator.reversed();
        }
        return parsed.stream().sorted(comparator).collect(Collectors.toList());
    }
}
