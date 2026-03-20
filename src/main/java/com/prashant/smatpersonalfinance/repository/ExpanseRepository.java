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

public interface ExpanseRepository extends JpaRepository<ExpanseEntity,Long> {

    List<ExpanseEntity> findByProfileIdOrderByDateDesc(Long profileId);
   Optional< ExpanseEntity> findByIdAndProfileId(Long id, Long profileId);
    List<ExpanseEntity> findByCategoryIdAndProfileId(Long id1,Long id2);
    @Query("Select SUM(e.amount) from ExpanseEntity e where e.profile.id=:profileId")
    BigDecimal findTotalByProfileId(Long profileId);
    List<ExpanseEntity> findByProfileIdAndDateBetween(Long id, LocalDate st,LocalDate ed);
    List<ExpanseEntity> findByProfileIdAndDate(Long id,LocalDate date);
    List<ExpanseEntity> findTop5ByProfileIdOrderByDateDesc(Long id);
    List<ExpanseEntity> findByProfileIdAndDateBetweenAndNameContainingIgnoreCase(Long id, LocalDate st,LocalDate ed,String name);
    @Query(
            value = """
SELECT * 
FROM tbl_expanse e
WHERE e.profile_id = :profileId
AND (:startDate IS NULL OR e.date >= :startDate)
AND (:endDate IS NULL OR e.date <= :endDate)
AND (:search IS NULL OR :search = '' 
     OR LOWER(e.name) LIKE LOWER(CONCAT('%', :search, '%')))
ORDER BY
CASE 
    WHEN :sortField = 'date' AND :sortOrder = 'asc' THEN e.date 
END ASC,
CASE 
    WHEN :sortField = 'date' AND :sortOrder = 'desc' THEN e.date 
END DESC,
CASE 
    WHEN :sortField = 'amount' AND :sortOrder = 'asc' THEN e.amount 
END ASC,
CASE 
    WHEN :sortField = 'amount' AND :sortOrder = 'desc' THEN e.amount 
END DESC
""",
            nativeQuery = true
    )
    List<ExpanseEntity> filterExpanses(
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
                    
                    delete from tbl_expanse i
                    where i.category_id=:id
                    """,
            nativeQuery = true
    )
    void deleteByCategoryId( @Param("id")  Long id);
}
