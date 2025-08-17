package com.Pdt.boxApiService.service.implementations;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.model.enums.State;
import com.Pdt.boxApiService.repository.BoxRepository;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ActiveProfiles("test")

class BoxServiceImplTest {

    @Autowired
    private BoxRepository boxRepository;

    private BoxService boxService;

    @BeforeEach
    void setUp() {
        boxService = new BoxServiceImpl(boxRepository);
    }

    @Test
    void createBox_success() {
        BoxRequest request = new BoxRequest("TEST", 500.0, 50);
        BoxResponse response = boxService.createBox(request);

        assertNotNull(response.id());
        assertEquals("TEST", response.txref());
        assertEquals(500.0, response.weightLimit());
        assertEquals(50, response.batteryCapacity());
        assertEquals(State.IDLE, response.state());
        assertTrue(response.items().isEmpty());
    }

    @Test
    void loadBoxWithItems() {
    }

    @Test
    void getLoadedItems() {
    }

    @Test
    void getAvailableBoxes() {
    }

    @Test
    void getBatteryLevel() {
    }
}