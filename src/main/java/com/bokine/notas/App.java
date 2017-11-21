package com.bokine.notas;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.swing.text.StyledEditorKit.ForegroundAction;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import com.bokine.DAO.Firebird;
import com.bokine.config.Config;
import com.bokine.modelo.Nota;
import com.fincatto.nfe310.classes.NFUnidadeFederativa;
import com.fincatto.nfe310.classes.statusservico.consulta.NFStatusServicoConsultaRetorno;
import com.fincatto.nfe310.webservices.WSFacade;

import br.com.samuelweb.certificado.Certificado;
import br.com.samuelweb.nfe.ConfiguracoesIniciaisNfe;
import br.com.samuelweb.nfe.Nfe;
import br.com.samuelweb.nfe.exception.NfeException;
import br.com.samuelweb.nfe.util.CertificadoUtil;
import br.com.samuelweb.nfe.util.ConstantesUtil;
import br.com.samuelweb.nfe.util.Estados;
import br.inf.portalfiscal.nfe.schema.conssitnfe.TConsSitNFe;
import br.inf.portalfiscal.nfe.schema.inutnfe.TInutNFe;
import br.inf.portalfiscal.nfe.schema.inutnfe.TInutNFe.InfInut;
import br.inf.portalfiscal.nfe.schema.retconssitnfe.TRetConsSitNFe;
import br.inf.portalfiscal.nfe.schema.retinutnfe.TRetInutNFe;

public class App 
{
	private static String database;
	private static String host;
	private static String user;
	private static String pass;
	private static Nota nota;
	public static int maiorNumero=0;
	
	public static List<Nota> NotasAnalisar = new ArrayList<Nota>();
	public static List<Nota> NotasNaoEncontradas = new ArrayList<Nota>();
	public static Set<Nota> NotasParaTriar = new HashSet<Nota>();
	public static  Map<String, Nota> notasFirebird = new HashMap<String, Nota>();
	
	private static final Logger logger = LogManager.getLogger(App.class.getName());

	public static void main( String[] args )
    {
        database = "C:/sys/base/BKN00001";
        host = "127.0.0.1";
        user = "SYSDBA";
        pass = "masterkey";
        
        maiorNumero=135963;
  
        logger.debug( "BOKINE - SISTEMA DE NFCe" );
		Firebird firebird = new Firebird(host, database,user, pass);
		System.out.println("Firebird conectado?:"+firebird.connect());
//		
		String sql = "select nf.nota,nf.idnfe,nf.modelo,nf.data from nf "+
				"where nf.idrecibo is not null "+
				"and nf.modelo<>32 "+
				"and nf.modelo <> 33 "+
				"and nf.data BETWEEN '07.11.2014' and '18.10.2017' "+
				"and nf.filial=5 ";
//		
		ResultSet rs = firebird.executar(sql);
		 try {
			while(rs.next()){
				
				Nota nota = new Nota();
				//Nota danfe = new Nota("2801", "13171014196380000277550010000028011006849811", "", "", "");
						
				try {
					nota.setNota(rs.getString("NOTA")); 
				} catch (Exception e) {
					nota.setNota("");
					System.out.println("Erro ao capturar numero da nota");
				}
				try {
					if(rs.getString(2).length()>0){
						nota.setIdNfe(rs.getString(2));
					}
				} catch (Exception e) {
					nota.setIdNfe("");
					//System.out.println("Erro ao capturar ID da nota");
				}
				try {
					nota.setData(rs.getString("DATA"));
				} catch (Exception e) {
					nota.setData("");
					System.out.println("Erro ao capturar Data da nota");
				}
				//validarNF(danfe);
				//inutilizar();
				notasFirebird.put(nota.getNota(), nota);
				NotasParaTriar.add(nota);
		 	}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: "+e);
		}
		 
		 
		logger.debug("### RESULTADO ### ");
		//logger.debug(NotasAnalisar);
		maiorNota(NotasParaTriar);
		triar(notasFirebird,maiorNumero);
//		for (Nota nota : NotasParaTriar) {
//			System.out.println("Nota:"+nota.getNota()+"|"+nota.getIdNfe());
//		}
		NotasNaoEncontradas.forEach(n->logger.debug(nota.getNota()));
    }

	private static void triar(Map<String,Nota> notas, int maiorNota) {
		boolean existe = false;
		for (int i = 0; i <= maiorNota; i++) {
			//System.out.printf("\nAnalisando... %d/%d",i,maiorNota);
				 for (String key : notas.keySet()) {
					 existe = false;
	                 Nota nota = notas.get(key);
	                 if(Integer.parseInt(nota.getNota())==i){
	 					existe = true;
	 					break;
	                 }
				 }
			if(existe==false){
				//System.out.println("Nao encontrou a nota:"+i);
				NotasNaoEncontradas.add(new Nota(Integer.toString(i), "", "", "", ""));
			}
			
		}
		
	}
	
	private static void triar2(Map<String,Nota> notas, int maiorNota) {
		notas.keySet().stream().forEach(); 
		
	}
	
	private static int maiorNota(Set<Nota> notasParaTriar){
		int maiorNumero = 0;
		for(Nota nota : notasParaTriar){
			if(Integer.parseInt(nota.getNota())>maiorNumero){
				maiorNumero = Integer.parseInt(nota.getNota());
			}
		}
		return maiorNumero;
	}

	private static void validarNF(Nota nota) {
		try {
			//Inicia As Configurações
			//Para Mais informações: https://github.com/Samuel-Oliveira/Java_NFe/wiki/Configura%C3%A7%C3%B5es-Nfe
			ConfiguracoesIniciaisNfe config = iniciaConfigurações();

			TConsSitNFe consSitNFe = new TConsSitNFe();
			consSitNFe.setVersao(config.getVersaoNfe());
			consSitNFe.setTpAmb(config.getAmbiente());
			consSitNFe.setXServ("CONSULTAR");
		    //Substitua os X Pela Chave que deseja Consultar
			consSitNFe.setChNFe(nota.getIdNfe());

			//Informe false para não fazer a validação do XML - Ganho De tempo.	
			TRetConsSitNFe retorno = Nfe.consultaXml(consSitNFe,false,ConstantesUtil.NFCE);
			//if( !retorno.getCStat().equals("100")&& !retorno.getCStat().equals("101")){
				System.out.println("Status:" + retorno.getCStat());
				System.out.println("Motivo:" + retorno.getXMotivo());
				System.out.println("Data:" + retorno.getProtNFe().getInfProt().getDhRecbto());
				nota.setStatus(retorno.getCStat());
				nota.setMotivo(retorno.getXMotivo());
				nota.setData(retorno.getProtNFe().getInfProt().getDhRecbto().toString());
				//NotasAnalisar.add(nota);
			//}
			
			//Transforma O ProtNfe do Retorno em XML
			//String xmlProtNfe = XmlUtil.objectToXml(retorno.getProtNFe());
			//System.out.println(xmlProtNfe);
		
		} catch (Exception e) {
			NotasNaoEncontradas.add(nota);
			logger.debug("Erro ao validar NFCe: " +nota.getNota()+" - "+nota.getData()+" - \n"+ e.getMessage());
		}
	}

	private static ConfiguracoesIniciaisNfe iniciaConfigurações() {
		// Certificado Arquivo, Parametros: -Caminho Certificado, - Senha
				Certificado certificado = null;
				try {
					certificado = CertificadoUtil.certificadoPfx("C:/Uteis/certificados digitais/2018/VERTICAL_COMERCIO.pfx", "123456");
				} catch (NfeException e) {
					e.printStackTrace();
				}

				return ConfiguracoesIniciaisNfe.iniciaConfiguracoes(Estados.AM , ConstantesUtil.AMBIENTE.PRODUCAO,
						certificado, "C:\\Users\\Administrator\\Documents\\Projeto-NFCe-Java\\Schemas\\Schemas", ConstantesUtil.VERSAO.V3_10);
			
	}
	
	private static void inutilizar(){
		 try{
				//Inicia As Configurações
				//Para Mais informações: https://github.com/Samuel-Oliveira/Java_NFe/wiki/Configura%C3%A7%C3%B5es-Nfe
				ConfiguracoesIniciaisNfe config = iniciaConfigurações();
				
				//Troque X Pelo Id 
				String id = "13141014196380000277650000000001051000000015";
				
				TInutNFe inutNFe = new TInutNFe();
				inutNFe.setVersao("3.10");
				
				InfInut infInut = new InfInut();
				infInut.setId(id);
				infInut.setTpAmb(config.getAmbiente());
				infInut.setXServ("INUTILIZAR");
				infInut.setCUF("13");
				infInut.setAno("15");
				
				//Toque X Pelo CNPJ
				infInut.setCNPJ("14196380000277");
				infInut.setMod("65");
				infInut.setSerie("0");
				
				//Troque x Pelo Numero da Nota
				infInut.setNNFIni("105");
				infInut.setNNFFin("105");
				
				infInut.setXJust("Teste na Inutilizacao da Nfe");
				inutNFe.setInfInut(infInut);
				
				//Informe false para não fazer a validação do XML - Ganho De tempo.
				TRetInutNFe retorno = Nfe.inutilizacao(inutNFe,false, ConstantesUtil.NFCE);
				br.inf.portalfiscal.nfe.schema.retinutnfe.TRetInutNFe.InfInut infRetorno = retorno.getInfInut();
				System.out.println("Status:" + infRetorno.getCStat());
				System.out.println("Motivo:" + infRetorno.getXMotivo());
				System.out.println("Data:" + infRetorno.getDhRecbto());

			} catch (NfeException e) {
				System.out.println("Erro:" + e.getMessage());
			}
	}
}
