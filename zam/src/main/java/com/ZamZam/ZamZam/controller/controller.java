 /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ZamZam.ZamZam.controller;

import java.util.Map.Entry;
import com.ZamZam.ZamZam.Dot.mailResponse;
import com.ZamZam.ZamZam.jwtFilter.JwtFilter;
import com.ZamZam.ZamZam.jwtUtil.JwtUtil;
import com.ZamZam.ZamZam.model.Cart;
import com.ZamZam.ZamZam.model.Orders;
import com.ZamZam.ZamZam.model.PaytmDetailPojo;
import com.ZamZam.ZamZam.model.createUser;
import com.ZamZam.ZamZam.model.uploadProducts;
import com.ZamZam.ZamZam.repository.repositoryCart;
import com.ZamZam.ZamZam.repository.repositoryCreateUser;
import com.ZamZam.ZamZam.repository.repositoryOrders;
import com.ZamZam.ZamZam.repository.repositorySellPro;
import com.ZamZam.ZamZam.service.service;
import com.paytm.pg.merchant.PaytmChecksum;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.TreeMap;
import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails; 
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;
/**
 *
 * @author ELCOT
 */
@Controller
public class controller implements ErrorController{
    @Autowired
    repositoryCart cart;
    @Autowired
    repositoryOrders ros;
    @Autowired
    JwtFilter filter;
   @Autowired
   JwtUtil jwtUtil;
    @Autowired
	private PaytmDetailPojo paytmDetailPojo;
	@Autowired
	private Environment env;
    @Autowired
    repositorySellPro rsp;
    @Autowired
    repositoryCreateUser rcu;
    @Autowired
    service serv;
    @Autowired
            AuthenticationManager authenticationManager;
     PasswordEncoder passwordEncoder;
    @Autowired
    public controller(PasswordEncoder passwordEncoder)
    {
        this.passwordEncoder=passwordEncoder;
    }
    @RequestMapping("/")
    public String home()
    {
        return "home";
    }
    @RequestMapping("/login")
    public String login()
    {
        return "login";
    }
    String cid;
    private String tkn;
    @RequestMapping("/authenticate")
    public String authenticate(@RequestParam("username")String username,@RequestParam("email")String email,@RequestParam("password")String password ,Model model)throws Exception
    {
        System.out.println("entered.............. authentication  .......");
        this.cid=username;
        try
        {
            Authentication a=authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            System.out.println("22222222222222222222222   "+a);
        }
        catch(AuthenticationException e)
        {
            throw new Exception("usernamePasswordAuthentication maager error ===================");
        }
        String br="Bearer ";
         this.tkn=jwtUtil.generateToken(username);
     String token= br.concat(tkn) ;
     filter.sams(token);
        System.out.println(username+"");
     model.addAttribute("ids", username);
        return "home";
    }
     @RequestMapping("/cart/authenticate")
    public String authenticate2(@RequestParam("username")String username,@RequestParam("email")String email,@RequestParam("password")String password)throws Exception
    {
         System.out.println("0000000000000000000000    username22222       0000000000000000000000000000000000000  =====   "+username);
        this.cid=username;
        try
        {
          Authentication auth=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            System.out.println("------------   auth  =======   "+auth);
        }
        catch(AuthenticationException e)
        {
            throw new Exception("usernamePasswordAuthentication maager error ===================");
        }
        String br="Bearer ";
         this.tkn=jwtUtil.generateToken(username);
     String token= br.concat(tkn) ;
     filter.sams(token);
        return "home";
    }
      @RequestMapping("/buy/authenticate")
    public String authenticate3(@RequestParam("username")String username,@RequestParam("email")String email,@RequestParam("password")String password)throws Exception
    {
        System.out.println("0000000000000000000000    username       0000000000000000000000000000000000000  =====   "+username);
        this.cid=username;
        try
        {
          Authentication auth=  authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username,password));
            System.out.println("------------   auth  =======   "+auth);
        }
        catch(AuthenticationException e)
        {
            throw new Exception("usernamePasswordAuthentication maager error ===================");
        }
        String br="Bearer ";
         this.tkn=jwtUtil.generateToken(username);
     String token= br.concat(tkn) ;
     filter.sams(token);
        return "home";
    }
           @RequestMapping("/cart/cart")
        public String viewcart2(Model model)
        {
            String un=this.cid;
          List<Cart> cl= cart.findByUsername(un);
          model.addAttribute("cl", cl);
            return "cart";
        }
        @RequestMapping("/signout")
        public String logout()
        {
            filter.sam("");
            
            return "home";
        }
    @RequestMapping("/createAccount")
    public String create()
    {
        return "createAccount";
    }
     @RequestMapping("/uploadProducts")
   
    public String upload(Model model)
    { 
       String request="riyasmuhamed182@gmail.coom";
       int r=serv.generateOTP(request);
        System.out.println("random=== "+r);
        model.addAttribute("ran", r);
        return "uploadProducts";
    }
    private String username;
    private String email;
    private String password;
    private String address;
    private String pincode;
    @PostMapping("/createuser")
    public String createuser(@RequestParam("username")String username,@RequestParam("email")String email,@RequestParam("password")String password,@RequestParam("address")String address,@RequestParam("pincode")String pincode)
    {
      createUser c= rcu.findByUsername(username);
      createUser cc=rcu.findByEmail(email);
        System.out.println("cccccccccccccccccccccccccccccccccccccc   "+cc);
      if(c!=null)
      {
          return "Eusername";
      }
      else if(cc!=null)
      {
          return "Eemail";
      }
      this.username=username;
        this.email=email;
        this.password=password;
        this.address=address;
        this.pincode=pincode;
        sendMail(email);
        return "otp";    
    }
    
      @RequestMapping("/contact")
    public String contact()
    {
        return "contact";
    }
      @PostMapping("/otps")
        public String optValid(@RequestParam("otp")int otp)
        {
            System.out.println("ooooooooooooooooooooooooooooooo    "+otp);
           boolean o= serv.valid(otp);
           if(o==true)
           {
               System.out.println("--------------------------------------");
               createUser u=new createUser();
               UserDetails us= User.builder().username(this.username).password(passwordEncoder.encode(this.password)).roles("USER").build();
      serv.createuser(us.getUsername(),us.getPassword(),this.email,this.address,this.pincode);
               return "home";
           }
           return "Faild";
        }
         public mailResponse sendMail(String request)
    {
          Map<String, Object> model = new HashMap<>();
         int otp=serv.generateOTP(request);
		model.put("Name", "riyas");
		model.put("location", "Bangalore,India");
                model.put("otp", otp);
		return serv.sendEmail(request, model);
    }
         @PostMapping("/uploads")
         public String sellproducts( @RequestParam("id")int id,@RequestParam("productname")String productname,@RequestParam("price")double price,@RequestParam("productsize")String productsize,@RequestParam("companyname")String companyname,@RequestParam("specification")String specification,@RequestParam("image")MultipartFile image) throws IOException
         {
             serv.sellpro(id,productname,price,productsize,companyname,specification,image);
             return "home";
         }
         @RequestMapping("/products")
         public String products(Model model)
         {
             List<uploadProducts> prodetails= serv.products();
             model.addAttribute("prodetails", prodetails);
             return "products";
         }
         @GetMapping("/proview/{id}")
         public String viewpro(@PathVariable("id")int id,Model model)
         {
             
           uploadProducts ups= serv.proview(id);
             model.addAttribute("select", ups);
             return "view";
         }
         public List<uploadProducts>filt;
         String sf;
         @RequestMapping("/search")
         public String search(@RequestParam("search")String search,Model model)
         {
           List<uploadProducts> lu=rsp.findByProductnameContainingIgnoreCase(search);
           this.filt=lu;
           if(!lu.isEmpty())
           {
               this.sf=search;
            model.addAttribute("search", lu);
           }
           if(lu.isEmpty())
           {
               List<uploadProducts>lu2=rsp.findByCompanynameContainingIgnoreCase(search);
               model.addAttribute("search", lu2);
           }
             return "search";
         }
         int proid;
         @RequestMapping("/buy/{id}")
         public String buy(@PathVariable int id,Model model)
         {
             this.proid=id;
             return "buy";
         }
         String mailid;
       @PostMapping(value = "/submitPaymentDetail")
	    public ModelAndView getRedirect(@RequestParam(name = "CUST_ID") String customerId,
	                                    @RequestParam(name = "TXN_AMOUNT") String transactionAmount,
	                                    @RequestParam(name = "ORDER_ID") String orderId) throws Exception {
                     
	        ModelAndView modelAndView = new ModelAndView("redirect:" + paytmDetailPojo.getPaytmUrl());
	          TreeMap<String, String> parameters = new TreeMap<>();
	        paytmDetailPojo.getDetails().forEach((k, v) -> parameters.put(k, v));
	        parameters.put("MOBILE_NO", env.getProperty("paytm.mobile"));
	        parameters.put("EMAIL", env.getProperty("paytm.email"));
	        parameters.put("ORDER_ID", orderId);
	        parameters.put("TXN_AMOUNT", transactionAmount);
	        parameters.put("CUST_ID", customerId);
	        String checkSum = getCheckSum(parameters);
	        parameters.put("CHECKSUMHASH", checkSum);
	        modelAndView.addAllObjects(parameters);
                mailid=customerId;
                System.out.println("mail and customrt = "+mailid+"   "+customerId);
	        return modelAndView;
	    }
	 
	 
	 @PostMapping(value = "/pgresponse")
	    public String getResponseRedirect(HttpServletRequest request, Model model) {

                System.out.println("finally =============  "+mailid);
	        Map<String, String[]> mapData = request.getParameterMap();
	        TreeMap<String, String> parameters = new TreeMap<String, String>();
	        String paytmChecksum = "";
	        for (Entry<String, String[]> requestParamsEntry : mapData.entrySet()) {
	            if ("CHECKSUMHASH".equalsIgnoreCase(requestParamsEntry.getKey())){
	                paytmChecksum = requestParamsEntry.getValue()[0];
	            } else {
	            	parameters.put(requestParamsEntry.getKey(), requestParamsEntry.getValue()[0]);
	            }
	        }
	        String result;

	        boolean isValideChecksum = false;
	        System.out.println("RESULT : "+parameters.toString());
                
	        try {
	            isValideChecksum = validateCheckSum(parameters, paytmChecksum);
	            if (isValideChecksum && parameters.containsKey("RESPCODE")) {
	                if (parameters.get("RESPCODE").equals("01")) {
	                    result = "Payment Successful";
                           Orders ol=new Orders();
                           uploadProducts up=rsp.findById(this.proid).orElse(new uploadProducts());
                       ol.setCustomerId(this.cid);
                       ol.setProducts(up);
                          ros.insert(ol);
	                } else {
	                    result = "Payment Failed";
	                }
	            } else {
	                result = "Checksum mismatched";
	            }
	        } catch (Exception e) {
	            result = e.toString();
	        }
	        model.addAttribute("result",result);
	        parameters.remove("CHECKSUMHASH");
	        model.addAttribute("parameters",parameters);
	        return "report";
	    }

	    private boolean validateCheckSum(TreeMap<String, String> parameters, String paytmChecksum) throws Exception {
	        return PaytmChecksum.verifySignature(parameters,
	                paytmDetailPojo.getMerchantKey(), paytmChecksum);
	    }
	private String getCheckSum(TreeMap<String, String> parameters) throws Exception {
		return PaytmChecksum.generateSignature(parameters, paytmDetailPojo.getMerchantKey());
	}
        @RequestMapping("/orders")
	public String orders(Model model)
        { 
           List<Orders>od= serv.orders(this.cid);
            String id=this.cid;
            System.out.println("................................       "+id);
            System.out.println("ooooooooooooooooooooooooooooo  od = "+od);
           model.addAttribute("od", od);
            return "viewOrder";
        }
        @RequestMapping("/cart/{id}")
        public String cart(@PathVariable("id")int id)
        {
            serv.addCart(id,this.cid);
            System.out.println("called ;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;");
            return "view";
        }
        @RequestMapping("/cart")
        public String viewcart(Model model)
        {
           List<Cart> cl= serv.cart(this.cid);
            System.out.println("cccccccccccccccccccccccccccccccccccc   "+cl);
          model.addAttribute("cl", cl);
          return "cart";
        }
        @RequestMapping("/deleteCart/{id}")
        public String detecart(@PathVariable("id")int  id)
        {
            System.out.println("id = "+id);
           serv.delet(id);
            return "cart";
        }
 
@RequestMapping(value = "/error")
public String error(HttpServletRequest request)
{
    Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
    
    if (status != null) {
        Integer statusCode = Integer.valueOf(status.toString());
    
        if(statusCode == HttpStatus.BAD_REQUEST.value()) {
            return "bad";
        }
        else if(statusCode == HttpStatus.INTERNAL_SERVER_ERROR.value()) {
            return "internalerror";
        }
        else if (statusCode==HttpStatus.FORBIDDEN.value())
        {
             return "login";
        }

}
    return "error";
}

    @Override
    public String getErrorPath() {
        return null;
    }
    @RequestMapping("/filter")
   public String filter(@RequestParam("fh")int price,Model model)
   {
     List<uploadProducts>lu3=  serv.filter(price,this.sf);
           model.addAttribute("search",lu3);
      return "search";
   }
}


