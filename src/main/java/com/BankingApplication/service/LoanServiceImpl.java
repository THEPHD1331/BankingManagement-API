package com.BankingApplication.service;

import com.BankingApplication.entity.Branch;
import com.BankingApplication.entity.Customer;
import com.BankingApplication.entity.Loan;
import com.BankingApplication.exception.LoanDetailsNotFound;
import com.BankingApplication.model.LoanRequest;
import com.BankingApplication.model.LoanTO;
import com.BankingApplication.repository.LoanRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@Slf4j
public class LoanServiceImpl implements LoanService{

    @Autowired
    LoanRepository loanRepo;

    @Override
    public List<LoanTO> getAllLoans() throws LoanDetailsNotFound {

        List<Loan> loans= loanRepo.findAll();

        if(CollectionUtils.isEmpty(loans)){
            log.info("Loan Not Found");
            throw new LoanDetailsNotFound("Loan details not found");
        }

        List<LoanTO> loanTOS= loans.stream().map(loan -> {

            LoanTO loanTO= new LoanTO();
            loanTO.setLoanId(loan.getLoanId());
            loanTO.setLoanAmt(loan.getLoanAmount());
            loanTO.setLoanType(loan.getLoanType());

            return loanTO;
        }).collect(Collectors.toList());
        return loanTOS;
    }

    @Override
    public LoanTO getLoanById(int loanId) throws LoanDetailsNotFound {
        log.info("Inside get Loan by Loan Id method, loanId:{}", loanId);

       Optional<Loan> loan= loanRepo.findById(loanId);

        if(!loan.isPresent()){
            log.info("Loan details not found");
            throw new LoanDetailsNotFound("Loan details not found!");
        }

        Loan loans= loan.get();
        LoanTO loanTO= new LoanTO();
        loanTO.setLoanId(loans.getLoanId());
        loanTO.setLoanType(loans.getLoanType());
        loanTO.setLoanAmt(loans.getLoanAmount());

        return loanTO;
    }

    @Override
    public LoanTO getLoanByType(String loanType) throws LoanDetailsNotFound {
        log.info("Inside get Loan by Loan type method, loanType:{}", loanType);

        Optional<Loan> loan= loanRepo.findByLoanType(loanType);

        if(!loan.isPresent()){
            log.info("Loan details not found");
            throw new LoanDetailsNotFound("Loan details not found!");
        }

        Loan loans= loan.get();
        LoanTO loanTO= new LoanTO();
        loanTO.setLoanId(loans.getLoanId());
        loanTO.setLoanType(loans.getLoanType());
        loanTO.setLoanAmt(loans.getLoanAmount());

        return loanTO;
    }

    @Override
    public LoanTO saveLoan(LoanRequest loanRequest) throws LoanDetailsNotFound {

        Loan loan= new Loan();
        loan.setLoanId(loanRequest.getLoanId());
        loan.setLoanType(loanRequest.getLoanType());
        loan.setLoanAmount(loanRequest.getLonaAmt());
        Branch branch= new Branch();
        branch.setBranchId(loanRequest.getBranchId());
        loan.setBranchId(branch);
        Customer customer= new Customer();
        customer.setCustId(loanRequest.getCustomerId());
        loan.setCustomerz(customer);

        Loan loanResp= loanRepo.save(loan);
        if(loanResp==null){
            log.info("Loan is not saved");
            throw new LoanDetailsNotFound("Loan not saved!");
        }
        LoanTO loanTO= new LoanTO();
        loanTO.setLoanId(loanResp.getLoanId());
        loanTO.setLoanType(loanResp.getLoanType());
        loanTO.setLoanAmt(loanResp.getLoanAmount());

        return loanTO;
    }

    @Override
    public LoanTO updateLoan(LoanRequest loanRequest) throws LoanDetailsNotFound {
        Loan loan= new Loan();
        loan.setLoanId(loanRequest.getLoanId());
        loan.setLoanType(loanRequest.getLoanType());
        loan.setLoanAmount(loanRequest.getLonaAmt());
        Branch branch= new Branch();
        branch.setBranchId(loanRequest.getBranchId());
        loan.setBranchId(branch);
        Customer customer= new Customer();
        customer.setCustId(loanRequest.getCustomerId());
        loan.setCustomerz(customer);

        Loan loanResp= loanRepo.save(loan);
        if(loanResp==null){
            log.info("Loan is not saved");
            throw new LoanDetailsNotFound("Loan not saved!");
        }
        LoanTO loanTO= new LoanTO();
        loanTO.setLoanId(loanResp.getLoanId());
        loanTO.setLoanType(loanResp.getLoanType());
        loanTO.setLoanAmt(loanResp.getLoanAmount());

        return loanTO;
    }

    @Override
    public String deleteLoanById(int loanId) throws LoanDetailsNotFound {
        Optional<Loan> loanOp= loanRepo.findById(loanId);
        if(loanOp==null){
            log.info("Loan Details does not exist");
            throw new LoanDetailsNotFound("Loan details does not exist");
        }
        loanRepo.deleteById(loanId);
        log.info("Loan is deleted by code:{}", loanId);
        return "Loan is deleted by loan Id: "+ loanId;
    }

    @Transactional
    @Override
    public String deleteLoanByType(String loanType) throws LoanDetailsNotFound {
        Optional<Loan> loanOp= loanRepo.findByLoanType(loanType);
        if(loanOp==null){
            log.info("Loan Details does not exist");
            throw new LoanDetailsNotFound("Loan details does not exist");
        }
        loanRepo.deleteByLoanType(loanType);
        log.info("Loan is deleted by type:{}", loanType);
        return "Loan is deleted by loan type: "+ loanType;
    }
}
