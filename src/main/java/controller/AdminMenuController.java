package controller;

import view.CategoryView;
import model.entity.User;
import view.EmployeeView;
import view.AdminMenuView;
import view.ChangePasswordView;
import view.LoginView;
import view.ResourceView;

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

        view.setChangePasswordAction(e -> openChangePassword());


        view.setLogoutAction(e -> logout());


        view.setEmployeesAction(e -> openEmployees());


        view.setCategoriesAction(e -> openCategories());


        view.setResourcesAction(e -> openResources());



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
    private void openCategories() {

        view.dispose();

        CategoryView categoryView =
                new CategoryView();

        new CategoryController(
                categoryView,
                user
        );

        categoryView.setVisible(true);
    }


    private void openResources() {

        view.dispose();

        ResourceView resourceView =
                new ResourceView();

        new ResourceController(
                resourceView,
                user
        );

        resourceView.setVisible(true);
    }
}
