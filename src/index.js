function openMenu() {
    document.getElementById("menu").style.width = "50%";
    document.getElementById("menu-button").style.display = "none";
    document.getElementById("open-menu-button").style.display = "block";
}
  
function closeMenu() {
    document.getElementById("menu").style.width = "0";
    document.getElementById("menu-button").style.display = "block";
    document.getElementById("open-menu-button").style.display = "none";
}

var slideIndex = 1;
showSlides(slideIndex);


function slide(n) {
    showSlides(slideIndex = n);
}

function showSlides(n) {
    var i;
    var slides = document.getElementsByClassName("mySlides");
    var dots = document.getElementsByClassName("dot");
    if (n > slides.length) {slideIndex = 1}
    if (n < 1) {slideIndex = slides.length}
    for (i = 0; i < slides.length; i++) {
        slides[i].style.display = "none";
    }
    for (i = 0; i < dots.length; i++) {
        dots[i].className = dots[i].className.replace(" active", "");
    }
    slides[slideIndex-1].style.display = "block";
    dots[slideIndex-1].className += " active";
}