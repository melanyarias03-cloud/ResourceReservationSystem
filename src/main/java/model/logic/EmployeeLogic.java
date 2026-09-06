package model.logic;

import model.entity.Employee;
import model.entity.Role;
import model.entity.User;
import model.exception.EmptyFieldException;
import  model.exception.InvalidDataException;

public class EmployeeLogic {


    // Validates the required employee information
    public void validateEmployeeData(
            String id,
            String name,
            String phone)
            throws EmptyFieldException,InvalidDataException {

        if (id == null || id.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The employee ID cannot be empty."
            );
        }

        if (name == null || name.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The employee name cannot be empty."
            );
        }

        if (phone == null || phone.trim().isEmpty()) {
            throw new EmptyFieldException(
                    "The employee phone cannot be empty."
            );
        }
    }


    // Creates an employee with an initial password equal to the ID
    public Employee createEmployee(
            String id,
            String name,
            String phone)
            throws EmptyFieldException,InvalidDataException {

        validateEmployeeData(id, name, phone);

        User user = new User(
                id,
                id,
                Role.EMPLOYEE
        );

        return new Employee(
                user,
                name,
                phone
        );
    }
    // Updates the employee's personal information
    public void updateEmployee(
            Employee employee,
            String name,
            String phone)
            throws EmptyFieldException,InvalidDataException {


        if (employee == null) {

            throw new InvalidDataException(
                    "Employee not found."
            );
        }



        validateEmployeeData(
                employee.getUser().getId(),
                name,
                phone
        );

        employee.setName(name);
        employee.setPhone(phone);
    }


}
