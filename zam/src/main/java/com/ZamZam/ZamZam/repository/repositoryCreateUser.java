/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.repository;

import com.ZamZam.ZamZam.model.createUser;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author ELCOT
 */
public interface repositoryCreateUser extends MongoRepository<createUser,String>{
    public createUser findByUsername(String username);
    public createUser findByEmail(String email);
}
