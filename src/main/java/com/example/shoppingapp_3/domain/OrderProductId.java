package com.example.shoppingapp_3.domain;

import lombok.Data;

import javax.persistence.Embeddable;
import java.io.Serializable;

@Data
@Embeddable
public class OrderProductId implements Serializable {
    private Long orderId;
    private Long productId;
}
