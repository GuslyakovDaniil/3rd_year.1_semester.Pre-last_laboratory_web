package com.app.services;

import com.app.models.Brands;
import com.app.repositories.BrandsRepository;
import com.app.dtos.AddBrandsDto;
import com.app.dtos.ShowBrandInfoDto;
import com.app.dtos.ShowDetailedBrandInfo;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewBrandsServices {
    private final BrandsRepository brandsRepository;
    private final ModelMapper mapper;

    public NewBrandsServices(BrandsRepository brandsRepository, ModelMapper mapper) {
        this.brandsRepository = brandsRepository;
        this.mapper = mapper;
    }

    @CacheEvict(cacheNames = "brands", allEntries = true)
    public void addBrands(AddBrandsDto addBrandsDto) {
        brandsRepository.saveAndFlush(mapper.map(addBrandsDto, Brands.class));
    }

    @Cacheable("brands")
    public List<ShowBrandInfoDto> allBrands() {
        return brandsRepository.findAll().stream().map(brands -> mapper.map(brands, ShowBrandInfoDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable("brandsDetails")
    public ShowDetailedBrandInfo brandsDetails(String name) {
        return mapper.map(brandsRepository.findByName(name).orElse(null), ShowDetailedBrandInfo.class);
    }

    @CacheEvict(cacheNames = "brands", allEntries = true)
    public void removeBrands(String name) {
        brandsRepository.deleteByName(name);
    }
}
