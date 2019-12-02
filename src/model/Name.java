package model;

public class Name {
    private String firstName;
    private String middleName;
    private String lastName;
    private String fullName;

    public Name(){

    }

    public Name(String firstName, String middleName, String lastName){
        setFirstName(firstName);
        setMiddleName(middleName);
        setLastName(lastName);
        String fullName = "";
        fullName += firstName;
        fullName += middleName.isEmpty() || middleName == null ? "" : " " + middleName;
        fullName += " " + lastName;
        setFullName(fullName);
    }

    public Name(String fullName){
        setFullName(fullName);
        String[] names = fullName.split(" ");
        setFirstName(names[0]);
        if(names.length == 3) {
            setMiddleName(names[1]);
        }
        setLastName(names[names.length - 1]);

    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String toString() {
        return fullName;
    }

}
