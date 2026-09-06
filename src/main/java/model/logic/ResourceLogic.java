package model.logic;

import model.entity.Category;
import model.entity.Resource;
import model.exception.EmptyFieldException;
import model.exception.InvalidDataException;

public class ResourceLogic {

    // Validates the required resource information
    public void validateResourceData(
            String id,
            Category category,
            String description)
            throws EmptyFieldException, InvalidDataException {

        if (id == null || id.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The resource ID cannot be empty."
            );
        }

        if (description == null || description.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The resource description cannot be empty."
            );
        }

        if (category == null) {
            throw new InvalidDataException(
                    "The resource must have a category."
            );
        }
    }

    // Creates a valid resource
    public Resource createResource(
            String id,
            Category category,
            String description)
            throws EmptyFieldException, InvalidDataException {

        validateResourceData(
                id,
                category,
                description
        );

        return new Resource(
                id.trim(),
                category,
                description.trim()
        );
    }


    // Updates the resource information
    public void updateResource(
            Resource resource,
            Category category,
            String description)
            throws EmptyFieldException, InvalidDataException {

        if (resource == null) {
            throw new InvalidDataException(
                    "The resource does not exist."
            );
        }

        validateResourceData(
                resource.getId(),
                category,
                description
        );

        resource.setCategory(category);
        resource.setDescription(description.trim());
    }
}
