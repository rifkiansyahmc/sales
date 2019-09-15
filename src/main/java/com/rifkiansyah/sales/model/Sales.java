package com.rifkiansyah.sales.model;

import javax.persistence.*;
import java.time.ZonedDateTime;
import java.util.Objects;

@Entity
@Table(name = "sales", catalog = "test")
public class Sales {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;

    @Column(name = "itemname")
    private String itemname;

    @Column(name = "transaction_time")
    private ZonedDateTime transactionTime;

    @Column(name = "quantity")
    private Integer quantity;

    @Column(name = "username")
    private String username;

    @Column(name = "itemcode")
    private Long itemcode;

    @Column(name = "receipt_id")
    private String receiptId;

    @Column(name = "price")
    private Long price;

    public Sales() {
    }

    public Sales(String itemname, ZonedDateTime transactionTime, Integer quantity, String username, Long itemcode, String receiptId, Long price) {
        this.itemname = itemname;
        this.transactionTime = transactionTime;
        this.quantity = quantity;
        this.username = username;
        this.itemcode = itemcode;
        this.receiptId = receiptId;
        this.price = price;
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

    public Long getItemcode() {
        return itemcode;
    }

    public void setItemcode(Long itemcode) {
        this.itemcode = itemcode;
    }

    public String getReceiptId() {
        return receiptId;
    }

    public void setReceiptId(String receiptId) {
        this.receiptId = receiptId;
    }

    public Long getPrice() {
        return price;
    }

    public void setPrice(Long price) {
        this.price = price;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Sales sales = (Sales) o;
        return id.equals(sales.id) &&
                Objects.equals(receiptId, sales.receiptId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, receiptId);
    }

    @Override
    public String toString() {
        return "Sales{" +
                "id=" + id +
                ", itemname='" + itemname + '\'' +
                ", transactionTime=" + transactionTime +
                ", quantity=" + quantity +
                ", username='" + username + '\'' +
                ", itemcode=" + itemcode +
                ", receiptId=" + receiptId +
                ", price=" + price +
                '}';
    }
}
