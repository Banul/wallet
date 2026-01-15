package com.banulsoft.wallet.portfoliodraft.web;

import com.banulsoft.wallet.portfolio.web.AssetCreateDto;
import com.banulsoft.wallet.portfolio.web.PortfolioCreateDto;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.UUID;

@Component
public class PortfolioClient {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public PortfolioClient(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public UUID createPortfolio(PortfolioCreateDto portfolioCreateDto) throws Exception {
        String jsonResponse = mockMvc.perform(MockMvcRequestBuilders.post("/portfolio")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(portfolioCreateDto)))
                .andReturn()
                .getResponse()
                .getContentAsString();


        return objectMapper.readValue(jsonResponse, UUID.class);
    }

    public static AssetCreateDto asset(String ticker, double amount) {
        return new AssetCreateDto(ticker, amount);
    }
}