function openMenu() {
    document.getElementById("menu").style.width = "100%";
    document.getElementById("menu-button").style.display = "none";
    document.getElementById("open-menu-button").style.display = "block";
}
  
function closeMenu() {
    document.getElementById("menu").style.width = "0";
    document.getElementById("menu-button").style.display = "block";
    document.getElementById("open-menu-button").style.display = "none";
}