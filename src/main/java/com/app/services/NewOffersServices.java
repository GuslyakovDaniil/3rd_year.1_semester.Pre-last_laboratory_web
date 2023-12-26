package com.app.services;

import com.app.models.Models;
import com.app.models.Offers;
import com.app.models.Users;
import com.app.repositories.ModelsRepository;
import com.app.repositories.OffersRepository;
import com.app.dtos.AddOffersDto;
import com.app.dtos.ShowOfferInfoDto;
import com.app.dtos.ShowDetailedOfferInfo;
import com.app.repositories.UsersRepository;
import org.modelmapper.ModelMapper;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class NewOffersServices {
    private final OffersRepository offersRepository;
    private final ModelsRepository modelsRepository;
    private final UsersRepository usersRepository;
    private final ModelMapper mapper;

    public NewOffersServices(OffersRepository offersRepository, ModelsRepository modelsRepository,
                             UsersRepository usersRepository, ModelMapper mapper) {
        this.offersRepository = offersRepository;
        this.modelsRepository = modelsRepository;
        this.usersRepository = usersRepository;
        this.mapper = mapper;
    }

    @CacheEvict(cacheNames = "offers", allEntries = true)
    public void addOffer(AddOffersDto offersDto) {
        Offers offer = mapper.map(offersDto, Offers.class);
        Users seller = usersRepository.findById(offersDto.getSeller_id())
                .orElseThrow(() -> new IllegalArgumentException("Error"));
        Models model = modelsRepository.findById(offersDto.getModel_id())
                .orElseThrow(() -> new IllegalArgumentException("Error"));
        offer.setSeller(seller);
        offer.setModel(model);
        offersRepository.saveAndFlush(offer);
    }

    @Cacheable("offers")
    public List<ShowOfferInfoDto> allOffers() {
        return offersRepository.findAll().stream().map(offers -> mapper.map(offers, ShowOfferInfoDto.class))
                .collect(Collectors.toList());
    }

    @Cacheable("offersDetails")
    public ShowDetailedOfferInfo offersDetails(String description) {
        return mapper.map(offersRepository.findByDescription(description).orElse(null), ShowDetailedOfferInfo.class);
    }

    @CacheEvict(cacheNames = "offers", allEntries = true)
    public void removeOffer(String description) {
        offersRepository.deleteByDescription(description);
    }

    @Cacheable("top10Offers")
    public List<ShowOfferInfoDto> top10Offers() {
        return offersRepository.findByOrderByYearDesc(PageRequest.of(0, 10))
                .stream()
                .map(offers -> mapper.map(offers, ShowOfferInfoDto.class))
                .collect(Collectors.toList());
    }
}
