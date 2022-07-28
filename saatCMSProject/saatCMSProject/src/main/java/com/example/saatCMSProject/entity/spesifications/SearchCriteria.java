package com.example.saatCMSProject.entity.spesifications;

import com.example.saatCMSProject.entity.enums.Predicate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class SearchCriteria {
    private String key;
    private String operation;
    private Object value;
    private Predicate type;

    public SearchCriteria(String type , String key, String operation, Object value) {
        this.key = key;
        this.operation = operation;
        this.value = value;
        this.type =  this.type.valueOf(type);

    }

}
