package main;

import controller.LoginController;
import view.LoginView;


import report.PdfReportGenerator;

import java.util.ArrayList;
import java.util.List;



public class Main {

    public static void main(String[] args) {

        LoginView loginView =new LoginView();

        new LoginController(loginView);

        loginView.setVisible(true);



    }

}
