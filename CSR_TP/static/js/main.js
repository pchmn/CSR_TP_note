$(document).ready(function() {
    $("#create-user-form button").click( function() {
        var new_user = new Object();

        new_user.nb_new_users = $('input#nb_new_users').val();

        console.log(new_user);

        $.ajax({
            type: "post",
            url: "/people",
            data: JSON.stringify(new_user),
            success: function(data){
                console.log(data);
                window.location = "/";
            },
            dataType: "json",
            contentType : "application/json"
        });
    });
    
    $("#create-bus-form button").click( function() {
        var new_buses = new Object();

        new_buses.nb_new_buses = $('input#nb_new_buses').val();
        new_buses.nb_places = $('input#nb_places').val();

        console.log(new_buses);

        $.ajax({
            type: "post",
            url: "/buses/",
            data: JSON.stringify(new_buses),
            success: function(data){
                console.log(data);
                window.location = "/";
            },
            dataType: "json",
            contentType : "application/json"
        });
    });

    if($('#users-table').length) {
        var users_table = $('#users-table tbody');

        $.ajax({
            type: "get",
            url: "/people",
            success: function(data){
                console.log(data);

                $.each(data, function (item) {
                    var id = data[item].id;
                    var url = data[item].url;

                    users_table.append(
                    '<tr>' +
                        '<th><a href="' + url + '">' + id + '</a></th>' +
                        '<td>' + url + '</td>' +
                        '<td><a type="button" class="btn btn-success btn-xs" href = "'+ url + '">details</a> ' +
                        '<td><a type="button" class="btn btn-success btn-xs" href = "'+ url + '/stats">stats</a> ' +
                    '</tr>'
                    );

                    $('button#delete-user-' + id).click(function() {
                        $.ajax({
                            type: "delete",
                            url: "/users/" + id,
                            success: function(data){
                                console.log("User " + data.id + " deleted");
                                window.location = "/";
                            }
                        });
                    });

                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ajax error");
            },
            dataType: "json",
            contentType : "application/json"
        });
    }

    if($('#details-list').length) {
        var tweets_list = $('ul#details-list');

        $.ajax({
            type: "get",
            url: window.location.href,
            success: function(data){
                console.log(data);
	              var id = data.id;
	              var state = data.state;
	              var url_stats = data.url_stats;
	              tweets_list.append('<li> id : ' + id + '</li>');
	              tweets_list.append('<li> state : ' + state + '</li>');
	              tweets_list.append('<li><a type="button" class="btn btn-success btn-xs" href = "'+ url_stats + '">stats</a> </li>');
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ajax error");
            },
            dataType: "json",
            contentType : "application/json"
        });
    }
    
    
    if($('#stat-user-list').length) {
        var tweets_list = $('ul#stat-user-list');

        $.ajax({
            type: "get",
            url: window.location.href,
            success: function(data){
                console.log(data);
	             $.each(data, function (index, element) {
                	tweets_list.append('<li> ' + index + ' : ' + element + '</li>');
                });
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ajax error");
            },
            dataType: "json",
            contentType : "application/json"
        });
    }
});
