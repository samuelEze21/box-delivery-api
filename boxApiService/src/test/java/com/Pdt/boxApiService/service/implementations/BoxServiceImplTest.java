package com.Pdt.boxApiService.service.implementations;

import com.Pdt.boxApiService.dtos.request.BoxRequest;
import com.Pdt.boxApiService.dtos.response.BoxResponse;
import com.Pdt.boxApiService.dtos.request.ItemRequest;
import com.Pdt.boxApiService.dtos.response.ItemResponse;
import com.Pdt.boxApiService.exception.*;
import com.Pdt.boxApiService.model.entity.Box;
import com.Pdt.boxApiService.model.entity.Item;
import com.Pdt.boxApiService.model.enums.State;
import com.Pdt.boxApiService.repository.BoxRepository;
import com.Pdt.boxApiService.service.interfaces.BoxService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@Transactional
class BoxServiceImplTest {

    @Autowired
    private BoxRepository boxRepository;

    private BoxService boxService;

    @BeforeEach
    void setUp() {
        boxRepository.deleteAll(); // Clear database
        boxService = new BoxServiceImpl(boxRepository);
    }

    @Test
    void createBox_success() throws BoxRequestNullException, InvalidBoxDataException {
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
    void createBox_nullRequest_throwsBoxRequestNullException() {
        RuntimeException exception = assertThrows(RuntimeException.class, () -> boxService.createBox(null));
        assertInstanceOf(BoxRequestNullException.class, exception.getCause());
        assertEquals("Box request cannot be null", exception.getCause().getMessage());
    }

    @Test
    void loadBoxWithItems_success() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(500.0);
        box.setBatteryCapacity(50);
        box.setState(State.IDLE);
        Box finalBox = boxRepository.save(box);

        ItemRequest itemRequest = new ItemRequest("item1", 100.0, "CODE1");
        boxService.loadBoxWithItems(finalBox.getId(), List.of(itemRequest));

        Box updatedBox = boxRepository.findById(finalBox.getId()).orElseThrow();
        assertEquals(State.LOADED, updatedBox.getState());
        assertEquals(1, updatedBox.getItems().size());
        assertEquals("item1", updatedBox.getItems().get(0).getName());
    }

    @Test
    void loadBoxWithItems_nullBoxId_throwsBoxIdNullException() {
        ItemRequest itemRequest = new ItemRequest("item1", 100.0, "CODE1");
        BoxIdNullException exception = assertThrows(BoxIdNullException.class, () -> boxService.loadBoxWithItems(null, List.of(itemRequest)));
        assertEquals("Box ID cannot be null", exception.getMessage());
    }

    @Test
    void loadBoxWithItems_nullItems_throwsItemRequestsNullOrEmptyException() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(500.0);
        box.setBatteryCapacity(50);
        box.setState(State.IDLE);
        Box finalBox = boxRepository.save(box);

        ItemRequestsNullOrEmptyException exception = assertThrows(ItemRequestsNullOrEmptyException.class, () -> boxService.loadBoxWithItems(finalBox.getId(), null));
        assertEquals("Item requests cannot be null or empty", exception.getMessage());
    }

    @Test
    void loadBoxWithItems_lowBattery_throwsLowBatteryException() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(500.0);
        box.setBatteryCapacity(20);
        box.setState(State.IDLE);
        Box finalBox = boxRepository.save(box);

        ItemRequest itemRequest = new ItemRequest("item1", 100.0, "CODE1");
        LowBatteryException exception = assertThrows(LowBatteryException.class, () -> boxService.loadBoxWithItems(finalBox.getId(), List.of(itemRequest)));
        assertEquals("Cannot load box with battery below 25%", exception.getMessage());
    }

    @Test
    void loadBoxWithItems_exceedsWeight_throwsWeightLimitExceededException() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(200.0);
        box.setBatteryCapacity(50);
        box.setState(State.IDLE);
        Box finalBox = boxRepository.save(box);

        ItemRequest itemRequest = new ItemRequest("item1", 300.0, "CODE1");
        WeightLimitExceededException exception = assertThrows(WeightLimitExceededException.class, () -> boxService.loadBoxWithItems(finalBox.getId(), List.of(itemRequest)));
        assertEquals("Total weight exceeds box limit", exception.getMessage());
    }

    @Test
    void loadBoxWithItems_notIdle_throwsBoxNotIdleStateException() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(500.0);
        box.setBatteryCapacity(50);
        box.setState(State.LOADED);
        Box finalBox = boxRepository.save(box);

        ItemRequest itemRequest = new ItemRequest("item1", 100.0, "CODE1");
        BoxNotIdleStateException exception = assertThrows(BoxNotIdleStateException.class, () -> boxService.loadBoxWithItems(finalBox.getId(), List.of(itemRequest)));
        assertEquals("Box must be in IDLE state to load", exception.getMessage());
    }

    @Test
    void getLoadedItems_success() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(500.0);
        box.setBatteryCapacity(50);
        box.setState(State.LOADED);
        Item item = new Item();
        item.setName("item1");
        item.setWeight(100.0);
        item.setCode("CODE1");
        item.setBox(box);
        box.getItems().add(item);
        Box finalBox = boxRepository.save(box);

        List<ItemResponse> items = boxService.getLoadedItems(finalBox.getId());
        assertEquals(1, items.size());
        assertEquals("item1", items.get(0).name());
        assertEquals(100.0, items.get(0).weight());
        assertEquals("CODE1", items.get(0).code());
    }

    @Test
    void getLoadedItems_nullBoxId_throwsBoxIdNullException() {
        BoxIdNullException exception = assertThrows(BoxIdNullException.class, () -> boxService.getLoadedItems(null));
        assertEquals("Box ID cannot be null", exception.getMessage());
    }

    @Test
    void getLoadedItems_notFound_throwsBoxNotFoundException() {
        BoxNotFoundException exception = assertThrows(BoxNotFoundException.class, () -> boxService.getLoadedItems(999L));
        assertEquals("Box not found with id: 999", exception.getMessage());
    }

    @Test
    void getAvailableBoxes_success() {
        Box box1 = new Box();
        box1.setTxref("TEST1");
        box1.setWeightLimit(500.0);
        box1.setBatteryCapacity(30);
        box1.setState(State.IDLE);
        boxRepository.save(box1);

        Box box2 = new Box();
        box2.setTxref("TEST2");
        box2.setWeightLimit(500.0);
        box2.setBatteryCapacity(20);
        box2.setState(State.IDLE);
        boxRepository.save(box2);

        List<BoxResponse> availableBoxes = boxService.getAvailableBoxes();
        assertEquals(1, availableBoxes.size());
        assertEquals("TEST1", availableBoxes.get(0).txref());
    }

    @Test
    void getBatteryLevel_success() {
        Box box = new Box();
        box.setTxref("TEST");
        box.setWeightLimit(500.0);
        box.setBatteryCapacity(50);
        box.setState(State.IDLE);
        Box finalBox = boxRepository.save(box);

        int batteryLevel = boxService.getBatteryLevel(finalBox.getId());
        assertEquals(50, batteryLevel);
    }

    @Test
    void getBatteryLevel_nullBoxId_throwsBoxIdNullException() {
        BoxIdNullException exception = assertThrows(BoxIdNullException.class, () -> boxService.getBatteryLevel(null));
        assertEquals("Box ID cannot be null", exception.getMessage());
    }

    @Test
    void getBatteryLevel_notFound_throwsBoxNotFoundException() {
        BoxNotFoundException exception = assertThrows(BoxNotFoundException.class, () -> boxService.getBatteryLevel(999L));
        assertEquals("Box not found with id: 999", exception.getMessage());
    }
}