package au.edu.utas.sddhewa.assignment.modal;

public class Customer {

    private int customerId;

    private String title;

    private String firstName;

    private String lastName;

    private int mobileNo;

    private String email;

    private String address;

    private String suburb;

    private String state;

    private String postCode;

    public Customer() {}

    public Customer(String title, String firstName, String lastName, int mobileNo, String email,
                    String address, String suburb, String state, String postCode) {
        this.title = title;
        this.firstName = firstName;
        this.lastName = lastName;
        this.mobileNo = mobileNo;
        this.email = email;
        this.address = address;
        this.suburb = suburb;
        this.state = state;
        this.postCode = postCode;
    }

    public int getCustomerId() {
        return customerId;
    }

    public void setCustomerId(int customerId) {
        this.customerId = customerId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public int getMobileNo() {
        return mobileNo;
    }

    public void setMobileNo(int mobileNo) {
        this.mobileNo = mobileNo;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getSuburb() {
        return suburb;
    }

    public void setSuburb(String suburb) {
        this.suburb = suburb;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getPostCode() {
        return postCode;
    }

    public void setPostCode(String postCode) {
        this.postCode = postCode;
    }

    public String getFullName () {
        return new StringBuilder(title).append(" ").append(firstName)
                .append(" ").append(lastName).toString();
    }

    public String getFullAddress () {
        return new StringBuilder(address).append(" ").append(suburb)
                .append(" ").append(state).append(postCode).toString();
    }

    @Override
    public String toString() {
        return new StringBuilder("Customer { customerId: ").append(customerId)
                .append("title: ").append(title).append("firstName: ").append(firstName)
                .append("lastName: ").append(lastName).append("mobileNo: ").append(mobileNo)
                .append("email: ").append(email).append("address: ").append(address)
                .append("suburb: ").append(suburb).append("state: ").append(state)
                .append("postCode: ").append(postCode).append(" }").toString();
    }
}
