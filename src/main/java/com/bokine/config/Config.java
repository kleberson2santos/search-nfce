package com.bokine.config;

import java.io.File;
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
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getCadeiaCertificados() throws IOException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getCadeiaCertificadosSenha() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public byte[] getCertificado() throws IOException {
		 try (InputStream is = CertificadoUtils.class.getResource("C:/Uteis/certificados digitais 2017/2017/NM_FILIAL.pfx").openStream()) {
				return IOUtils.toByteArray(is);
			}
	}

	@Override
	public String getCertificadoSenha() {
		
		return "123456";
	}

	@Override
	public String getCodigoSegurancaContribuinte() {
		// TODO Auto-generated method stub
		return "d5d209f1439b9e45";
	}

	@Override
	public Integer getCodigoSegurancaContribuinteID() {
		return 000002;
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
