package com.atividades.ac1poo.dtos;

public class AttendInsertDTO {
    
    /**
     * ------------
     * @Declaration
     * ------------
     **/    
    private Long id;
    private String name;
    private String email;
    private Double balance;

    /**
     * @Getters_and_Setters
     */
    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public Double getBalance() {
        return balance;
    }
    public void setBalance(Double balance) {
        this.balance = balance;
    }

    
}