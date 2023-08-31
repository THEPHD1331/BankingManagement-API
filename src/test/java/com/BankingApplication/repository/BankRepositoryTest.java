package com.BankingApplication.repository;

import com.BankingApplication.entity.Bank;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.junit4.SpringRunner;
import java.util.List;
import static org.junit.Assert.assertEquals;

@RunWith(SpringRunner.class)
@DataJpaTest
public class BankRepositoryTest {

    @Autowired
    BankRepository bankRepository;

    @Test
    public void testFindAll(){
        Bank bank= new Bank();
        bank.setBankName("SBI");
        bank.setBankAddress("Mumbai");
        bankRepository.save(bank);

        List<Bank> bankList= bankRepository.findAll();
        assertEquals(1, bankList.size());
    }
    @Test
    public void testSave(){
        Bank bank= new Bank();
        bank.setBankName("SBI");
        bank.setBankAddress("Mumbai");
        bankRepository.save(bank);

       Bank bankSave= bankRepository.save(bank);
        assertEquals(bank, bankSave);
    }

    @Test
    public void testUpdate(){
        Bank bank= new Bank();
        bank.setBankCode(1);
        bank.setBankName("QPI");
        bank.setBankAddress("Punjab");
        bankRepository.save(bank);

        Bank bankSave= bankRepository.save(bank);
        assertEquals(bank, bankSave);
    }
}
