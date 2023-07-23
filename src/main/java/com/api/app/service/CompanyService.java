package com.api.app.service;

import com.api.app.model.Company;
import com.api.app.repository.CompanyRepository;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.fileupload.FileUtils;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Base64;
import java.util.List;

@Service
@AllArgsConstructor
public class CompanyService {
  private final CompanyRepository repository;

  public List<Company> getCompanies(){
    return repository.findAll();
  }

  public static void main(String[] args) throws IOException {
    Base64.Encoder encoder = Base64.getEncoder();
    Path path = Paths.get("/home/nyhasina/prog4/app/src/main/resources/templates/logo.jpg");
    String encoded = encoder.encodeToString(Files.readAllBytes(path));
    System.out.println(encoded);
  }
}
