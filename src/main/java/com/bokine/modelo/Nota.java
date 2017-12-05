package com.bokine.modelo;
import java.time.LocalDateTime;

public class Nota {
	
	private String Nota;
	private String IdNfe;
	private String Status;
	private String Motivo;
	private LocalDateTime Data;
	
	public Nota(){
		
	}
	
	public Nota(String nota, String idNfe, String status,String motivo, LocalDateTime data) {
		Nota = nota;
		IdNfe = idNfe;
		Status = status;
		Motivo = motivo;
		Data = data;
	}

	public String getNota() {
		return Nota;
	}
	public void setNota(String nota) {
		Nota = nota;
	}
	public String getIdNfe() {
		return IdNfe;
	}
	public void setIdNfe(String idNfe) {
		IdNfe = idNfe;
	}
	public String getStatus() {
		return Status;
	}
	public void setStatus(String status) {
		Status = status;
	}
	public String getMotivo() {
		return Motivo;
	}
	public void setMotivo(String motivo) {
		Motivo = motivo;
	}
	public LocalDateTime getData() {
		return Data;
	}
	public void setData(LocalDateTime data) {
		Data = data;
	}
	
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((IdNfe == null) ? 0 : IdNfe.hashCode());
		result = prime * result + ((Nota == null) ? 0 : Nota.hashCode());
		return result;
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Nota other = (Nota) obj;
		if (IdNfe == null) {
			if (other.IdNfe != null)
				return false;
		} else if (!IdNfe.equals(other.IdNfe))
			return false;
		if (Nota == null) {
			if (other.Nota != null)
				return false;
		} else if (!Nota.equals(other.Nota))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Nota [Nota=" + Nota + ", IdNfe=" + IdNfe + ", Status=" + Status + ", Motivo=" + Motivo + ", Data="
				+ Data + "]\n";
	}
	
}
