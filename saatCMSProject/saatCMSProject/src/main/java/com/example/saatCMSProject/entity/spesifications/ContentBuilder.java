package com.example.saatCMSProject.entity.spesifications;

import com.example.saatCMSProject.entity.Content;
import com.example.saatCMSProject.entity.enums.Predicate;
import org.springframework.data.jpa.domain.Specification;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class ContentBuilder{
 private final List<SearchCriteria> params;

    public ContentBuilder() {
        this.params = new ArrayList<>();
    }
    public ContentBuilder with(String type , String key , String operation , Object value){
        params.add(new SearchCriteria(type , key , operation , value));
        return this;
    }
    public Specification<Content> build(){
        if (params.size() == 0) {
            return null;
        }
        List<Specification<Content>> specs = params.stream().map(ContentSpesification::new).collect(Collectors.toList());

        Specification<Content> result = specs.get(0);

        for(int i = 1; i < params.size(); i++){
            result = params.get(i).getType().equals(Predicate.or) ? Specification.where(result).or(specs.get(i))
                    : Specification.where(result).and(specs.get(i));
        }
return result;
    }
}
