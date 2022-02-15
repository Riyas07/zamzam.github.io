
function check()
{
    var userid=document.querySelector('.userid');
    var email=document.querySelector('.emails');
    var password=document.querySelector('.passwords');
    var address=document.querySelector('.address');
    var pincode=document.querySelector('.pincode');
if(userid.value.trim()=="")
{
    document.getElementById("lu").style.visibility="visible";
return false;
}
else if(email.value.trim()=="")
{
    document.getElementById("le").style.visibility="visible";
return false;
}
else if(password.value.trim()=="")
{
    document.getElementById("lp").style.visibility="visible";
return false;
}
else if(address.value.trim()=="")
{
    document.getElementById("la").style.visibility="visible";
return false;
}
else if(pincode.value.trim()=="")
{
    document.getElementById("lpin").style.visibility="visible";
return false;
}
else{
    return true;
}
}