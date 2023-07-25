
$('.update-btn').on('click', function(){
                 var name = $(this).parent().parent().find(".api-name")[0].innerHTML
                 var parent = $(this).parent().parent().attr("api-parent")
                 var row = $(this).parent().parent().find(".user-input")
                 var json = {}

                 row.each(function() {
                    json[this.getAttribute("name")] = this.value
                 })

                 $.ajax('/mock/api/id/' + parent + '/' + name, {
                     contentType: "application/json; charset=utf-8",
                     type: 'POST',
                     data: JSON.stringify(json),
                     success: function (data, status, xhr) {
                         //$('p').append('status: ' + status + ', data: ' + data);
                     },
                     error: function (jqXhr, textStatus, errorMessage) {
                             //$('p').append('Error' + errorMessage);
                     }
                 });
})

$('.return-btn').on('click', function(){
                 var name = $(this).parent().parent().find(".api-name")[0].innerHTML
                 var parent = $(this).parent().parent().attr("api-parent")
                 var row = $(this).parent().parent().find(".user-input")

                 $.ajax('/mock/api/id/' + parent + '/' + name + '/startup', {
                     type: 'GET',
                     success: function (data, status, xhr) {
                        row.each(function() {
                            this.value = data[this.getAttribute("name")]
                        })
                     },
                     error: function (jqXhr, textStatus, errorMessage) {}
                 });
})