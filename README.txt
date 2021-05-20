Group members: Mia Vu, Jesus Salazar, James Hsiao

Site opens up on the home page, from home.html. All other pages are reachable from the collapsible hamburger menu button on the top-right corner. A user can log in directly from the secondary banner on the homepage, and logging in leads to a page that displays a user's most recently purchased items. The Team page includes a brief introduction to all 3 of our team members. The Products page includes a responsive grid containing all 12 of our products, rendered by pulling data directly from a database of our products. Each individual product page is reached by clicking on a product from the Products page, also rendered dynamically through use of servlets. Products are added to a cart from an individual product page, and the order can be completed on the cart page. 

The requirements for the project are filled as followed:
1. We have a JSP (productsPage.jsp) that is dedicated to displaying all our products by pulling information from a database. This was reimplemented to use JSP for loops to fetch and render the list of products.
   We have a JSP (thankYou.jsp) for showing order confirmation. This was reimplemented with JSP to show request parameters to user.
   We have a JSP (cart.jsp) for showing cart inforation. This was reimplemented with JSP to access cart variables and show them to user in table form.
2. 



* the names of all group members is included in the head of each individual page, as well as on the Team page. 