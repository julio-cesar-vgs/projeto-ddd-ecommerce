package br.edu.ddd.project.productcatalog.domain.model.entities;

import br.edu.ddd.project.productcatalog.domain.model.valueobjects.CategoryId;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Category {
    private CategoryId id;
    private String name;
    private String description;
    private CategoryId parentCategoryId;
    private boolean active;
    private LocalDateTime createdAt;

    public static Category create(String name, String description, CategoryId parentCategoryId) {
        return Category.builder().id(CategoryId.generate()).name(name).description(description).parentCategoryId(parentCategoryId).active(true).createdAt(LocalDateTime.now()).build();
    }

    public void deactivate() {
        this.active = false;
    }

    public boolean isRootCategory() {
        return parentCategoryId == null;
    }
}
