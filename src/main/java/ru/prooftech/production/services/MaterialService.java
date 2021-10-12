package ru.prooftech.production.services;

import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;
import ru.prooftech.production.entities.Material;
import ru.prooftech.production.repositories.MaterialRepository;

import java.util.List;

@AllArgsConstructor
@Service("materialService")
public class MaterialService {
    private MaterialRepository materialRepository;

    public Material findById(Long id) {
        return materialRepository.findById(id).orElseThrow(() -> {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Not found material by id - " + id);
        });
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
        return true;
    }
}
