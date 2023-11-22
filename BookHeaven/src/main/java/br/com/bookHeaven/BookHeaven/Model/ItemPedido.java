package br.com.bookHeaven.BookHeaven.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class ItemPedido {
	private int id;
	private Pedido pedido;
	private Livro livro;
	private int qtd;
}