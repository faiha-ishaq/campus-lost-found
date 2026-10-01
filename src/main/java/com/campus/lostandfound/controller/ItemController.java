package com.campus.lostandfound.controller;

import com.campus.lostandfound.model.Item;
import com.campus.lostandfound.model.ItemStatus;
import com.campus.lostandfound.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    private final ItemRepository itemRepository;

    public ItemController(ItemRepository itemRepository) {
        this.itemRepository = itemRepository;
    }

    // CREATE: Report a new item
    @PostMapping
    public ResponseEntity<Item> createItem(@Valid @RequestBody Item item) {
        Item savedItem = itemRepository.save(item);
        return new ResponseEntity<>(savedItem, HttpStatus.CREATED);
    }

    // READ: Get all items (optional filters: ?status=LOST or ?category=Electronics)
    @GetMapping
    public ResponseEntity<List<Item>> getAllItems(
            @RequestParam(required = false) ItemStatus status,
            @RequestParam(required = false) String category) {

        if (status != null) {
            return ResponseEntity.ok(itemRepository.findByStatus(status));
        }
        if (category != null && !category.isBlank()) {
            return ResponseEntity.ok(itemRepository.findByCategoryIgnoreCase(category));
        }
        return ResponseEntity.ok(itemRepository.findAll());
    }

    // READ: Get single item by ID
    @GetMapping("/{id}")
    public ResponseEntity<Item> getItemById(@PathVariable Long id) {
        return itemRepository.findById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // UPDATE: Update item details or status
    @PutMapping("/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable Long id, @Valid @RequestBody Item updatedItem) {
        return itemRepository.findById(id).map(existingItem -> {
            existingItem.setTitle(updatedItem.getTitle());
            existingItem.setDescription(updatedItem.getDescription());
            existingItem.setCategory(updatedItem.getCategory());
            existingItem.setStatus(updatedItem.getStatus());
            existingItem.setLocation(updatedItem.getLocation());
            existingItem.setContactEmail(updatedItem.getContactEmail());
            return ResponseEntity.ok(itemRepository.save(existingItem));
        }).orElse(ResponseEntity.notFound().build());
    }

    // DELETE: Remove item
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable Long id) {
        if (!itemRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        itemRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
    @GetMapping("/test")
    public String test() {
        return "API is up and running!";
    }
    
}
