package com.example.sajja_000.test1;

/**
 * Created by sajja_000 on 27/03/2015.
 */
public class Student
{
    private String _firstName,_lastName,_address;
    private int _Id;

    public Student(int id,String firstName,String lastName,String address)
    {
        _Id=id;
        _firstName=firstName;
        _lastName=lastName;
        _address=address;


    }
    public int getId(){return _Id;}
    public String getFirstName(){return _firstName;}
    public String getLastName(){return _lastName;}
    public String getAddress(){return _address;}

}
