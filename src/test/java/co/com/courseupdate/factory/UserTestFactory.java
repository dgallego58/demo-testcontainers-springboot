package co.com.courseupdate.factory;

import co.com.courseupdate.models.DocType;
import co.com.courseupdate.models.User;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;

public final class UserTestFactory {


    public static User createUser(){
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("dd-MM-yyyy");
        int calculatedAge = Period.between(LocalDate.parse("29-04-1994", dtf), LocalDate.now()).getYears();
       return User.create()
                .setCareer("Software engineering")
                .setName("David")
                .setLastName("Gallego")
                .setDocType(DocType.CC)
                .setDni("1023932858")
                .setAge(calculatedAge);

    }

}
