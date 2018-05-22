

# How to test

* Download repository

* Navigate terminal to the project root folder.

* Run command 
 ```bash
  mvn install
 ```
* Run command 
 ```bash
 mvn spring-boot:run
 ```


# Description

Sometime you may need to use DTO's(Data Transfer Objects) in your application in order to hide some entity data(usually) in your REST'full API. For instance: You have a User entity which has some fields: username, email, password, ID.
You have an API which performs some CRUD operations on this entity and you want to hide user password at the moment when GET(only) operation is performed.
For such a feature you need to have a DTO Entity where you exclude the password field from the base Entity. You need somehow to map all the attributes from base entity to DTO entity end vice-versa
and it is nice if you'll do this automatically without mapping the attributes manually.

Here is how we can achieve this using ModelMapping...

## Project structure.

![](https://www.dropbox.com/s/apjbmgaqnziyeh2/Screen%20Shot%202018-05-21%20at%2014.55.50.png?raw=1)


## User model

Here we have a simple User class with some attributes.

 ```java
public class User {

    private int id;
    private String name;
    private String email;
    private String password;

    public User() {}

    public int getId() {
     return id;
    }

    public void setId(int id) {
     this.id = id;
    }

    public String getName() {
     return name;
    }

    public void setName(String name) {
     this.name = name;
    }

    public String getEmail() {
     return email;
    }

    public void setEmail(String email) {
     this.email = email;
    }

    public String getPassword() {
     return password;
    }

    public void setPassword(String password) {
     this.password = password;
    }

    @Override
    public String toString() {
     return "User{" +
     "id=" + id +
     ", name='" + name + '\'' +
     ", email='" + email + '\'' +
     ", password='" + password + '\'' +
     '}';
     }
}

 ```

## User service

Here is a simple User service implementation. The database connection and data persistance is not the goal at this point.

 ```java
@Service
public class UserService {

   public DTOEntity createUser(){
    User user = new User();
    user.setId(1);
    user.setName("User number 1");
    user.setEmail("Email number 1");
    user.setPassword("Password number 1");

    return new DtoUtils().convertToDto(user, new UserCreateDTO());
   }

   public DTOEntity readUser(){
    User user = new User();
    user.setId(1);
    user.setName("User number 1");
    user.setEmail("Email number 1");
    user.setPassword("Password number 1");

    return new DtoUtils().convertToDto(user, new UserReadDTO());
   }

   public DTOEntity updateUser(DTOEntity userDTO) {
    User user = (User) new DtoUtils().convertToEntity(new User(), userDTO);

    System.out.println(user.toString());

    return new DtoUtils().convertToDto(user, new UserUpdateDTO());
   }
}

 ```
## TDO Utils 

The important part here is this utility class: 

 ```java
public class DtoUtils {
   public DTOEntity convertToDto(Object obj, DTOEntity mapper) {
    return new ModelMapper().map(obj, mapper.getClass());
   }

   public Object convertToEntity(Object obj, DTOEntity mapper) {
    return new ModelMapper().map(mapper, obj.getClass());
   }
}

 ```

As you can see, here we have two methods for in/out mapping. I tried to create them as generic is possible in order to user them for all entities.

Now, the DTO's entities.

## UserRead DTO

 ```java
public class UserReadDTO implements DTOEntity {

    private String name;
    private String email;

    public UserReadDTO(){}

    public String getName() {
     return name;
    }

    public void setName(String name) {
     this.name = name;
    }

    public String getEmail() {
     return email;
    }

    public void setEmail(String email) {
     this.email = email;
    }
}
 ```

Here we implemented DTOEntity interface: 

 ```java
 public interface DTOEntity {}
 ```

in order to have a generic mapping. So all our DTO's will implement this interface.


!In this entity we excluded the password field.


## The User Controller

Now, that we have all needed models and helpers, our controller looks in this way:
 ```java
@RestController
@RequestMapping("api/")
public class UserController {

    private UserService updateService;

    @Autowired
    public UserController(UserService updateService){
     this.updateService = updateService;
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    public DTOEntity createPost(@RequestBody UserCreateDTO userCreateDTO) {  
     return updateService.createUser();
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    public DTOEntity readUser() {
     return updateService.readUser();
    }

    @RequestMapping(value = "/update", method = RequestMethod.PATCH)
    public DTOEntity updateUser(@RequestBody UserUpdateDTO userUpdateDTO) {
     return updateService.updateUser(userUpdateDTO);
    }
}
 ```

## See also the test cases:

 ```java
public class UserDtoUnitTest {

@Test
public void userEntityToUserDto() {

   // Given
   User user = new User();
   user.setId(1);
   user.setEmail("user1@example.com");
   user.setName("user1");
   user.setPassword("user1Password");

   // When
   UserCreateDTO userCreateDTO =  (UserCreateDTO) new DtoUtils().convertToDto(user, new UserCreateDTO());

   // Then
   assertEquals(user.getEmail(), userCreateDTO.getEmail());
   assertEquals(user.getName(), userCreateDTO.getName());
   assertEquals(user.getPassword(), userCreateDTO.getPassword());
  }

  @Test
  public void userDtoToUserEntity() {
   // Given
   UserCreateDTO userCreateDTO = new UserCreateDTO();
   userCreateDTO.setEmail("user1@example.com");
   userCreateDTO.setName("user1");
   userCreateDTO.setPassword("user1Password");

   // When
   User user =  (User) new DtoUtils().convertToEntity(new User(), userCreateDTO);

   // Then
   assertEquals(user.getEmail(), userCreateDTO.getEmail());
   assertEquals(user.getName(), userCreateDTO.getName());
   assertEquals(user.getPassword(), userCreateDTO.getPassword());
  }
}
 ```
 
## Testing

### User creation

 ```bash
 curl -X POST \
 http://localhost:8080/api/create \
 -H 'Cache-Control: no-cache' \
 -H 'Content-Type: application/json' \
 -d '{"name":"User number 1","email":"Email number 2", "password": "userPassword"}'
 ```
### User update
 ```bash
 curl -X PATCH \
 http://localhost:8080/api/update \
 -H 'Cache-Control: no-cache' \
 -H 'Content-Type: application/json' \
 -d '{"name":"User number 1","email":"Email number 2", "password": "pass"}'
 ```

### User read
 ```bash
 curl -X GET \
 http://localhost:8080/api/list \
 -H 'Cache-Control: no-cache' \
 -H 'Content-Type: application/json' \
 -d '{"name":"User number 1","email":"Email number 2", "password": "pass"}'
 ```
