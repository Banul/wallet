package com.banulsoft.wallet.portfolio.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

@Component
class PortfolioClient {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;

    public PortfolioClient(MockMvc mockMvc, ObjectMapper objectMapper) {
        this.mockMvc = mockMvc;
        this.objectMapper = objectMapper;
    }

    public void createPortfolio(PortfolioCreateDto portfolioCreateDto) throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/portfolio")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(portfolioCreateDto)));
    }

    public static AssetCreateDto asset(String ticker, double amount) {
        return new AssetCreateDto(ticker, amount);
    }
}