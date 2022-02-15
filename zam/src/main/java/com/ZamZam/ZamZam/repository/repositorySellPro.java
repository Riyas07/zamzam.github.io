/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.repository;

import com.ZamZam.ZamZam.model.uploadProducts;
import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;

/**
 *
 * @author ELCOT
 */
public interface repositorySellPro extends MongoRepository<uploadProducts, Integer>{
    public List findByProductnameContainingIgnoreCase(String productname);
    public List findByCompanynameContainingIgnoreCase(String companyname);
}
