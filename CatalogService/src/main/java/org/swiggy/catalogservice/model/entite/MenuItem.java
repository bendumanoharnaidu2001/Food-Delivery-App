package org.swiggy.catalogservice.model.entite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import static jakarta.persistence.ConstraintMode.CONSTRAINT;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class MenuItem {
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    private Long id;

    private String name;
    private double price;
    @JsonIgnore
    @ManyToOne()
    @JoinColumn(name = "restaurant_id",foreignKey = @ForeignKey(value = CONSTRAINT,
            foreignKeyDefinition = "FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE"))
    private Restaurant restaurant;

}
