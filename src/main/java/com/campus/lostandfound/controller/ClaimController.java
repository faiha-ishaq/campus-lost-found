package com.campus.lostandfound.controller;

import com.campus.lostandfound.model.Claim;
import com.campus.lostandfound.model.ClaimStatus;
import com.campus.lostandfound.model.ItemStatus;
import com.campus.lostandfound.repository.ClaimRepository;
import com.campus.lostandfound.repository.ItemRepository;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/claims")
public class ClaimController {

    private final ClaimRepository claimRepository;
    private final ItemRepository itemRepository;

    public ClaimController(ClaimRepository claimRepository, ItemRepository itemRepository) {
        this.claimRepository = claimRepository;
        this.itemRepository = itemRepository;
    }

    // CREATE: Submit a claim for an item
    @PostMapping
    public ResponseEntity<?> submitClaim(@Valid @RequestBody Claim claim) {
        if (!itemRepository.existsById(claim.getItemId())) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Item with ID " + claim.getItemId() + " does not exist.");
        }
        Claim savedClaim = claimRepository.save(claim);
        return new ResponseEntity<>(savedClaim, HttpStatus.CREATED);
    }

    // READ: Get claims by Item ID
    @GetMapping("/item/{itemId}")
    public ResponseEntity<List<Claim>> getClaimsByItem(@PathVariable Long itemId) {
        return ResponseEntity.ok(claimRepository.findByItemId(itemId));
    }

    // UPDATE: Approve or Reject a claim
    @PatchMapping("/{id}/status")
    public ResponseEntity<?> updateClaimStatus(@PathVariable Long id, @RequestParam ClaimStatus status) {
        return claimRepository.findById(id).map(claim -> {
            claim.setClaimStatus(status);
            claimRepository.save(claim);

            // If claim is approved, automatically mark the item as CLAIMED
            if (status == ClaimStatus.APPROVED) {
                itemRepository.findById(claim.getItemId()).ifPresent(item -> {
                    item.setStatus(ItemStatus.CLAIMED);
                    itemRepository.save(item);
                });
            }
            return ResponseEntity.ok(claim);
        }).orElse(ResponseEntity.notFound().build());
    }
}