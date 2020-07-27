package com.nnk.springboot.repositories;

import com.nnk.springboot.domain.CurvePoint;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(collectionResourceRel = "curvePoints", path = "curvePoints")
public interface CurvePointRestRepository extends JpaRepository<CurvePoint, Integer> {

}
