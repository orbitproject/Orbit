<!doctype html>
<html lang="en">
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

        <link rel="stylesheet" href="//code.jquery.com/ui/1.10.4/themes/smoothness/jquery-ui.css">


        <script src="js/ui.js"></script>
        <script src="js/orbitfunction.js"></script>


    </head>
    <body>

        <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->

        <div id="layout">
            <!-- Menu toggle -->
            <a href="#menu" id="menuLink" class="menu-link">
                <!-- Hamburger icon -->
                <span></span>
            </a>

            <div id="menu">

            </div>

            <div id="main">
                <div class="header">
                    <h1>Book Your Travel</h1>
                    <h2>Quick and Cheap Flights</h2>
                </div>

                <div class="content">

                    <h2 class="content-subhead">Check out last minute flight</h2>


                    <form id="flight-home" class="pure-form pure-form-stacked" method="POST" action="flightlist">
                        <fieldset>
                            <legend>Book Flight</legend>


                            <div class="pure-g-r">

                                <div class="pure-u-1-3">                                                                                                                                                                                   

                                    <label for="option-one" class="pure-radio">                                                
                                        <input id="one-way" type="radio" name="optionsRadios" value="one-way" checked> One-Way                                        
                                    </label>

                                    <label for="option-two" class="pure-radio">                                                
                                        <input id="round-trip" type="radio" name="optionsRadios" value="round-trip"> Round-Trip                                       
                                    </label>     

                                    <label for="option-three" class="pure-radio">                                               
                                        <input id="multi-city" type="radio" name="optionsRadios" value="multi-city"> Multi-Destination                                        
                                    </label>

                                    <div class="pure-u-1 pure-u-med-1-2">
                                        <label for="select-fare">Fare Options</label>
                                        <select id="select-fare" name='select-fare' class="pure-input-1-2">
                                            <option>Lowest Fare</option>
                                            <option>Morning</option>
                                            <option>Afternoon</option>
                                            <option>Evening</option>
                                        </select>
                                    </div>

                                </div>


                                <div>&nbsp</div>



                                <div class="pure-u-1-3">                                                                                                                                                                                   


                                    <div class="pure-g">
                                        <div class="pure-u-1 pure-u-med-1-3">
                                            <label for="leaving-from">Leaving from</label>
                                            <input id="leaving-from" name='leaving-from' type="text">
                                        </div>

                                        <div class="pure-u-1 pure-u-med-1-8">
                                            <label for="going-to">Going to</label>
                                            <input id="going-to" name='going-to' type="text">
                                        </div>  
                                    </div>      
                                </div>


                                <div class="pure-u-1-3">

                                    <div class="pure-u-1 pure-u-med-1-3">
                                        <label for="departing-on">Departing on</label>
                                        <input id="departing-on" name='departing-on' type="date">
                                    </div>

                                    <div id="returning-input" class="pure-u-1 pure-u-med-1-3">
                                        <!--                                                 <label for="arriving-on">Returning on</label>
                                                                                         <input id="returning-on" name='returning-on' type="date">-->
                                    </div>
                                </div>     


                            </div>


                            <div>&nbsp</div>

                            <div id="multi-source" class="pure-g-r">


                            </div>



                            <div>&nbsp</div>

                            <button type="submit" class="pure-button pure-button-primary">Find Flights</button>
                        </fieldset>
                    </form>

                    <table id="flight-data" class="pure-table">


                        <thead>
                            <tr>
                                <th>#</th>
                                <th>Make</th>
                                <th>Model</th>
                                <th>Year</th>
                            </tr>
                        </thead>

                        <tbody>
                            <tr class="pure-table-odd">
                                <td>1</td>
                                <td>Honda</td>
                                <td>Accord</td>
                                <td>2009</td>
                            </tr>

                            <tr>
                                <td>2</td>
                                <td>Toyota</td>
                                <td>Camry</td>
                                <td>2012</td>
                            </tr>

                            <tr class="pure-table-odd">
                                <td>3</td>
                                <td>Hyundai</td>
                                <td>Elantra</td>
                                <td>2010</td>
                            </tr>

                            <tr>
                                <td>4</td>
                                <td>Ford</td>
                                <td>Focus</td>
                                <td>2008</td>
                            </tr>

                            <tr class="pure-table-odd">
                                <td>5</td>
                                <td>Nissan</td>
                                <td>Sentra</td>
                                <td>2011</td>
                            </tr>

                            <tr>
                                <td>6</td>
                                <td>BMW</td>
                                <td>M3</td>
                                <td>2009</td>
                            </tr>

                            <tr class="pure-table-odd">
                                <td>7</td>
                                <td>Honda</td>
                                <td>Civic</td>
                                <td>2010</td>
                            </tr>

                            <tr>
                                <td>8</td>
                                <td>Kia</td>
                                <td>Soul</td>
                                <td>2010</td>
                            </tr>
                        </tbody>

                    </table>

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
    </body>
</html>