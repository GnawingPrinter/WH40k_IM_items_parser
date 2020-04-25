package com.example.demo.domain;

import java.util.HashMap;
import java.util.Map;

public class ParsedItem {

    private final Map<String, Object> additions = new HashMap<>();

    public Map<String, Object> getAdditions() {
        return additions;
    }

    public void setAdditions(Map<String, Object> additions) {
        this.additions.clear();
        additions.entrySet().forEach(e -> this.addAddition(e.getKey(), e.getValue()));
    }

    public void addAddition(String name, Object value) {
        if (this.additions.containsKey(name)) {
            addAddition("_" + name, value);
        }
        this.additions.put(name, value);
    }


}
