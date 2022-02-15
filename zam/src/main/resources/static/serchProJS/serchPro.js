

window.addEventListener('scroll',()=>
{
    var s=document.querySelector('.navbar');
    s.classList.toggle('navbar2',window.scrollY >0);
});
const sn=document.querySelector('.sn');
const mic=document.getElementById('mic');
const contentName=document.querySelector('.content-name');
const contentName1=document.querySelector('.content-name1');
const speechRecognition=window.speechRecognition || window.webkitSpeechRecognition;
const recognition=new webkitSpeechRecognition();
recognition.onstart=function()
{
      contentName.style.display='none';
  contentName1.style.display='inline';
     contentName.style.transition='0.5s all ease';
  contentName1.style.transition='0.5s all ease';
  console.log('activated brooooo .....');
};
recognition.onend=function()
{
      contentName.style.display='inline';
  contentName1.style.display='none';
        contentName.style.transition='0.5s all ease';
  contentName1.style.transition='0.5s all ease';
  console.log('end brooooo .....');
};

recognition.onresult=function(event)
{
   var current=event.resultIndex;
   var transcript=event.results[current][0].transcript;
   console.log(transcript);
   sn.value=transcript;
   readOut(transcript);
};
var flag=false;
mic.addEventListener('click',()=>
{
      recognition.start();
  
});
function readOut(message)
{
    const speech=new SpeechSynthesisUtterance();
  speech.text=message;
    if(message.includes('what the f***'))
   {
       console.log('what the f');
       speech.text='it is bad word do not repeat again';
   }
   else if(message.includes('how are you'))
   {
      speech.text='iam good little piece of love';
   }
   else if(message.includes('who made you'))
   {
       speech.text='the CEO of zam zam riyas';
   }
   else if(message.includes('Hai Zam Zam'))
   {
       speech.text='hai dude';
   }
   else if(message.includes('f***'))
   {
         console.log('f');
       speech.text='it is bad word do not repeat again';
   }
   else if(message.includes('f*** off'))
   {
         console.log('f off');
       speech.text='it is bad word do not repeat again';
   } 
   else if(message.includes('God of brain'))
   {
       speech.text='is rinshad';
   }
    speech.volume=1;
    speech.rate=1;
    speech.pitch=1;
    window.speechSynthesis.speak(speech);
}
var f=document.querySelector('.btnf');
var itm=document.querySelector('.lists');
var arr1=document.querySelector('.arrow1');
var arr2=document.querySelector('.arrow2');
var state=false;
f.addEventListener('click',()=>
{
    if(state)
    {
         arr1.style.visibility="visible";
    arr2.style.visibility="hidden";
    arr2.style.opacity="0";
    arr1.style.opacity="1";
    state=false;
    }
   else
   {
        arr1.style.visibility="hidden";
    arr2.style.visibility="visible";
    arr2.style.opacity="1";
    arr1.style.opacity="0";
    state=true;
   }
    console.log('jjjjjjjjjjjjjjjjjjj');
  itm.classList.toggle('lists2');
});