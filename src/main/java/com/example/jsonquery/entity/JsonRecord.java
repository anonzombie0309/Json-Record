
package com.example.jsonquery.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "json_records")
public class JsonRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "dataset_name")
    private String datasetName;

    @Column(columnDefinition = "json")
    private String data;

    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getDatasetName() { return datasetName; }
    public void setDatasetName(String datasetName) { this.datasetName = datasetName; }

    public String getData() { return data; }
    public void setData(String data) { this.data = data; }
}
