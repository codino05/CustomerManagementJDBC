package costumer.management.system;

public class Customer {

    private int id;
    private String name;
    private String email;
    private String phone;

    public Customer() {

    }

    public Customer(int id) {
        this.id = id;
    }

    public Customer(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public Customer(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public Customer(int id, String name, String email, String phone) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.phone = phone;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getEmail() {
        return email;
    }

    public String getPhone() {
        return phone;
    }

    @Override
    public String toString() {
        return "Customer ID: " + id +
                ", Name : " + name +
                ", Email : " + email +
                ", phone : " + phone;
    }
}
