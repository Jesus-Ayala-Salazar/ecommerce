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
    var id = form.pid.value;
    var quant = form.quantity.value;
    var email = form.email.value;
    var phonenum = form.phone.value;
    var cardEntry = form.card.value;
    var code = form.cvc.value;
    var street = form.street.value;
    var city = form.city.value;
    var state = form.state.value;
    var zipcode = form.zipcode.value;
    var subject = "Early Birds Order Confirmation";
    var name = form.fname.value + " " + form.lname.value;
    var addr = form.street.value + " " + form.bldg.value + ", " + form.city.value + ", " + form.state.value + " " + form.zipcode.value;
    var card = "XXXX XXXX XXXX " + form.card.value.substring(12);

    var body = name + ",%0d%0aYour order from Early Birds has been confirmed. Order information provided below.%0d%0aProduct ID: " + form.pid.value + "%0d%0aQuanitity: " + form.quantity.value + "%0d%0aShip to: " + addr + "%0d%0aShipping method: " + form.ship.value + "%0d%0aBilled to: " + card + "%0d%0aPhone Number: " + form.phone.value + "%0d%0aThank you for your patronage!";

    var mailto = "mailto:" + email + "?subject=" + subject + "&body=" + body;
    if (id == "" || email == "" || quant == "" || phonenum == "" || name == " " || cardEntry == "" || code == ""
        || street == "" || city == "" || state == "" || zipcode == "")
    {
        alert("Please fill out all the required fields!");
        return false;
    }
    else if(phonenum.match(/^[0-9]+$/) == null || cardEntry.match(/^[0-9]+$/) == null || code.match(/^[0-9]+$/) == null
        ||zipcode.match(/^[0-9]+$/) == null)
    {
        alert("Please enter only numeric values for phone number, credit card number, security code, and zipcode.");
        return false;
    }
    else if(phonenum.length <7)
    {
        alert("Phone number contains at least 7 digits.");
        return false;
    }
    else if(cardEntry.length <16)
    {
        alert("card number contains 16 digits.");
        return false;
    }
    else if(code.length != 3)
    {
        alert("Security code must be a 3 digit number.");
        return false;
    }
    else if(zipcode.length != 5)
    {
        alert("Zipcode must be a 5 digit number.");
        return false;
    }
    else
    {window.open(mailto);}
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

