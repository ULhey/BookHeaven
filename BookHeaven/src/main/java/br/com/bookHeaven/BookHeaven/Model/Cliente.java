package br.com.bookHeaven.BookHeaven.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Cliente {
	private String nome;
	private String cpf;
	private String dataNasc;
	private String telefone;
	private String endereco;
	private String email;
	private String senha;
}