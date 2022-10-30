package me.ketlas.openlab.billingservice.exceptions;

public class CustomerNotFoundException extends RuntimeException{

    public CustomerNotFoundException(String id){
        super(String.format("Customer with \"%s\" not found!",id));
    }
}
