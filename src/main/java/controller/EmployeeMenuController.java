package controller;

import model.entity.User;
import view.ReservationView;
import view.ChangePasswordView;
import view.EmployeeMenuView;
import view.LoginView;

public class EmployeeMenuController {



    private final EmployeeMenuView view;
    private final User user;

    public EmployeeMenuController(
            EmployeeMenuView view,
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

        view.setReservationsAction(
                e -> openReservations()
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

    private void openReservations() {

        view.dispose();

        ReservationView reservationView =
                new ReservationView();

        new ReservationController(
                reservationView,
                user
        );

        reservationView.setVisible(true);
    }
}
