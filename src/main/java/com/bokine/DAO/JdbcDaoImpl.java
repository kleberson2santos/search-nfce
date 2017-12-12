package com.bokine.DAO;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

import com.bokine.modelo.Nota;
import com.bokine.util.Comparador;

public class JdbcDaoImpl {

	private String host;
	private String user;
	private String pass;
	private String database;

	public Connection c;
	private String filial = "5";

	/**
	 * Construtor da classe
	 *
	 * @param host
	 *            Host em que se deseja conectar
	 * @param database
	 *            Nome do database em que se deseja conectar
	 * @param user
	 *            Nome do usuário
	 * @param pass
	 *            Senha do usuário
	 */
	public JdbcDaoImpl(String host, String database, String user, String pass) {
		this.pass = pass;
		this.user = user;
		this.host = host;
		this.database = database;
	}

	public Map<String, Nota> NfceComProtocolo() {
		/* Filial 00002 = id (3)
		 * Filial 00003 = id (5)
		 * Filial 00004 = id (7)
		 * Filial 00005 = id (9)
		 * Filial 00006 = id (11)
		 * */
		
		Set<Nota> notasList = new HashSet<Nota>();
		String sql = "select nf.nota,nf.idnfe,nf.modelo,nf.data from nf "
				+ "where nf.idnfe is not null and nf.filial="+filial+" and nf.modelo<>32 and nf.modelo=35 order by 1";

		String portNumber = "3050";
		String url = "jdbc:firebirdsql:" + this.host + "/" + portNumber + ":" + this.database;
		;
		String userName = this.user;
		String passName = this.pass;

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		ResultSet rs = executar(sql);

		try {
			while (rs.next()) {
				Nota nota = new Nota();

				try {
					nota.setNota(rs.getString("NOTA"));
					// System.out.print(rs.getString("NOTA")+"-");
				} catch (Exception e) {
					nota.setNota("");
					System.out.println("Erro ao capturar numero da nota");
				}
				try {
					if (rs.getString(2).length() > 0) {
						nota.setIdNfe(rs.getString(2));
					}
				} catch (Exception e) {
					nota.setIdNfe(null);
				}
				try {
					nota.setData(rs.getTimestamp("DATA").toLocalDateTime());
				} catch (Exception e) {
					nota.setData(null);
					System.out.println("Erro ao capturar Data");
				}
				notasList.add(nota);

			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: " + e);
		}
		ArrayList<Nota> list = new ArrayList<Nota>(notasList);
		list.sort(new Comparador());

		Map<String, Nota> result1 = list.stream().sorted(new Comparador())
				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new // returns a LinkedHashMap, keep order
		));

		return result1;
	}
	
	public Map<String, Nota> NfceSemProtocolo() {
		Set<Nota> notasList = new HashSet<Nota>();
		String sql = "select nf.nota,nf.idnfe,nf.modelo,nf.data from nf "
				+ "where nf.idnfe is null and nf.filial="+filial+" and nf.modelo<>32 and nf.modelo=35 order by 1";

		String portNumber = "3050";
		String url = "jdbc:firebirdsql:" + this.host + "/" + portNumber + ":" + this.database;
		;
		String userName = this.user;
		String passName = this.pass;

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		ResultSet rs = executar(sql);

		try {
			while (rs.next()) {
				Nota nota = new Nota();

				try {
					nota.setNota(rs.getString("NOTA"));
					// System.out.print(rs.getString("NOTA")+"-");
				} catch (Exception e) {
					nota.setNota("");
					System.out.println("Erro ao capturar numero da nota");
				}
				try {
					if (rs.getString(2).length() > 0) {
						nota.setIdNfe(rs.getString(2));
					}
				} catch (Exception e) {
					nota.setIdNfe(null);
				}
				try {
					nota.setData(rs.getTimestamp("DATA").toLocalDateTime());
				} catch (Exception e) {
					nota.setData(null);
					System.out.println("Erro ao capturar Data");
				}
				notasList.add(nota);

			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: " + e);
		}
		ArrayList<Nota> list = new ArrayList<Nota>(notasList);
		list.sort(new Comparador());

		Map<String, Nota> result1 = list.stream().sorted(new Comparador())
				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new // returns a LinkedHashMap, keep order
		));

		return result1;
	}

	public Map<String, Nota> NfceSemRecibo() {
		Set<Nota> notasList = new HashSet<Nota>();
		String sql = "select " + "nf.nota,nf.idnfe,nf.modelo,nf.data from nf where nf.idrecibo is null "
				+ "and nf.filial="+filial+" and nf.modelo<>32 and nf.modelo=35 order by 1";

		String portNumber = "3050";
		String url = "jdbc:firebirdsql:" + this.host + "/" + portNumber + ":" + this.database;
		;
		String userName = this.user;
		String passName = this.pass;

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		ResultSet rs = executar(sql);

		try {
			while (rs.next()) {
				Nota nota = new Nota();

				try {
					nota.setNota(rs.getString("NOTA"));
				} catch (Exception e) {
					nota.setNota("");
					System.out.println("Erro ao capturar numero da nota");
				}
				try {
					if (rs.getString(2).length() > 0) {
						nota.setIdNfe(rs.getString(2));
					}
				} catch (Exception e) {
					nota.setIdNfe(null);
				}
				try {
					nota.setData(rs.getTimestamp("DATA").toLocalDateTime());
				} catch (Exception e) {
					nota.setData(null);
					System.out.println("Erro ao capturar Data");
				}
				notasList.add(nota);

			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: " + e);
		}
		ArrayList<Nota> list = new ArrayList<Nota>(notasList);
		list.sort(new Comparador());

		Map<String, Nota> result1 = list.stream().sorted(new Comparador())
				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new // returns a LinkedHashMap, keep order
		));

		return result1;
	}

	public Long maiorNota() {
		String sql = "select max(nf.nota) from nf where nf.filial="+filial+" and nf.modelo<>32 and nf.modelo=35 order by 1";

		String portNumber = "3050";
		String url = "jdbc:firebirdsql:" + this.host + "/" + portNumber + ":" + this.database;
		;
		String userName = this.user;
		String passName = this.pass;

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		ResultSet rs = executar(sql);
		Long maxID = null;
		try {
			while (rs.next()) {
				maxID = rs.getLong("MAX");
			}
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return maxID;
	}

	/**
	 * Método que estabelece a conexão com o banco de dados
	 *
	 * @return True se conseguir conectar, falso em caso contrário.
	 */
	public boolean connect() {
		boolean isConnected = false;

		String url;
		String portNumber = "3050";
		String userName = this.user;
		String passName = this.pass;
		url = "jdbc:firebirdsql:" + this.host + "/" + portNumber + ":" + this.database;

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
			isConnected = true;
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
			isConnected = false;
		}

		return isConnected;
	}

	/**
	 * Esse método executa a query dada, e retorna um ResultSet Talvez fosse melhor
	 * idéia fazer esse método lançar uma exception a faze-lo retornar null como eu
	 * fiz, porém isso é apenas um exemplo para demonstrar a funcionalidade do
	 * comando execute
	 *
	 * @param query
	 *            String contendo a query que se deseja executar
	 * @return ResultSet em caso de estar tudo Ok, null em caso de erro.
	 */
	private ResultSet executar(String query) {
		Statement st;
		ResultSet rs;

		try {
			st = this.c.createStatement();
			rs = st.executeQuery(query);
			return rs;
		} catch (SQLException e) {
			e.printStackTrace();
		}

		return null;
	}

	public Map<String, Nota> todasNfce() {
		Set<Nota> notasList = new HashSet<Nota>();
		String sql = "select nf.nota,nf.idnfe,idrecibo,nf.modelo,nf.data from nf "
				+ "where nf.filial="+filial+" and nf.modelo<>32 and nf.modelo=35 order by 1";

		String portNumber = "3050";
		String url = "jdbc:firebirdsql:" + this.host + "/" + portNumber + ":" + this.database;
		;
		String userName = this.user;
		String passName = this.pass;

		try {
			Class.forName("org.firebirdsql.jdbc.FBDriver").newInstance();
			this.c = DriverManager.getConnection(url, userName, passName);
		} catch (SQLException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (InstantiationException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		} catch (IllegalAccessException e) {
			e.printStackTrace();
			System.out.println(e.getMessage());
		}

		ResultSet rs = executar(sql);

		try {
			while (rs.next()) {
				Nota nota = new Nota();

				try {
					nota.setNota(rs.getString("NOTA"));
				} catch (Exception e) {
					nota.setNota("");
					System.out.println("Erro ao capturar numero da nota");
				}
				try {
					if (rs.getString(2).length() > 0) {
						nota.setIdNfe(rs.getString(2));
					}
				} catch (Exception e) {
					nota.setIdNfe(null);
				}
				try {
					nota.setData(rs.getTimestamp("DATA").toLocalDateTime());
				} catch (Exception e) {
					nota.setData(null);
					System.out.println("Erro ao capturar Data");
				}
				notasList.add(nota);

			}
		} catch (Exception e) {
			System.out.println("Erro ao buscar elemento: " + e);
		}
		ArrayList<Nota> list = new ArrayList<Nota>(notasList);
		list.sort(new Comparador());

		Map<String, Nota> result1 = list.stream().sorted(new Comparador())
				.collect(Collectors.toMap(Nota::getNota, n -> n, // key = name, value = websites
						(oldValue, newValue) -> oldValue, // if same key, take the old key
						LinkedHashMap::new // returns a LinkedHashMap, keep order
		));

		return result1;
	}

}