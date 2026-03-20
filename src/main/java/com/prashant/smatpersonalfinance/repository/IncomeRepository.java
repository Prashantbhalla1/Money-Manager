package com.prashant.smatpersonalfinance.repository;

import com.prashant.smatpersonalfinance.entity.ExpanseEntity;
import com.prashant.smatpersonalfinance.entity.IncomeEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface IncomeRepository extends JpaRepository<IncomeEntity,Long> {
    List<IncomeEntity> findByProfileIdOrderByDateDesc(Long profileId);
     Optional<IncomeEntity> findByIdAndProfileId(Long id, Long profileId);
    List<IncomeEntity> findByCategoryIdAndProfileId(Long id1,Long id2);
    @Query("Select SUM(e.amount) from IncomeEntity e where e.profile.id=:profileId")
    BigDecimal findTotalByProfileId(Long profileId);
    List<IncomeEntity> findByProfileIdAndDateBetween(Long id, LocalDate st, LocalDate ed);
    List<IncomeEntity> findTop5ByProfileIdOrderByDateDesc(Long id);
    List<IncomeEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long id, LocalDate st,LocalDate ed,String name);
    @Query(
            value = """
SELECT *
FROM tbl_incomes i
WHERE i.profile_id = :profileId
AND (:startDate IS NULL OR i.date >= :startDate)
AND (:endDate IS NULL OR i.date <= :endDate)
AND (:search IS NULL OR :search = ''
     OR LOWER(i.name) LIKE LOWER(CONCAT('%', :search, '%')))
ORDER BY
CASE
    WHEN :sortField = 'date' AND :sortOrder = 'asc' THEN i.date
END ASC,
CASE
    WHEN :sortField = 'date' AND :sortOrder = 'desc' THEN i.date
END DESC,
CASE
    WHEN :sortField = 'amount' AND :sortOrder = 'asc' THEN i.amount
END ASC,
CASE
    WHEN :sortField = 'amount' AND :sortOrder = 'desc' THEN i.amount
END DESC
""",
            nativeQuery = true
    )
    List<IncomeEntity> filterIncomes(
            @Param("profileId") Long profileId,
            @Param("startDate") LocalDate startDate,
            @Param("endDate") LocalDate endDate,
            @Param("search") String search,
            @Param("sortField") String sortField,
            @Param("sortOrder") String sortOrder
    );
    @Modifying
    @Query(
            value = """
                    
                    delete from tbl_incomes i
                    where i.category_id=:id
                    """,
            nativeQuery = true
    )
    void deleteByCategoryId( @Param("id")  Long id);
}
