package jpa.entitymodels;

import lombok.*;
import lombok.experimental.FieldDefaults;

import javax.persistence.*;
import java.util.Objects;

@Getter @Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level=AccessLevel.PRIVATE)
@NamedQuery(name="getCourseById", query= "Select c from Course c where id = :id")
@Entity
@Table(name= "Course")
public class Course {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(columnDefinition = "INT(11) UNSIGNED", name = "id", nullable = false)
    private int cId;
    @Column(columnDefinition = "VARCHAR(50)", name = "name", nullable = false)
    private String cName;
    @Column(columnDefinition = "VARCHAR(50)", name = "instructor", nullable = false)
    private String cInstructorName;



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Course)) return false;
        Course course = (Course) o;
        return cId == course.cId && cName.equals(course.cName) && cInstructorName.equals(course.cInstructorName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(cId, cName, cInstructorName);
    }

}
