$(document).ready(function() {

    $('#menu').load('menu.html');

    var i = 1;



    $('input:radio[name="optionsRadios"]').change(
            function() {
                if ($(this).is(':checked') && $(this).val() === 'round-trip') {
                    // append goes here            

//            alert($(this).val());
                    i = 1;
                    $('#returning-input').html('');
                    $('#multi-source').html("");
                    $('#returning-input').append('<label for="arriving-on">Returning on</label><input id="returning-on" name="returning-on" type="date" required>');
                }
                else if ($(this).is(':checked') && $(this).val() === 'multi-city') {


                    $('#multi-source').html("");

                    $('#returning-input').html('');

                    var lol = '<div class="pure-u-1 pure-u-med-1-8"><label for="going-to">Going to</label><input id="going-to" name="going-to" type="text"><label class="pure-menu-separator">Flight 2</label></div></div></div><div class="pure-u-1-3"><div class="pure-u-1 pure-u-med-1-3">';

                    var kool = '<label for="departing-on">Departing on</label><input id="departing-on" name="departing-on" type="date"></div></div>';

                    $('#multi-source').append('<div class="pure-u-1-3"><label class="pure-menu-heading">Flight 2</label></div><div class="pure-u-1-3"><div class="pure-g"><div class="pure-u-1 pure-u-med-1-3"><label for="leaving-from">Leaving from</label><input id="leaving-from" name="leaving-from" type="text"></div>' + lol + kool);

                    $('#multi-source').append('<div class="pure-u-1-3"><label class="pure-menu-heading">Flight 3</label></div><div class="pure-u-1-3"><div class="pure-g"><div class="pure-u-1 pure-u-med-1-3"><label for="leaving-from">Leaving from</label><input id="leaving-from" name="leaving-from" type="text"></div>' + lol + kool);

                    $('#multi-source').append('<div class="pure-u-1-3"><label class="pure-menu-heading">Flight 4</label></div><div class="pure-u-1-3"><div class="pure-g"><div class="pure-u-1 pure-u-med-1-3"><label for="leaving-from">Leaving from</label><input id="leaving-from" name="leaving-from" type="text"></div>' + lol + kool);


                }
                else {
                    i = 1;
                    $('#multi-source').html("");
                    $('#returning-input').html('');
                }
            });

//    $("#leaving-from").autocomplete({
//        source: makeRequest,     
//     
//    });


    $.ajax({
        type: 'post',
        url: 'airportcode',
        dataType: 'text',
        success: function(jsonData) {


            var parsedJSON = jQuery.parseJSON(jsonData);
            var data = parsedJSON.data[0];

            var data1 = [];


            for (var i = 0; i < parsedJSON.data.length; i++)
                data1.push(parsedJSON.data[i].airlineID);

            var NoResultsLabel = "No Results";



            $("#leaving-from").autocomplete({
                minLength: 0,
                source: function(request, response) {
                    var results = $.ui.autocomplete.filter(data1, request.term);

                    if (!results.length) {
                        results = [NoResultsLabel];
                    }

                    response(results);
                }

            });

            $("#going-to").autocomplete({
                minLength: 0,
                source: function(request, response) {
                    var results = $.ui.autocomplete.filter(data1, request.term);

                    if (!results.length) {
                        results = [NoResultsLabel];
                    }

                    response(results);
                }

            });

        },
        error: function(jsonData) {
            alert('Error displaying flights' + jsonData);
        }
    });

    /*$('#flight-home').submit(
     retrieveFlightList()
     );*/


    var check = 0;

    $(function() {
        $('#flight-home').submit(function() {
            $('#flight-data').html("");
            $('#roundtrip-flight-data').html("");
            $.post('flightlist', $(this).serialize(), function(jsonData) {
                $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                                <th>LegNo</th><th>DepTime</th><th>From</th><th>ArrTime</th>\n\
                                <th>To</th><th>Book Flight</th></tr></thead>");

                var parsedJSON = jQuery.parseJSON(jsonData);
                var flightInfo;
                var currentIndex;
                
//<a class="pure-button" href="#">A Pure Button</a>
                if ($('#one-way').is(':checked')) {

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        flightInfo = parsedJSON.data[i];

                        if (flightInfo.toAirport === $('#going-to').val())
                            $('#flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport
                                    + '</td><td>' + '<a id="book-flight" class="button-secondary pure-button" href="makeReservation.jsp">Book Flight</button>' + '</td></tr>');
                        else if (flightInfo.legNo === 1) {
                            $('#flight-data').append('<tr class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td></tr>'
                                    + '</td><td>' + "" + '</td></tr>');
                        }
                        else
                            $('#flight-data').append('<tr  class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td><td>' + " " + '</td></tr>');

                    }
                }//one-way ends
                else if ($('#round-trip').is(':checked'))
                {
                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        flightInfo = parsedJSON.data[i];
                        
                        // move on to returning flight of roundtrip
                        if (flightInfo.fromAirport === $('#going-to').val())
                        {
//                            $('#roundtrip-flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
//                                <th>LegNo</th><th>DepTime</th><th>From</th><th>ArrTime</th>\n\
//                                <th>To</th><th>Book Flight</th></tr></thead>");
                            currentIndex = i;
                            break;
                        }

                        if (flightInfo.toAirport === $('#going-to').val())
                            $('#flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport
                                    + '</td><td>' + '</td></tr>');
                        else if (flightInfo.legNo === 1 && flightInfo.fromAirport === $('#leaving-from').val()) {
                            $('#flight-data').append('<tr class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td></tr>'
                                    + '</td><td>' + "" + '</td></tr>');
                        }
                        else
                            $('#flight-data').append('<tr  class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td><td>' + " " + '</td></tr>');

                    }
                    
                    for (var j = currentIndex; j < parsedJSON.data.length; j++)
                    {
                        flightInfo = parsedJSON.data[j];
                        
                         if (flightInfo.toAirport === $('#leaving-from').val()) {
                            $('#flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport
                                    + '</td><td>'+'<a id="book-flight" class="button-secondary pure-button" href="makeReservation.jsp">Book Flight</button>' + '</td></tr>');
                        }
                        else
                            $('#flight-data').append('<tr class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td><td>' + " " + '</td></tr>');
                        
                        /*$('#roundtrip-flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
                                flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td></tr>'
                                + '</td><td>' + "" + '</td></tr>');*/
                    }
                }

            }/*, 'json'*/);
            return false;
        });
    });
    
    $(function() {
        $('#managerForm').submit(function() {
            $.post('manager', $(this).serialize(), function(jsonData) {
                var parsedJSON = jQuery.parseJSON(jsonData);
                var rowInfo;
                
                if ($('#employees').is(':checked'))
                {
                    $('#employee-data').html("");
                    
                    if ($('#add').is(':checked'))
                    {
                        // returns nothing right now
                    }
                    else if ($('#update').is(':checked'))
                    {
                        // returns nothing right now
                    }
                    else if ($('#delete').is(':checked'))
                    {
                        // returns nothing right now
                    }
                }
                else if ($('#flights').is(':checked'))
                {
                    $('#flight-data').html("");
                    
                    if ($('#allFlights').is(':checked'))
                    {
                        $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                            <th>NoOfSeats</th><th>DaysOperating</th><th>MinLengthOfStay</th>\n\
                            <th>MaxLengthOfStay</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#flight-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                                rowInfo.flightNo + '</td><td>' + rowInfo.noOfSeats + '</td><td>' +
                                rowInfo.daysOperating + '</td><td>' + rowInfo.minLengthOfStay + '</td><td>' +
                                rowInfo.maxLengthOfStay + '</td></tr>');
                        }
                    }
                    else if ($('#flightsForAirport').is(':checked'))
                    {
                        $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                            <th>NoOfSeats</th><th>DaysOperating</th><th>MinLengthOfStay</th>\n\
                            <th>MaxLengthOfStay</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#flight-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                                rowInfo.flightNo + '</td><td>' + rowInfo.noOfSeats + '</td><td>' +
                                rowInfo.daysOperating + '</td><td>' + rowInfo.minLengthOfStay + '</td><td>' +
                                rowInfo.maxLengthOfStay + '</td></tr>');
                        }
                    }
                    else if ($('#mostActive').is(':checked'))
                    {
                        $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                            <th>ResrCount</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#flight-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                                rowInfo.flightNo + '</td><td>' + rowInfo.resrCount + '</td></tr>');
                        }
                    }
                    else if ($('#onTime').is(':checked'))
                    {
                        $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                            <th>NoOfSeats</th><th>DaysOperating</th><th>MinLengthOfStay</th>\n\
                            <th>MaxLengthOfStay</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#flight-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                                rowInfo.flightNo + '</td><td>' + rowInfo.noOfSeats + '</td><td>' +
                                rowInfo.daysOperating + '</td><td>' + rowInfo.minLengthOfStay + '</td><td>' +
                                rowInfo.maxLengthOfStay + '</td></tr>');
                        }
                    }
                    else if ($('#delayed').is(':checked'))
                    {
                        $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                            <th>NoOfSeats</th><th>DaysOperating</th><th>MinLengthOfStay</th>\n\
                            <th>MaxLengthOfStay</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#flight-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                                rowInfo.flightNo + '</td><td>' + rowInfo.noOfSeats + '</td><td>' +
                                rowInfo.daysOperating + '</td><td>' + rowInfo.minLengthOfStay + '</td><td>' +
                                rowInfo.maxLengthOfStay + '</td></tr>');
                        }
                    }
                }
                else if ($('#customers').is(':checked'))
                {
                    $('#customer-data').html("");
                    
                    $('#customer-data').append("<thead><tr><th>AccountNo</th><th>Id</th>\n\
                        <th>FirstName</th><th>LastName</th><th>SeatNo</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#customer-data').append('<tr><td>' + rowInfo.accountNo + '</td><td>' + 
                            rowInfo.id + '</td><td>' + rowInfo.firstName + '</td><td>' +
                            rowInfo.lastName + '</td><td>' + rowInfo.seatNo + '</td></tr>');
                    }
                }
                else if ($('#reservations').is(':checked'))
                {
                    $('#reservation-data').html("");
                    
                    if ($('#res-flight-num').is(':checked'))
                    {
                        $('#reservation-data').append("<thead><tr><th>ResrNo</th><th>ResrDate</th>\n\
                            <th>BookingFee</th><th>TotalFare</th><th>RepSSN</th>\n\
                            <th>FirstName</th><th>LastName</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#reservation-data').append('<tr><td>' + rowInfo.resrNo + '</td><td>' + 
                                rowInfo.resrDate + '</td><td>' + rowInfo.bookingFee + '</td><td>' +
                                rowInfo.totalFare + '</td><td>' + rowInfo.repSSN + '</td><td>' +
                                rowInfo.firstName + '</td><td>' + rowInfo.lastName + '</td></tr>');
                        }
                    }
                    else if ($('#res-cust-name').is(':checked'))
                    {
                        $('#reservation-data').append("<thead><tr><th>ResrNo</th><th>ResrDate</th>\n\
                            <th>BookingFee</th><th>TotalFare</th><th>RepSSN</th>\n\
                            <th>FirstName</th><th>LastName</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#reservation-data').append('<tr><td>' + rowInfo.resrNo + '</td><td>' + 
                                rowInfo.resrDate + '</td><td>' + rowInfo.bookingFee + '</td><td>' +
                                rowInfo.totalFare + '</td><td>' + rowInfo.repSSN + '</td><td>' +
                                rowInfo.firstName + '</td><td>' + rowInfo.lastName + '</td></tr>');
                        }
                    }
                }
                else if ($('#revenue').is(':checked'))
                {
                    $('#revenue-data').html("");
                    
                    if ($('#revByFlight').is(':checked'))
                    {
                        $('#revenue-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                            <th>Revenue</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#revenue-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                                rowInfo.flightNo + '</td><td>' + rowInfo.revenue + '</td></tr>');
                        }
                    }
                    else if ($('#revByDest').is(':checked'))
                    {
                        $('#revenue-data').append("<thead><tr><th>City</th><th>Revenue</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#revenue-data').append('<tr><td>' + rowInfo.city + '</td><td>' + 
                                rowInfo.revenue + '</td></tr>');
                        }
                    }
                    else if ($('#revByCust').is(':checked'))
                    {
                        $('#revenue-data').append("<thead><tr><th>AccountNo</th><th>FirstName</th>\n\
                            <th>LastName</th><th>Revenue</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#revenue-data').append('<tr><td>' + rowInfo.accountNo + '</td><td>' + 
                                rowInfo.firstName + '</td><td>' + rowInfo.lastName + '</td><td>' +
                                rowInfo.revenue + '</td></tr>');
                        }
                    }
                    else if ($('#custRepRev').is(':checked'))
                    {
                        $('#revenue-data').append("<thead><tr><th>RepSSN</th><th>FirstName</th>\n\
                            <th>LastName</th><th>TotalRevenue</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#revenue-data').append('<tr><td>' + rowInfo.repSSN + '</td><td>' + 
                                rowInfo.firstName + '</td><td>' + rowInfo.lastName + '</td><td>' +
                                rowInfo.totalRevenue + '</td></tr>');
                        }
                    }
                    else if ($('#custRev').is(':checked'))
                    {
                        $('#revenue-data').append("<thead><tr><th>AccountNo</th><th>FirstName</th>\n\
                            <th>LastName</th><th>TotalRevenue</th></tr></thead>");

                        for (var i = 0; i < parsedJSON.data.length; i++)
                        {
                            rowInfo = parsedJSON.data[i];

                            $('#revenue-data').append('<tr><td>' + rowInfo.accountNo + '</td><td>' + 
                                rowInfo.firstName + '</td><td>' + rowInfo.lastName + '</td><td>' +
                                rowInfo.totalRevenue + '</td></tr>');
                        }
                    }
                }
                else if ($('#salesreport').is(':checked'))
                {
                    $('#sales-report-data').html("");
                    
                     $('#sales-report-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                        <th>TotalSales</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#sales-report-data').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                            rowInfo.flightNo + '</td><td>' + rowInfo.totalSales + '</td></tr>');
                    }
                }
            });
            return false;
        });
    });
    
    $(function() {
        $('#testForm').submit(function() {
            $.post('customerrep', $(this).serialize(), function(jsonData) {
                var parsedJSON = jQuery.parseJSON(jsonData);
                var rowInfo;
                
                if ($('#recordResr').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    // returns nothing
                }
                else if ($('#addCust').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    // returns nothing
                }
                else if ($('#updateCust').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    // returns nothing
                }
                if ($('#deleteCust').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    // returns nothing
                }
                else if ($('#mailingLists').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>FirstName</th><th>LastName</th>\n\
                        <th>Email</th><th>Address</th><th>City</th><th>State</th>\n\
                        <th>ZipCode</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.firstName + '</td><td>' + 
                            rowInfo.lastName + '</td><td>' + rowInfo.email + '</td><td>' +
                            rowInfo.address + '</td><td>' + rowInfo.city + '</td><td>' +
                            rowInfo.state + '</td><td>' + rowInfo.zipCode + '</td></tr>');
                    }
                }
                else if ($('#flightSuggest').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                        <th>LegNo</th><th>DepTime</th><th>FromAirport</th><th>ArrTime</th>\n\
                        <th>toAirport</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                            rowInfo.flightNo + '</td><td>' + rowInfo.legNo + '</td><td>' +
                            rowInfo.depTime + '</td><td>' + rowInfo.fromAirport + '</td><td>' +
                            rowInfo.arrTime + '</td><td>' + rowInfo.toAirport + '</td></tr>');
                    }
                }
                else if ($('#viewBids').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>AccountNo</th><th>AirlineID</th>\n\
                        <th>FlightNo</th><th>Class</th><th>BidDate</th><th>NYOP</th>\n\
                        <th>Accepted</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.accountNo + '</td><td>' + 
                            rowInfo.airlineID + '</td><td>' + rowInfo.flightNo + '</td><td>' +
                            rowInfo.class + '</td><td>' + rowInfo.bidDate + '</td><td>' +
                            rowInfo.nYOP + '</td><td>' + rowInfo.accepted + '</td></tr>');
                    }
                }
                else if ($('#approveOrDeny').is(':checked'))
                {
                     $('#testTable').html("");
                    
                    // returns nothing
                }
            });
            return false;
        });
    });
    
    $(function() {
        $('#testForm').submit(function() {
            $.post('customer', $(this).serialize(), function(jsonData) {
                var parsedJSON = jQuery.parseJSON(jsonData);
                var rowInfo;
                
                if ($('#makeResr').is(':checked'))
                {
                     $('#testTable').html("");
                    
                    // returns nothing
                }
                else if ($('#cancelResr').is(':checked'))
                {
                     $('#testTable').html("");
                    
                    // returns nothing
                }
                else if ($('#viewResr').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>ResrNo</th><th>ResrDate</th>\n\
                        <th>BookingFee</th><th>TotalFare</th><th>RepSSN</th><th>AccountNo</th>\n\
                        </tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.resrNo + '</td><td>' + 
                            rowInfo.resrDate + '</td><td>' + rowInfo.bookingFee + '</td><td>' +
                            rowInfo.totalFare + '</td><td>' + rowInfo.repSSN + '</td><td>' +
                            rowInfo.accountNo + '</td></tr>');
                    }
                }
                else if ($('#travelItin').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>ResrNo</th><th>AirlineID</th>\n\
                        <th>FlightNo</th><th>Departing</th><th>Arriving</th><th>DepTime</th>\n\
                        <th>ArrTime</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.resrNo + '</td><td>' + 
                            rowInfo.airlineID + '</td><td>' + rowInfo.flightNo + '</td><td>' +
                            rowInfo.departing + '</td><td>' + rowInfo.arriving + '</td><td>' +
                            rowInfo.depTime + '</td><td>' + rowInfo.arrTime + '</td></tr>');
                    }
                }
                else if ($('#viewCurrBid').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>AccountNo</th><th>AirlineID</th>\n\
                        <th>FlightNo</th><th>Class</th><th>BidDate</th><th>NYOP</th>\n\
                        <th>Accepted</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.accountNo + '</td><td>' + 
                            rowInfo.airlineID + '</td><td>' + rowInfo.flightNo + '</td><td>' +
                            rowInfo.class + '</td><td>' + rowInfo.bidDate + '</td><td>' +
                            rowInfo.nYOP + '</td><td>' + rowInfo.accepted + '</td></tr>');
                    }
                }
                 else if ($('#viewBidHist').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>AccountNo</th><th>AirlineID</th>\n\
                        <th>FlightNo</th><th>Class</th><th>BidDate</th><th>NYOP</th>\n\
                        <th>Accepted</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.accountNo + '</td><td>' + 
                            rowInfo.airlineID + '</td><td>' + rowInfo.flightNo + '</td><td>' +
                            rowInfo.class + '</td><td>' + rowInfo.bidDate + '</td><td>' +
                            rowInfo.nYOP + '</td><td>' + rowInfo.accepted + '</td></tr>');
                    }
                }
                 else if ($('#viewResrHist').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>ResrNo</th><th>ResrDate</th>\n\
                        <th>BookingFee</th><th>TotalFare</th><th>RepSSN</th><th>AccountNo</th>\n\
                        </tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.resrNo + '</td><td>' + 
                            rowInfo.resrDate + '</td><td>' + rowInfo.bookingFee + '</td><td>' +
                            rowInfo.totalFare + '</td><td>' + rowInfo.repSSN + '</td><td>' +
                            rowInfo.accountNo + '</td></tr>');
                    }
                }
                 else if ($('#viewBestSell').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                        <th>ResrCount</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                            rowInfo.flightNo + '</td><td>' + rowInfo.resrCount + '</td></tr>');
                    }
                }
                else if ($('#flightSuggest').is(':checked'))
                {
                    $('#testTable').html("");
                    
                    $('#testTable').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                        <th>LegNo</th><th>DepTime</th><th>FromAirport</th><th>ArrTime</th>\n\
                        <th>toAirport</th></tr></thead>");

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        rowInfo = parsedJSON.data[i];

                        $('#testTable').append('<tr><td>' + rowInfo.airlineID + '</td><td>' + 
                            rowInfo.flightNo + '</td><td>' + rowInfo.legNo + '</td><td>' +
                            rowInfo.depTime + '</td><td>' + rowInfo.fromAirport + '</td><td>' +
                            rowInfo.arrTime + '</td><td>' + rowInfo.toAirport + '</td></tr>');
                    }
                }
                else if ($('#placeBid').is(':checked'))
                {
                     $('#testTable').html("");
                    
                    // returns nothing
                }
            });
            return false;
        });
    });
});

function makeRequest(request, response) {
    $.ajax({
        url: 'http://query.yahooapis.com/v1/public/yql',
        data: {
            q: buildQuery(request.term),
            format: "json"
        },
        dataType: "jsonp",
        success: function(data) {
            var airports = [];
            if (data && data.query && data.query.results && data.query.results.json && data.query.results.json.json) {
                airports = data.query.results.json.json;
            }

            response($.map(airports, function(item) {
                return {
                    label: item.code + (item.name ? ", " + item.location : "") + ", " + item.location,
                    value: item.code
                };
            }));
        },
        error: function() {
            response([]);
        }
    });
}

/*function retrieveFlightList()
 {
 $.ajax({
 type: 'post',
 url: 'flightlist',
 dataType: 'text',
 success: function(jsonData) {
 $('#flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
 <th>LegNo</th><th>DepTime</th><th>FromAirport</th><th>ArrTime</th>\n\
 <th>ToAirport</th></tr></thead>");
 var parsedJSON = jQuery.parseJSON(jsonData);
 var flightInfo;
 
 for(var i=0; i<parsedJSON.data.length; i++)
 {
 flightInfo = parsedJSON.data[i];
 $('#flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
 flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
 flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
 flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td></tr>');
 }       
 },
 
 error: function(jsonData) {
 alert('Error displaying flights' + jsonData);
 }
 });
 }*/

function buildQuery(term) {
    return "select * from json where url = 'http://airportcode.riobard.com/search?fmt=JSON&q=" + encodeURI(term) + "'";
}