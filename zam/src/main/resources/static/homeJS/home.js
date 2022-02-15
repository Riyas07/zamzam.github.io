

var burger=document.querySelector('.burger');
var click=()=>
{
 var link =document.querySelector('.links');
 var alink=document.querySelectorAll('.links li a')
 var name=document.querySelector('.names');
 console.log(name);
 link.classList.toggle('links2');
 alink.forEach((link,index)=>
 {
  if(link.style.animation)
  {
   link.style.animation="";
  }
  else{
    link.style.animation=`alink 1.5s ease forwards`;
  }
 });
 burger.classList.toggle('toggle');
}
burger.addEventListener('click',click);
