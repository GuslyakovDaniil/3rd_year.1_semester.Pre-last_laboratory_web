package com.app.services;

import com.app.dtos.AddModelsDto;
import com.app.dtos.ShowModelInfoDto;
import com.app.dtos.ShowDetailedModelInfo;
import com.app.enums.Category;
import com.app.models.Brands;
import com.app.models.Models;
import com.app.repositories.BrandsRepository;
import com.app.repositories.ModelsRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewModelsServices {
    private final ModelsRepository modelsRepository;
    private final BrandsRepository brandsRepository;
    private final ModelMapper mapper;

    public NewModelsServices(ModelsRepository modelsRepository, BrandsRepository brandsRepository, ModelMapper mapper) {
        this.modelsRepository = modelsRepository;
        this.brandsRepository = brandsRepository;
        this.mapper = mapper;
    }

    @CacheEvict(cacheNames = "models", allEntries = true)
    public void addModels(AddModelsDto models) {
        Models newModels = mapper.map(models, Models.class);
        Brands brand = brandsRepository.findById(models.getBrand_id())
                .orElseThrow(() -> new IllegalArgumentException("Error"));
        newModels.setBrand(brand);
        newModels.setBrand_id(brand.getId());
        modelsRepository.saveAndFlush(newModels);
    }

    public List<ShowModelInfoDto> modelsByCategory(Category category) {
        return modelsRepository.findByCategory(category).stream()
                .map(models -> mapper.map(models, ShowModelInfoDto.class))
                .collect(Collectors.toList());
    }


    @Cacheable("models")
    public List<ShowModelInfoDto> allModels() {
        return modelsRepository.findAll().stream().map(models -> mapper.map(models, ShowModelInfoDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable("modelsDetails")
    public ShowDetailedModelInfo modelsDetails(String name) {
        return mapper.map(modelsRepository.findByName(name).orElse(null), ShowDetailedModelInfo.class);
    }

    @CacheEvict(cacheNames = "models", allEntries = true)
    public void removeModel(String name) {
        modelsRepository.deleteByName(name);
    }
}
