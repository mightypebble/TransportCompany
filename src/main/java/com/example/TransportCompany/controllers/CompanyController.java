package com.example.TransportCompany.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.ui.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import com.example.TransportCompany.dtos.ClientRegisterDto;
import com.example.TransportCompany.dtos.CompanyRegisterDto;
import com.example.TransportCompany.dtos.EmployeeRegisterDto;
import com.example.TransportCompany.dtos.VehicleRegisterDto;
import com.example.TransportCompany.entities.*;
import com.example.TransportCompany.repositories.CompanyRepository;
import com.example.TransportCompany.services.ClientService;
import com.example.TransportCompany.services.CompanyService;
import com.example.TransportCompany.services.EmployeeService;
import com.example.TransportCompany.services.VehicleService;

@Controller
public class CompanyController {

    private final CompanyService companyService;
    private final CompanyRepository companyRepository;
    private final ClientService clientService;
    private final VehicleService vehicleService;
    private final EmployeeService employeeService;

    @Autowired
    public CompanyController(CompanyService companyService, CompanyRepository companyRepository,
    ClientService clientService, VehicleService vehicleService, EmployeeService employeeService) {
        this.companyService = companyService;
        this.companyRepository = companyRepository;
        this.clientService = clientService;
        this.vehicleService = vehicleService;
        this.employeeService = employeeService;
    }
    
    @RequestMapping("/")
    public String index(Model model) {
        List<CompanyEntity> companyList = companyRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
        model.addAttribute("companyList", companyList);

        return "index";
    }

    @RequestMapping(value = "/", method = RequestMethod.POST)
    public Object register(Model model, CompanyRegisterDto companyToRegister) {
        List<CompanyEntity> companyList = companyRepository.findAll().stream()
                .collect(Collectors.toUnmodifiableList());
        model.addAttribute("companyList", companyList);
        this.companyService.register(companyToRegister);

        return "redirect:/";
    }

    @RequestMapping(value = "/edit-company/{name}", method = RequestMethod.POST)
    public String updateCompany(CompanyRegisterDto companyRegisterDto, @PathVariable String name) {
        companyService.updateCompany(companyRegisterDto, name);
        return "redirect:/";
    }

    @RequestMapping(value = "/open-company/{name}")
    public String openCompany(Model model, @PathVariable String name) {
        CompanyEntity company = companyService.getCompanyByName(name);
        List<ClientEntity> clientList = clientService.getClients();
        List<ClientEntity> companyClients = new ArrayList<ClientEntity>();
        for (ClientEntity client : clientList) {
            if(client.getCompany().getId() == company.getId()) companyClients.add(client);
        }
        List<VehicleEntity> vehicleList = vehicleService.getVehicles();
        List<VehicleEntity> companyVehicles = new ArrayList<VehicleEntity>();
        for (VehicleEntity vehicle : vehicleList) {
            if(vehicle.getCompany().getId() == company.getId()) companyVehicles.add(vehicle);
        }
        List<EmployeeEntity> employeeList = employeeService.getEmployees();
        List<EmployeeEntity> companyEmployees = new ArrayList<EmployeeEntity>();
        for (EmployeeEntity employee : employeeList) {
            if(employee.getCompany().getId() == company.getId()) companyEmployees.add(employee);
        }
        model.addAttribute("company", company);
        model.addAttribute("companyClients", companyClients);
        model.addAttribute("companyVehicles", companyVehicles);
        model.addAttribute("companyEmployees", companyEmployees);
        return "company";
    }

    @RequestMapping(value = "/view-company-clients/{name}")
    public String openCompanyClients(Model model, @PathVariable String name) {
        CompanyEntity company = companyService.getCompanyByName(name);
        List<ClientEntity> clientList = clientService.getClients();
        List<ClientEntity> companyClients = new ArrayList<ClientEntity>();
        for (ClientEntity client : clientList) {
            if(client.getCompany().getId() == company.getId()) companyClients.add(client);
        }
        model.addAttribute("company", company);
        model.addAttribute("companyClients", companyClients);

        return "clients";
    }

    @RequestMapping(value="/add-client/{name}", method=RequestMethod.POST)
    public String addClient(ClientRegisterDto clientToRegister) {
        this.clientService.register(clientToRegister);

        return "redirect:/open-company/{name}";
    }

    @RequestMapping(value = "/edit-client/{name}/{companyName}", method = RequestMethod.POST)
    public String updateClient(ClientRegisterDto clientRegisterDto, @PathVariable String name) {
        clientService.updateClient(clientRegisterDto, name);
        return "redirect:/view-company-clients/{companyName}";
    }

    @RequestMapping(value = "/delete-client/{name}/{companyName}")
    public String deleteClient(@PathVariable String name) {
        clientService.deleteClient(name);

        return "redirect:/view-company-clients/{companyName}";
    }

    @RequestMapping(value = "/view-company-vehicles/{name}")
    public String openCompanyVehicles(Model model, @PathVariable String name) {
        CompanyEntity company = companyService.getCompanyByName(name);
        List<VehicleEntity> vehicleList = vehicleService.getVehicles();
        List<VehicleEntity> companyVehicles = new ArrayList<VehicleEntity>();
        for (VehicleEntity vehicle : vehicleList) {
            if(vehicle.getCompany().getId() == company.getId()) companyVehicles.add(vehicle);
        }
        model.addAttribute("company", company);
        model.addAttribute("companyVehicles", companyVehicles);

        return "vehicles";
    }

    @RequestMapping(value="/add-vehicle/{name}", method=RequestMethod.POST)
    public String addVehicle(VehicleRegisterDto vehicleToRegister) {
        this.vehicleService.register(vehicleToRegister);

        return "redirect:/open-company/{name}";
    }

    @RequestMapping(value = "/edit-vehicle/{registration}/{companyName}", method = RequestMethod.POST)
    public String updateVehicle(VehicleRegisterDto clientRegisterDto, @PathVariable String registration) {
        vehicleService.updateVehicle(clientRegisterDto, registration);
        return "redirect:/view-company-vehicles/{companyName}";
    }

    @RequestMapping(value = "/delete-vehicle/{registration}/{companyName}")
    public String deleteVehicle(@PathVariable String registration) {
        vehicleService.deleteVehicle(registration);

        return "redirect:/view-company-vehicles/{companyName}";
    }

    @RequestMapping(value = "/view-company-employees/{name}")
    public String openCompanyEmployees(Model model, @PathVariable String name) {
        CompanyEntity company = companyService.getCompanyByName(name);
        List<EmployeeEntity> employeeList = employeeService.getEmployees();
        List<EmployeeEntity> companyEmployees = new ArrayList<EmployeeEntity>();
        for (EmployeeEntity employee : employeeList) {
            if(employee.getCompany().getId() == company.getId()) companyEmployees.add(employee);
        }
        model.addAttribute("company", company);
        model.addAttribute("companyEmployees", companyEmployees);

        return "employees";
    }

    @RequestMapping(value="/add-employee/{name}", method=RequestMethod.POST)
    public String addEmployee(EmployeeRegisterDto employeeToRegister) {
        this.employeeService.register(employeeToRegister);

        return "redirect:/open-company/{name}";
    }

    @RequestMapping(value = "/edit-employee/{name}/{companyName}", method = RequestMethod.POST)
    public String updateEmployee(EmployeeRegisterDto employeeRegisterDto, @PathVariable String name) {
        employeeService.updateEmployee(employeeRegisterDto, name);
        return "redirect:/view-company-employees/{companyName}";
    }

    @RequestMapping(value = "/delete-employee/{name}/{companyName}")
    public String deleteEmployee(@PathVariable String name) {
        employeeService.deleteEmployee(name);

        return "redirect:/view-company-employees/{companyName}";
    }
}
