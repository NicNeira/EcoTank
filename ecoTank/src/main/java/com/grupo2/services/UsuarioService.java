package com.grupo2.services;

import java.util.List;

import javax.validation.Valid;

import org.mindrot.jbcrypt.BCrypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.grupo2.models.Usuario;
import com.grupo2.repositories.UsuarioRepository;


@Service
public class UsuarioService {
	
	@Autowired
	UsuarioRepository usuarioRepository;

	//Metodos
	
	//Metodo para registrar el usuario en la base de datos
	public boolean saveUsuario(@Valid Usuario usuario) {
		boolean validar = false;
		//Validar si existe email
		Usuario existeUsuario = usuarioRepository.findByCorreo(usuario.getCorreo());
		// Si existe o no el usuario lo creamos
		
		 if (existeUsuario == null) {
		 
			// 2.- encriptar el password
			String passEncriptado = BCrypt.hashpw(usuario.getClave(), BCrypt.gensalt());
			usuario.setClave(passEncriptado);
			usuarioRepository.save(usuario);
			validar = true;
		}
		
		//Si existe, retornamos un boolean true
		else {
			validar = false;
		}
		 
		return validar;
		
	}
	
	
	//Metodo para listar los usarios ingresados
	public List<Usuario> findAll() {
		
		return usuarioRepository.findAll();
	}

	public Usuario getOneUser(Long id) {
		//TODO Auto-generated method stub
		return usuarioRepository.findById(id).get();
	}

	public void registrarUsuario(Usuario usuario) {
		usuarioRepository.save(usuario);
	}

	
	public boolean validarUsuario(String correo, String clave) {
		boolean error = true;
		// verificar el email
		Usuario existeUsuario = usuarioRepository.findByCorreo(correo);
		if (existeUsuario == null) {//no existe el usuario, error de ingreso
			error = true;
			System.out.println("correo no existe");
		}else {
			// verificar password contra password base datos desencriptada
			if(BCrypt.checkpw(clave, existeUsuario.getClave())) {
				//password iguales, email y password igual a la base datos
				error= false;
				System.out.println("clave correcta");
			}else {
				//password distintos, error 
				error = true;
				System.out.println("clave incorrecta");
			}
		}
		return error;
	}


	public Usuario findByCorreo(String correo) {
		// TODO Auto-generated method stub
		return usuarioRepository.findByCorreo(correo);
	}
	
	
}