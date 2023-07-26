package com.api.app.repository;

import com.api.app.model.Employee;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, String> {
    Optional<Employee> findByPrincipalUsername(String username);
    @Query(value = "SELECT * FROM employee e " +
            "WHERE (:firstName IS NULL OR LOWER(e.first_name) LIKE CONCAT('%', LOWER(:firstName), '%')) " +
            "AND (:lastName IS NULL OR LOWER(e.last_name) LIKE CONCAT('%', LOWER(:lastName), '%')) " +
            "AND (:sex IS NULL OR LOWER(e.sex) LIKE CONCAT('%', LOWER(:sex), '%')) " +
            "AND (:job IS NULL OR LOWER(e.job) LIKE CONCAT('%', LOWER(:job), '%')) " +
            "ORDER BY " +
            "CASE WHEN :firstNameOrder IS NULL THEN NULL ELSE e.first_name END :#{#firstNameOrder == 'DESC' ? 'DESC' : 'ASC'}, " +
            "CASE WHEN :lastNameOrder IS NULL THEN NULL ELSE e.last_name END :#{#lastNameOrder == 'DESC' ? 'DESC' : 'ASC'}, " +
            "CASE WHEN :sexOrder IS NULL THEN NULL ELSE e.sex END :#{#sexOrder == 'DESC' ? 'DESC' : 'ASC'}, " +
            "CASE WHEN :jobOrder IS NULL THEN NULL ELSE e.job END :#{#jobOrder == 'DESC' ? 'DESC' : 'ASC'}",
            nativeQuery = true)
    List<Employee> findByCriteria(
            String firstName,
            String lastName,
            String sex,
            String job,
            String firstNameOrder,
            String lastNameOrder,
            String sexOrder,
            String jobOrder,
            Pageable pageable);
}
