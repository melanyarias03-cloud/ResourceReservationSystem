package model.logic;

import model.entity.Category;
import model.exception.EmptyFieldException;

public class CategoryLogic {

    public void validateCategoryData(String description)
            throws EmptyFieldException {

        if (description == null || description.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The category description cannot be empty."
            );
        }
    }
    public String generateId(int nextNumber) {
        return String.format("CAT-%06d", nextNumber);
    }

    public Category createCategory(
            String description,
            int nextNumber)
            throws EmptyFieldException {

        validateCategoryData(description);

        String id = generateId(nextNumber);

        return new Category(
                id,
                description.trim()
        );
    }

    public void updateCategory(
            Category category,
            String description)
            throws EmptyFieldException {

        validateCategoryData(description);

        category.setDescription(
                description.trim()
        );
    }
}
