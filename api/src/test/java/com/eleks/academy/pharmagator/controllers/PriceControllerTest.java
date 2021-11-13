package com.eleks.academy.pharmagator.controllers;

import com.eleks.academy.pharmagator.dataproviders.dto.input.PriceDto;
import com.eleks.academy.pharmagator.entities.Price;
import com.eleks.academy.pharmagator.services.PriceService;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.mockito.junit.jupiter.MockitoSettings;
import org.mockito.quality.Strictness;
import org.modelmapper.ModelMapper;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(MockitoExtension.class)
@MockitoSettings(strictness = Strictness.LENIENT)
public class PriceControllerTest {

    private MockMvc mockMvc;

    @Mock
    private PriceService priceService;

    @InjectMocks
    private PriceController priceController;

    @Mock
    private final ModelMapper modelMapper = new ModelMapper();

    private final String CONTENT = "{\"price\": 11 , \"externalId\": \"11\" }";

    private static Price price1;
    private static Price price2;
    private static PriceDto priceDto1;
    private static ArrayList<Price> priceList;

    private static final Long MEDICINE_ID = 1L;
    private static final Long PHARMACY_ID = 1L;

    private final String URI = "/prices";
    private final String URI_PRICE = "/pharmacyId/" + PHARMACY_ID + "/medicineId/" + MEDICINE_ID;
    private final String URI_NON_EXISTENT_PRICE = "/pharmacyId/" + 23L + "/medicineId/" + 45L;


    @BeforeAll
    static void setUpComponents() {

        price1 = new Price();
        price1.setPrice(BigDecimal.valueOf(11));
        price1.setPharmacyId(PHARMACY_ID);
        price1.setMedicineId(MEDICINE_ID);
        price1.setExternalId("11");

        price2 = new Price();
        price2.setPrice(BigDecimal.valueOf(11));
        price2.setPharmacyId(2L);
        price2.setMedicineId(MEDICINE_ID);
        price2.setExternalId("11");

        priceDto1 = new PriceDto();
        priceDto1.setPrice(BigDecimal.valueOf(11));
        priceDto1.setExternalId("11");

        priceList = new ArrayList<>();
        priceList.add(price1);
        priceList.add(price2);
    }

    @BeforeEach
    public void init() {

        mockMvc = MockMvcBuilders
                .standaloneSetup(priceController)
                .setCustomArgumentResolvers(new PageableHandlerMethodArgumentResolver())
                .build();
    }

    @Test
    void getAllPrices_isOk() throws Exception {

        when(priceService.findAll()).thenReturn(priceList);
        when(modelMapper.map(eq(price1), eq(PriceDto.class))).thenReturn(priceDto1);
        when(modelMapper.map(eq(price2), eq(PriceDto.class))).thenReturn(priceDto1);

        mockMvc.perform(get(URI))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[*].price", Matchers.hasItems(11)));

        verify(priceService).findAll();
    }

    @Test
    void getById_isOk() throws Exception {

        when(modelMapper.map(eq(price1), eq(PriceDto.class))).thenReturn(priceDto1);
        when(priceService.findById(PHARMACY_ID, MEDICINE_ID)).thenReturn(Optional.of(price1));

        mockMvc.perform(get(URI + URI_PRICE))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(jsonPath("$.price").value(priceDto1.getPrice()));

        verify(priceService).findById(eq(PHARMACY_ID), eq(MEDICINE_ID));
    }

    @Test
    void getById_noExistsId_isNotFound() throws Exception {

        mockMvc.perform(get(URI + URI_NON_EXISTENT_PRICE)).andExpect(status().isNotFound());
    }

    @Test
    void updatePharmacyById_isOk() throws Exception {

        when(priceService.update(eq(PHARMACY_ID), eq(MEDICINE_ID), eq(priceDto1))).thenReturn(Optional.of(price1));
        when(modelMapper.map(eq(price1), eq(PriceDto.class))).thenReturn(priceDto1);

        mockMvc.perform(put(URI + URI_PRICE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CONTENT)).andExpect(status().isOk())
                .andExpect(jsonPath("$.price").value(priceDto1.getPrice()));

        verify(priceService).update(eq(PHARMACY_ID), eq(MEDICINE_ID), eq(priceDto1));
    }

    @Test
    void updatePharmacyById_noExistsId_NotFound() throws Exception {

        mockMvc.perform(put(URI + URI_NON_EXISTENT_PRICE)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(CONTENT))
                .andExpect(status().isNotFound());
    }

    @Test
    void delete_isOk() throws Exception {

        mockMvc.perform(delete(URI + URI_PRICE)).andExpect(status().isNoContent());
        verify(priceService).deleteById(PHARMACY_ID, MEDICINE_ID);
    }

}

