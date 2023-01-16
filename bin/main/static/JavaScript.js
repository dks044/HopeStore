window.addEventListener('scroll',function(){
    if(window.pageYOffset > 10){
       navColorWhite();
    }else{
        navColorTransparent();
    }
  })
function navColorWhite(){
    var nav = document.getElementById('navbar')
    nav.style.backgroundColor='white';

}
function navColorTransparent(){
    var nav = document.getElementById('navbar')
    nav.style.backgroundColor='transparent';

}