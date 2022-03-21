package com.example.demoApp.service;

import com.example.demoApp.dto.EmployeeDTO;
import com.example.demoApp.dto.EmployeeSearchCriteria;
import com.example.demoApp.entity.Employee;
import com.example.demoApp.repository.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.BeanPropertySqlParameterSource;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.core.namedparam.SqlParameterSource;
import org.springframework.stereotype.Service;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import static org.apache.commons.lang3.StringUtils.isNotBlank;

@Service
public class EmployeeService {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbcTemplate;

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
        //dept is mandatory
        StringBuffer sql = new StringBuffer("select id, name, dept from employee where 1=1 ") ;
        List<Object> parameters = new ArrayList<>();
        if(isNotBlank(searchCriteria.getDept()) ){
            sql.append( "and dept = ? ");
            parameters.add(searchCriteria.getDept());
        }
        if(isNotBlank(searchCriteria.getName()) ){
            sql.append( "and name = ? ");
            parameters.add(searchCriteria.getName());
        }
        if(searchCriteria.getId() != null ){
            sql.append( "and id = ?");
            parameters.add(searchCriteria.getId());
        }
        System.out.println("SQL generated for search employee : "+sql.toString());

        try {
            return jdbcTemplate.queryForObject(sql.toString(), new EmployeeRowMapper(),
                    parameters.toArray()
            );
        } catch (EmptyResultDataAccessException e) {
            return new EmployeeDTO();
        }

    }

    public EmployeeDTO find(EmployeeSearchCriteria searchCriteria) {
        //dept is mandatory
        MapSqlParameterSource parameters = new MapSqlParameterSource();
        StringBuffer sql = new StringBuffer("select id, name, dept from employee where 1=1 ") ;
        if(isNotBlank(searchCriteria.getDept()) ){
            sql.append( "and dept = :dept ");
            parameters.addValue("dept", searchCriteria.getDept());
        }
        if(isNotBlank(searchCriteria.getName()) ){
            sql.append( "and name = :name ");
            parameters.addValue("name", searchCriteria.getName());
        }
        if(searchCriteria.getId() != null ){
            sql.append( "and id = :id");
            parameters.addValue("id", searchCriteria.getId());
        }
        System.out.println("SQL generated for search employee : "+sql.toString());

        try {
            return namedParameterJdbcTemplate.queryForObject(sql.toString(), parameters, new EmployeeRowMapper());
/*            return jdbcTemplate.queryForObject(sql.toString(), new EmployeeRowMapper(),
                    parameters.toArray()
            );*/
        } catch (EmptyResultDataAccessException e) {
            return new EmployeeDTO();
        }

    }

    public EmployeeDTO findUsingBean(EmployeeSearchCriteria searchCriteria) {
        //dept is mandatory
        BeanPropertySqlParameterSource parameters = new BeanPropertySqlParameterSource(searchCriteria);
        StringBuffer sql = new StringBuffer("select id, name, dept from employee where 1=1 ") ;
        if(isNotBlank(searchCriteria.getDept()) ){
            sql.append( "and dept = :dept ");
        }
        if(isNotBlank(searchCriteria.getName()) ){
            sql.append( "and name = :name ");
        }
        if(searchCriteria.getId() != null ){
            sql.append( "and id = :id");
        }
        System.out.println("SQL generated for search employee : "+sql.toString());

        try {
            return namedParameterJdbcTemplate.queryForObject(sql.toString(), parameters, new EmployeeRowMapper());
/*            return jdbcTemplate.queryForObject(sql.toString(), new EmployeeRowMapper(),
                    parameters.toArray()
            );*/
        } catch (EmptyResultDataAccessException e) {
            return new EmployeeDTO();
        }

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
