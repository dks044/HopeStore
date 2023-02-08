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
const popoverTriggerList = document.querySelectorAll('[data-bs-toggle="popover"]')
const popoverList = [...popoverTriggerList].map(popoverTriggerEl => new bootstrap.Popover(popoverTriggerEl))

const myModal = document.getElementById('myModal')
const myInput = document.getElementById('myInput')

myModal.addEventListener('shown.bs.modal', () => {
  myInput.focus()
})

$(".btn-modal").click(function(){
	var data = $(this).data('id');
    $("#contents.body-contents").val(data);
    $("#text-contents.body-contents").html(data);
});