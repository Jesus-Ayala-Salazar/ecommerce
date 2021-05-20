/**
 * 
 */

function getCityState(zip) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var result = xhr.responseText;
			var place = result.split (', ');
			if (document.getElementById ("city").value == "") {
				document.getElementById ("city").value = place[0];
			}
			if (document.getElementById ("state").value == "") {
				document.getElementById ("state").value = place[1];
			}
		}
	};
	xhr.open("GET", "getCityStateServlet?zip=" + zip, true);
	xhr.send();
}


function calcTax(zip, total) {
	var xhr = new XMLHttpRequest();
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var result = xhr.responseText;
			
			var totalTax = result*total;
			
			document.getElementById("tax").innerHTML = totalTax.toFixed(2);
			document.getElementById("orderTotal").innerHTML = (total+totalTax).toFixed(2);

		}
	};
	xhr.open("GET", "taxServlet?zip=" + zip, true);
	xhr.send();
}

function processZip(zip,total) {
	calcTax(zip,total);
	getCityState(zip);
}
