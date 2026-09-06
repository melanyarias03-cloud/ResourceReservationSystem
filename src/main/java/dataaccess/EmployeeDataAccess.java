package dataaccess;


import model.entity.Employee;
import model.entity.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;


public class EmployeeDataAccess {

    private static final String FILE_PATH =
            "src/main/resources/data/employees.xml";

    private final XmlManager xmlManager;
    private final UserDataAccess userDataAccess;


    public EmployeeDataAccess() {
        this.xmlManager = new XmlManager();
        this.userDataAccess = new UserDataAccess();
    }

    // Saves a new employee into the XML file
    public void save(Employee employee)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        Element root =
                document.getDocumentElement();

        Element employeeElement =
                document.createElement("employee");

        Element userIdElement =
                document.createElement("userId");

        userIdElement.setTextContent(
                employee.getUser().getId()
        );

        Element nameElement =
                document.createElement("name");

        nameElement.setTextContent(
                employee.getName()
        );

        Element phoneElement =
                document.createElement("phone");

        phoneElement.setTextContent(
                employee.getPhone()
        );

        employeeElement.appendChild(userIdElement);
        employeeElement.appendChild(nameElement);
        employeeElement.appendChild(phoneElement);

        root.appendChild(employeeElement);

        xmlManager.saveDocument(
                document,
                FILE_PATH
        );
    }

    // Returns all employees stored in XML
    public List<Employee> findAll()
            throws Exception {

        List<Employee> employees =
                new ArrayList<>();

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList employeeNodes =
                document.getElementsByTagName("employee");

        for (int i = 0;
             i < employeeNodes.getLength();
             i++) {

            Node node =
                    employeeNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String userId =
                        element.getElementsByTagName("userId")
                                .item(0)
                                .getTextContent();

                String name =
                        element.getElementsByTagName("name")
                                .item(0)
                                .getTextContent();

                String phone =
                        element.getElementsByTagName("phone")
                                .item(0)
                                .getTextContent();

                User user =
                        userDataAccess.findById(userId);

                if (user != null) {

                    Employee employee =
                            new Employee(
                                    user,
                                    name,
                                    phone
                            );

                    employees.add(employee);
                }
            }
        }

        return employees;
    }


    // Searches for an employee by ID
    public Employee findById(String id)
            throws Exception {

        List<Employee> employees =
                findAll();

        for (Employee employee : employees) {

            if (employee.getUser()
                    .getId()
                    .equals(id)) {

                return employee;
            }
        }

        return null;
    }

    // Searches employees by name
    public List<Employee> findByName(String name)
            throws Exception {

        List<Employee> result =
                new ArrayList<>();

        List<Employee> employees =
                findAll();

        for (Employee employee : employees) {

            if (employee.getName()
                    .toLowerCase()
                    .contains(name.toLowerCase())) {

                result.add(employee);
            }
        }

        return result;
    }


    // Updates an existing employee
    public boolean update(Employee employee)
            throws Exception {

        Document document = xmlManager.loadDocument(FILE_PATH);

        NodeList employeeNodes = document.getElementsByTagName("employee");

        for (int i = 0; i < employeeNodes.getLength(); i++) {

            Node node = employeeNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element =  (Element) node;

                String currentId = element.getElementsByTagName("userId")
                                .item(0)
                                .getTextContent();

                if (currentId.equals(employee.getUser().getId())) {

                    element.getElementsByTagName("name")
                            .item(0)
                            .setTextContent(
                                    employee.getName()
                            );

                    element.getElementsByTagName("phone")
                            .item(0)
                            .setTextContent(
                                    employee.getPhone()
                            );

                    xmlManager.saveDocument(
                            document,
                            FILE_PATH
                    );

                    return true;
                }
            }
        }

        return false;
    }

    // Deletes an employee by ID
    public boolean delete(String id)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList employeeNodes = document.getElementsByTagName("employee");

        for (int i = 0; i < employeeNodes.getLength(); i++) {

            Node node =
                    employeeNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String currentId =
                        element.getElementsByTagName("userId")
                                .item(0)
                                .getTextContent();

                if (currentId.equals(id)) {

                    element.getParentNode()
                            .removeChild(element);

                    xmlManager.saveDocument(
                            document,
                            FILE_PATH
                    );

                    return true;
                }
            }
        }

        return false;
    }

}
