$(document).ready(function() {
    $("#create-user-form button").click( function() {
        var new_user = new Object();

        new_user.nb_new_users = $('input#nb_new_users').val();

        console.log(new_user);

        $.ajax({
            type: "post",
            url: "/people/",
            data: JSON.stringify(new_user),
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
            url: "/people/",
            success: function(data){
                console.log(data);

                $.each(data, function (item) {
                    var id = data[item].id;
                    var url = data[item].url;

                    users_table.append(
                    '<tr>' +
                        '<th><a href="' + url + '">' + id + '</a></th>' +
                        '<td>' + url + '</td>' +
                        '<td><a type="button" class="btn btn-success btn-xs" href = "'+ tweet_url + '">tweets</a> ' +
                        '<button type="button" class="btn btn-danger btn-xs" id="delete-user-' + id +'">delete</button></td>' +
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

    if($('#tweets-list').length) {
        var tweets_list = $('ul#tweets-list');

        $.ajax({
            type: "get",
            url: window.location.href,
            success: function(data){
                console.log(data);
                if(data.length == 0) {
                  tweets_list.append('<li>Aucun tweet</li>');
                }
                else {
                  $.each(data, function (item) {
                      var tweet = data[item].content;
                      tweets_list.append('<li>' + tweet + '</li>');
                  });
                }
            },
            error: function(jqXHR, textStatus, errorThrown) {
                console.log("ajax error");
            },
            dataType: "json",
            contentType : "application/json"
        });
    }
});
