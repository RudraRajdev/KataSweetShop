package com.KataSweetShop.Main.controller;

import com.KataSweetShop.Main.dto.SweetResponse;
import com.KataSweetShop.Main.security.JwtAuthenticationFilter;
import com.KataSweetShop.Main.security.JwtUtils;
import com.KataSweetShop.Main.service.SweetService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.boot.webmvc.test.autoconfigure.WebMvcTest;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(SweetController.class)
@AutoConfigureMockMvc(addFilters = false) // disable security for controller test
public class SweetControllerTest {


        @Autowired
        private MockMvc mockMvc;

        @MockitoBean
        private SweetService sweetService;

        @MockitoBean
        private JwtAuthenticationFilter jwtAuthenticationFilter;

        @MockitoBean
        private JwtUtils jwtUtils;

        @Test
        void shouldReturnAllSweets() throws Exception {

            SweetResponse response = SweetResponse.builder()
                    .id(1L)
                    .name("Jalebi")
                    .category("Sweet")
                    .price(150L)
                    .quantity(20)
                    .build();

            when(sweetService.listall()).thenReturn(List.of(response));

            mockMvc.perform(get("/api/sweets"))
                    .andExpect(status().isOk())
                    .andExpect(jsonPath("$[0].name").value("Jalebi"));
        }

        @Test
        void shouldSearchSweets() throws Exception {

            when(sweetService.search("jalebi", null, null, null))
                    .thenReturn(List.of());

            mockMvc.perform(get("/api/sweets/search")
                            .param("name", "jalebi"))
                    .andExpect(status().isOk());
        }
    }


