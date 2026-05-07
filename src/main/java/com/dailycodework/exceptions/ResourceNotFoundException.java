package com.dailycodework.exceptions;

public class ResourceNotFoundException extends RuntimeException{

  public ResourceNotFoundException(String message){
    super(message);
  }
  
}
