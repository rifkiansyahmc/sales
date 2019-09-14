package com.rifkiansyah.sales.model;

import javax.persistence.*;
import java.time.ZonedDateTime;

@Entity
@Table(name = "sales", catalog = "test")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    private String itemname;

    private ZonedDateTime transactionTime;

    private Integer quantity;

    private String username;

    public Sales() {
    }

    public Sales(String itemname, ZonedDateTime transactionTime, Integer quantity, String username) {
        this.itemname = itemname;
        this.transactionTime = transactionTime;
        this.quantity = quantity;
        this.username = username;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getItemname() {
        return itemname;
    }

    public void setItemname(String itemname) {
        this.itemname = itemname;
    }

    public ZonedDateTime getTransactionTime() {
        return transactionTime;
    }

    public void setTransactionTime(ZonedDateTime transactionTime) {
        this.transactionTime = transactionTime;
    }

    public Integer getQuantity() {
        return quantity;
    }

    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
