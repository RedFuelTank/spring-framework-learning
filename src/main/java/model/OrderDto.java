package model;

import jakarta.persistence.*;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.List;

@Data
@Entity
@Table(name = "orders")
public class OrderDto {
    @Id
    @SequenceGenerator(name = "my_seq",
            sequenceName = "seq1",
    allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "my_seq")
    private Long id;

    @NotNull
    @Column(name = "order_number")
    private String orderNumber;

    @Valid
    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "order_rows",
    joinColumns = {@JoinColumn(name = "orders_id")})
    private List<Item> orderRows;

    @Data
    @Embeddable
    public static class Item {
        @Column(name = "item_name")
        private String itemName;

        @NotNull
        @Min(1)
        private int quantity;

        @NotNull
        @Min(1)
        private int price;
    }
}
