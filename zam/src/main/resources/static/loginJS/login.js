
function check()
{
    var userid=document.querySelector('.userid');
    var email=document.querySelector('.emails');
    var password=document.querySelector('.passwords');
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
else{
    return true;
}
}