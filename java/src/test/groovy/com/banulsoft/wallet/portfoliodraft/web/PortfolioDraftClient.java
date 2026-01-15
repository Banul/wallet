package com.banulsoft.wallet.portfoliodraft.web;

import com.banulsoft.wallet.portfoliodraft.domain.PortfolioDraftId;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Component
class PortfolioDraftClient {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public PortfolioDraftClient(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    PortfolioDraftResponseDto getDraft(PortfolioDraftId id) throws Exception {
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders
                        .get("/portfolio-draft/{id}", id.draftId()))
                .andReturn()
                .getResponse()
                .getContentAsString();

        return objectMapper.readValue(jsonResponse, PortfolioDraftResponseDto.class);
    }
}