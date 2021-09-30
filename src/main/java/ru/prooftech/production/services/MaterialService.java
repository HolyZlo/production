package ru.prooftech.production.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.repositories.MaterialRepository;

import java.util.List;

@Service("materialService")
public class MaterialService {
    private MaterialRepository materialRepository;

    @Autowired
    public void setMaterialRepository(MaterialRepository materialRepository) {
        this.materialRepository = materialRepository;
    }

    public Material getById(Long id) {
        return materialRepository.getById(id);
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

}
