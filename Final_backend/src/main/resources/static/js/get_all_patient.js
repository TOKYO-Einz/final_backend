window.addEventListener('load', function () {
    (function(){

      const url = '/patient';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {
            for(patient of data){
            var table = document.getElementById("patientTable");
            var patientRow =table.insertRow();
            let tr_id = 'tr_' + patient.id;
            patientRow.id = tr_id;

            let deleteLink='<a id=\"a_delete_'+patient.id+'\"'+' href=\"#\" '+
            'onclick=\"deleteBy('+patient.id+')\" class=\"link-danger\">Borrar</a>';

            patientRow.innerHTML =
                    '<td class=\"td_id\">' + patient.id + '</td>' +
                    '<td class=\"td_apellido\">' + patient.lastname.toUpperCase() + '</td>' +
                    '<td class=\"td_nombre\">' + patient.name.toUpperCase() + '</td>' +
                    '<td class=\"td_email\">' + patient.email.toUpperCase()+ '</td>'+
                    '<td class=\"td_dni\">' + patient.dni + '</td>'+
                    '<td>'+deleteLink+'</td>';

        };

    })
    })