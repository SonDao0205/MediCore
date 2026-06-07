package com.btvn.medicore.repository;
import com.btvn.medicore.entity.PrescriptionItem;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PrescriptionItemRepository
        extends JpaRepository<PrescriptionItem, Long> {
}