package com.eleks.academy.pharmagator.scheduler;

import com.eleks.academy.pharmagator.dataproviders.DataProvider;
import com.eleks.academy.pharmagator.dataproviders.dto.MedicineDto;
import com.eleks.academy.pharmagator.entities.Medicine;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.repositories.MedicineRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.Instant;
import java.util.Collection;
import java.util.List;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.random.RandomGenerator;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

@Slf4j
@Component
@Profile("!test")
@RequiredArgsConstructor
public class Scheduler {

    private final List<DataProvider> dataProviderList;

    private final MedicineRepository medicineRepository;

    private final ModelMapper modelMapper;

    @Scheduled(fixedDelay = 10, timeUnit = TimeUnit.MINUTES)
    public void schedule() {

        log.info("Scheduler started at {}", Instant.now());

        dataProviderList.stream().flatMap(DataProvider::loadData).forEach(this::storeToDatabase);

    }

    private void storeToDatabase(MedicineDto dto) {

        log.info(dto.getTitle() + " - " + dto.getPrice());

    }

}
