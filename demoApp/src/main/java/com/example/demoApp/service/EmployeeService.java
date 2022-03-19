package com.example.demoApp.service;

import com.example.demoApp.dto.EmployeeDTO;
import com.example.demoApp.dto.EmployeeSearchCriteria;
import com.example.demoApp.entity.Employee;
import com.example.demoApp.repository.EmployeeRepository;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    public EmployeeDTO add(EmployeeDTO employee){
        // some business logic here
        Employee employeeDO = new Employee();
        employeeDO.setDept(employee.getDept());
        employeeDO.setName(employee.getFirstName()+" "+employee.getLastName());

        Employee savedEmployeeDO = employeeRepository.save(employeeDO);

        String[] names = savedEmployeeDO.getName().split(" ");
        String firstName = names[0];
        String lastName = names[1];

        return new EmployeeDTO(firstName, lastName, savedEmployeeDO.getDept());
    }

    public EmployeeDTO search(EmployeeSearchCriteria searchCriteria) {
        StringBuffer sql = new StringBuffer("select id, name, dept from employee where ") ;
        if(isNotBlank(searchCriteria.getDept()) ){
            sql.append( "dept = ? ");
        }
        if(isNotBlank(searchCriteria.getName()) ){
            sql.append( "and name = ? ");
        }
        if(searchCriteria.getId() != null ){
            sql.append( "and id = ?");
        }
        System.out.println("SQL generated for search employee : "+sql.toString());

       EmployeeDTO employeeDTO = jdbcTemplate.queryForObject(sql.toString(), new Object[]{searchCriteria.getDept(),
                searchCriteria.getName(), searchCriteria.getId()
        }, new EmployeeRowMapper() );
        return employeeDTO;
    }

}

class EmployeeRowMapper implements RowMapper<EmployeeDTO>{
    @Override
    public EmployeeDTO mapRow(ResultSet rs, int rowNum) throws SQLException {
        EmployeeDTO employeeDTO = new EmployeeDTO();
        employeeDTO.setDept(rs.getString("dept"));
        String[] names = rs.getString("name").split(" ");
        String firstName = names[0];
        String lastName = names[1];
        employeeDTO.setFirstName(firstName);
        employeeDTO.setLastName(lastName);
        return employeeDTO;
    }
}
