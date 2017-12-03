package com.bokine.util;

import java.util.ArrayList;
import java.util.List;

import com.bokine.config.Configuracao;
import com.bokine.modelo.Nota;

import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;
import br.com.samuelweb.nfe.Nfe;
import br.com.samuelweb.nfe.util.ConstantesUtil;
import br.inf.portalfiscal.nfe.schema.conssitnfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.schema.retconssitnfe.TRetConsSitNFe;

public class Validador {
	
	public static List<Nota> NotasNaoEncontradas = new ArrayList<Nota>();
	
	public Validador() {}

	public String validador(Nota nota, ConfiguracoesIniciaisNfe config) {
		try {
			//Inicia As Configurações
			//Para Mais informações: https://github.com/Samuel-Oliveira/Java_NFe/wiki/Configura%C3%A7%C3%B5es-Nfe

			TConsSitNFe consSitNFe = new TConsSitNFe();
			consSitNFe.setVersao(config.getVersaoNfe());
			consSitNFe.setTpAmb(config.getAmbiente());
			consSitNFe.setXServ("CONSULTAR");
		    //Substitua os X Pela Chave que deseja Consultar
			consSitNFe.setChNFe(nota.getIdNfe());

			//Informe false para não fazer a validação do XML - Ganho De tempo.	
			TRetConsSitNFe retorno = Nfe.consultaXml(consSitNFe,false,ConstantesUtil.NFCE);
				//if( !retorno.getCStat().equals("100")&& !retorno.getCStat().equals("101"))
				System.out.println("Status:" + retorno.getCStat());
				System.out.println("Motivo:" + retorno.getXMotivo());
				System.out.println("Data:" + retorno.getProtNFe().getInfProt().getDhRecbto());
				nota.setStatus(retorno.getCStat());
				nota.setMotivo(retorno.getXMotivo());
				nota.setData(retorno.getProtNFe().getInfProt().getDhRecbto().toString());

			
			//Transforma O ProtNfe do Retorno em XML
			//String xmlProtNfe = XmlUtil.objectToXml(retorno.getProtNFe());
			//System.out.println(xmlProtNfe);
				
			return retorno.getCStat();
		
		} catch (Exception e) {
			NotasNaoEncontradas.add(nota);
			System.out.println("Erro ao validar NFCe: " 
					+nota.getNota()+" - "+nota.getData()+" - \n"+ e.getCause());
		}
		return null;
		
	}

}
