package controller;


import dataaccess.UserDataAccess;

import model.entity.User;
import model.logic.UserLogic;

import view.ChangePasswordView;

public class ChangePasswordController {


    private final ChangePasswordView view;

    private final User user;

    private final UserLogic userLogic;

    private final UserDataAccess userDataAccess;

    public ChangePasswordController(
            ChangePasswordView view,
            User user) {

        this.view = view;
        this.user = user;

        this.userLogic =
                new UserLogic();

        this.userDataAccess =
                new UserDataAccess();

        initializeEvents();
    }

    private void initializeEvents() {

        view.setChangeAction(
                e -> changePassword()
        );

        view.setCancelAction(
                e -> view.dispose()
        );
    }

    private void changePassword() {

        try {

            String currentPassword =
                    view.getCurrentPasswordInput();

            String newPassword =
                    view.getNewPasswordInput();

            String confirmPassword =
                    view.getConfirmPasswordInput();

            userLogic.changePassword(
                    user,
                    currentPassword,
                    newPassword,
                    confirmPassword
            );

            userDataAccess.updatePassword(
                    user.getId(),
                    user.getPassword()
            );

            view.showMessage(
                    "Password changed successfully."
            );

            view.dispose();

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }
}
