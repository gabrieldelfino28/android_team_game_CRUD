package br.edu.fateczl.time_jogadores.model;

import androidx.annotation.NonNull;

public class Time {
	private int codTime;
	private String nomeTime;
	private String cidade;

	public Time() {
		super();
	}

	public int getCodTime() {
		return codTime;
	}

	public void setCodTime(int codTime) {
		this.codTime = codTime;
	}

	public String getNomeTime() {
		return nomeTime;
	}

	public void setNomeTime(String nomeTime) {
		this.nomeTime = nomeTime;
	}

	public String getCidade() {
		return cidade;
	}

	public void setCidade(String cidade) {
		this.cidade = cidade;
	}

	@NonNull
	@Override
	public String toString() {
		return codTime + " - " +  nomeTime  + " - " + cidade;
	}

}
