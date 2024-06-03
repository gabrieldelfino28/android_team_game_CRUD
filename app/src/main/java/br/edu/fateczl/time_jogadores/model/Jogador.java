package br.edu.fateczl.time_jogadores.model;

import androidx.annotation.NonNull;

public class Jogador {
	private int idJogador;
	private String nomeJogador;
	private String dataNasc;
	private float altura;
	private float peso;
	private Time time;

	public Jogador() {
		super();
	}

	public int getIdJogador() {
		return idJogador;
	}

	public void setIdJogador(int idJogador) {
		this.idJogador = idJogador;
	}

	public String getNomeJogador() {
		return nomeJogador;
	}

	public void setNomeJogador(String nomeJogador) {
		this.nomeJogador = nomeJogador;
	}

	public String getDataNasc() {
		return dataNasc;
	}

	public void setDataNasc(String dataNasc) {
		this.dataNasc = dataNasc;
	}

	public float getAltura() {
		return altura;
	}

	public void setAltura(float altura) {
		this.altura = altura;
	}

	public float getPeso() {
		return peso;
	}

	public void setPeso(float peso) {
		this.peso = peso;
	}

	public Time getTime() {
		return time;
	}

	public void setTime(Time time) {
		this.time = time;
	}

	@NonNull
	@Override
	public String toString() {
		return idJogador + " - " + nomeJogador + " - " + dataNasc + " - " + altura + " - " + peso  + " - " + time.getNomeTime();
	}

}
