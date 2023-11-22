package br.com.bookHeaven.BookHeaven.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Pedido {
	private int id;
	private Cliente cliente;
	private String dataPedido;
	private String situacao;
}