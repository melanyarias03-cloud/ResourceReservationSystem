package controller;

import dataaccess.UserDataAccess;
import model.entity.User;
import model.logic.UserLogic;
import view.LoginView;
import model.entity.Role;
import view.AdminMenuView;
import view.EmployeeMenuView;


public class LoginController {

    private final LoginView view;
    private final UserDataAccess userDataAccess;
    private final UserLogic userLogic;

    public LoginController(LoginView view) {

        this.view = view;

        this.userDataAccess =
                new UserDataAccess();

        this.userLogic =
                new UserLogic();

        initializeEvents();
    }

    private void initializeEvents() {

        view.setLoginAction(
                e -> login()
        );

        view.setClearAction(
                e -> view.clearFields()
        );

    }

    private void login() {

        try {

            String id = view.getIdInput();

            String password = view.getPasswordInput();

            User user = userDataAccess.findById(id);

            userLogic.validateCredentials(user, id, password);

            view.showMessage("Login successful.");

            openMenu(user);

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void openMenu(User user) {

        view.dispose();

        if (user.getRole() == Role.ADMIN) {

            AdminMenuView adminMenuView =
                    new AdminMenuView();

            new AdminMenuController(
                    adminMenuView,
                    user
            );

            adminMenuView.setVisible(true);

        } else if (user.getRole() == Role.EMPLOYEE) {

            EmployeeMenuView employeeMenuView =
                    new EmployeeMenuView();

            new EmployeeMenuController(
                    employeeMenuView,
                    user
            );

            employeeMenuView.setVisible(true);
        }
    }
}

