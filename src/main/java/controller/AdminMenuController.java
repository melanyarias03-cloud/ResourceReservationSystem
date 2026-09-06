package controller;


import model.entity.User;
import view.EmployeeView;
import view.AdminMenuView;
import view.ChangePasswordView;
import view.LoginView;

public class AdminMenuController {

    private final AdminMenuView view;
    private final User user;

    public AdminMenuController(
            AdminMenuView view,
            User user) {

        this.view = view;
        this.user = user;

        initializeEvents();
    }

    private void initializeEvents() {

        view.setChangePasswordAction(
                e -> openChangePassword()
        );

        view.setLogoutAction(
                e -> logout()
        );
        view.setEmployeesAction(
                e -> openEmployees()
        );
    }

    private void openChangePassword() {

        ChangePasswordView changePasswordView =
                new ChangePasswordView();

        new ChangePasswordController(
                changePasswordView,
                user
        );

        changePasswordView.setVisible(true);
    }

    private void logout() {

        view.dispose();

        LoginView loginView =
                new LoginView();

        new LoginController(
                loginView
        );

        loginView.setVisible(true);
    }

    private void openEmployees() {

        view.dispose();

        EmployeeView employeeView =
                new EmployeeView();

        new EmployeeController(
                employeeView,user);

        employeeView.setVisible(true);
    }
}
