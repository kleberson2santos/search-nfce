package com.bokine.config;

import br.com.samuelweb.certificado.Certificado;
import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.CertificadoUtil;
import br.com.samuelweb.nfe.util.ConstantesUtil;
import br.com.samuelweb.nfe.util.Estados;

public class Configuracao{
	
	public static ConfiguracoesIniciaisNfe iniciaConfigurações() {
		// Certificado Arquivo, Parametros: -Caminho Certificado, - Senha
		Certificado certificado = null;
		try {
			certificado = CertificadoUtil.certificadoPfx(
					//"/home/saturno/Java-Projects/uteis/Certificados-2017/VERTICAL_COMERCIO.p12", 
					"/home/kleber/Documentos/Repositorios_Java/uteis/certificados/VERTICAL_COMERCIO.p12",
					"123456");
		} catch (NfeException e) {
			System.out.println("Certificado nao encontrado, "+e.getCause());
		}

		return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(
				Estados.AM , ConstantesUtil.AMBIENTE.PRODUCAO,certificado, 
//				"/home/saturno/Java-Projects/uteis/Schemas/", 
				"/home/kleber/Documentos/Repositorios_Java/uteis/Schemas/", 
				ConstantesUtil.VERSAO.V3_10);
	}

}
