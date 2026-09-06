package model.entity;

public class Employee {

    private User user;
    private String name;
    private String phone;


    public Employee() {

    }

    public Employee(User user, String name, String phone) {
        this.user = user;
        this.name = name;
        this.phone = phone;
    }


    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    @Override
    public String toString() {
        return "Employee{" +
                "id='" + user.getId() + '\'' +
                ", name='" + name + '\'' +
                ", phone='" + phone + '\'' +
                '}';
    }
}
