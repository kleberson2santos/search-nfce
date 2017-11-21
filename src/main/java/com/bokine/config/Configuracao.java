package com.bokine.config;

import br.com.samuelweb.certificado.Certificado;
import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.CertificadoUtil;
import br.com.samuelweb.nfe.util.ConstantesUtil;
import br.com.samuelweb.nfe.util.Estados;

public class Configuracao {
	
	public static ConfiguracoesIniciaisNfe iniciaConfigurações() throws NfeException {
		// Certificado Arquivo, Parametros: -Caminho Certificado, - Senha
		Certificado certificado = CertificadoUtil.certificadoPfx("C:/Uteis/certificados digitais 2017/2017/NM_FILIAL.pfx", "123456");

		return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(Estados.AM , ConstantesUtil.AMBIENTE.HOMOLOGACAO,
				certificado, "C:\\Users\\Administrator\\Documents\\Projeto-NFCe-Java\\Schemas\\Schemas", ConstantesUtil.VERSAO.V3_10);
	}

}
