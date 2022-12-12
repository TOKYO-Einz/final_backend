window.addEventListener('load', function () {
    (function(){


      const url = '/dentist';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {

         for(dentist of data){
            var table = document.getElementById("dentistTable");
            var dentistRow =table.insertRow();
            let tr_id = 'tr_' + dentist.id;
            dentistRow.id = tr_id;

            let deleteLink='<a id=\"a_delete_'+dentist.id+'\"'+' href=\"#\" '+
            'onclick=\"deleteBy('+dentist.id+')\" class=\"link-danger\">Borrar</a>';


            dentistRow.innerHTML =
                    '<td class=\"td_id\">' + dentist.id + '</td>' +
                    '<td class=\"td_apellido\">' + dentist.lastname.toUpperCase() + '</td>' +
                    '<td class=\"td_nombre\">' + dentist.name.toUpperCase() + '</td>' +
                    '<td class=\"td_matricula\">' + dentist.registration + '</td>'+
                    '<td>'+deleteLink+'</td>';

        };

    })
    })