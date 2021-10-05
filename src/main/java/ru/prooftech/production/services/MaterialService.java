package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.repositories.MaterialRepository;

import java.util.List;
import java.util.Optional;

@Service("materialService")
public class MaterialService {
    private MaterialRepository materialRepository;

    @Autowired
    public void setMaterialRepository(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Optional<Material> findById(Long id) {
        return materialRepository.findById(id);
    }

    public Material save(Material material) {
        return materialRepository.save(material);
    }

    public void saveAll(Iterable<Material> iterable) {
        materialRepository.saveAll(iterable);
    }

    public List<Material> findAll() {
        return materialRepository.findAll();
    }

    public boolean deleteById(Long id) {
        materialRepository.deleteById(id);
        return findById(id).isEmpty();
    }
}
