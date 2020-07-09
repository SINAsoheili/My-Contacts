package ir.sinasoheili.mycontacts.MODEL;

import androidx.annotation.NonNull;

import java.io.Serializable;

public class UserContact implements Serializable
{
    public static final String INTENT_KEY = "USER_CONTACT";

    private String name;
    private String phone;
    private String birthDate;
    private String imagePath;

    //Constructor
    public UserContact(String name , String phone)
    {
        this.name = name;
        this.phone = phone;
    }

    public UserContact(String name , String phone , String birthDate)
    {
        this(name , phone);
        this.birthDate = birthDate;
    }

    public UserContact(String name, String phone , String birthDate , String imagePath)
    {
        this(name, phone , birthDate);
        this.imagePath = imagePath;
    }

    //Getter
    public String getName()
    {
        return name;
    }

    public String getPhone()
    {
        return phone;
    }

    public String getBirthDate()
    {
        return birthDate;
    }

    public String getImagePath()
    {
        return imagePath;
    }

    //Setter
    public void setName(String name)
    {
        this.name = name;
    }

    public void setPhone(String phone)
    {
        this.phone = phone;
    }

    public void setBirthDate(String birthDate)
    {
        this.birthDate = birthDate;
    }

    public void setImagePath(String imagePath)
    {
        this.imagePath = imagePath;
    }

    //toString
    @NonNull
    @Override
    public String toString()
    {
        return this.name +" : "+this.phone;
    }
}
