package dataaccess;

import model.entity.Category;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class CategoryDataAccess {

    private static final String FILE_PATH =
            "src/main/resources/data/categories.xml";

    private final XmlManager xmlManager;

    public CategoryDataAccess() {
        this.xmlManager = new XmlManager();
    }


    // Saves a new category into XML
    public void save(Category category)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        Element root =
                document.getDocumentElement();

        Element categoryElement =
                document.createElement("category");

        Element idElement =
                document.createElement("id");

        idElement.setTextContent(
                category.getId()
        );

        Element descriptionElement =
                document.createElement("description");

        descriptionElement.setTextContent(
                category.getDescription()
        );

        categoryElement.appendChild(idElement);
        categoryElement.appendChild(descriptionElement);

        root.appendChild(categoryElement);

        xmlManager.saveDocument(
                document,
                FILE_PATH
        );
    }

    // Returns all categories
    public List<Category> findAll()
            throws Exception {

        List<Category> categories =
                new ArrayList<>();

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList categoryNodes =
                document.getElementsByTagName("category");

        for (int i = 0;
             i < categoryNodes.getLength();
             i++) {

            Node node =
                    categoryNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String id =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                String description =
                        element.getElementsByTagName("description")
                                .item(0)
                                .getTextContent();

                Category category =
                        new Category(
                                id,
                                description
                        );

                categories.add(category);
            }
        }

        return categories;
    }


    // Searches a category by ID
    public Category findById(String id)
            throws Exception {

        List<Category> categories =
                findAll();

        for (Category category : categories) {

            if (category.getId().equals(id)) {
                return category;
            }
        }

        return null;
    }

    // Searches categories by description
    public List<Category> findByDescription(
            String description)
            throws Exception {

        List<Category> result =
                new ArrayList<>();

        List<Category> categories =
                findAll();

        for (Category category : categories) {

            if (category.getDescription()
                    .toLowerCase()
                    .contains(
                            description.toLowerCase()
                    )) {

                result.add(category);
            }
        }

        return result;
    }


    // Updates a category
    public boolean update(Category category)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList categoryNodes =
                document.getElementsByTagName("category");

        for (int i = 0;
             i < categoryNodes.getLength();
             i++) {

            Node node =
                    categoryNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String currentId =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                if (currentId.equals(
                        category.getId())) {

                    element.getElementsByTagName("description")
                            .item(0)
                            .setTextContent(
                                    category.getDescription()
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

    // Deletes a category by ID.
    public boolean delete(String id)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList categoryNodes =
                document.getElementsByTagName("category");

        for (int i = 0;
             i < categoryNodes.getLength();
             i++) {

            Node node =
                    categoryNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String currentId =
                        element.getElementsByTagName("id")
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


    // Returns the next available number
    // for generating a category ID
    public int getNextNumber()
            throws Exception {

        List<Category> categories =
                findAll();

        int maxNumber = 0;

        for (Category category : categories) {

            String id =
                    category.getId();

            if (id != null
                    && id.startsWith("CAT-")) {

                String numberPart =
                        id.substring(4);

                int number =
                        Integer.parseInt(numberPart);

                if (number > maxNumber) {
                    maxNumber = number;
                }
            }
        }

        return maxNumber + 1;
    }



}
