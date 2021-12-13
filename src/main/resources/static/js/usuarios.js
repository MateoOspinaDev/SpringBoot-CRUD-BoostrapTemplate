// Call the dataTables jQuery plugin
//Nuestra lógica para tratar la información de los usuarios, esto sería parte del front end
$(document).ready(function() {
    cargarUsuarios();
  $('#usuarios').DataTable();
 actualizarEmailDelUsuario();
});

function actualizarEmailDelUsuario() {
    document.getElementById('txt-email-usuario').outerHTML = localStorage.email;
}

//Peticion fetch
async function cargarUsuarios() {
  const request = await fetch('api/usuarios', {
    method: 'GET',
    headers: getHeaders()
  });
  const usuarios = await request.json();

//Lógica para renderizar la respuesta
  let listadoHTML='';

  for(let usuario of usuarios){
    let tel=usuario.telefono == null ? '-' : usuario.telefono;
    let botonEliminar = '<a href="#" onclick="eliminarUsuario('+usuario.id+')" class="btn btn-danger btn-circle btn-sm"><i class="fas fa-trash"></i></a>';
    let usuarioHTML = '<tr><td>'+usuario.id+'</td><td>'+usuario.nombre+' '+usuario.apellido+'</td><td>'+usuario.email+'</td><td>'+tel+'</td><td>'+botonEliminar+'</td></tr>';

    listadoHTML+=usuarioHTML;
  }

document.querySelector("#usuarios tbody").outerHTML=listadoHTML;
}

function getHeaders() {
    return {
     'Accept': 'application/json',
     'Content-Type': 'application/json',
     'Authorization': localStorage.token
   };
}

async function eliminarUsuario(id){
      if(!confirm("¿Desea eliminar este usuario?")){//Confirm para eliminar usuario
        return;
      }
      const request = await fetch('api/usuarios/'+id, {
              method: 'DELETE',
              headers: getHeaders
      });
      location.reload();//Recara la pagina para que se vea el cambio
}