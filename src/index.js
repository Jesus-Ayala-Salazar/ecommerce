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

function confirmOrder(form) {
    var email = form.email.value;
    var subject = "Early Birds Order Confirmation";
    var name = form.fname.value + " " + form.lname.value;
    var addr = form.street.value + " " + form.bldg.value + ", " + form.city.value + ", " + form.state.value + " " + form.zipcode.value;
    var card = "XXXX XXXX XXXX " + form.card.value.substring(12);

    var body = name + ",%0d%0aYour order from Early Birds has been confirmed. Order information provided below.%0d%0aProduct ID: " + form.pid.value + "%0d%0aQuanitity: " + form.quantity.value + "%0d%0aShip to: " + addr + "%0d%0aShipping method: " + form.ship.value + "%0d%0aBilled to: " + card + "%0d%0aPhone Number: " + form.phone.value + "%0d%0aThank you for your patronage!";

    var mailto = "mailto:" + email + "?subject=" + subject + "&body=" + body;

    window.open(mailto);
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

