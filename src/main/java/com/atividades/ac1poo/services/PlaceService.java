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
public class PlaceService{      
   
    @Autowired
    private PlaceRepository placeRepository;

    public Page<PlaceDTO> getPlace(PageRequest pageRequest, String name, String address) {
         
        Page<Place> list = placeRepository.find(pageRequest, name, address);
        return list.map( e -> new PlaceDTO(e));  
    }

    public PlaceDTO getPlaceById(Long id) {
        Optional<Place> op = placeRepository.findById(id);
        Place Place = op.orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found anywhere. It may never have been scheduled"));
        return new PlaceDTO(Place);
    }

    public PlaceDTO insert(PlaceInsertDTO insertDTO){
        Place entity = new Place(insertDTO);
        placeRepository.save(entity);
        return new PlaceDTO(entity);
    }
    
    public PlaceDTO update(Long id, PlaceUpdateDTO placeDTO) {
        try{
            Place entity = catchPlaceById(id);
            if(!placeDTO.getAddress().isEmpty())       
                entity.setAddress(placeDTO.getAddress());
            if(!placeDTO.getName().isEmpty())       
                entity.setName(placeDTO.getName());
            return new PlaceDTO(entity);
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR"); 
        }
    }

    public void delete(Long id) {
        try{
            Place place = catchPlaceById(id);
            if((place.getEvents()).isEmpty()){
                placeRepository.delete(place);
            }
            else{
                throw new ResponseStatusException(HttpStatus.FORBIDDEN, "There has been already events scheduled in this Location!");
            }
        }  catch (EmptyResultDataAccessException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "ERROR");
        }
    }
    
    private Place catchPlaceById(Long id){
        try{
            Place place = placeRepository.getOne(id);
            return place;
        } catch (EntityNotFoundException e) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Place not found");
        } catch (Exception e) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "ERROR"); 
        }
    }
}