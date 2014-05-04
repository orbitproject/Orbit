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
                    $('#returning-input').append('<label for="arriving-on">Returning on</label><input id="returning-on" name="returning-on" type="date">');
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
                                <th>LegNo</th><th>DepTime</th><th>FromAirport</th><th>ArrTime</th>\n\
                                <th>ToAirport</th><th>Book Flight</th></tr></thead>");

                var parsedJSON = jQuery.parseJSON(jsonData);
                var flightInfo;
                var currentIndex;
                

                if ($('#one-way').is(':checked')) {

                    for (var i = 0; i < parsedJSON.data.length; i++)
                    {
                        flightInfo = parsedJSON.data[i];

                        if (flightInfo.toAirport === $('#going-to').val())
                            $('#flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport
                                    + '</td><td>' + '<button class="button-secondary pure-button">Book Flight</button>' + '</td></tr>');
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
                            $('#roundtrip-flight-data').append("<thead><tr><th>AirlineID</th><th>FlightNo</th>\n\
                                <th>LegNo</th><th>DepTime</th><th>FromAirport</th><th>ArrTime</th>\n\
                                <th>ToAirport</th><th>Book Flight</th></tr></thead>");
                            currentIndex = i;
                            break;
                        }

                        if (flightInfo.toAirport === $('#going-to').val())
                            $('#flight-data').append('<tr><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport
                                    + '</td><td>' + '<button class="button-secondary pure-button">Book Flight</button>' + '</td></tr>');
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
                    
                    for (var j = currentIndex; j < parsedJSON.data.length; j++)
                    {
                        flightInfo = parsedJSON.data[j];
                        
                         if (flightInfo.legNo === 1) {
                            $('#roundtrip-flight-data').append('<tr class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
                                    flightInfo.flightNo + '</td><td>' + flightInfo.legNo + '</td><td>' +
                                    flightInfo.depTime + '</td><td>' + flightInfo.fromAirport + '</td><td>' +
                                    flightInfo.arrTime + '</td><td>' + flightInfo.toAirport + '</td></tr>'
                                    + '</td><td>' + "" + '</td></tr>');
                        }
                        else
                            $('#roundtrip-flight-data').append('<tr class="pure-table-odd"><td>' + flightInfo.airlineID + '</td><td>' +
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