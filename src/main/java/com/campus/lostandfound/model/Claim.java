package com.campus.lostandfound.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

@Entity
@Table(name = "claims")
public class Claim {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull(message = "Item ID is required")
    private Long itemId;

    @NotBlank(message = "Claimant name is required")
    private String claimantName;

    @Email(message = "Claimant email must be valid")
    private String claimantEmail;

    private String proofDescription; // e.g., "Has a black sticker on the bottom"

    @Enumerated(EnumType.STRING)
    private ClaimStatus claimStatus;

    private LocalDate claimDate;

    public Claim() {
        this.claimDate = LocalDate.now();
        this.claimStatus = ClaimStatus.PENDING;
    }

    public Claim(Long itemId, String claimantName, String claimantEmail, String proofDescription) {
        this.itemId = itemId;
        this.claimantName = claimantName;
        this.claimantEmail = claimantEmail;
        this.proofDescription = proofDescription;
        this.claimStatus = ClaimStatus.PENDING;
        this.claimDate = LocalDate.now();
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Long getItemId() { return itemId; }
    public void setItemId(Long itemId) { this.itemId = itemId; }

    public String getClaimantName() { return claimantName; }
    public void setClaimantName(String claimantName) { this.claimantName = claimantName; }

    public String getClaimantEmail() { return claimantEmail; }
    public void setClaimantEmail(String claimantEmail) { this.claimantEmail = claimantEmail; }

    public String getProofDescription() { return proofDescription; }
    public void setProofDescription(String proofDescription) { this.proofDescription = proofDescription; }

    public ClaimStatus getClaimStatus() { return claimStatus; }
    public void setClaimStatus(ClaimStatus claimStatus) { this.claimStatus = claimStatus; }

    public LocalDate getClaimDate() { return claimDate; }
    public void setClaimDate(LocalDate claimDate) { this.claimDate = claimDate; }
}