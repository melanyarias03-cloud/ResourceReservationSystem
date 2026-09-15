package dataaccess;

import model.entity.Employee;
import model.entity.Reservation;
import model.entity.Resource;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class ReservationDataAccess {

    private static final String FILE_PATH =
            "src/main/resources/data/reservations.xml";

    private final XmlManager xmlManager;
    private final EmployeeDataAccess employeeDataAccess;
    private final ResourceDataAccess resourceDataAccess;


    public ReservationDataAccess() {
        this.xmlManager = new XmlManager();
        this.employeeDataAccess =
                new EmployeeDataAccess();
        this.resourceDataAccess =
                new ResourceDataAccess();

    }

    // Saves a reservation into XML
    public void save(Reservation reservation)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        Element root =
                document.getDocumentElement();

        Element reservationElement =
                document.createElement("reservation");

        Element idElement =
                document.createElement("id");

        idElement.setTextContent(
                reservation.getId()
        );

        Element employeeIdElement =
                document.createElement("employeeId");

        employeeIdElement.setTextContent(
                reservation.getEmployee()
                        .getUser()
                        .getId()
        );

        Element activityElement =
                document.createElement("activity");

        activityElement.setTextContent(
                reservation.getActivity()
        );

        Element dateElement =
                document.createElement("date");

        dateElement.setTextContent(
                reservation.getDate().toString()
        );

        Element startTimeElement =
                document.createElement("startTime");

        startTimeElement.setTextContent(
                reservation.getStartTime().toString()
        );

        Element endTimeElement =
                document.createElement("endTime");

        endTimeElement.setTextContent(
                reservation.getEndTime().toString()
        );

        Element resourcesElement =
                document.createElement("resources");

        for (Resource resource :
                reservation.getResources()) {

            Element resourceIdElement =
                    document.createElement("resourceId");

            resourceIdElement.setTextContent(
                    resource.getId()
            );

            resourcesElement.appendChild(
                    resourceIdElement
            );
        }

        reservationElement.appendChild(idElement);
        reservationElement.appendChild(employeeIdElement);
        reservationElement.appendChild(activityElement);
        reservationElement.appendChild(dateElement);
        reservationElement.appendChild(startTimeElement);
        reservationElement.appendChild(endTimeElement);
        reservationElement.appendChild(resourcesElement);

        root.appendChild(reservationElement);

        xmlManager.saveDocument(
                document,
                FILE_PATH
        );
    }

    // Returns all reservations
    public List<Reservation> findAll()
            throws Exception {

        List<Reservation> reservations =
                new ArrayList<>();

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList reservationNodes =
                document.getElementsByTagName(
                        "reservation"
                );

        for (int i = 0;
             i < reservationNodes.getLength();
             i++) {

            Node node =
                    reservationNodes.item(i);

            if (node.getNodeType()
                    == Node.ELEMENT_NODE) {

                Element element =
                        (Element) node;

                String id =
                        element.getElementsByTagName("id")
                                .item(0)
                                .getTextContent();

                String employeeId =
                        element.getElementsByTagName("employeeId")
                                .item(0)
                                .getTextContent();

                String activity =
                        element.getElementsByTagName("activity")
                                .item(0)
                                .getTextContent();

                LocalDate date =
                        LocalDate.parse(
                                element.getElementsByTagName("date")
                                        .item(0)
                                        .getTextContent()
                        );

                LocalTime startTime =
                        LocalTime.parse(
                                element.getElementsByTagName("startTime")
                                        .item(0)
                                        .getTextContent()
                        );

                LocalTime endTime =
                        LocalTime.parse(
                                element.getElementsByTagName("endTime")
                                        .item(0)
                                        .getTextContent()
                        );

                Employee employee =
                        employeeDataAccess.findById(
                                employeeId
                        );

                List<Resource> resources =
                        new ArrayList<>();

                NodeList resourceNodes =
                        element.getElementsByTagName(
                                "resourceId"
                        );

                for (int j = 0;
                     j < resourceNodes.getLength();
                     j++) {

                    String resourceId =
                            resourceNodes.item(j)
                                    .getTextContent();

                    Resource resource =
                            resourceDataAccess.findById(
                                    resourceId
                            );

                    if (resource != null) {
                        resources.add(resource);
                    }
                }

                if (employee != null) {

                    Reservation reservation =
                            new Reservation(
                                    id,
                                    employee,
                                    activity,
                                    date,
                                    startTime,
                                    endTime,
                                    resources
                            );

                    reservations.add(reservation);
                }
            }
        }

        return reservations;
    }


    // Searches a reservation by ID
    public Reservation findById(String id)
            throws Exception {

        List<Reservation> reservations =
                findAll();

        for (Reservation reservation :
                reservations) {

            if (reservation.getId().equals(id)) {
                return reservation;
            }
        }

        return null;
    }

    // Returns reservations for one employee
    public List<Reservation> findByEmployee(
            String employeeId)
            throws Exception {

        List<Reservation> result =
                new ArrayList<>();

        List<Reservation> reservations =
                findAll();

        for (Reservation reservation :
                reservations) {

            if (reservation.getEmployee()
                    .getUser()
                    .getId()
                    .equals(employeeId)) {

                result.add(reservation);
            }
        }

        return result;
    }


    // Returns reservations for a specific date
    public List<Reservation> findByDate(
            LocalDate date)
            throws Exception {

        List<Reservation> result =
                new ArrayList<>();

        List<Reservation> reservations =
                findAll();

        for (Reservation reservation :
                reservations) {

            if (reservation.getDate()
                    .equals(date)) {

                result.add(reservation);
            }
        }

        return result;
    }

    // Deletes a reservation
    public boolean delete(String id)
            throws Exception {

        Document document =
                xmlManager.loadDocument(FILE_PATH);

        NodeList reservationNodes =
                document.getElementsByTagName(
                        "reservation"
                );

        for (int i = 0;
             i < reservationNodes.getLength();
             i++) {

            Node node =
                    reservationNodes.item(i);

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
    public int getNextNumber() throws Exception {

        List<Reservation> reservations =
                findAll();

        int maxNumber = 0;

        for (Reservation reservation : reservations) {

            String id =
                    reservation.getId();

            if (id != null &&
                    id.startsWith("RES-")) {

                try {

                    int number =
                            Integer.parseInt(
                                    id.substring(4)
                            );

                    if (number > maxNumber) {

                        maxNumber = number;
                    }

                } catch (NumberFormatException e) {

                    // Ignore IDs with an invalid format
                }
            }
        }

        return maxNumber + 1;
    }

}