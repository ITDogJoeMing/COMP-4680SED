package com.comp4680.webportal.repos;

import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import com.comp4680.webportal.models.Items;

public interface ItemsRepo extends JpaRepository<Items, Integer> {
    @Query("select i from Items i where i.id != :id and i.code = :code")
    List<Items> findItemsWithSameCode(@Param("id") int id, @Param("code") String code);

    @Query("select i from Items i where i.id = :id and i.status = 'S'")
    Optional<Items> isItemsSold(@Param("id") int id);
}