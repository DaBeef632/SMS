package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Getter @Setter
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@NamedQuery(name="find student by email", query= "select s from Student s where email = :email")
@Entity
@Table(name="Student")
public class Student {
    @Id
    @Column(columnDefinition = "VARCHAR(50)", name = "email", nullable = false)
    private String sEmail;
    @Column(columnDefinition = "VARCHAR(50)", name = "name", nullable = false)
    private String sName;
    @Column(columnDefinition = "VARCHAR(50)", name = "password", nullable = false)
    private String sPass;

    @OneToMany(fetch = FetchType.EAGER)
    @JoinTable(joinColumns = @JoinColumn(name = "email"),
            inverseJoinColumns = @JoinColumn(name = "id"))
    private List<Course> sCourses;


    @Override
    public String toString() {
        return "Student{" +
                "sEmail='" + sEmail + '\'' +
                ", sName='" + sName + '\'' +
                ", sPass='" + sPass + '\'' +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Student)) return false;
        Student student = (Student) o;
        return Objects.equals(sEmail, student.sEmail) && Objects.equals(sName, student.sName) && Objects.equals(sPass, student.sPass);
    }

    @Override
    public int hashCode() {

        return Objects.hash(sEmail, sName, sPass);
    }

}
