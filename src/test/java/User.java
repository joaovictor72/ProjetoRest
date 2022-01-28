import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class User {
    public User(String name, Integer age) {
        this.name = name;
        this.age = age;
        this.salary = salary;
    }

    private String name;
    private Integer age;
    private Double salary;


}
