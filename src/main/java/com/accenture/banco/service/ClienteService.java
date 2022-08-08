package com.accenture.banco.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.accenture.banco.entity.Cliente;
import com.accenture.banco.repository.ClienteRepo;

@Service
public class ClienteService {
	
	@Autowired
	private ClienteRepo clienteRepo;
	
	public List<Cliente> listaTodosClientes(){
		return (List<Cliente>) clienteRepo.findAll();
	}
	
	public Cliente salvar(Cliente cliente) {
		return clienteRepo.save(cliente);
	}
	
	public Boolean checkExistingCpf(String cpf){
		return clienteRepo.existsByClienteCPF(cpf);
	}
	
	public Boolean isValidCpf(String cpf) {

        int[] cpfValido = new int[11];
        String letra;

        for (int i = 0; i < cpf.length(); i++) {
            letra = String.valueOf(cpf.charAt(i));
            cpfValido[i] = Integer.parseInt(letra);
        }

        int [] peso = {11,10,9,8,7,6,5,4,3,2};
        int primeirodigito = 0, segundodigito = 0;

        int resultado1 = cpfValido[0]*peso[1] + cpfValido[1]*peso[2] + cpfValido[2]*peso[3] +
                         cpfValido[3]*peso[4] + cpfValido[4]*peso[5] + cpfValido[5]*peso[6] +
                         cpfValido[6]*peso[7] + cpfValido[7]*peso[8] + cpfValido[8]*peso[9];

        int resultado2 = cpfValido[0]*peso[0] + cpfValido[1]*peso[1] + cpfValido[2]*peso[2] +
                         cpfValido[3]*peso[3] + cpfValido[4]*peso[4] + cpfValido[5]*peso[5] +
                         cpfValido[6]*peso[6] + cpfValido[7]*peso[7] + cpfValido[8]*peso[8] +
                         cpfValido[9]*peso[9];

        int resto1 = resultado1 % 11;
        int resto2 = resultado2 % 11;

        if (resto1 < 2){
            primeirodigito = 0;
            } else {
                primeirodigito = 11 - resto1;
            }

        if (resto2 < 2){
            segundodigito = 0;
            } else {
                segundodigito = 11 - resto2;
            }

        if (primeirodigito == cpfValido[9] && segundodigito == cpfValido[10]){
            return true;
        }else {
        	return false;
        }
    }
}
