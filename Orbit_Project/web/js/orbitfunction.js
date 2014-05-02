$(document).ready(function() {
    
    
//    
//    $("#departing-on").val(new Date());
//    
//    $('ul#menu li').click(function() {
//        $('li.menu-item-divided pure-menu-selected').removeClass('menu-item-divided pure-menu-selected');
//        $(this).addClass('menu-item-divided pure-menu-selected');
//    });    
    
    var i = 1;

    
    
    $('input:radio[name="optionsRadios"]').change(
    function(){
        if ($(this).is(':checked') && $(this).val() === 'round-trip') {
            // append goes here            
            
//            alert($(this).val());
            i=1;
            $('#returning-input').html(''); 
            $('#multi-source').html("");            
            $('#returning-input').append('<label for="arriving-on">Returning on</label><input id="returning-on" name="returning-on" type="date">');
        }
        else if($(this).is(':checked') && $(this).val() === 'multi-city'){
            

            $('#multi-source').html("");

            $('#returning-input').html('');
                                                               
                    var lol = '<div class="pure-u-1 pure-u-med-1-8"><label for="going-to">Going to</label><input id="going-to" name="going-to" type="text"><label class="pure-menu-separator">Flight 2</label></div></div></div><div class="pure-u-1-3"><div class="pure-u-1 pure-u-med-1-3">';
                          
                    var kool = '<label for="departing-on">Departing on</label><input id="departing-on" name="departing-on" type="date"></div></div>';

            $('#multi-source').append('<div class="pure-u-1-3"><label class="pure-menu-heading">Flight 2</label></div><div class="pure-u-1-3"><div class="pure-g"><div class="pure-u-1 pure-u-med-1-3"><label for="leaving-from">Leaving from</label><input id="leaving-from" name="leaving-from" type="text"></div>'+lol+kool);

            $('#multi-source').append('<div class="pure-u-1-3"><label class="pure-menu-heading">Flight 3</label></div><div class="pure-u-1-3"><div class="pure-g"><div class="pure-u-1 pure-u-med-1-3"><label for="leaving-from">Leaving from</label><input id="leaving-from" name="leaving-from" type="text"></div>'+lol+kool);

            $('#multi-source').append('<div class="pure-u-1-3"><label class="pure-menu-heading">Flight 4</label></div><div class="pure-u-1-3"><div class="pure-g"><div class="pure-u-1 pure-u-med-1-3"><label for="leaving-from">Leaving from</label><input id="leaving-from" name="leaving-from" type="text"></div>'+lol+kool);


        }
        else{ 
            i=1;
            $('#multi-source').html("");
           $('#returning-input').html(''); 
        }
    });
    
    $("#leaving-from").autocomplete({
        source: makeRequest,     
     
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
                    label: item.code, //+ (item.name ? ", " + item.location : "") + ", " + item.location,
                    value: item.code
                };
            }));
        },
        error: function () {
            response([]);
        }
    });
}

function buildQuery(term) {
    return "select * from json where url = 'http://airportcode.riobard.com/search?fmt=JSON&q=" + encodeURI(term) + "'";
}