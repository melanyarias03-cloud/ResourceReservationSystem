package dataaccess;

import model.entity.Role;
import model.entity.User;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;



public class UserDataAccess {

    private static final String FILE_PATH =
            "src/main/resources/data/users.xml";

    private final XmlManager xmlManager;


    public UserDataAccess() {
        this.xmlManager = new XmlManager();
    }



    // Saves a new user into the XML file
    public void save(User user) throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        Element root =
                document.getDocumentElement();

        Element userElement =
                document.createElement("user");

        Element idElement =
                document.createElement("id");

        idElement.setTextContent(
                user.getId()
        );

        Element passwordElement =
                document.createElement("password");

        passwordElement.setTextContent(
                user.getPassword()
        );

        Element roleElement =
                document.createElement("role");

        roleElement.setTextContent(
                user.getRole().name()
        );

        userElement.appendChild(idElement);
        userElement.appendChild(passwordElement);
        userElement.appendChild(roleElement);

        root.appendChild(userElement);

        xmlManager.saveDocument(
                document,
                FILE_PATH
        );
    }



    // Returns all users stored in the XML file
    public List<User> findAll() throws Exception {

        List<User> users =
                new ArrayList<>();

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList userNodes =
                document.getElementsByTagName("user");

        for (int i = 0; i < userNodes.getLength(); i++) {

            Node node =
                    userNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String id =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                String password =
                        element.getElementsByTagName("password")
                                .item(0)
                                .getTextContent();

                String roleText =
                        element.getElementsByTagName("role")
                                .item(0)
                                .getTextContent();

                Role role =
                        Role.valueOf(roleText);

                User user =
                        new User(
                                id,
                                password,
                                role
                        );

                users.add(user);
            }
        }

        return users;
    }


    // Searches for a user by ID
    public User findById(String id)
            throws Exception {

        List<User> users =
                findAll();

        for (User user : users) {

            if (user.getId().equals(id)) {
                return user;
            }
        }

        return null;
    }


    // Updates the password of an existing user
    public boolean updatePassword(
            String id,
            String newPassword)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList userNodes =
                document.getElementsByTagName("user");

        for (int i = 0; i < userNodes.getLength(); i++) {

            Node node =
                    userNodes.item(i);

            if (node.getNodeType() == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String currentId =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                if (currentId.equals(id)) {

                    element.getElementsByTagName("password")
                            .item(0)
                            .setTextContent(newPassword);

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

    public boolean delete(
            String id)
            throws Exception {

        Document document =
                xmlManager.loadDocument(
                        FILE_PATH
                );

        NodeList userNodes =
                document.getElementsByTagName(
                        "user"
                );

        for (int i = 0;
             i < userNodes.getLength();
             i++) {

            Element userElement =
                    (Element)
                            userNodes.item(i);

            String currentId =
                    userElement
                            .getElementsByTagName("id")
                            .item(0)
                            .getTextContent();

            if (currentId.equals(id)) {

                userElement
                        .getParentNode()
                        .removeChild(userElement);

                xmlManager.saveDocument(
                        document,
                        FILE_PATH
                );

                return true;
            }
        }

        return false;
    }



}
