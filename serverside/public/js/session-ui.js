$(document).ready(function(){

    /**
     *
     */
    $('.task-open').click(function (e){

        var user_id = $('#user').attr('data');
        var session_id = $(".table").attr('id');
        var todo_id = $(this).next().text();

        $.ajax({
            url: '/'+ user_id + '/' + session_id + '/todos/' + todo_id + '/tasks',
            success: function(data) {
                console.log(data);

                if( data === 'array') {

                    // TODO add partial table with data from server here

                }
            }
        });

    });
});