package dataaccess;

import model.entity.Category;
import model.entity.Resource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.util.ArrayList;
import java.util.List;

public class ResourceDataAccess {

    private static final String FILE_PATH =
            "src/main/resources/data/resources.xml";

    private final XmlManager xmlManager;
    private final CategoryDataAccess categoryDataAccess;

    public ResourceDataAccess() {
        this.xmlManager = new XmlManager();
        this.categoryDataAccess = new CategoryDataAccess();
    }

    public void save(Resource resource)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        Element root =
                document.getDocumentElement();

        Element resourceElement =
                document.createElement("resource");

        Element idElement =
                document.createElement("id");

        idElement.setTextContent(
                resource.getId()
        );

        Element categoryIdElement =
                document.createElement("categoryId");

        categoryIdElement.setTextContent(
                resource.getCategory().getId()
        );

        Element descriptionElement =
                document.createElement("description");

        descriptionElement.setTextContent(
                resource.getDescription()
        );

        resourceElement.appendChild(idElement);
        resourceElement.appendChild(categoryIdElement);
        resourceElement.appendChild(descriptionElement);

        root.appendChild(resourceElement);

        xmlManager.saveDocument(
                document,
                FILE_PATH
        );
    }

    public List<Resource> findAll()
            throws Exception {

        List<Resource> resources =
                new ArrayList<>();

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList resourceNodes =
                document.getElementsByTagName("resource");

        for (int i = 0;
             i < resourceNodes.getLength();
             i++) {

            Node node =
                    resourceNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String id =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                String categoryId =
                        element.getElementsByTagName("categoryId")
                                .item(0)
                                .getTextContent();

                String description =
                        element.getElementsByTagName("description")
                                .item(0)
                                .getTextContent();

                Category category =
                        categoryDataAccess.findById(
                                categoryId
                        );

                if (category != null) {

                    Resource resource =
                            new Resource(
                                    id,
                                    category,
                                    description
                            );

                    resources.add(resource);
                }
            }
        }

        return resources;
    }
    public Resource findById(String id)
            throws Exception {

        List<Resource> resources =
                findAll();

        for (Resource resource : resources) {

            if (resource.getId().equals(id)) {
                return resource;
            }
        }

        return null;
    }

    public List<Resource> findByCategory(
            String categoryId)
            throws Exception {

        List<Resource> result =
                new ArrayList<>();

        List<Resource> resources =
                findAll();

        for (Resource resource : resources) {

            if (resource.getCategory()
                    .getId()
                    .equals(categoryId)) {

                result.add(resource);
            }
        }

        return result;
    }


    public boolean update(Resource resource)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList resourceNodes =
                document.getElementsByTagName("resource");

        for (int i = 0;
             i < resourceNodes.getLength();
             i++) {

            Node node =
                    resourceNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String currentId =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                if (currentId.equals(
                        resource.getId())) {

                    element.getElementsByTagName("categoryId")
                            .item(0)
                            .setTextContent(
                                    resource.getCategory()
                                            .getId()
                            );

                    element.getElementsByTagName("description")
                            .item(0)
                            .setTextContent(
                                    resource.getDescription()
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

    public boolean delete(String id)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList resourceNodes =
                document.getElementsByTagName("resource");

        for (int i = 0;
             i < resourceNodes.getLength();
             i++) {

            Node node =
                    resourceNodes.item(i);

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


}
