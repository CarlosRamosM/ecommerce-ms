package com.ecommerce.inventory_service.dm.repository;

import com.ecommerce.inventory_service.dm.model.Inventory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface InventoryRepository extends JpaRepository<Inventory, Long> {

    Optional<Inventory> findBySku(final String sku);
}
