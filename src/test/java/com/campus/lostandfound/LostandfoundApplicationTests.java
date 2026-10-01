package com.campus.lostandfound;

import com.campus.lostandfound.controller.ItemController;
import com.campus.lostandfound.model.Item;
import com.campus.lostandfound.model.ItemStatus;
import com.campus.lostandfound.repository.ItemRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class LostandfoundApplicationTests {

    @Autowired
    private ItemController itemController;

    @Autowired
    private ItemRepository itemRepository;

    @BeforeEach
    void cleanUp() {
        itemRepository.deleteAll();
    }

    @Test
    @DisplayName("1. Create Item - Should save and return HTTP 201 Created")
    void testCreateItem() {
        Item item = new Item("Calculus Textbook", "Hardcover 9th edition", "Books",
                ItemStatus.LOST, "Math Hall 101", "student@campus.edu");

        ResponseEntity<Item> response = itemController.createItem(item);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("Calculus Textbook", response.getBody().getTitle());
    }

    @Test
    @DisplayName("2. Get All Items - Should return items list and HTTP 200 OK")
    void testGetAllItems() {
        Item item = new Item("Water Bottle", "Hydroflask blue", "Personal",
                ItemStatus.FOUND, "Cafeteria", "staff@campus.edu");
        itemRepository.save(item);

        ResponseEntity<List<Item>> response = itemController.getAllItems(null, null);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(1, response.getBody().size());
        assertEquals("Water Bottle", response.getBody().get(0).getTitle());
    }

    @Test
    @DisplayName("3. Get Item By ID - Non-existent ID should return HTTP 404")
    void testGetItemByIdNotFound() {
        ResponseEntity<Item> response = itemController.getItemById(999L);
        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    @DisplayName("4. Update Item - Should modify item fields and return HTTP 200 OK")
    void testUpdateItem() {
        Item saved = itemRepository.save(new Item("Keys", "Room key with red tag", "Keys",
                ItemStatus.LOST, "Gym", "alex@campus.edu"));

        Item updatedData = new Item("Keys", "Room key with red tag", "Keys",
                ItemStatus.FOUND, "Security Office", "guard@campus.edu");

        ResponseEntity<Item> response = itemController.updateItem(saved.getId(), updatedData);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(ItemStatus.FOUND, response.getBody().getStatus());
        assertEquals("Security Office", response.getBody().getLocation());
    }

    @Test
    @DisplayName("5. Delete Item - Should remove item and return HTTP 204 No Content")
    void testDeleteItem() {
        Item saved = itemRepository.save(new Item("Umbrella", "Black folding", "Other",
                ItemStatus.FOUND, "Main Gate", "guard@campus.edu"));

        ResponseEntity<Void> response = itemController.deleteItem(saved.getId());

        assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
        assertFalse(itemRepository.existsById(saved.getId()));
    }
}