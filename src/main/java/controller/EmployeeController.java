package controller;


import dataaccess.EmployeeDataAccess;
import model.entity.Employee;
import view.EmployeeView;
import java.util.List;
import dataaccess.UserDataAccess;
import model.logic.EmployeeLogic;
import view.EmployeeFormView;
import model.entity.User;
import view.AdminMenuView;
import report.EmployeeReport;

public class EmployeeController {

    private final EmployeeView view;
    private final User user;
    private final EmployeeDataAccess employeeDataAccess;
    private final UserDataAccess userDataAccess;
    private final EmployeeLogic employeeLogic;
    private final EmployeeReport employeeReport;


    public EmployeeController(
            EmployeeView view,User user) {

        this.view = view;
        this.user = user;


        this.employeeDataAccess =
                new EmployeeDataAccess();

        this.userDataAccess =
                new UserDataAccess();

        this.employeeLogic =
                new EmployeeLogic();

        this.employeeReport =
                new EmployeeReport();

        initializeEvents();

        loadEmployees();
    }




    private void initializeEvents() {

        view.setSearchAction(
                e -> searchEmployees()
        );

        view.setNewAction(
                e -> openNewEmployeeForm()
        );
        view.setEditAction(
                e -> openEditEmployeeForm()
        );

        view.setDeleteAction(
                e -> deleteEmployee()
        );

        view.setBackAction(
                e -> back()
        );


        view.setGeneratePdfAction(
                e -> generatePdf()
        );
    }





    private void loadEmployees() {

        try {

            List<Employee> employees =
                    employeeDataAccess.findAll();

            view.showEmployees(
                    employees
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }




    private void searchEmployees() {

        try {

            String name =
                    view.getSearchInput();

            List<Employee> employees;

            if (name.isEmpty()) {

                employees =
                        employeeDataAccess.findAll();

            } else {

                employees =
                        employeeDataAccess.findByName(
                                name
                        );
            }

            view.showEmployees(
                    employees
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }




    private void openNewEmployeeForm() {

        EmployeeFormView formView =
                new EmployeeFormView();

        formView.setIdEditable(true);

        formView.setSaveAction(
                e -> saveNewEmployee(
                        formView
                )
        );

        formView.setCancelAction(
                e -> formView.dispose()
        );

        formView.setVisible(true);
    }


    private void generatePdf() {

        try {

            List<Employee> employees =
                    employeeDataAccess
                            .findAll();

            employeeReport.generate(
                    employees
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void saveNewEmployee(
            EmployeeFormView formView) {

        try {

            String id =
                    formView.getIdInput();

            String name =
                    formView.getNameInput();

            String phone =
                    formView.getPhoneInput();

            Employee employee =
                    employeeLogic.createEmployee(
                            id,
                            name,
                            phone
                    );

            userDataAccess.save(
                    employee.getUser()
            );

            employeeDataAccess.save(
                    employee
            );

            formView.showMessage(
                    "Employee created successfully."
            );

            formView.dispose();

            loadEmployees();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }

    private void openEditEmployeeForm() {

        try {

            String id =
                    view.getSelectedEmployeeId();

            if (id == null) {

                view.showError(
                        "Select an employee first."
                );

                return;
            }

            Employee employee =
                    employeeDataAccess.findById(
                            id
                    );

            if (employee == null) {

                view.showError(
                        "Employee not found."
                );

                return;
            }

            EmployeeFormView formView =
                    new EmployeeFormView();

            formView.setEmployeeData(
                    employee.getUser().getId(),
                    employee.getName(),
                    employee.getPhone()
            );

            formView.setIdEditable(
                    false
            );

            formView.setSaveAction(
                    e -> updateEmployee(
                            formView,
                            employee
                    )
            );

            formView.setCancelAction(
                    e -> formView.dispose()
            );

            formView.setVisible(
                    true
            );

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void updateEmployee(
            EmployeeFormView formView,
            Employee employee) {

        try {

            String name =
                    formView.getNameInput();

            String phone =
                    formView.getPhoneInput();

            employeeLogic.updateEmployee(
                    employee,
                    name,
                    phone
            );

            employeeDataAccess.update(
                    employee
            );

            formView.showMessage(
                    "Employee updated successfully."
            );

            formView.dispose();

            loadEmployees();

        } catch (Exception e) {

            formView.showError(
                    e.getMessage()
            );
        }
    }



    private void deleteEmployee() {

        try {

            String id =
                    view.getSelectedEmployeeId();

            if (id == null) {

                view.showError(
                        "Select an employee first."
                );

                return;
            }

            boolean confirmed =
                    view.confirmDelete();

            if (!confirmed) {
                return;
            }

            employeeDataAccess.delete(
                    id
            );

            userDataAccess.delete(
                    id
            );

            view.showMessage(
                    "Employee deleted successfully."
            );

            loadEmployees();

        } catch (Exception e) {

            view.showError(
                    e.getMessage()
            );
        }
    }

    private void back() {

        view.dispose();

        AdminMenuView adminMenuView =
                new AdminMenuView();

        new AdminMenuController(
                adminMenuView,
                user
        );

        adminMenuView.setVisible(true);
    }
}
