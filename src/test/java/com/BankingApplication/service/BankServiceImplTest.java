package com.BankingApplication.service;

import com.BankingApplication.entity.Bank;
import com.BankingApplication.entity.Branch;
import com.BankingApplication.exception.BankDetailsNotFound;
import com.BankingApplication.model.BankRequest;
import com.BankingApplication.model.BankTO;
import com.BankingApplication.model.BranchRequest;
import com.BankingApplication.repository.BankRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class BankServiceImplTest {

    @Mock
    private BankRepository bankRepository;

    @InjectMocks
    private BankServiceImpl bankService;

    @Test // Test for returning input of getAllBanks
    public void testGetAllBanks() throws BankDetailsNotFound {

        List<Bank> banks= new ArrayList<>();
        Bank bank= new Bank();
        bank.setBankName("HDFC");
        bank.setBankAddress("SitaBuldi");
        banks.add(bank);

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankTO> bankTOS= bankService.getAllBanks();
        assertEquals(1,bankTOS.size());
    }

    @Test(expected = BankDetailsNotFound.class) // Test for checking null for null
    public void testGetAllBanksEmptyBank() throws BankDetailsNotFound {

        List<Bank> banks= null;

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankTO> bankTOS= bankService.getAllBanks();
        assertNull(bankTOS);
    }
    @Test // TO cover the rest of impl method of getAllBanks
    public void testGetAllBanksWithBranch() throws BankDetailsNotFound {

        List<Bank> banks= new ArrayList<>();
        Bank bank= new Bank();
        bank.setBankName("HDFC");
        bank.setBankAddress("SitaBuldi");
        banks.add(bank);

        Set<Branch> branchList= new HashSet<>();
        Branch branch= new Branch();
        branch.setBranchId(1);
        branch.setBranchName("Gittikhadan");
        branch.setBranchAddress("Gittikhadan, Nagpur");
        branchList.add(branch);
        bank.setBranchSet(branchList);

        when(bankRepository.findAll()).thenReturn(banks);

        List<BankTO> bankTOS= bankService.getAllBanks();
        assertEquals(1,bankTOS.size());
    }

    @Test // Test for returning input of getBankById
    public void testGetBankByCode() throws BankDetailsNotFound {

        Bank bank= new Bank();
        bank.setBankCode(34);
        bank.setBankName("HDFC");
        bank.setBankAddress("SitaBuldi");
        BankTO bank1= new BankTO();
        bank1.setBankName(bank.getBankName());
        bank1.setBankAddress(bank.getBankAddress());

        when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

        BankTO bankTOS= bankService.getBankByCode(34);
        assertEquals(bank1.getBankName(),bankTOS.getBankName());

    }

    @Test
    public void testGetBankByCodeWithBranch() throws BankDetailsNotFound {
        Bank bank= new Bank();
        bank.setBankCode(34);
        bank.setBankName("HDFC");
        bank.setBankAddress("SitaBuldi");
        BankTO bank1= new BankTO();
        bank1.setBankName(bank.getBankName());
        bank1.setBankAddress(bank.getBankAddress());

        Set<Branch> branchList= new HashSet<>();
        Branch branch= new Branch();
        branch.setBranchId(1);
        branch.setBranchName("Gittikhadan");
        branch.setBranchAddress("Gittikhadan, Nagpur");
        branchList.add(branch);
        bank.setBranchSet(branchList);

        when(bankRepository.findById(anyInt())).thenReturn(Optional.of(bank));

        BankTO bankTOS= bankService.getBankByCode(34);
        assertEquals(bank1.getBankName(),bankTOS.getBankName());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void testGetBankByCodeNullCheck() throws BankDetailsNotFound{

        Bank banks= null;

        when(bankRepository.findById(anyInt())).thenReturn(Optional.ofNullable(banks));

        BankTO bankTOS= bankService.getBankByCode(8);
        assertNull(bankTOS);
    }

    @Test
    public void testGetBankByName() throws BankDetailsNotFound {
        Bank bank= new Bank();
        bank.setBankCode(34);
        bank.setBankName("HDFC");
        bank.setBankAddress("SitaBuldi");
        BankTO bank1= new BankTO();
        bank1.setBankName(bank.getBankName());
        bank1.setBankAddress(bank.getBankAddress());

        when(bankRepository.findByBankName(anyString())).thenReturn(Optional.of(bank));

        BankTO bankTOS= bankService.getBankByName("HDFC");
        assertEquals(bank1.getBankName(),bankTOS.getBankName());
    }

    @Test
    public void testGetBankByNameWithBranch() throws BankDetailsNotFound {
        Bank bank= new Bank();
        bank.setBankCode(34);
        bank.setBankName("HDFC");
        bank.setBankAddress("SitaBuldi");
        BankTO bank1= new BankTO();
        bank1.setBankName(bank.getBankName());
        bank1.setBankAddress(bank.getBankAddress());

        Set<Branch> branchList= new HashSet<>();
        Branch branch= new Branch();
        branch.setBranchId(1);
        branch.setBranchName("Gittikhadan");
        branch.setBranchAddress("Gittikhadan, Nagpur");
        branchList.add(branch);
        bank.setBranchSet(branchList);

        when(bankRepository.findByBankName(anyString())).thenReturn(Optional.of(bank));

        BankTO bankTOS= bankService.getBankByName("HDFC");
        assertEquals(bank1.getBankName(),bankTOS.getBankName());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void testGetBankByNameNullCheck() throws BankDetailsNotFound{

        Bank banks= null;

        when(bankRepository.findByBankName(anyString())).thenReturn(Optional.ofNullable(banks));

        BankTO bankTOS= bankService.getBankByName("");
        assertNull(bankTOS);
    }

    @Test
    public void testSaveBank() throws BankDetailsNotFound {

        Bank bank= new Bank();
        bank.setBankName("IndusInd");
        bank.setBankAddress("Gorewada");

        Set<Branch> branchList= new HashSet<>();
        Branch branch= new Branch();
        branch.setBranchId(1);
        branch.setBranchName("Gittikhadan");
        branch.setBranchAddress("Gittikhadan, Nagpur");
        branchList.add(branch);
        bank.setBranchSet(branchList);

        BankRequest bankRequest= new BankRequest();
        bankRequest.setBankName("IndusInd");
        bankRequest.setBankAddress("Gorewada");

        Set<BranchRequest> branchRequests= new HashSet<>();
        BranchRequest branchRequest= new BranchRequest();
        branchRequest.setBranchName("Gittikhadan");
        branchRequest.setBranchAddress("Gittikhadan, Nagpur");
        branchRequests.add(branchRequest);
        bankRequest.setBranchRequests(branchRequests);

        when(bankRepository.save(any())).thenReturn(bank);
        BankTO bankTO= bankService.saveBank(bankRequest);
        assertEquals("IndusInd",bankTO.getBankName());
    }

    @Test
    public void testUpdateBank() throws BankDetailsNotFound{
        Bank bank= new Bank();
        bank.setBankCode(2);
        bank.setBankName("YesBank");
        bank.setBankAddress("Delhi");

        Set<Branch> branchList= new HashSet<>();
        Branch branch= new Branch();
        branch.setBranchId(1);
        branch.setBranchName("Gittikhadan");
        branch.setBranchAddress("Gittikhadan, Nagpur");
        branchList.add(branch);
        bank.setBranchSet(branchList);

        BankRequest bankRequest= new BankRequest();
        bankRequest.setBankCode(2);
        bankRequest.setBankName("YesBank");
        bankRequest.setBankAddress("Delhi");

        Set<BranchRequest> branchRequests= new HashSet<>();
        BranchRequest branchRequest= new BranchRequest();
        branchRequest.setBranchName("Gittikhadan");
        branchRequest.setBranchAddress("Gittikhadan, Nagpur");
        branchRequests.add(branchRequest);
        bankRequest.setBranchRequests(branchRequests);

        when(bankRepository.save(any())).thenReturn(bank);
        BankTO bankTO= bankService.updateBank(bankRequest);
        assertEquals("YesBank",bankTO.getBankName());
    }

    @Test(expected = BankDetailsNotFound.class)
    public void testSaveBankNullCheck() throws BankDetailsNotFound {

    Bank bank= null;
    BankRequest request= new BankRequest();
    request.setBankCode(4);
    request.setBankName("ICICI");
    request.setBankAddress("Aurangabad");

    when(bankRepository.save(any())).thenReturn(bank);
    BankTO bankTO= bankService.saveBank(request);
    assertNull(bankTO);
    }

    @Test(expected = BankDetailsNotFound.class)
    public void testUpdateBankNullCheck() throws BankDetailsNotFound {

        Bank bank= null;
        BankRequest request= new BankRequest();
        request.setBankCode(4);
        request.setBankName("ICICI");
        request.setBankAddress("Aurangabad");

        when(bankRepository.save(any())).thenReturn(bank);
        BankTO bankTO= bankService.updateBank(request);
        assertNull(bankTO);
    }

    @Test
    public void testDeleteBankById() throws BankDetailsNotFound {
        Bank bank= new Bank();
        bank.setBankCode(5);
        bank.setBankName("OP");
        bank.setBankAddress("Pavni");

       bankRepository.deleteById(anyInt());
       bankService.deleteBankByCode(5);
    }

    @Test
    public void testDeleteBankByName() throws BankDetailsNotFound {
        Bank bank= new Bank();
        bank.setBankCode(5);
        bank.setBankName("OP");
        bank.setBankAddress("Pavni");

        bankRepository.deleteByBankName(anyString());
        bankService.deleteBankByName("OP");
    }

    @Test(expected = BankDetailsNotFound.class)
    public void testDeleteBankByCodeNullCheck() throws BankDetailsNotFound {

        Bank bank= null;
        when(bankRepository.findById(anyInt())).thenReturn(Optional.ofNullable(bank));
        bankService.getBankByCode(anyInt());
        bankService.deleteBankByCode(9);

    }

    @Test(expected = BankDetailsNotFound.class)
    public void testDeleteBankByNameNullCheck() throws BankDetailsNotFound {

        Bank bank= null;
        when(bankRepository.findByBankName(anyString())).thenReturn(Optional.ofNullable(bank));
        bankService.getBankByName(anyString());
        bankService.deleteBankByName("");
    }
}
