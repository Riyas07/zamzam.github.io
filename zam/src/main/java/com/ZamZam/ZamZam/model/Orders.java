/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 *
 * @author ELCOT
 */
@Document(collection = "orders")
public class Orders {
    uploadProducts products;
    String customerId;

    public uploadProducts getProducts() {
        return products;
    }

    public void setProducts(uploadProducts products) {
        this.products = products;
    }

    public String getCustomerId() {
        return customerId;
    }

    public void setCustomerId(String customerId) {
        this.customerId = customerId;
    }

    

    @Override
    public String toString() {
        return "Orders{" + "products=" + products + ", CustomerId=" + customerId + '}';
    }

   
    
}
