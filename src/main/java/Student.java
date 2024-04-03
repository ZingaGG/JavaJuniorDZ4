import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

//* 1. Создать сущность Student с полями:
//1.1 id - int
//1.2 firstName - string
//1.3 secondName - string (исключил это, так как нигде не используется + читабельность) !!!
//1.4 age - int
//2. Подключить hibernate. Реализовать простые запросы: Find(by id), Persist, Merge, Remove
//3. Попробовать написать запрос поиска всех студентов старше 20 лет (session.createQuery)
@Entity
@Table(name = "students")
@Getter
@Setter
@ToString
public class Student {
    @Id
    private Long id;
    @Column(name = "fName")
    private String firstName;
    @Column(name = "age")
    private Integer age;
}
