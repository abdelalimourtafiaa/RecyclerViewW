package com.example.recyclerview;

public class Post {
    String ID;
    String FirstName;
    String LastName;


    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getFirstName() {
        return FirstName;
    }

    public void setFirstName(String firstName) {
        FirstName = firstName;
    }

    public String getLastName() {
        return LastName;
    }

    public void setLastName(String lastName) {
        LastName = lastName;
    }



    public Post(String ID, String firstName, String lastEmail) {
        this.ID = ID;
        this.FirstName = firstName;
        this.LastName = lastEmail;
    }

    public Post(){

    }


}
