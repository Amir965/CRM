package com.crm.service;

import com.crm.entity.Employee;
import com.crm.exception.ResourceNotFound;
import com.crm.payload.EmployeeDto;
import com.crm.repository.EmployeeRepository;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class EmployeeService {
    private final EmployeeRepository employeeRepository;
    private final ModelMapper modelMapper;
    public EmployeeService(EmployeeRepository employeeRepository, ModelMapper modelMapper) {
        this.employeeRepository = employeeRepository;
        this.modelMapper = modelMapper;
    }

//    public Employee addEmployee(Employee employee) {
//        return employeeRepository.save(employee);
//    }

    public EmployeeDto addEmployee(EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        Employee emp=employeeRepository.save(employee);
        return mapToDto(emp);
    }
    public void deleteEmployee(long id) {
        employeeRepository.deleteById(id);
    }


//    public void updateEmployee(long id, EmployeeDto dto) {
//        Optional<Employee> opEmp = employeeRepository.findById(id);
//        Employee employee = opEmp.get();
//        employee.setName(dto.getName());
//        employee.setEmailId(dto.getEmailId());
//        employee.setMobile(dto.getMobile());
//        employeeRepository.save(employee);
//
//    }

    //using dto
    public EmployeeDto updateEmployee(long id, EmployeeDto dto) {
        Employee employee = mapToEntity(dto);
        employee.setId(id);
        Employee updateEmployees=employeeRepository.save(employee);
        return mapToDto(updateEmployees);

    }

//    public List<Employee> getEmployees() {
//        return employeeRepository.findAll();
//    }

    public List<EmployeeDto> getEmployees(int pageNo, int pageSize, String sortBy, String sortDir) {
        Sort sort=sortDir.equalsIgnoreCase("asc")?Sort.by(sortBy).ascending():Sort.by(sortBy).descending();
        Pageable page=PageRequest.of(pageNo, pageSize, sort);
        Page<Employee> all = employeeRepository.findAll(page);
        List<Employee> employees = all.getContent();
        return employees.stream().map(this::mapToDto).collect(Collectors.toList());
    }

    //convert entity to dto
EmployeeDto mapToDto(Employee employee){
    //      EmployeeDto dto = new EmployeeDto();
//      dto.setId(employee.getId());
//      dto.setName(employee.getName());
//      dto.setEmailId(employee.getEmailId());
//      dto.setMobile(employee.getMobile());
      return modelMapper.map(employee, EmployeeDto.class);
}

// convert dto to entity
    Employee mapToEntity(EmployeeDto dto){
        //        Employee emp=new Employee();
//        emp.setId(dto.getId());
//        emp.setName(dto.getName());
//        emp.setEmailId(dto.getEmailId());
//        emp.setMobile(dto.getMobile());
        return modelMapper.map(dto, Employee.class);
    }

    public EmployeeDto getEmployeeById(long empId) {
       Employee employee = employeeRepository.findById(empId).orElseThrow(
            ()->new ResourceNotFound("Record not found with id " + empId)
    );
        return mapToDto(employee);
}
}
