window.addEventListener('load', function () {

   (function(){

      const url = '/dentist';
      const settings = {
        method: 'GET'
      }

      fetch(url,settings)
      .then(response => response.json())
      .then(data => {

         for(odontologo of data){

            var formGroupOd = document.getElementById("dentistFormControlSelect1");
            var dentistTurnRow =formGroupOd.insertRow();
            let select_id = '<select>' + dentist.id + '</select>';
            dentistTurnRow.id = select_id;

            let deleteLink='<a id=\"a_delete_'+dentist.id+'\"'+' href=\"#\" '+
            'onclick=\"deleteBy('+dentist.id+')\" class=\"link-danger\">Borrar</a>';

            dentistTurnRow.innerHTML =
                    '<option>' + dentist.id + ' ' +
                    dentist.lastname.toUpperCase() + ' ' +
                    dentist.name.toUpperCase() + '</option>';
        };

    })
   });

   (function () {
       var today = new Date();
       var date = today.getFullYear()+'-'+(today.getMonth()+1)+'-'+today.getDate();
       var time = today.getHours() + ":" + today.getMinutes();
       var dateTime = date+' '+time;
       $("#form_datetime").datetimepicker({
           format: 'yyyy-mm-dd hh:ii',
           autoclose: true,
           todayBtn: true,
           startDate: dateTime
       });
   });

    const formulario = document.querySelector('#add_new_odontologo');


    formulario.addEventListener('submit', function (event) {


        const formData = {
            apellido: document.querySelector('#lastname').value,
            nombre: document.querySelector('#name').value,
            matricula: document.querySelector('#registration').value,

        };

        const url = '/dentist';
        const settings = {
            method: 'POST',
            headers: {
                'Content-Type': 'application/json',
            },
            body: JSON.stringify(formData)
        }

        fetch(url, settings)
            .then(response => response.json())
            .then(data => {
                 let successAlert = '<div class="alert alert-success alert-dismissible">' +
                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                     '<strong></strong> Dentist success </div>'

                 document.querySelector('#response').innerHTML = successAlert;
                 document.querySelector('#response').style.display = "block";
                 resetUploadForm();

            })
            .catch(error => {
                    let errorAlert = '<div class="alert alert-danger alert-dismissible">' +
                                     '<button type="button" class="close" data-dismiss="alert">&times;</button>' +
                                     '<strong> Error intente nuevamente</strong> </div>'

                      document.querySelector('#response').innerHTML = errorAlert;
                      document.querySelector('#response').style.display = "block";
                     resetUploadForm();})
    });




})