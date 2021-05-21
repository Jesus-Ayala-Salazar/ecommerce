Group members: Mia Vu, Jesus Salazar, James Hsiao

Site opens up on the home page, from home.html. All other pages are reachable from the collapsible hamburger menu button on the top-right corner. A user can log in directly from the secondary banner on the homepage, and logging in leads to a page that displays a user's most recently purchased items. The Team page includes a brief introduction to all 3 of our team members. The Products page includes a responsive grid containing all 12 of our products, rendered by pulling data directly from a database of our products. Each individual product page is reached by clicking on a product from the Products page, also rendered dynamically through use of servlets. Products are added to a cart from an individual product page, and the order can be completed on the cart page. 

The requirements for the project are filled as followed:
1. We have a JSP (productsPage.jsp) that is dedicated to displaying all our products by pulling information from a database. This was reimplemented to use JSP for loops to fetch and render the list of products.
   We have a JSP (thankYou.jsp) for showing order confirmation. This was reimplemented with JSP to show request parameters to user.
   We have a JSP (cart.jsp) for showing cart inforation. This was reimplemented with JSP to access cart variables and show them to user in table form.

2. On the cart.jsp page, our order form dynamically updates the page's information once a zip code is input. The state and city fields of the form are autofilled with information from our backend database based on the inputted zipcode. Additionally, the Tax and Total fields in the table that holds all the cart items are updated once a zipcode is put in as well, also using information from our backend databases. Tax is calcualted based on the inputted zipcode, and the Tax field and Total field are adjusted thusly.

3. We created all our our REST services and made sure they were able to interact with our backend database, but we couldn't manage to integrate them fully with our site. All the files have been included in our project recardless.



* the names of all group members is included in the head of each individual page, as well as on the Team page. 
