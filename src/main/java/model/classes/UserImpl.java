package model.classes;

import lombok.Getter;
import lombok.Setter;
import model.interfaces.User;

@Getter
@Setter
public class UserImpl implements User {

   private int userid;
   private String password;
   private String name;
   private String lastname;
   private String email;


}
