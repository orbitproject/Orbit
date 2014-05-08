<%-- 
    Document   : testPage
    Created on : May 7, 2014, 3:20:50 PM
    Author     : Jessica
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
            
                <div class="content">
                    <form id="testForm" class="pure-form pure-form-stacked">
                        <div class="pure-menu pure-menu-open pure-menu-horizontal" id="managerHeaderMenu">
                        <!--<label for="recordResr" class="pure-radio">
                            <input id="recordResr" type="radio" name="optionsRadios" value="recordResr" checked> Record a Reservation
                        </label>
                        <label for="addCust" class="pure-radio">                                                
                            <input id="addCust" type="radio" name="optionsRadios" value="addCust"> Add Customer                                    
                        </label>
                        <label for="updateCust" class="pure-radio">                                                
                            <input id="updateCust" type="radio" name="optionsRadios" value="updateCust"> Update Customer                                       
                        </label>     
                        <label for="deleteCust" class="pure-radio">                                               
                            <input id="deleteCust" type="radio" name="optionsRadios" value="deleteCust"> Delete Customer                                        
                        </label>
                        <label for="mailingLists" class="pure-radio">                                               
                            <input id="mailingLists" type="radio" name="optionsRadios" value="mailingLists"> Get Customer Mailing Lists                                      
                        </label>
                        <label for="flightSuggest" class="pure-radio">                                               
                            <input id="flightSuggest" type="radio" name="optionsRadios" value="flightSuggest"> View Flight Suggestions                                        
                        </label>
                        <label for="viewBids" class="pure-radio">                                               
                            <input id="viewBids" type="radio" name="optionsRadios" value="viewBids"> View Current Bids                                        
                        </label>
                        <label for="approveOrDeny" class="pure-radio">
                            <input id="approveOrDeny" type="radio" name="optionsRadios" value="approveOrDeny"> Approve or Deny Bids
                        </label>-->
                        <label for="makeResr" class="pure-radio">
                            <input id="makeResr" type="radio" name="optionsRadios" value="makeResr" checked> Make a Reservation
                        </label>
                        <label for="cancelResr" class="pure-radio">
                            <input id="cancelResr" type="radio" name="optionsRadios" value="cancelResr"> Cancel Reservation
                        </label>
                        <label for="viewResr" class="pure-radio">                                               
                            <input id="viewResr" type="radio" name="optionsRadios" value="viewResr"> View Current Reservations                                        
                        </label>
                        <label for="travelItin" class="pure-radio">                                               
                            <input id="travelItin" type="radio" name="optionsRadios" value="travelItin"> View Travel Itinerary                                        
                        </label>
                        <label for="viewCurrBid" class="pure-radio">                                               
                            <input id="viewCurrBid" type="radio" name="optionsRadios" value="viewCurrBid"> View Current Bid                                        
                        </label>
                        <label for="viewBidHist" class="pure-radio">                                               
                            <input id="viewBidHist" type="radio" name="optionsRadios" value="viewBidHist"> View Bid History                                        
                        </label>
                        <label for="viewResrHist" class="pure-radio">                                               
                            <input id="viewResrHist" type="radio" name="optionsRadios" value="viewResrHist"> View Reservation History                                        
                        </label>
                        <label for="viewBestSell" class="pure-radio">                                               
                            <input id="viewBestSell" type="radio" name="optionsRadios" value="viewBestSell"> View Best-seller List of Flights                                        
                        </label>
                        <label for="flightSuggest" class="pure-radio">                                               
                            <input id="flightSuggest" type="radio" name="optionsRadios" value="flightSuggest"> View Flight Suggestions                                        
                        </label>
                        <label for="placeBid" class="pure-radio">                                               
                            <input id="placeBid" type="radio" name="optionsRadios" value="placeBid"> Place Bid                                        
                        </label>
                        </div>
                        
                        <button type="submit" class="pure-button pure-button-primary">Refresh</button>
                        
                        <div class="pure-u-3-4">
                             <div class="pure-u-1 pure-u-med-1-3">
                                <table id="testTable" class="pure-table">
                                            
                                </table>
                            </div>
                        </div>
                    </form>
                </div>
            </div>
        </div>
    </body>
</html>
