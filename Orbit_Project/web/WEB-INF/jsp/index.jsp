<!doctype html>
<html lang="en">
<head>
    <meta charset="utf-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <meta name="description" content="Orbit Travel Reservation System Website for CSE 305">

    <title>Orbit Travel Reservation &ndash; CSE 305 Project</title>

        <link rel="stylesheet" href="http://yui.yahooapis.com/pure/0.4.2/pure.css">
        <link rel="stylesheet" href="css/layouts/side-menu.css">  
</head>
    <body>

        <div id="layout">
            <!-- Menu toggle -->
            <a href="#menu" id="menuLink" class="menu-link">
            <!-- Hamburger icon -->
                <span></span>
            </a>

    <div id="menu">
        <div class="pure-menu pure-menu-open">
            <a class="pure-menu-heading" href="#">Orbit Travel</a>

            <ul>
                <li><a href="#">Home</a></li>
                <li><a href="#">Flights</a></li>

                <li class="menu-item-divided pure-menu-selected">
                    <a href="#">Accounts</a>
                </li>

                <li><a href="#">Contact</a></li>
            </ul>
        </div>
    </div>

    <div id="main">
        <div class="header">
            <h1>Book Your Travel</h1>
            <h2>Quick and Cheap Flights</h2>
        </div>

        <div class="content">
            
            <h2 class="content-subhead">Check out last minute flight</h2>
            
            <form class="pure-form pure-form-stacked">
                <fieldset>
                    <legend>Book Flight</legend>

                    <div class="pure-g">
                        <div class="pure-u-1 pure-u-med-1-4">
                            <label for="leaving-from">Leaving from</label>
                            <input id="first-name" type="text">
                        </div>

                        <div class="pure-u-1 pure-u-med-1-4">
                            <label for="going-to">Going to</label>
                            <input id="last-name" type="text">
                        </div>

                        <div class="pure-u-1 pure-u-med-1-4">
                            <label for="departing-on">Departing on</label>
                            <input id="email" type="email">
                        </div>

                        <div class="pure-u-1 pure-u-med-1-3">
                            <label for="select-time">Select time</label>
                            <select id="state" class="pure-input-1-2">
                                <option>Lowest Fare</option>
                                <option>Morning</option>
                                <option>Afternoon</option>
                                <option>Evening</option>
                            </select>
                        </div>
                        
                        <div class="pure-u-1 pure-u-med-1-3">
                            <label for="arriving-on">Arriving on</label>
                            <input id="city" type="text">
                        </div>

                        <div class="pure-u-1 pure-u-med-1-2">
                            <label for="select-time">Select time</label>
                            <select id="state" class="pure-input-1-2">
                                <option>Lowest Fare</option>
                                <option>Morning</option>
                                <option>Afternoon</option>
                                <option>Evening</option>
                            </select>
                        </div>
                    </div>

                    <label for="terms" class="pure-checkbox">
                        <input id="terms" type="checkbox"> I've read the terms and conditions*
                    </label>
                    
                    <button type="submit" class="pure-button pure-button-primary">Find Flights</button>
                </fieldset>
            </form>

            <h2 class="content-subhead">Most popular Flights</h2>
            <p>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
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

            <h2 class="content-subhead">Try Resizing your Browser</h2>
            <p>
                Lorem ipsum dolor sit amet, consectetur adipisicing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam, quis nostrud exercitation ullamco laboris nisi ut aliquip ex ea commodo consequat. Duis aute irure dolor in reprehenderit in voluptate velit esse cillum dolore eu fugiat nulla pariatur. Excepteur sint occaecat cupidatat non proident, sunt in culpa qui officia deserunt mollit anim id est laborum.
            </p>
        </div>
    </div>
</div>

<script src="js/ui.js"></script>

</body>
</html>