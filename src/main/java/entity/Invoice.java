package entity;

import org.apache.commons.lang.builder.EqualsBuilder;
import org.apache.commons.lang.builder.HashCodeBuilder;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
public class Invoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    private long id;

    @Column(name = "fact_sum", nullable = true, precision = 2)
    private BigDecimal factSum;

    @Column(name = "all_sum", nullable = true, precision = 2)
    private BigDecimal allSum;

    @Column(name = "paid", nullable = true)
    private boolean paid;

    @Column(name = "description", nullable = true, length = 200)
    private String description;

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public BigDecimal getFactSum() {
        return factSum;
    }

    public void setFactSum(BigDecimal factSum) {
        this.factSum = factSum;
    }

    public BigDecimal getAllSum() {
        return allSum;
    }

    public void setAllSum(BigDecimal allSum) {
        this.allSum = allSum;
    }

    public boolean getPaid() {
        return paid;
    }

    public void setPaid(boolean paid) {
        this.paid = paid;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;

        if (!(o instanceof Invoice)) return false;

        Invoice invoice = (Invoice) o;

        return new EqualsBuilder()
                .append(id, invoice.id)
                .append(factSum, invoice.factSum)
                .append(allSum, invoice.allSum)
                .append(paid, invoice.paid)
                .isEquals();
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder(17, 37)
                .append(id)
                .append(factSum)
                .append(allSum)
                .append(paid)
                .toHashCode();
    }
}
