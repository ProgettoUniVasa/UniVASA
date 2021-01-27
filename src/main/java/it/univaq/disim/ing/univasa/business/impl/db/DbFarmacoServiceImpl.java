package it.univaq.disim.ing.univasa.business.impl.db;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.Instant;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

import it.univaq.disim.ing.univasa.business.BusinessException;
import it.univaq.disim.ing.univasa.business.FarmacoService;
import it.univaq.disim.ing.univasa.domain.Farmaco;

public class DbFarmacoServiceImpl implements FarmacoService {

	private static final String url = "jdbc:mysql://localhost:3306/pharmadb?noAccessToProcedureBodies=true&serverTimezone=Europe/Rome";
	private static final String user = "dbuser";
	private static final String password = "sql_password123";

	// Definizione query in Java
	private static final String inserisciFarmaco = "insert into farmaco (nome, principioAttivo, produttore, scadenza, costo, quantitaDisponibile, quantitaMinima) values (?,?,?,?,?,?,?) ";
	private static final String aggiornaFarmaco = "update farmaco set nome=?, principioAttivo=?, produttore=?, scadenza=?, costo=?, quantitaDisponibile=?, quantitaMinima=? where id = ?";
	private static final String cancellaFarmaco = "delete from farmaco where id=?";

	private static final String trovaTuttiFarmaci = "select * from farmaco";
	private static final String trovaFarmacoDaId = "select * from farmaco where id=?";
	private static final String trovaNomiFarmaci = "select nome from farmaco";
	private static final String farmaciInEsaurimento = "select * from farmaco where quantitaDisponibile <= quantitaMinima";

	@Override
	public void creaFarmaco(Farmaco farmaco) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data = java.sql.Date.valueOf(farmaco.getScadenza());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(inserisciFarmaco);
			ps.setString(1, farmaco.getNome());
			ps.setString(2, farmaco.getPrincipioAttivo());
			ps.setString(3, farmaco.getProduttore());
			ps.setDate(4, data);
			ps.setDouble(5, farmaco.getCosto());
			ps.setInt(6, farmaco.getQuantitaDisponibile());
			ps.setInt(7, farmaco.getQuantitaMinima());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void aggiornaFarmaco(Farmaco farmaco) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data = java.sql.Date.valueOf(farmaco.getScadenza());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(aggiornaFarmaco);

			ps.setString(1, farmaco.getNome());
			ps.setString(2, farmaco.getPrincipioAttivo());
			ps.setString(3, farmaco.getProduttore());
			ps.setDate(4, data);
			ps.setDouble(5, farmaco.getCosto());
			ps.setInt(6, farmaco.getQuantitaDisponibile());
			ps.setInt(7, farmaco.getQuantitaMinima());
			ps.setInt(8, farmaco.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void eliminaFarmaco(Farmaco farmaco) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(cancellaFarmaco);

			ps.setInt(1, farmaco.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Farmaco> trovaTuttiFarmaci() throws BusinessException {
		List<Farmaco> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiFarmaci);
			r = ps.executeQuery();

			while (r.next()) {
				Farmaco farmaco = new Farmaco();
				farmaco.setId(r.getInt(1));
				farmaco.setNome(r.getString(2));
				farmaco.setPrincipioAttivo(r.getString(3));
				farmaco.setProduttore(r.getString(4));
				// Conversione da Date a LocalDate
				farmaco.setScadenza(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				farmaco.setCosto(r.getDouble(6));
				farmaco.setQuantitaDisponibile(r.getInt(7));
				farmaco.setQuantitaMinima(r.getInt(8));
				result.add(farmaco);
			}

			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public Farmaco trovaFarmacoDaId(int id) throws BusinessException {
		Farmaco farmaco = new Farmaco();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaFarmacoDaId);

			ps.setInt(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				farmaco.setId(id);
				farmaco.setNome(r.getString(2));
				farmaco.setPrincipioAttivo(r.getString(3));
				farmaco.setProduttore(r.getString(4));
				// Conversione da Date a LocalDate
				farmaco.setScadenza(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				farmaco.setCosto(r.getDouble(6));
				farmaco.setQuantitaDisponibile(r.getInt(7));
				farmaco.setQuantitaMinima(r.getInt(8));
			}
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return farmaco;
	}

	@Override
	public List<String> nomiFarmaci() throws BusinessException {
		List<String> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaNomiFarmaci);
			r = ps.executeQuery();

			while (r.next()) {
				String nomeFarmaco;
				nomeFarmaco = (r.getString(1));
				result.add(nomeFarmaco);
			}

			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

	@Override
	public List<Farmaco> farmaciInEsaurimento() throws BusinessException {
		List<Farmaco> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(farmaciInEsaurimento);
			r = ps.executeQuery();

			while (r.next()) {
				Farmaco farmaco = new Farmaco();
				farmaco.setId(r.getInt(1));
				farmaco.setNome(r.getString(2));
				farmaco.setPrincipioAttivo(r.getString(3));
				farmaco.setProduttore(r.getString(4));
				// Conversione da Date a LocalDate
				farmaco.setScadenza(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				farmaco.setCosto(r.getDouble(6));
				farmaco.setQuantitaDisponibile(r.getInt(7));
				farmaco.setQuantitaMinima(r.getInt(8));
				result.add(farmaco);
			}
			r.close();
		} catch (SQLException ex) {
			ex.printStackTrace();
		} finally {
			if (r != null) {
				try {
					r.close();
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
			}
		}
		return result;
	}

}
