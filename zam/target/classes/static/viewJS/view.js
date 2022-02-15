var container=document.querySelector('.container');
var card=document.querySelector('.card img');
var dt1=document.querySelector('.dt1');
var dt2=document.querySelector('.dt2');
var dt3=document.querySelector('.dt3');
var dt4=document.querySelector('.dt4');
var btn=document.querySelector('.btn');
container.addEventListener('mousemove',(e)=>
{
   let xAxis=(window.innerWidth/2 - e.pageX)/13;
   let yAxis=(window.innerHeight/2 - e.pageY)/13;
   container.style.transition="none";
container.style.transform=`rotateY(${xAxis}deg) rotateX(${yAxis}deg)`;
card.style.transform=`translateZ(150px) rotateZ(40deg)`;
dt1.style.transform="translateZ(150px)";

});
container.addEventListener('mouseleave', (e)=>{
    container.style.transition="all 0.5s ease";
container.style.transform=`rotateY(0deg) rotateX(0deg)`;
dt1.style.transform="translateZ(0px)";
dt1.style.transform="translateZ(0px)";
card.style.transform=`translateZ(0px) rotateZ(0deg)`;
});
