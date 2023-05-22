package nt.uz.ecommerce.model;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.springframework.data.annotation.CreatedDate;

import java.util.Date;

@Entity
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Review {
    @Id
    @GeneratedValue(generator = "review_id_seq")
    @SequenceGenerator(name = "review_id_seq", sequenceName = "review_id_seq", allocationSize = 1)
    private Integer id;
    private String description;
    private Integer rank;
    @CreatedDate
    @CreationTimestamp
    private Date createdDate;
    @ManyToOne
    private Users users;
    @ManyToOne
    private Products products;
}
