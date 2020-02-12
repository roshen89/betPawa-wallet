package com.betPawa.wallet.client.controller;

import static org.assertj.core.api.Java6Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import com.betPawa.wallet.client.dto.WalletClientRequest;
import com.betPawa.wallet.client.dto.WalletClientResponse;
import com.betPawa.wallet.client.service.impl.WalletClientServiceImpl;
import com.betPawa.wallet.proto.Operation;
import com.betPawa.wallet.proto.Status;
import com.fasterxml.jackson.databind.ObjectMapper;
import java.util.EnumMap;
import java.util.Map;
import java.util.concurrent.atomic.AtomicLong;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

@RunWith(SpringJUnit4ClassRunner.class)
public class WalletClientControllerTest {

  @InjectMocks
  WalletClientController clientController;

  @Mock
  WalletClientServiceImpl clientService;

  private Map<Operation, Map<Status, AtomicLong>> operationMapMap;

  private WalletClientRequest clientRequest;
  private WalletClientResponse clientResponse;

  protected MockMvc mockMvc;
  protected ObjectMapper mapper;

  @Before
  public void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(clientController).build();
    mapper = new ObjectMapper();
    clientRequest = WalletClientRequest.builder().countOfRequests(1L).countOfRounds(1L).countOfUsers(100L).build();
    operationMapMap = new EnumMap<>(Operation.class);
    clientResponse = WalletClientResponse.builder().transactions(operationMapMap).timeTaken(0L).build();
  }

  @Test
  public void execute() throws Exception {
    Mockito.when(clientService.run(clientRequest)).thenReturn(operationMapMap);
    MvcResult mvcResult = mockMvc.perform(MockMvcRequestBuilders.post("/")
        .contentType(MediaType.APPLICATION_JSON)
        .content(mapper.writeValueAsString(clientRequest)))
        .andExpect(status().isOk())
        .andReturn();
    assertThat(mvcResult.getResponse().getContentAsString()).isEqualTo(mapper.writeValueAsString(clientResponse));
  }
}