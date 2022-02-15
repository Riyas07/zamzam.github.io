/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.service;

import com.ZamZam.ZamZam.Dot.mailResponse;
import com.ZamZam.ZamZam.model.Cart;
import com.ZamZam.ZamZam.model.Orders;
import com.ZamZam.ZamZam.model.createUser;
import com.ZamZam.ZamZam.model.uploadProducts;
import com.ZamZam.ZamZam.repository.repositoryCart;
import com.ZamZam.ZamZam.repository.repositoryCreateUser;
import com.ZamZam.ZamZam.repository.repositoryOrders;
import com.ZamZam.ZamZam.repository.repositorySellPro;
import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author ELCOT
 */
@Service
public class service implements UserDetailsService{
    @Autowired
    repositoryCreateUser rcu;
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
       
      createUser c=  rcu.findByUsername(username);
       System.out.println("---------       second          ---------- "+username+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"+c.getPassword());
      if(c==null)
      {
         throw new UsernameNotFoundException(username+"uuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuuu"+c.getPassword());
      }
      return new org.springframework.security.core.userdetails.User(c.getUsername(), c.getPassword(), new ArrayList<>());
    }
    @Autowired
	private JavaMailSender sender;
	
	@Autowired
	private Configuration config;
	
	public mailResponse sendEmail(String request, Map<String, Object> model) {
		mailResponse response = new mailResponse();
		MimeMessage message = sender.createMimeMessage();
		try {
			// set mediaType
			MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
					StandardCharsets.UTF_8.name());
			// add attachment
			Template t = config.getTemplate("email-template.ftl");
			String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
                         // System.out.println(t+"   ttttttttttttttttt");
                         // System.out.println(html+"  hhhhhhhhhhhhhhhhhhhhhhhhhhhhh");
			helper.setTo(request);
			helper.setText(html, true);
			helper.setSubject("ZamZam OTP verify");
			helper.setFrom("muhammedriyas6262@gmail.com");
			sender.send(message);
                       //System.out.println(helper+" $$$$$$$$$$$$$$$$$$$$$$$$$$     helper       $$$$$$$$$$$$$$$$$$$$$$$$$$");
                        //System.out.println(message+"  ################################       message      #################################");
			response.setMessage("mail send to : " + request);
			response.setStatus(Boolean.TRUE);

		} catch (MessagingException | IOException | TemplateException e) {
			response.setMessage("Mail Sending failure : "+e.getMessage());
			response.setStatus(Boolean.FALSE);
		}

		return response;
	}
        private static final Integer EXPIRE_MINS = 20;
    private LoadingCache<String, Integer> otpCache;
    public service()
    {
      super();
      otpCache = CacheBuilder.newBuilder().
      expireAfterWrite(EXPIRE_MINS, TimeUnit.MINUTES)
      .build(new CacheLoader<String, Integer>() {
      @Override
      public Integer load(String key) {
             return 0;
       }
   });
 }
String key;
public int generateOTP(String key){
    this.key=key;
Random random = new Random();
int otp = 10000 + random.nextInt(1759);
otpCache.put(key, otp);
return otp;
 }
 
 public int getOtp(String key){ 
try{
 return otpCache.get(key); 
}catch (ExecutionException e){
 return 0; 
}
 }

public void clearOTP(String key){ 
 otpCache.invalidate(key);
 }
	public boolean valid(int otp) 
        {
            try
            {
                System.out.println("key valid ==== "+key);
                int ot= otpCache.get(key);
                if(ot==otp)
                {
                    return true;
                }
            }
        catch(ExecutionException e)
        {
            System.out.println("vvvvvvvvvvvvvvvvvvvvvvvvvvvvvvalid "+e);
        }
            return false;
        }
        public int ran()
        {
            Random ra= new Random(1759);
           int i= 100+ra.nextInt();
           return i;
        }
        @Autowired
        repositorySellPro rsp;
        public List<uploadProducts>filter(int price,String sf)
        {
            List<uploadProducts> all=rsp.findAll();
            List <uploadProducts>f1=all.stream().filter(a->a.getProductname().equalsIgnoreCase(sf)).collect(Collectors.toList());
            List<uploadProducts>f2=f1.stream().filter(b->b.getPrice()<price).collect(Collectors.toList());
            return f2;
        }
        @Autowired
        repositoryCart cart;
        public void delet(int id)
        {
            cart.deleteByProid(id);
        }
       public List<Cart> cart(String proname)
       {
          return cart.findByUsername(proname);
       }
       public void addCart(int id,String username)
       {
           Cart c=new Cart();
           uploadProducts up=rsp.findById(id).orElse(new uploadProducts());
           c.setCompanyname(up.getCompanyname());
           c.setImage(up.getImage());
           c.setPrice(up.getPrice());
           c.setProductname(up.getProductname());
           c.setProductsize(up.getProductsize());
           c.setProid(up.getId());
           c.setSpecification(up.getSpecification());
          c.setUsername(username);
          cart.insert(c);
       }
        @Autowired 
       repositoryOrders rod;
       public List<Orders> orders(String username)
       {
           List<Orders> lo=rod.findByCustomerId(username);
           return lo;
       }
       public uploadProducts proview(int id)
       {
          uploadProducts up= rsp.findById(id).orElse(new uploadProducts());
          return up;
       }
       public List<uploadProducts> products()
       {
           List<uploadProducts> up=rsp.findAll();
           return up;
       }
       public void sellpro(int id,String productname,double price,String productsize,String companyname,String specification,MultipartFile image) throws IOException
       {
           uploadProducts u=new uploadProducts();
           u.setProductname(productname);
           u.setId(id);
           u.setPrice(price);
           u.setCompanyname(companyname);
           u.setProductsize(productsize);
           u.setSpecification(specification);
           u.setImage(Base64.getEncoder().encodeToString(image.getBytes()));
           rsp.insert(u);
       }
       public void createuser(String username,String email,String password,String address,String pincode)
       {
           createUser cu=new createUser();
           cu.setUsername(username);
           cu.setEmail(email);
           cu.setPassword(password);
           cu.setAddress(address);
           cu.setPincode(pincode);
           rcu.insert(cu);
       }
}
