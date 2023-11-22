package br.com.bookHeaven.BookHeaven.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Pagamento {
	private int cod;
	private Pedido pedido;
	private String dataPagamento;
	private float subTotal;
}
