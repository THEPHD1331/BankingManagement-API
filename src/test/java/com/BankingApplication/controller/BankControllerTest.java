package com.BankingApplication.controller;

import com.BankingApplication.exception.BankDetailsNotFound;
import com.BankingApplication.model.BankTO;
import com.BankingApplication.model.BranchTO;
import com.BankingApplication.service.BankService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.testcontainers.shaded.com.fasterxml.jackson.databind.ObjectMapper;
import java.util.ArrayList;
import java.util.List;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@RunWith(SpringRunner.class)
@SpringBootTest
@AutoConfigureMockMvc
public class BankControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    BankService bankService;

    @Test
    public void testGetBanks() throws Exception{

        List<BankTO> bankTOList= new ArrayList<>();
        BankTO bankTO= new BankTO();
        bankTO.setBankName("Joke");
        bankTO.setBankAddress("Jodhpur");

        List<BranchTO> branchTOList= new ArrayList<>();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName("Mazak");
        branchTO.setBranchAddress("Amritsar");
        branchTOList.add(branchTO);
        bankTO.setBranches(branchTOList);
        bankTOList.add(bankTO);

        when(bankService.getAllBanks()).thenReturn(bankTOList);
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testGetBanksNotFoundException() throws Exception {
        when(bankService.getAllBanks()).thenThrow(new BankDetailsNotFound("Bank details not found"));
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isNotFound());
    }

    @Test
    public void testGetBanksException() throws Exception{
        when(bankService.getAllBanks()).thenThrow(new NullPointerException());
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetBankByCode() throws Exception{
        BankTO bankTO= new BankTO();
        bankTO.setBankName("Joke");
        bankTO.setBankAddress("Jodhpur");

        List<BranchTO> branchTOList= new ArrayList<>();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName("Mazak");
        branchTO.setBranchAddress("Amritsar");
        branchTOList.add(branchTO);
        bankTO.setBranches(branchTOList);

        when(bankService.getBankByCode(anyInt())).thenReturn(bankTO);
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/4")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testGetBankByCodeNotFound() throws Exception{
        when(bankService.getBankByCode(anyInt())).thenThrow(new BankDetailsNotFound("Bank details not found"));
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/9")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isNotFound());
    }

    @Test
    public void testGetBankByCodeException() throws Exception{
        when(bankService.getBankByCode(anyInt())).thenThrow(new NullPointerException());
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/8")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isInternalServerError());
    }

    @Test
    public void testGetBankByName() throws Exception{
        BankTO bankTO= new BankTO();
        bankTO.setBankName("Joke");
        bankTO.setBankAddress("Jodhpur");

        List<BranchTO> branchTOList= new ArrayList<>();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName("Mazak");
        branchTO.setBranchAddress("Amritsar");
        branchTOList.add(branchTO);
        bankTO.setBranches(branchTOList);

        when(bankService.getBankByName(anyString())).thenReturn(bankTO);
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/byName?bankname=Joke")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

//    @Test
//    public void testGetBankByNameNotFound() throws Exception{
//        when(bankService.getBankByName(anyString())).thenThrow(new BankDetailsNotFound("Bank details not found"));
//        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/byName?bankname=Side")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(builder).andExpect(status().isNotFound());
//    }
//
//    @Test
//    public void testGetBankByNameException() throws Exception{
//        when(bankService.getBankByName(anyString())).thenThrow(new NullPointerException());
//        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/byName?bankname=Side")
//                .contentType(MediaType.APPLICATION_JSON);
//
//        mockMvc.perform(builder).andExpect(status().isInternalServerError());
//    }

    @Test
    public void testGetBankByNameNullCheck() throws Exception{
        when(bankService.getBankByName(anyString())).thenThrow(new NullPointerException());
        RequestBuilder builder= MockMvcRequestBuilders.get("/api/banks/byName?bankname=")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    @Test
    public void testSaveBank() throws Exception{
        BankTO bankTO= new BankTO();
        bankTO.setBankName("Joke");
        bankTO.setBankAddress("Jodhpur");
        List<BranchTO> branchTOList= new ArrayList<>();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName("Mazak");
        branchTO.setBranchAddress("Amritsar");
        branchTOList.add(branchTO);
        bankTO.setBranches(branchTOList);

        when(bankService.saveBank(any())).thenReturn(bankTO);
        RequestBuilder builder= MockMvcRequestBuilders.post("/api/banks")
                .content(asJsonString(bankTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testSaveBankNotFound() throws Exception{
        when(bankService.saveBank(any())).thenThrow(new BankDetailsNotFound("Bank details not saved"));
        RequestBuilder builder= MockMvcRequestBuilders.post("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isNotFound());
    }

    @Test
    public void testSaveBankNullCheck() throws Exception{
        when(bankService.saveBank(any())).thenThrow(new NullPointerException());
        RequestBuilder builder= MockMvcRequestBuilders.post("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    @Test
    public void testUpdateBank() throws Exception{
        BankTO bankTO= new BankTO();
        bankTO.setBankName("Joke");
        bankTO.setBankAddress("Jodhpur");
        List<BranchTO> branchTOList= new ArrayList<>();
        BranchTO branchTO= new BranchTO();
        branchTO.setBranchName("Mazak");
        branchTO.setBranchAddress("Amritsar");
        branchTOList.add(branchTO);
        bankTO.setBranches(branchTOList);

        when(bankService.updateBank(any())).thenReturn(bankTO);
        RequestBuilder builder= MockMvcRequestBuilders.put("/api/banks")
                .content(asJsonString(bankTO))
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testUpdateBankNullCheck() throws Exception{
        when(bankService.updateBank(any())).thenThrow(new NullPointerException());
        RequestBuilder builder= MockMvcRequestBuilders.post("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    @Test
    public void testDeleteBank() throws Exception{
        when(bankService.deleteBankByCode(anyInt())).thenReturn("Bank is deleted");
        RequestBuilder builder= MockMvcRequestBuilders.delete("/api/banks?bankCode=4")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isOk());
    }

    @Test
    public void testDeleteBankNullCheck() throws Exception{
        when(bankService.deleteBankByCode(anyInt())).thenThrow(new NullPointerException());
        RequestBuilder builder= MockMvcRequestBuilders.delete("/api/banks")
                .contentType(MediaType.APPLICATION_JSON);

        mockMvc.perform(builder).andExpect(status().isBadRequest());
    }

    public String asJsonString(final Object obj){
        try{
            final ObjectMapper mapper= new ObjectMapper();
            final String jsonContent= mapper.writeValueAsString(obj);
            return jsonContent;
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
