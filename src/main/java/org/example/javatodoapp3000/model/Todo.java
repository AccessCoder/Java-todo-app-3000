package org.example.javatodoapp3000.model;

import lombok.Builder;

@Builder
public record Todo(String id,
                   String description,
                   String status) {
}
