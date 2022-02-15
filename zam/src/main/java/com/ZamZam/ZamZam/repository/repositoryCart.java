/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.repository;

import com.ZamZam.ZamZam.model.Cart;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author ELCOT
 */
public interface repositoryCart extends MongoRepository<Cart, Object>{
   public List<Cart> findByUsername(String username);
   public Cart deleteByProid(int id);
}
