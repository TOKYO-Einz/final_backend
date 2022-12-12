window.addEventListener('load', function () {
    (function(){

      const url = '/turn';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
         for(turn of data){
            var table = document.getElementById("turnTable");
            var turnRow =table.insertRow();
            let tr_id = 'tr_' + turn.id;
            turnRow.id = tr_id;

            let deleteLink='<a id=\"a_delete_'+turn.id+'\"'+' href=\"#\" '+
            'onclick=\"deleteBy('+turn.id+')\" class=\"link-danger\">Borrar</a>';

            turnRow.innerHTML =
                    '<td class=\"td_id\">' + turn.id + '</td>' +
                    '<td class=\"td_apellido\">' + turn.lastname.toUpperCase() + '</td>' +
                    '<td class=\"td_nombre\">' + turn.name.toUpperCase() + '</td>' +
                    '<td class=\"td_email\">' + turn.email.toUpperCase()+ '</td>'+
                    '<td class=\"td_dni\">' + turn.dni + '</td>'+
                    '<td>'+deleteLink+'</td>';

        };

    })
    })
