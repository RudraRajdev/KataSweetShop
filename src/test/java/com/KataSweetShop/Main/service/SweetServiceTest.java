package com.KataSweetShop.Main.service;

import com.KataSweetShop.Main.dto.SweetRequest;
import com.KataSweetShop.Main.dto.SweetResponse;
import com.KataSweetShop.Main.entity.Sweet;
import com.KataSweetShop.Main.repository.SweetRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.mockito.internal.verification.VerificationModeFactory.times;

@ExtendWith(MockitoExtension.class)

public class SweetServiceTest {
        @Mock
        private SweetRepository sweetRepository;

        @InjectMocks
        private SweetService sweetService;

        @Test
        void shouldAddSweetSuccessfully() {
            SweetRequest request = new SweetRequest();
            request.setName("Kaju Katli");
            request.setCategory("Dry Fruit");
            request.setPrice(500L);
            request.setQuantity(10);

            Sweet savedSweet = Sweet.builder()
                    .id(1L)
                    .name("Kaju Katli")
                    .category("Dry Fruit")
                    .price(500L)
                    .quantity(10)
                    .build();

            when(sweetRepository.save(any(Sweet.class))).thenReturn(savedSweet);

            SweetResponse result = sweetService.addSweet(request);

            assertNotNull(result);
            assertEquals("Kaju Katli", result.getName());
            verify(sweetRepository, times(1)).save(any(Sweet.class));
        }

        @Test
        void shouldDeleteSweet() {
            when(sweetRepository.existsById(1L)).thenReturn(true);

            sweetService.deleteSweet(1L);

            verify(sweetRepository).deleteById(1L);
        }

        @Test
        void shouldThrowWhenDeletingNonExistingSweet() {
            when(sweetRepository.existsById(1L)).thenReturn(false);

            assertThrows(RuntimeException.class,
                    () -> sweetService.deleteSweet(1L));
        }
    }


