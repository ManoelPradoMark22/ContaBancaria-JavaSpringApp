package com.accenture.banco.init;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import com.accenture.banco.entity.Agencia;
import com.accenture.banco.entity.Cliente;
import com.accenture.banco.repository.AgenciaRepo;
import com.accenture.banco.repository.ClienteRepo;
import com.accenture.banco.service.AgenciaService;

@Component
public class Init implements ApplicationListener<ContextRefreshedEvent>{
	
	@Autowired
	AgenciaRepo agenciaRepo;
	
	@Autowired
	ClienteRepo clienteRepo;
	
	public void onApplicationEvent(ContextRefreshedEvent event) {
		Agencia agencia1 = new Agencia();
		Cliente cliente1 = new Cliente();
		
		agencia1.setNomeAgencia("Raio solar");
		agencia1.setTelefone("3123123");
		agencia1.setEndereco("Rua 12 - BH");
		
		//agenciaRepo.save(agencia1);
		
		System.out.println(agencia1.getNomeAgencia());
		
		cliente1.setClienteNome("Manoel");
		cliente1.setClienteCPF("545333");
		cliente1.setClienteFone("99234847");
		cliente1.setStyleId("sport");
		cliente1.setAgencia(agencia1);
		
		//clienteRepo.save(cliente1);
		
/*
		List<Agencia> arrayAgencias = agenciaService.listaTodasAgencias();
		System.out.println("Agencias: ");
		System.out.println(arrayAgencias);
 * */
	}
	
}
