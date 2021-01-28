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
import it.univaq.disim.ing.univasa.business.EventoService;
import it.univaq.disim.ing.univasa.domain.Evento;

public class DbEventoServiceImpl implements EventoService {

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
	public void creaEvento(Evento evento) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data = java.sql.Date.valueOf(evento.getScadenza());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(inserisciFarmaco);
			ps.setString(1, evento.getNome());
			ps.setString(2, evento.getPrincipioAttivo());
			ps.setString(3, evento.getProduttore());
			ps.setDate(4, data);
			ps.setDouble(5, evento.getCosto());
			ps.setInt(6, evento.getQuantitaDisponibile());
			ps.setInt(7, evento.getQuantitaMinima());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void creaReport(Evento evento) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data = java.sql.Date.valueOf(evento.getScadenza());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(inserisciFarmaco);
			ps.setString(1, evento.getNome());
			ps.setString(2, evento.getPrincipioAttivo());
			ps.setString(3, evento.getProduttore());
			ps.setDate(4, data);
			ps.setDouble(5, evento.getCosto());
			ps.setInt(6, evento.getQuantitaDisponibile());
			ps.setInt(7, evento.getQuantitaMinima());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void aggiornaReport(Evento evento) throws BusinessException {
		// Conversione da LocalDate a Date
		Date data = java.sql.Date.valueOf(evento.getScadenza());

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(aggiornaFarmaco);

			ps.setString(1, evento.getNome());
			ps.setString(2, evento.getPrincipioAttivo());
			ps.setString(3, evento.getProduttore());
			ps.setDate(4, data);
			ps.setDouble(5, evento.getCosto());
			ps.setInt(6, evento.getQuantitaDisponibile());
			ps.setInt(7, evento.getQuantitaMinima());
			ps.setInt(8, evento.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public void eliminaEvento(Evento evento) throws BusinessException {
		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(cancellaFarmaco);

			ps.setInt(1, evento.getId());

			ps.executeUpdate();

		} catch (SQLException ex) {
			ex.printStackTrace();
		}
	}

	@Override
	public List<Evento> trovaTuttiEventi() throws BusinessException {
		List<Evento> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaTuttiFarmaci);
			r = ps.executeQuery();

			while (r.next()) {
				Evento evento = new Evento();
				evento.setId(r.getInt(1));
				evento.setNome(r.getString(2));
				evento.setPrincipioAttivo(r.getString(3));
				evento.setProduttore(r.getString(4));
				// Conversione da Date a LocalDate
				evento.setScadenza(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setCosto(r.getDouble(6));
				evento.setQuantitaDisponibile(r.getInt(7));
				evento.setQuantitaMinima(r.getInt(8));
				result.add(evento);
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
	public Evento trovaEventoDaId(int id) throws BusinessException {
		Evento evento = new Evento();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaFarmacoDaId);

			ps.setInt(1, id);
			r = ps.executeQuery();

			while (r.next()) {
				evento.setId(id);
				evento.setNome(r.getString(2));
				evento.setPrincipioAttivo(r.getString(3));
				evento.setProduttore(r.getString(4));
				// Conversione da Date a LocalDate
				evento.setScadenza(
						Instant.ofEpochMilli(r.getDate(5).getTime()).atZone(ZoneId.systemDefault()).toLocalDate());
				evento.setCosto(r.getDouble(6));
				evento.setQuantitaDisponibile(r.getInt(7));
				evento.setQuantitaMinima(r.getInt(8));
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
		return evento;
	}

	@Override
	public List<String> nomiEventi() throws BusinessException {
		List<String> result = new ArrayList<>();
		ResultSet r = null;

		// Connessione al Database e richiamo query
		try (Connection c = DriverManager.getConnection(url, user, password);) {

			PreparedStatement ps = c.prepareStatement(trovaNomiFarmaci);
			r = ps.executeQuery();

			while (r.next()) {
				String nomeEvento;
				nomeEvento = (r.getString(1));
				result.add(nomeEvento);
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
