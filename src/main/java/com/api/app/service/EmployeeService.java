package com.api.app.service;

import com.api.app.model.Employee;
import com.api.app.repository.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.supercsv.io.CsvBeanWriter;
import org.supercsv.io.ICsvBeanWriter;
import org.supercsv.prefs.CsvPreference;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class EmployeeService {
    private final EmployeeRepository repository;

    public List<Employee> getEmployees() {
        return repository.findAll();
    }

    public Employee getEmployee(String id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Employee not found"));
    }

    public void crupdateEmployee(Employee employee) {
        repository.save(employee);
    }

    public void generateCSV(List<Employee> employees, HttpServletResponse response) {
        try {
            response.setContentType("text/csv");
            DateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd_HH-mm");
            String currentDateTime = dateFormatter.format(new Date());
            String headerKey = "Content-Disposition";
            String headerValue = "attachment; filename=users_" + currentDateTime;
            response.setHeader(headerKey, headerValue);
            ICsvBeanWriter csvWriter = new CsvBeanWriter(response.getWriter(), CsvPreference.STANDARD_PREFERENCE);
            List<String> heardersAndMapping = Arrays.stream(Employee.class.getDeclaredFields()).map(Field::getName).collect(Collectors.toList());
            String[] stringArray = new String[heardersAndMapping.size()];
            csvWriter.writeHeader(heardersAndMapping.toArray(stringArray));
            for (Employee employee : employees) {
                csvWriter.write(employee, heardersAndMapping.toArray(stringArray));
            }
            csvWriter.close();
        } catch (IOException e) {
            throw new RuntimeException();
        }
    }
}
