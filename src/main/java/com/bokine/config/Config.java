package com.bokine.config;

import java.io.IOException;
import java.io.InputStream;

import org.apache.commons.io.IOUtils;

import com.fincatto.nfe310.NFeConfig;
import com.fincatto.nfe310.classes.NFAmbiente;
import com.fincatto.nfe310.classes.NFTipoEmissao;
import com.fincatto.nfe310.classes.NFUnidadeFederativa;

public class Config implements NFeConfig{
	
	private final boolean ehAmbienteDeTeste;
	
	public Config(final boolean ehAmbienteDeTeste) {
        this.ehAmbienteDeTeste = ehAmbienteDeTeste;
    }

	@Override
	public NFAmbiente getAmbiente() {
		return this.ehAmbienteDeTeste ? NFAmbiente.HOMOLOGACAO : NFAmbiente.PRODUCAO;
	}

	@Override
	public NFUnidadeFederativa getCUF() {
		return null;
	}

	@Override
	public byte[] getCadeiaCertificados() throws IOException {
		return null;
	}

	@Override
	public String getCadeiaCertificadosSenha() {
		return null;
	}

	@Override
	public byte[] getCertificado() throws IOException {
		 try (InputStream is = CertificadoUtils.class.getResource("/home/saturno/Java-Projects/uteis/Certificados-2017/VERTICAL_COMERCIO.pfx").openStream()) {
				return IOUtils.toByteArray(is);
			}
	}

	@Override
	public String getCertificadoSenha() {
		
		return "123456";
	}

	@Override
	public String getCodigoSegurancaContribuinte() {
		return "e522b5582e72f707";
	}

	@Override
	public Integer getCodigoSegurancaContribuinteID() {
		return 000001;
	}

	@Override
	public String getSSLProtocolo() {
		return "TLSv1";
	}

	@Override
	public NFTipoEmissao getTipoEmissao() {
		return NFTipoEmissao.EMISSAO_NORMAL;
	}
	

}
