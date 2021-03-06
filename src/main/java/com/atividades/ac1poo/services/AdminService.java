package com.atividades.ac1poo.services;

import org.springframework.web.server.ResponseStatusException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.beans.factory.annotation.Autowired;
import javax.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import com.atividades.ac1poo.repositories.*;
import org.springframework.http.HttpStatus;
import com.atividades.ac1poo.entities.*;
import com.atividades.ac1poo.dtos.*;

import java.util.Optional;

@Service
public class AdminService{      
   
    @Autowired
    private AdminRepository adminRepository;

    public Page<AdminDTO> getAdmin(PageRequest pageRequest, String name, String emailContact) {
         
        Page<Admin> list = adminRepository.find(pageRequest, name, emailContact);
        return list.map( e -> new AdminDTO(e));  
    }


    public AdminDTO getAdminById(Long id) {
        Optional<Admin> op = adminRepository.findById(id);
        Admin admin = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found"));
        return new AdminDTO(admin);
    } 

    public AdminDTO insert(AdminInsertDTO insertDTO){
        Admin entity = new Admin(insertDTO);
        adminRepository.save(entity);
        return new AdminDTO(entity);
    }

    public AdminDTO update(Long id, AdminUpdateDTO adminDTO) {
        try{
            Admin entity = adminRepository.getOne(id);
            if(adminDTO.getPhoneNumber() != null)       
                entity.setPhoneNumber(adminDTO.getPhoneNumber());
            if(!adminDTO.getName().isEmpty())       
                entity.setName(adminDTO.getName());
                
            return new AdminDTO(entity);
            
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR"); 
        }
    }
    
    public void delete(Long id) {
        try{
            Admin admin = catchAdminById(id);
            adminRepository.delete(admin);
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }  catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        }
    }
    private Admin catchAdminById(Long id){
        try{
            Admin adm = adminRepository.getOne(id);
            return adm;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Admin not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR"); 
        }
    }
}