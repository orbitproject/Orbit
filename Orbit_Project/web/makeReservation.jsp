<%-- 
    Document   : makeReservation
    Created on : May 3, 2014, 5:57:44 PM
    Author     : commy_000
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta charset="utf-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
        <meta name="description" content="Orbit Travel Reservation System Website for CSE 305">

        <title>Orbit Travel Reservation &ndash; CSE 305 Project</title>     

        <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.4.2/pure.css">
        <link rel="stylesheet" href="css/layouts/side-menu.css">  

        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.0/jquery.min.js"></script>      
        <script src="https://code.jquery.com/jquery-1.10.2.js"></script>
        <script src="https://code.jquery.com/ui/1.10.4/jquery-ui.js"></script>

        <script src="js/ui.js"></script>
        <script src="js/orbitfunction.js"></script>  
    </head>
    <body>
        <div id="layout">

            <div id="menu"></div>

            <div id="main">
                <div class="header">
                    <h1>Book Your Travel</h1>
                    <h2>Quick and Cheap Flights</h2>
                </div>

                <div class="content">

                    <h2 class="content-subhead">Reserve a Flight</h2>


                    <form class="pure-form pure-form-stacked" method="POST" action='signup'> <!-- TODO: add servlet to post to -->
                        <fieldset>
                            <legend>Enter details below</legend>

                            <!--
                                things user enters:
                                    passenger names, contact info [list]
                                    legs? [list]
                                        from-airport, to-airport
                                        flight num / airline
                                        departure day/time
                                        special meal ordered
                                        seat number
                                        class
                                
                                things page figures out:
                                    reservation number
                                    date reservation made
                                    total fare/fare restrictions?
                                    booking fee
                                    customer representative
                            -->


                            <div class="pure-g-r">


                                <div class="pure-u-1-3">                                                                                                                                                                                   

                                    <div class="pure-u-1 pure-u-med-1-3">
                                        <label for="passengerList">Passengers</label>
                                        <!-- make this an expandable list somehow -->

                                        <div id="passenger-list">

                                            <div id="pass0">
                                                <fieldset class="pure-group">
                                                    <input type="text" class="" placeholder="First name">
                                                    <input type="text" class="" placeholder="Last name">
                                                    <input type="text" class="" placeholder="Telephone">
                                                </fieldset>
                                                <!-- <button class="pure-button-secondary" id="remove-btn0" onclick="removePassengerField">Remove</button> -->
                                            </div>
                                        </div>
                                        
                                        <button id="pass-add-button" class="pure-button" >Add passenger</button>
                                        <button class="pure-button" id="pass-remove-button" onclick="removePassengerField">Remove passenger</button>


                                        <script>
                                            var passcount = 1;

                                            //onclick="appendPassengerField"
                                            $("#pass-add-button").click(function(e) {
                                                appendPassengerField();
                                                e.preventDefault();
                                            });

                                            $("#pass-remove-button").click(function(e) {
                                                removePassengerField();
                                                e.preventDefault();
                                            });


                                            //$(".remove-btn").click(function(e) {

                                            //e.preventDefault(); 
                                            //}); 

                                            function appendPassengerField() {
                                                //$("#passenger-list").append("<fieldset class='pure-group'><input type='text' class='' placeholder='First name'><input type='text' class='' placeholder='Last name'><input type='text' class='' placeholder='Telephone'></fieldset>"); 
                                                //$("#passenger-list").append("<div id='pass" + passcount + "'><fieldset class='pure-group'><input type='text' class='' placeholder='First name'><input type='text' class='' placeholder='Last name'><input type='text' class='' placeholder='Telephone'></fieldset><button class='pure-button-secondary' id='remove-btn" + passcount + "' onclick='removePassengerField(" + passcount + ")'>Remove</button></div>"); 
                                                $("#passenger-list").append("<div id='pass" + passcount + "'><fieldset class='pure-group'><input type='text' class='' placeholder='First name'><input type='text' class='' placeholder='Last name'><input type='text' class='' placeholder='Telephone'></fieldset></div>");
                                                passcount += 1;
                                            }

                                            function removePassengerField() {
                                                //$("#pass0").remove();
                                                if (passcount > 1) {
                                                    $("#pass" + (passcount-1)).remove();
                                                    passcount -= 1; 
                                                }
                                                //$(this).remove(); 
                                                //$("#pass" + i).remove(); 
                                            }
                                        </script>


                                    </div>
                                </div>

                                <div class="pure-u-1-3">                                                                                                                                                                                   

                                    <div class="pure-u-1 pure-u-med-1-3">
                                        <label for="first-name">Itinerary</label>
                                        <label id="flight-info">
                                            <!-- flight info / legs go here -->
                                        </label>
                                    </div>

                                </div>



                                <div class="pure-u-1-3">

                                    <div class="pure-u-1 pure-u-med-1-8">
                                        <label for="TotalFareLabel">Total fare: </label>
                                        <label id="total-fare-amount">$ 00.00</label>
                                    </div>

                                    <div class="pure-u-1 pure-u-med-1-3">
                                        <label for="CustomerRepLabel">Customer Representative: </label>
                                        <label id="customer-rep-name">name</label>
                                    </div>

                                </div>

                            </div>                                                                     

                        </fieldset>

                        <div>&nbsp</div>

                        <button type="submit" class="pure-button pure-button-primary">Create Account!</button>
                        <span id="signupError" style="color:red">${requestScope.errorMessage}</span>
                    </form>




                    <h2 class="content-subhead">We Love Travel</h2>
                    <p>
                        Welcome to Orbit Travel - Your Online Travel Agency!
                        As one of the Btony Brook's leading online travel student website, we make it easy to plan your next business or leisure trip.                     
                        Explore our site on mobile and Facebook page to discover destination ideas, get information about flights , then book and purchase your trip.
                        Wherever you go, we're with you every step of the way!

                    </p>

                    <div class="pure-g">
                        <div class="pure-u-1-4">
                            <img class="pure-img-responsive" src="http://farm3.staticflickr.com/2875/9069037713_1752f5daeb.jpg" alt="Peyto Lake">
                        </div>
                        <div class="pure-u-1-4">
                            <img class="pure-img-responsive" src="http://farm3.staticflickr.com/2813/9069585985_80da8db54f.jpg" alt="Train">
                        </div>
                        <div class="pure-u-1-4">
                            <img class="pure-img-responsive" src="http://farm6.staticflickr.com/5456/9121446012_c1640e42d0.jpg" alt="T-Shirt Store">
                        </div>
                        <div class="pure-u-1-4">
                            <img class="pure-img-responsive" src="http://farm8.staticflickr.com/7357/9086701425_fda3024927.jpg" alt="Mountain">
                        </div>
                    </div>

                    <h2 class="content-subhead">Project Specification</h2>
                    <p>
                        The basic idea behind your on-line travel reservation system is that it will allow customers to use the web to browse/search the contents of your database (at least that part you want the customer to see) and to make flight reservations over the web. Your web site should allow users to make both domestic and international reservations. It should also allow users to query the database for available flights (direct or indirect) between a pair of cities for a given date and "approximate" time.

                        Your system should also support reverse auction, in which individuals specify the price they are willing to pay for a seat and the airlines either agree to sell it at that price or not. Reverse auction sites include priceline.com and expedia.com, a Microsoft-owned travel site that has a feature enabling customers to name their price.

                        Actual travel sites allow you to do a lot more than simply make flight reservations. For example, you can book a rental car or a hotel room. Due to time limitations, we will stick to flight reservations only this semester.

                        Your database system must be based on the specifications and requirements that follow.     
                    </p>
                </div>
            </div>
        </div>

        <script src="js/ui.js"></script>
    </body>
</html>
