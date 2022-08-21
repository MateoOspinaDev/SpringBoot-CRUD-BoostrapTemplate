// Call the dataTables jQuery plugin
//Nuestra lógica para tratar la información de los usuarios, esto sería parte del front end
$(document).ready(function() {
    //cargarUsuarios(); YA no se hace porque el registro se hace cuando se presiona un botón
  //$('#usuarios').DataTable();
});



//Lógica para tomar los datos de los inputs
async function registrarUsuario() {
  let datos = {};
  datos.nombre = document.getElementById('txtNombre').value;
  datos.apellido = document.getElementById('txtApellido').value;
  datos.email = document.getElementById('txtEmail').value;
  datos.password = document.getElementById('txtPassword').value;

  let repetirPassword = document.getElementById('txtRepetirPassword').value;

//Comparamos los passwords
  if (repetirPassword != datos.password) {
    alert('La contraseña que escribiste es diferente.');
    return;
  }

//Peticcion al backend para enviar los datos
  const request = await fetch('api/usuarios', {
    method: 'POST',
    headers: {
      'Accept': 'application/json',
      'Content-Type': 'application/json'
    },
    body: JSON.stringify(datos) //Tomar cualquier objeto de js y convertirlo a un string de JSON
  });
  alert("La cuenta fue creada con exito!");
  window.location.href = 'login.html'

}



