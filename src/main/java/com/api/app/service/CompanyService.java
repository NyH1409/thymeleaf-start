package com.api.app.service;

import com.api.app.model.Company;
import com.api.app.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {
  private final CompanyRepository repository;

  public List<Company> getCompanies(){
    return repository.findAll();
  }

}
