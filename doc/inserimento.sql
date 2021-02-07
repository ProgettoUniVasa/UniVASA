INSERT INTO `utente` (`id`, `nome`, `cognome`, `email`, `username`, `password`, `telefono`, `data_nascita`, `professione`, `nome_universita`, `dipartimento`, `matricola`, `tipo_utente`) VALUES
(1, 'Ciccio', 'Parrucco', 'aba@gmail.com', 'a', 'a', '1230928292', '1999-06-16', 'professore', 'AQ', 'DISIM', '', 'amministratore'),
(2, 'Elena', 'Ricci', 'bcb@gmail.com', 'e', 'e', '4562222221', '2003-10-23', 'studente', 'AQ', 'DISIM', '255222', 'elettore'),
(3, 'Chiara', 'Ferry', 'cdc@gmail.com', 'o', 'o', '7892222222', '2000-04-15', 'impiegato', 'AQ', 'DISCAB', NULL, 'operatore'),
(13, 'Martina', 'Nolletti', 'martina@nolletti.org', 'martina', 'martina', '3459319859', '1999-05-24', 'impiegato', 'UniBocconi', 'DISIM', NULL, 'amministratore'),
(14, 'Marco', 'Chiavaroli', 'marco@chiav.top', 'marco', 'marco', '3890459739', '1999-08-23', 'studente', 'Univaq', 'DISCAB', NULL, 'operatore'),
(15, 'Samuel', 'Cipriani', 'samuel@cipciop.it', 'samuel', 'samuel', '3893833983', '2000-02-29', 'professore', 'Univaq', 'DISIM', '289282', 'elettore'),
(16, 'Lorenzo', 'Solazzo', 'lollo@sola.it', 'lorenzo', 'lorenzo', '3859786959', '2000-05-11', 'professore', 'Univaq', 'DISIM', '989839', 'elettore'),
(17, 'Enrico', 'Adamelli', 'enry@adams.it', 'enrico', 'enrico', '3980987695', '2000-01-16', 'impiegato', 'Univaq', 'DISIM', '123456', 'elettore'),
(18, 'Andrea', 'Fonte', 'andrea@fonte.com', 'andrea', 'andrea', '1234567892', '1999-05-13', 'professore', 'Univaq', 'DISIM', '192948', 'elettore');



INSERT INTO `evento` (`id`, `nome`, `regolamento`, `data_inizio`, `data_fine`, `ora_inizio`, `ora_fine`, `luogo`, `report_risultati`, `report_statistiche`, `numero_preferenze_esprimibili`, `stato`) VALUES
(1, 'RAPPRESENTANTI UNI', 'si vota una persona per scheda elettorale', '2021-03-10', '2021-03-11', '8:00', '20:00', 'L\'Aquila', NULL, NULL, 1, 'programmato'),
(9, 'Rappresentanti Prof', 'Votate max 2 persone', '2021-02-03', '2021-02-06', '11:00', '18:00', 'Coppito 2', 'Ha vinto Samuel.', 'Martina ha preso 0 voti.', 2, 'terminato'),
(10, 'Elezione segreteria', 'Massimo 3 preferenze', '2021-02-17', '2021-02-22', '10:00', '23:00', 'Blocco 0', NULL, NULL, 3, 'programmato');




INSERT INTO `candidato` (`id`, `nome`, `cognome`, `email`, `telefono`, `data_nascita`, `nome_universita`, `voti_ricevuti`, `id_evento`) VALUES
(13, 'Enrico', 'Adamelli', 'enry@adams.it', '3980987695', '2000-01-16', 'Univaq', 0, 1),
(14, 'Lorenzo', 'Solazzo', 'lollo@sola.it', '3859786959', '2000-05-11', 'Univaq', 0, 1),
(15, 'Martina', 'Nolletti', 'martina@nolletti.org', '3459319859', '1999-05-24', 'UniBocconi', 0, 9),
(16, 'Samuel', 'Cipriani', 'samuel@cipciop.it', '3893833983', '2000-02-29', 'Univaq', 10, 9),
(17, 'Andrea', 'Fonte', 'andrea@fonte.com', '1234567892', '1999-05-13', 'Univaq', 0, 10),
(18, 'Samuel', 'Cipriani', 'samuel@cipciop.it', '3893833983', '2000-02-29', 'Univaq', 0, 10);



INSERT INTO `prenotazione` (`id`, `id_utente`, `id_evento`, `tipo_prenotazione`, `stato`, `certificato`, `voti_espressi`) VALUES
(1, 2, 1, 'online', 'no', NULL, 0),
(11, 17, 10, 'in presenza', 'no', NULL, 0),
(12, 17, 1, 'online', 'no', NULL, 0);


INSERT INTO `turnazione` (`id`, `id_utente`, `id_evento`, `fascia`, `data_giorno`) VALUES
(4, 3, 1, 'mattina', '2021-03-11'),
(5, 14, 9, 'pomeriggio', '2021-02-10'),
(6, 14, 10, 'sera', '2021-02-18');
