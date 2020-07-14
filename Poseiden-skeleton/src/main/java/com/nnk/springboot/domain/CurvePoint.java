package com.nnk.springboot.domain;

import com.nnk.springboot.annotations.RangeDouble;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
public class CurvePoint {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @NotBlank(message = "Curve Id must no be null")
    private Integer curveId;
    private Timestamp asOfDate;
    @RangeDouble(min = 0.0)
    private Double term;
    @RangeDouble(min = 0.0)
    private Double value;
    private Timestamp creationDate;

    public CurvePoint() {
    }

    public CurvePoint(Integer id,
                      Integer curveId,
                      Timestamp asOfDate,
                      Double term,
                      Double value,
                      Timestamp creationDate) {
        this.id = id;
        this.curveId = curveId;
        this.asOfDate = asOfDate;
        this.term = term;
        this.value = value;
        this.creationDate = creationDate;
    }

    public Integer getId() {
        return id;
    }

    public Integer getCurveId() {
        return curveId;
    }

    public void setCurveId(Integer curveId) {
        this.curveId = curveId;
    }

    public Timestamp getAsOfDate() {
        return asOfDate;
    }

    public void setAsOfDate(Timestamp asOfDate) {
        this.asOfDate = asOfDate;
    }

    public Double getTerm() {
        return term;
    }

    public void setTerm(Double term) {
        this.term = term;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public Timestamp getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Timestamp creationDate) {
        this.creationDate = creationDate;
    }
}
