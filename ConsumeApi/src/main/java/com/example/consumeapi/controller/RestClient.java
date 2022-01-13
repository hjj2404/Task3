package com.example.consumeapi.controller;

import com.example.consumeapi.entity.Employee;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.*;
import org.springframework.web.client.RestTemplate;

import javax.persistence.criteria.CriteriaBuilder;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;


public class RestClient {

    private static final String GET_ALL_Emps_API= "http://localhost:8080/api/emp";
    private static final String GET_ALL_ById_API= "http://localhost:8080/api/emp/{id}";
    private static final String Create_API= "http://localhost:8080/api/emp";
    private static final String Update_API= "http://localhost:8080/api/emp/{id}";
    private static final String Delete_API= "http://localhost:8080/api/emp/{id}";

    static RestTemplate restTemplate=new RestTemplate();

    public static void main (String[] args){
        callGetAllEmpsApi();
        callGetEmpById();
        callCreateEmpApi();
        callUpdateEmp();
        DelById();
    }

    private static void callGetAllEmpsApi(){
        HttpHeaders headers=new HttpHeaders();
        headers.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<String> entity=new HttpEntity<>("parameters",headers);
        ResponseEntity<String> result  =restTemplate.exchange(GET_ALL_Emps_API, HttpMethod.GET,entity,String.class);
        System.out.println(result);
    }

    private static void callGetEmpById(){
        Map<String,Integer> param= new HashMap<>();
        param.put("id",1);
        Employee emp = restTemplate.getForObject(GET_ALL_ById_API, Employee.class,param);
        System.out.println(emp.getFirstName());
        System.out.println(emp.getLastName());
        System.out.println(emp.getEmail());
    }

    private static void callCreateEmpApi(){
        Employee emp= new Employee("Ashish","Sail","ash@gmail.com");
       ResponseEntity<Employee> emp2= restTemplate.postForEntity(Create_API,emp,Employee.class);
       System.out.println(emp2.getBody());
    }

    private static void callUpdateEmp(){
        Map<String,Integer> param= new HashMap<>();
        param.put("id",2);
        Employee emp=new Employee("Ash","Sail","ashish@gmail.com");
        restTemplate.put(Update_API,emp,param);
        System.out.println("employee record updated");
    }

    private static void DelById(){
        Map<String,Integer> param= new HashMap<>();
        param.put("id",2);
        restTemplate.delete(Delete_API,param);
        System.out.println("Employee deleted");
    }
}
