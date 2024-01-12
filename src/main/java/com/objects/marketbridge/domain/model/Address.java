package com.objects.marketbridge.domain.model;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Address extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member memberId;

    @Embedded
    private AddressValue addressValue;

    private boolean isDefault;

    @Builder
    private Address(Member memberId, AddressValue addressValue, boolean isDefault) {
        this.memberId = memberId;
        this.addressValue = addressValue;
        this.isDefault = isDefault;
    }

}
