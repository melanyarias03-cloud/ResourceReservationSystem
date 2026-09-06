package model.logic;

import model.entity.User;
import model.exception.EmptyFieldException;
import model.exception.InvalidDataException;

public class UserLogic {


    // Validates that login fields are not empty.
    public void validateLoginFields(
            String id,
            String password)
            throws EmptyFieldException {

        if (id == null || id.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "Identification cannot be empty."
            );
        }

        if (password == null
                || password.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "Password cannot be empty."
            );
        }
    }

    // Validates user credentials.
    public void validateCredentials(
            User user,
            String id,
            String password)
            throws EmptyFieldException,
            InvalidDataException {

        validateLoginFields(
                id,
                password
        );

        if (user == null) {

            throw new InvalidDataException(
                    "User not found."
            );
        }

        if (!user.getId().equals(id)) {

            throw new InvalidDataException(
                    "Invalid identification."
            );
        }

        if (!user.getPassword()
                .equals(password)) {

            throw new InvalidDataException(
                    "Invalid password."
            );
        }
    }

    // Validates password change fields.
    public void validatePasswordChange(
            String currentPassword,
            String newPassword,
            String confirmPassword)
            throws EmptyFieldException,
            InvalidDataException {

        if (currentPassword == null
                || currentPassword.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "Current password cannot be empty."
            );
        }

        if (newPassword == null
                || newPassword.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "New password cannot be empty."
            );
        }

        if (confirmPassword == null
                || confirmPassword.trim().isEmpty()) {

            throw new EmptyFieldException(
                    "Password confirmation cannot be empty."
            );
        }

        if (!newPassword.equals(
                confirmPassword)) {

            throw new InvalidDataException(
                    "New passwords do not match."
            );
        }
    }

    // Changes the password in the User object.
    public void changePassword(
            User user,
            String currentPassword,
            String newPassword,
            String confirmPassword)
            throws EmptyFieldException,
            InvalidDataException {

        validatePasswordChange(
                currentPassword,
                newPassword,
                confirmPassword
        );

        if (user == null) {

            throw new InvalidDataException(
                    "User does not exist."
            );
        }

        if (!user.getPassword()
                .equals(currentPassword)) {

            throw new InvalidDataException(
                    "Current password is incorrect."
            );
        }

        user.setPassword(
                newPassword
        );
    }
}
