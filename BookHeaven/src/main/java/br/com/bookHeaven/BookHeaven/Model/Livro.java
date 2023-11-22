package br.com.bookHeaven.BookHeaven.Model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString

public class Livro {
	private int cod;
	private String titulo;
	private int numPaginas;
	private float preco;
	private String genero;
	private String nomeAutor;
	private String dataPublicao;
	private String sinopse;
	private String linkImagem;
	private int numVendas;
}