package com.nnk.springboot.domain;

import com.nnk.springboot.annotations.RangeDouble;
import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.sql.Timestamp;


@Entity
public @Data
class CurvePoint {
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

    protected CurvePoint() {
    }

    public CurvePoint(Integer curveId,
                      Double term,
                      Double value) {
        this.curveId = curveId;
        this.term = term;
        this.value = value;
    }
}
